
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <math.h>

/**
 * Global variable to hold the hashed names.
 */
 char* arrHash[6968][15];

/**
 * Global variable to help store names. Holds the number of names after reading has happened.
 */
int nameCharacterCounter = 0;
/**
 * Global variable to hold a char* version of charArr (in main) so the split function can cycle through splitting up names.
 */
char* charArrPointer;

/**
 * Function to take the next name from charArrPointer.
 */
char* split() {
    //Holds the value of the pointer before it is moved.
    char* outputString = charArrPointer;
    
    for(int i = 0; i < nameCharacterCounter; i ++) {
        //If a comma is found it sets that comma to now be an end of string character and returns the initial value of itself stored in outputString.
        if(*charArrPointer == ',') {
            *charArrPointer = '\0';
            charArrPointer++;
            return outputString;
        }else{
            //When it doesnt find a comma it just increments the pointer.
            charArrPointer++;
        }
    }
    //Returns NULL when end is reached.
    return NULL;
    
}
/**
 * Function to calcualte a words hash value and returns it.
 */
 long hashIt( char *str){
    
     long hash = 5381;
     int c;
     int i = 0;
    
     //Hash increases for each letter.
     while (str[i] != '\0'){
        c = (int)str[i];
         hash = (((hash << 7) + hash) + c);

         i++;
     }
    return hash;
}

/**
 * Function to take the hash value and name and store it in the hash array.
 */
void hash(char* arr[100000], int length){
    //Found keeps track on whether or not a space for the next name has been found.
    int found = 0;
    
    //The hash value is saved to hashWordValue.
    long hashWordValue = 0;
    
    //Counter loops through to find a spot for new name.
    int counter = 0;
    
    //Total counter keeps track of the total collisions.
    int totalCounter = 0;
    
    //A loop through all names to be stored in the hash.
    for(int j = 0; j <length;j++){
        //Finds hash value for current name.
        hashWordValue = hashIt(arr[j]);
        
        //Fits the hash value to the array.
        hashWordValue = hashWordValue % 6967;
        if (hashWordValue<0){
            hashWordValue *= -1;
        }
        
        //Loops through to find a clear spot for the new name.
        counter =0;
        while (found==0){
            //Looks for a NULL spot.
            if(arrHash[hashWordValue][counter] == NULL){
                arrHash[hashWordValue][counter] = arr[j];
                //Now found it can move to the next name.
                found = 1;
            }else{
                counter++;
                totalCounter++;
            }
        }
        found = 0;
        counter = 0;
    }
}

/**
 * Function to compare 2 strings.
 */
int compare(char* string1, char* string2){
    
    //Checks if either reach end of string.
    while(*string1 && *string2){
        //if they are the same value then go to next char else go to return.
        if(*string1 == *string2){
            string1++;
            string2++;
            
        }else{
            break;
        }
    }
    //Returns differnece. Negative if string1 is first alphabetically, positive if string2 is else 0.
    return (*string1 - *string2);
}


/**
 * Function to search for a name in a hash array. Im using 1 and 0 instead of a boolean.
 */
int search(char* word){
    
    long hashWordValue = 0;

    //Finds hash value for current name.
    hashWordValue = hashIt(word);
    
    //Fits the hash value to the array.
    hashWordValue = hashWordValue % 6967;
    if (hashWordValue<0){
        hashWordValue *= -1;
    }
    //Prints out address being checked.
    printf("Address %d ",(int)hashWordValue);
    
    int counter = 0;
    int found = 0;
    int fail = 0;
    //Loops untill a NULL is reached or name is found.
    while (found!=1 && fail!=1){
        if (arrHash[hashWordValue][counter]!=NULL){
            //If found set found to 1.
            if (compare(arrHash[hashWordValue][counter],word)==0){
                found = 1;
            }
        }else{
            //NULL found so set fail to 1.
            fail = 1;
        }
        counter++;
    }
    return found;
}

/**
 * Function to add a name in a hash array.
 */
