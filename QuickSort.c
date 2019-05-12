
#include <stdio.h>
#include <string.h>
#include <unistd.h>


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
        if(*charArrPointer == ',' || *charArrPointer == '\n' ) {
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
 * Function to split a given section of the array into values smaller than and bigger than a chosen pivot.
 */
int splitArr (char* arr[], int low, int high){
    //Choses pivot.
    char* pivot = arr[high];
    int i = low;
    char* placeholder;
    //Fills the array with values lower that the pivot by swapping them with the value at the bottom of the given section of the array.
    for (int y = low; y < high; y++){
        //Loops through given section of array and compares against the pivot.
        if ( compare(arr[y], pivot)<0){
            
            placeholder = arr[i];
            arr[i] = arr[y];
            arr[y] =placeholder;
            //Moves up the array as it assigns values.
            i++;
            
        }
    }
    //Places pivot between the two sections (one all bigger than the pivot and the other smaller).
    placeholder = arr[i];
    arr[i] = arr[high];
    arr[high] =placeholder;
    
    //Returns the postition of the pivot.
    return (i);
}

/**
 * Function to sort the given array recursively.
 */
void quickSort(char* nameArr[], int bot, int top){
    //If bot is no longer smaller the section of the array is one element so doesnt need to be sorted.
    if (bot < top){
        //Splits array into sections that are bigger than the pivot and smaller than the pivot.
        int pivot = splitArr(nameArr, bot, top);
        //Repeats with these new now smaller sections.
        quickSort(nameArr, bot, pivot - 1);
        quickSort(nameArr, pivot + 1, top);
    }
    
}

/**
 * Main function to read and write to files.
 */
int main(void) {
    //Declares the arrays for later use.
    char charArray[1000000];
    char* arr[6000];
    
    FILE *fp;
    fp = fopen("names.txt", "r");

    char character;
    //Reads in from the file character by character.
    while((character = fgetc(fp)) != EOF){
        charArray[nameCharacterCounter]=character;
        nameCharacterCounter++;
    }
    fclose(fp);
    charArray[nameCharacterCounter] = ',';
    nameCharacterCounter++;
    
    
    //Sets the pointer array equal to the same for splitting.
    charArrPointer = charArray;
    //Finds first name.
    char *name = split();
    //Counter used for putting names into the array and storing number fo names.
    int nameCounter = 0;
    while (name != NULL){
        arr[nameCounter] = name;
        nameCounter++;
        //Finds next name.
        name = split();
    }
    //arr[nameCounter] = charArrPointer;
    
    //Sorts names.
    quickSort(arr,0,nameCounter-1);

    //Writes to new file and prints names.
    FILE *fp2;
    fp2 = fopen("sortedNames.txt", "w");
    
    for (int i = 0;i<nameCounter;i++){
        printf("%s", arr[i]);
        fprintf(fp2,"%s", arr[i]);
        
        if (i!=nameCounter-1){
            printf(",");
            fprintf(fp2,",");
        }
    }
    return 0;
    
}
