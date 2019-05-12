#include <stdio.h>
#include <string.h>

/**
 * Global variable to help store names. Holds the number of names after reading has happened.
 */
int splitCharacterCounter = 0;

/**
 * Global variable to hold a char* version of charArr (in main) so the split function can cycle through splitting up names.
 */
char* charArrPointer[5];

/**
 * Global variable to hold the position of the split() through the array when reading in.
 */
int position = 0;

/**
 * Store of the words to redact from the given text.
 */
char* wordsToRedact[100][2];

/**
 * Store of the text that needs to be redacted.
 */
char* wordsArr[10000];

/**
 * Store of the second words to redact from text. (e.g. United from Manchester United would be stored here).
 */
char* secondWordArr[100];

/**
 * Store of the number of words in wordsToRedact.
 */
int numWordsToRedact = 0;

/**
 * Store of postitions of words that need to be redacted.
 */
int redacted[100][2];

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
 * Function to take the next name from charArrPointer.
 */
char* split(char splitChar, int timesRan) {
    //Holds the value of the pointer before it is moved.
    char* outputString = charArrPointer[timesRan];
    for(int i = position; i < splitCharacterCounter; i++) {
        position++;
        //If a comma is found it sets that comma to now be an end of string character and returns the initial value of itself stored in outputString.
        if(*charArrPointer[timesRan] == splitChar) {
            *charArrPointer[timesRan] = '\0';
            charArrPointer[timesRan]++;
            return outputString;
        }else{
            //When it doesnt find a comma it just increments the pointer.
            charArrPointer[timesRan]++;
        }
        if (position==splitCharacterCounter-1){
            return outputString;
        }
    }
    //Returns NULL when end is reached.
    return NULL;
    
}
/**
 * Function to compare if 2 strings are equal.
 */
int compareEqual(char* string1, char* string2){
    
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
    if (*string1){
        return 0;
    }else{
        return 1;
    }
}

/**
 * Checks to see if one string contains another.
 */
char* contains(char* string1, char* string2){
    
    //Checks while not end of String1
    while (*string1){
        //Checks if value is equal.
        if (*string1 == *string2){
            //Checks if equal
            if (compareEqual(string2, string1)==1){
                return (string1);
            }
        }
        string1++;
    }
    return (NULL);
}

/**
 * This calcualtes which words need to be redacted and stores them in redacted.
 */
void redact(){
    //Counters
    int i =0;
    int j = 0;
    //Keeps track of saving words to redact.
    int recdatedCounter = 0;
    
    //Makes current word lowercase.
    while (wordsArr[i]!=NULL){
        
        while (wordsArr[i][j]!='\0'){
            if (wordsArr[i][j]>= 'A' && wordsArr[i][j] <= 'Z') {
                wordsArr[i][j] = wordsArr[i][j]  + 32;
            }
            j++;
        }
        j=0;
        
        //Keeps track of second words.
        int secondWordCounter =0;
        
        //Loops through the words to redact.
        for(int z = 0;z<numWordsToRedact;z++) {
            int found = 0;
            //If the word contains something to redact.
            if (contains(wordsArr[i], wordsToRedact[z][0]) != NULL){
                //If word has a second word.
                if (wordsToRedact[z][1] != NULL && i>0){
                    i++;
                    //Makes next word lowercase.
                    char* lowercase = wordsArr[i];
                    while (lowercase[j]!='\0'){
                        if (lowercase[j]>= 'A' && lowercase[j] <= 'Z') {
                            lowercase[j] = lowercase[j]  + 32;
                        }
                        j++;
                    }
                    j=0;
                    //Checks if this next word contains the according word.
                    if(contains(wordsArr[i], secondWordArr[secondWordCounter]) != NULL){
                        //Adds the first word to the redact list.
                        i--;
                        redacted[recdatedCounter][0] = i;
                        int p = 0;
                        //Calculates length of redaction.
                        while (wordsToRedact[z][0][p] !='\0'){
                            p++;
                        }
                        redacted[recdatedCounter][1] = p;
                        recdatedCounter++;
                        
                        i++;
                        //Adds the second word to the redact list.
                        redacted[recdatedCounter][0] = i;
                        p = 0;
                        //Calculates length of redaction.
                        while (secondWordArr[secondWordCounter][p] !='\0'){
                            p++;
                        }
                        //Moves second counter to next word.
                        secondWordCounter++;
                        //Stores length.
                        redacted[recdatedCounter][1] = p;
                        //Moves to next spot.
                        recdatedCounter++;
                        break;
                    }
                    //Sets found to true.
                    found = 1;
                }else{
                    //Moves second counter to next word.
                    secondWordCounter++;
                }
                //If a word hasn't been found redact one.
                if (found == 0){
                    redacted[recdatedCounter][0] = i;
                    int p = 0;
                    while (wordsToRedact[z][0][p] !='\0'){
                        p++;
                    }
                    redacted[recdatedCounter][1] = p;
                    recdatedCounter++;
                    break;
                }
            }
        }
        i++;
    }
}

/**
 * Reads in from file and runs code to redact from redacted.
 */