void add(char* name){
    long hashWordValue = 0;
    
    //Finds hash value for current name.
    hashWordValue = hashIt(name);
    
    //Fits the hash value to the array.
    hashWordValue = hashWordValue % 6967;
    if (hashWordValue<0){
        hashWordValue *= -1;
    }
    
    
    int found = 0;
    int counter =0;
    
    //Loops till found a NULL spot.
    while (found==0){
        //Looks for a NULL spot.
        if(arrHash[hashWordValue][counter] == NULL){
            //Set name to null spot.
            arrHash[hashWordValue][counter] = name;
            //Now found it can move to the next name.
            found = 1;
        }else{
            counter++;
        }
    }
}

/**
 * Function to search for a name in a hash array. Im using 1 and 0 instead of a boolean.
 */
int removeString(char* name){
    long hashWordValue = 0;
    
    //Finds hash value for current name.
    hashWordValue = hashIt(name);
    
    //Fits the hash value to the array.
    hashWordValue = hashWordValue % 6967;
    if (hashWordValue<0){
        hashWordValue *= -1;
    }
    
    //Found keeps track of whether or not the name is removed.
    int found = 0;
    int counter =0;
    int reachedEnd = 0;
    
    //Loops untill NULL is reached.
    while (reachedEnd == 0){
        //Looks for the name.
        if(arrHash[hashWordValue][counter] == NULL){
            //NULL reached so set reachedEnd to 1.
            reachedEnd = 1;
        }else{
            if (found==1){
                //If name is already found and end is not reached, names need to be moved down one spot.
                arrHash[hashWordValue][counter] = arrHash[hashWordValue][counter-1];
            }else{
                //If name found set it to NULL and go next
                if(compare(arrHash[hashWordValue][counter], name) == 0){
                    arrHash[hashWordValue][counter] = NULL;
                    //Set found to 1 as name has been found.
                    found = 1;
                    counter++;
                }
            }
            counter++;
        }
    }
    //Return whether or not name was removed.
    return found;
}


/**
 * Main fucntion that reads in and stores values in the hash array.
 */
int main(int argc, const char * argv[]) {
    char* arr[10000];
    FILE *fp;

    fp = fopen("names.txt", "r");

    //Read characters and fill the character array for splitting later.
    char charArray[100000];
    char character;
    while((character = fgetc(fp)) != EOF){
        //Gets rid of " from the text.
        if (character!='"'){
            charArray[nameCharacterCounter]=character;
            nameCharacterCounter++;
        }
        //printf("%c",character);
    }
    fclose(fp);
    
    //Adds a extra comma so the split function catches the last name.
    charArray[nameCharacterCounter] = ',';
    nameCharacterCounter++;
    
    //Sets the char pointer to the char array.
    charArrPointer = charArray;
    //Finds first name.
    char *name = split();
    //Counter used for putting names into the array and storing number for names.
    int nameCounter = 0;
    while (name != NULL){
        arr[nameCounter] = name;
        nameCounter++;
        //Finds next name.
        name = split();
    }
    
    //Puts all the names in the hash table.
    hash(arr, nameCounter);
    
    //Trys to remove TOBIAS.
    char* target = "TOBIAS";
    int result = removeString(target);
    
    if (result == 0){
        printf("FAIL, NOT FOUND %s.\n",target);
    }else{
        printf("SUCCSESS %s removed.\n",target);
    }
    
    target = "PETERI";
    //Trys to add PETERI.
    add(target);
    
    //Trys to search for PETERI.
    result = search(target);
    
    if (result == 0){
        printf("FAIL, NOT FOUND %s.\n",target);
    }else{
        printf("SUCCSESS %s found.\n",target);
    }
    
    //Trys to search for TOBIAS
    target = "TOBIAS";
    result = search(target);
    
    if (result == 0){
        printf("FAIL, NOT FOUND %s.\n",target);
    }else{
        printf("SUCCSESS %s found.\n",target);
    }
    
    //Prints all names stored in table.
    printf("\n");
    for(int i = 0; i<6967;i++){
        for (int j = 0; j<15;j++){
            if (arrHash[i][j] != NULL){
                printf("%s ",arrHash[i][j]);
            }
        }
    }
    
    return 0;
    
}


