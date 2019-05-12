
#include <stdio.h>
int main(int argc, const char * argv[]) {
    FILE *fpText;
    //Array to store the ciphered text.
    char cipherArr[8][10000];
    //Placeholder array to allow swapping of collumns.
    char arrPlaceholder[10000];
    char punctuation[] = {'.','"',')','(','!','?','>','<',':',';',',',' ','-','\n'};
    char keyword[8]= "LOVELACE";

    fpText = fopen("text.txt", "r"); // read mode

    //Counters to fill cipherArr.
    int collumnCounter= 0;
    int rowCounter = 0;
    char character;
    
    while((character = fgetc(fpText)) != EOF){
        int punctuationCheck = 0;
        //Checks for punctuation.
        for (int i = 0; i<14;i++){
            if(punctuation[i]== character){
                punctuationCheck =1;
            }
        }
        //If not punctuation, add.
        if (punctuationCheck==0){
            if(collumnCounter == 8){
                rowCounter++;
                collumnCounter = 0;
            }
            cipherArr[collumnCounter][rowCounter]=character;
            collumnCounter++;
        }
    }
    
    //Fill in final row.
    for (int i = collumnCounter; i<8;i++){
        cipherArr[i][rowCounter] = 'x';
    }
    
    char placeholder;
    //Sorts keyword and array.
    for (int j =0; j<7; j++){
        for (int i = 0; i<7;i++){
            if (keyword[i] > keyword[i+1]){
                placeholder = keyword[i];
                //Swaps around keyword and array.
                for (int j = 0;j<rowCounter+1;j++){
                    arrPlaceholder[j] = cipherArr[i][j];
                }
                
                keyword[i] = keyword[i+1];
                for (int j = 0;j<rowCounter+1;j++){
                    cipherArr[i][j] = cipherArr[i+1][j];
                }
                
                
                keyword[i+1] = placeholder;
                for (int j = 0;j<rowCounter+1;j++){
                    cipherArr[i+1][j] = arrPlaceholder[j];
                }
            }
        }
    }
    
    fclose(fpText);
    FILE * fpOutput;

    /* open the file for writing*/
    fpOutput = fopen ("encryptedFile.txt","w");
    
    //Write to file and print.
    for (int j = 0;j<rowCounter+1;j++){
        for (int i = 0; i<8; i++){
            fprintf(fpOutput,"%c",cipherArr[i][j]);
            printf("%c",cipherArr[i][j]);
        }
    }
    fclose (fpOutput);
}