int main(int argc, const char * argv[]) {
    FILE *fpRedact;
    
    
    fpRedact = fopen("redact.txt", "r");
    
    //Set up variables.
    char redactCharArray[100000];
    char secondCharArr[100000];
    
    int secondWordCharCounter = 0;
    
    int redactCharCounter = 0;
    //Keeps track of additional second words.
    int positionOfSecondWord = 0;
    
    //Reads in character by character.
    char character;
    while((character = fgetc(fpRedact)) != EOF){
        //Makes them lowercase.
        if (character >= 'A' && character <= 'Z') {
            character =character + 32;
        }
        //If a space is not found add to the char array.
        if(character != ' '){
            //If a \n character is found increment counter as that means a word has been found.
            if (character == '\n'){
                positionOfSecondWord++;
            }
            redactCharArray[redactCharCounter]=character;
            redactCharCounter++;
        
        }else{
            //If a space is found make next char a \n to seperate first word.
            redactCharArray[redactCharCounter] = '\n';
            redactCharCounter++;
            //Get next character.
            character = fgetc(fpRedact);
            //Set current word being read in to have a second letter by setting its second value to non null.
            wordsToRedact[positionOfSecondWord][1] = "a";
            //Loops untill the \n char is found.
            while (character != '\n'){
                //Makes it lowrcase.
                if (character >= 'A' && character <= 'Z') {
                    character =character + 32;
                }
                //Adds next char.
                secondCharArr[secondWordCharCounter] = character;
                secondWordCharCounter++;
                //Get next char.
                character = fgetc(fpRedact);
                //If it does equal a \n, and the \n and a \0.
                if (character == '\n'){
                    secondCharArr [secondWordCharCounter] ='\n';
                    secondWordCharCounter++;
                    secondCharArr [secondWordCharCounter]='\0';;
                }
            }
            
        }
    }
    fclose(fpRedact);
    
    //Set the pointer to this charArray and set the length of the char array for the splitter.
    splitCharacterCounter = redactCharCounter;
    charArrPointer[0] = redactCharArray;
    //Finds first name.
    char *wordSet1[1000];
    //WordSets hold the values of split pointers.
    wordSet1[0]= split('\n',0);
    //Counter used for putting names into the array and storing number for names.
    int wordCounter = 0;
    while (wordSet1[wordCounter] != NULL){
        wordsToRedact[numWordsToRedact][0] = wordSet1[wordCounter];
        numWordsToRedact++;
        
        //Finds next name.
        wordCounter++;
        wordSet1[wordCounter] = split('\n',0);
    }
    
    //Reset the position.
    position = 0;
    //Set the pointer to this charArray and set the length of the char array for the splitter.
    splitCharacterCounter = secondWordCharCounter;
    charArrPointer[1] = secondCharArr;
    int secondWordCounter = 0;
    //WordSets hold the values of split pointers.
    char *wordSet2[1000];
    //Sets first value.
    wordSet2[0] = split('\n',1);
    wordCounter = 0;
    while (wordSet2[wordCounter] != NULL){
        secondWordArr[secondWordCounter] = wordSet2[wordCounter];
        
        secondWordCounter++;
        wordCounter++;
        //Finds next name.
        wordSet2[wordCounter] = split('\n',1);
    }
    
    FILE *fpDebate;
    
    
    fpDebate = fopen("debate.txt", "r"); // read mode
    
    //Reads in chars.
    char debateCharArray[100000];
    int debateCharCounter = 0;
    while((character = fgetc(fpDebate)) != EOF){
        debateCharArray[debateCharCounter]=character;
        debateCharCounter++;
    }
    fclose(fpDebate);
    
    
 
    //WordSets hold the values of split pointers.
    char *wordSet3[1000];
    //Resets position.
    position= 0;
    //Set the pointer to this charArray and set the length of the char array for the splitter.
    splitCharacterCounter = debateCharCounter;
    charArrPointer[2] = debateCharArray;
    int debateWordCounter = 0;
    //Sets first value.
    wordSet3[0] = split(' ',2);
    wordCounter = 0;
    while (wordSet3[wordCounter] != NULL){
        wordsArr[debateWordCounter] = wordSet3[wordCounter];
        debateWordCounter++;
        
        //Finds next name.
        wordCounter++;
        wordSet3[wordCounter] = split(' ',2);
    }
    
    //Redacts.
    redact();
    
    //Reads in again to get the inital non lowercase input.
    fpDebate = fopen("debate.txt", "r");

    
    debateCharCounter = 0;
    while((character = fgetc(fpDebate)) != EOF){
        debateCharArray[debateCharCounter]=character;
        debateCharCounter++;
    }
    fclose(fpDebate);
    
    //WordSets hold the values of split pointers.
    char *wordSet4[1000];
    //Resets position.
    position = 0;
    //Set the pointer to this charArray and set the length of the char array for the splitter.
    splitCharacterCounter = debateCharCounter;
    charArrPointer[3] = debateCharArray;
    debateWordCounter = 0;
    //Sets first value.
    wordSet4[0] = split(' ',3);
    wordCounter = 0;
    while (wordSet4[wordCounter] != NULL){
        wordsArr[debateWordCounter] = wordSet4[wordCounter];
        debateWordCounter++;
        //Finds next name.
        wordCounter++;
        wordSet4[wordCounter] = split(' ',3);
    }
    
    int redactedCounter = 0;
    //Loops through the wordsArr. If redacted has been stored for that word print *'s.
    for(int i = 0;i<debateWordCounter;i++){
        if (i == redacted[redactedCounter][0]){
            //Loops through printing as many * as was stored.
            for(int p = 0;p<redacted[redactedCounter][1];p++){
                wordsArr[i][p] = '*';
            }
            redactedCounter++;
        }
        printf("%s ",wordsArr[i]);
    }
}


