import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class problem6 {

	/**
	 * Global variable storing the length of the redacted words
	 */
	static int redactedWordsCounter = 0;

	/**
	 * Global variable storing the length of the only chars array.
	 */
	static int onlyCharsLength = 0;

	/**
	 * Global variable storing the length of the line array.
	 */
	static int lineLength = 0;

	/**
	 * Checks if a word is a Roman numeral.
	 */
	static boolean isRomanNumeral(String input) {
		boolean result = true;
		//Loops through characters.
		for (int i =0;i<length(input);i++) {
			if (!(input.charAt(i) == 'X' || input.charAt(i) == 'V' || input.charAt(i) == 'I')) {
				result = false;
			}
		}
		return result;
	}

	/**
	 * This checks if a string array contains a string.
	 */
	static boolean contains(String arr[], String input) {

		boolean result = false;
		for (String word:arr) {
			if (compare(word,input)==0) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * This converts a word to lowercase.
	 */
	static String toLowercase(String input) {
		String word = "";
		for (int i = 0; i<length(input);i++) {
			if((int)input.charAt(i)>64 && (int)input.charAt(i)<91) {
				//Make letter lowercase.
				word += (char)(input.charAt(i)+32);
			}else {
				word += input.charAt(i);
			}
		}
		return word;

	}

	/**
	 * This gets the substring of a string between a start and end.
	 */
	static String substring(String input, int start, int end) {
		String word = "";
		for (int i = start; i<end;i++) {
			word += input.charAt(i);
		}
		return word;

	}

	/**
	 * This gets the substring of a string after a start.
	 */
	static String substring(String input, int start) {
		String word = "";
		for (int i = start; i<length(input);i++) {
			word += input.charAt(i);
		}
		return word;
	}

	/**
	 * This splits a String into an array of strings by splitting at every space.
	 */
	static String[] splitSpace(String input) {

		String[] words = new String[100];
		int wordCounter = 0;
		String word = "";

		//Loops through all letters in input.
		for (int i = 0; i<length(input);i++) {
			//If space is found then set word to next space in the output array and reset values.
			if (input.charAt(i) == ' '){
				words[wordCounter] = word;
				wordCounter++;
				//Keep track of elements.
				lineLength = wordCounter;
				word = "";
			}else {
				//Add letter to word.
				word += input.charAt(i);
			}
		}
		//if the last word is not nothing add it to array.
		if (word != "") {
			words[wordCounter] = word;
			wordCounter++;
		}
		//Keep track of elements.
		lineLength = wordCounter;
		return words;
	}

	/**
	 * This finds the length of a string.
	 */
	static int length(String word) {
		word = word +'\0';
		int i = 0;

		while (word.charAt(i)!='\0') {
			i++;
		}

		return i;
	}

	/**
	 * This splits a String into an array of strings by splitting at every non letter.
	 */
	static String[] splitPunctuation(String input) {

		String[] words = new String[100];
		int wordCounter = 0;
		String word = "";
		onlyCharsLength = 0;

		//Loops through all letters in input.
		for (int i = 0; i<length(input);i++) {
			//If non letter is found then set word to next space in the output array and reset values.
			if (((int)input.charAt(i))<65 || ((int)input.charAt(i))>90 &&((int)input.charAt(i))<97 || ((int)input.charAt(i))>122){
				words[wordCounter] = word;
				wordCounter++;
				//Keep track of elements.
				onlyCharsLength = wordCounter;
				word = "";
			}else {
				//Add letter to word.
				word += input.charAt(i);
			}
		}
		//if the last word is not nothing add it to array.
		if (word != "") {
			words[wordCounter] = word;
			wordCounter++;
		}
		//Keep track of elements.
		onlyCharsLength = wordCounter;
		return words;
	}

	/**
	 * This compares 2 strings. Returns 1 if string1 is alphabetically later than string2 else returns -1.
	 */
	static String[] splitPunctuation(String input, char character) {

		String[] words = new String[100];
		int wordCounter = 0;
		String word = "";
		onlyCharsLength = 0;

		//Loops through all letters in input.
		for (int i = 0; i<length(input);i++) {
			//If non letter AND not 'character' is found then set word to next space in the output array and reset values.
			if ((((int)input.charAt(i))<65 || ((int)input.charAt(i))>90 &&((int)input.charAt(i))<97 || ((int)input.charAt(i))>122) && input.charAt(i) !=character){
				words[wordCounter] = word;
				wordCounter++;
				//Keep track of elements.
				onlyCharsLength = wordCounter;
				word = "";
			}else {
				//Add letter to word.
				word += input.charAt(i);
			}

		}
		//if the last word is not nothing add it to array.
		if (word != "") {
			words[wordCounter] = word;
			wordCounter++;
		}
		//Keep track of elements.
		onlyCharsLength = wordCounter;
		return words;
	}

	/**
	 * This removes all punctuation from a word.
	 */
	static String removeAllPunct(String word) {
		int i = 0;
		int length = length(word);
		//Loops through all letters.
		while (i<length){
			//If non letter remove character and decrease length.
			if (((int)word.charAt(i))<65 || ((int)word.charAt(i))>90 &&((int)word.charAt(i))<97 || ((int)word.charAt(i))>122){
				word = substring(word,0,i)+substring(word,i+1);
				length--;
			}
			i++;
		}
		return word;
	}

	/**
	 * This compares 2 strings. Returns 1 if string1 is alphabetically later than string2 else returns 0 if equal and -1 else.
	 */
	private static int compare(String string1,String string2) {

		int value1 = 0;
		int value2 = 0;
		int i = 0;

		string1 += '\0';
		string2 += '\0';

		while(true) {
			//If string1 has reached its end it means it is the same as the other string or before in the alphabet so returns -1.
			if (string1.charAt(i) !='\0') {
				//Sets value1 to the current char being checked.
				value1 = (int)string1.charAt(i);
			}else {
				if (string2.charAt(i) == '\0') {
					return 0;
				}else {
					return -1;
				}

			}
			//If string2 has reached its end it means it is before in the alphabet so returns 1.
			if (string2.charAt(i) !='\0') {
				//Sets value2 to the current char being checked.
				value2 = (int)string2.charAt(i);
			}else {
				return 1;
			}

			//Compares values, if equal it goes to the next character else returns accordingly.
			if (value1 != value2) {
				return -1;
			}
			i++;
		}
	}

	/**
	 * This redacts the current word only if it is exactly on of the redacted words.
	 */
	static boolean redactExact (String[] words, String[] lineArr, int i,int j) {
		int wordCounter = 0;
		boolean redactedWord = false;

		//Loops though all redacted words untill one that is the same as the input is found.
		while(wordCounter<redactedWordsCounter && !redactedWord) {
			//Checks if they are equal.
			if(compare(lineArr[i],words[wordCounter]) == 0) {
				redactedWord = true;
				//Redacts word.
				while(j!=length(lineArr[i])) {
					lineArr[i] = substring(lineArr[i],0,j)+'*'+substring(lineArr[i],j+1);
					j++;
				}
			}
			wordCounter++;
		}
		return redactedWord;
	}

	/**
	 * This redacts a word if it is the same as one of the redacted words when all punctuation is removed. (e.g. Ma-da-gas-car is redacted here).
	 */
	static boolean redactFromWordsWhole (String[] words, String[] lineArr, int i,int j) {

		int wordCounter = 0;
		boolean redactedWord = false;
		if (lineArr[i]!=null) {
			//This holds the value of the word without punctuation.
			String nonPunctuation = removeAllPunct(lineArr[i]);
			//Loops though all redacted words untill one that is the same as the non punctuation input is found.
			while(words[wordCounter]!=null && !redactedWord) {
				//Checks if they are equal.
				if(compare(nonPunctuation,words[wordCounter]) == 0) {
					redactedWord = true;
					//Redacts word.
					while(j!=length(lineArr[i])) {
						//Makes sure any following punctuation isnt redacted.
						if (((int)lineArr[i].charAt(j))<65 || (((int)lineArr[i].charAt(j))>90 &&((int)lineArr[i].charAt(j))<97) || ((int)lineArr[i].charAt(j))>122  ) {
							if (lineArr[i].charAt(j)!= 'ü' && lineArr[i].charAt(j)!= 'ö' && lineArr[i].charAt(j)!= 'ä' && lineArr[i].charAt(j)!= 'ï' && lineArr[i].charAt(j)!= '-'&& lineArr[i].charAt(j)!= '’'){
								break;
							}
						}
						//Makes sure to keep redacting on these execptions but not redact the puncuation.
						if (lineArr[i].charAt(j)!= '-' && lineArr[i].charAt(j)!= '’') {
							lineArr[i] = substring(lineArr[i],0,j)+'*'+substring(lineArr[i],j+1);
						}
						j++;
					}
				}
				wordCounter++;
			}
		}
		wordCounter = 0;
		return redactedWord;
	}

	/**
	 * This redacts a word if when split into multiple Strings by the punctuation, any of these smaller strings equal a redacted word. (e.g. un-Russian is redacted here).
	 */
	static boolean redactFromWordsSplit(String[] onlyCharacters, String[] words, String[] lineArr, int i, int j) {
		int wordCounter = 0;
		boolean redactedWord = false;
		//Loops through the onlyCharacters array.
		for(int regexCounter = 0;regexCounter<onlyCharsLength;regexCounter++){
			//Loops though all redacted words untill one that is the same as the word being checked is found.
			//System.out.println(onlyCharacters[regexCounter]);
			while(words[wordCounter]!=null && !redactedWord) {
				//Checks if they are equal.
				if (compare(onlyCharacters[regexCounter],words[wordCounter]) == 0) { 
					redactedWord = true;
					//Redacts word.
					while(j!=length(lineArr[i])) {
						//Makes sure any punctuation isnt redacted.
						if (((int)lineArr[i].charAt(j))<65 || (((int)lineArr[i].charAt(j))>90 &&((int)lineArr[i].charAt(j))<97) || ((int)lineArr[i].charAt(j))>122 ) {

							if (lineArr[i].charAt(j)!= 'ü' && lineArr[i].charAt(j)!= 'ö' && lineArr[i].charAt(j)!= 'ä' && lineArr[i].charAt(j)!= 'ï' && lineArr[i].charAt(j)!= '-'&& lineArr[i].charAt(j)!= '’'){
								break;
							}
						}
						//Makes sure to keep redacting on these execptions but not redact the puncuation.
						if (lineArr[i].charAt(j)!= '-' && lineArr[i].charAt(j)!= '’') {
							lineArr[i] = substring(lineArr[i],0,j)+'*'+substring(lineArr[i],j+1);
						}
						j++;
					}
				}
				wordCounter++;
			}
			wordCounter = 0;
		}
		return redactedWord;
	}

	/**
	 * This looks for new words to redact.
	 */
	static void redactWordNormally(String[] onlyCharacters, String[] words, String[] lineArr,String[] punctuation, String[] commonWords, int i, int j) {
		String newWord = "";
		//If its the first word of the line we can't redact normally.
		if(i!=0) {
			if (length(lineArr[i-1]) !=0) {
				onlyCharacters = splitPunctuation(lineArr[i]);
				onlyCharacters[0] = toLowercase(onlyCharacters[0]);
				//Checks exceptions like if the word is a common word like The and checks if the word is longer than 1 letter to avoid (for example) I.
				//Also checks if the previous word ended with specific punctuation.
				if (!contains(punctuation,String.valueOf(lineArr[i-1].charAt(length(lineArr[i-1])-1))) && compare(lineArr[i-1],"*")!=0 && !contains(commonWords,onlyCharacters[0]) && !(((int)lineArr[i].charAt(j+1))<65 || (((int)lineArr[i].charAt(j+1))>90 &&((int)lineArr[i].charAt(j+1))<97) || ((int)lineArr[i].charAt(j+1))>122)){
					//Redacts word.
					while(j!=length(lineArr[i])) {
						//Makes sure not to redact punctuation.
						if (((int)lineArr[i].charAt(j))<65 || (((int)lineArr[i].charAt(j))>90 &&((int)lineArr[i].charAt(j))<97) || ((int)lineArr[i].charAt(j))>122) {
							if (lineArr[i].charAt(j)!= 'ü' && lineArr[i].charAt(j)!= 'ö' && lineArr[i].charAt(j)!= 'ä' && lineArr[i].charAt(j)!= 'ï' && lineArr[i].charAt(j)!= '-' && lineArr[i].charAt(j)!= '’'){
								break;
							}
						}
						//Does't redact ' or - but continues redacting.
						if (lineArr[i].charAt(j)!= '-' && lineArr[i].charAt(j)!= '’') {
							//Builds new word.
							newWord += substring(lineArr[i],j,j+1);
							lineArr[i] = substring(lineArr[i],0,j)+'*'+substring(lineArr[i],j+1);
						}
						j++;
					}
					//Makes sure word hasn't been added before.
					if (!contains(words,newWord)) {
						words[redactedWordsCounter] = newWord;
						redactedWordsCounter++;
					}
					//Resets newWord.
					newWord = "";
				}
			}
		}	
	}

	/**
	 * Main function reads in input and calls other functions to redact input.
	 */
	public static void main(String[] args) {

		String punctuation[] = {".","’", "“","!","?",">","<","”","‘",":",";"};

		//Sets up reader for commonWords.
		String fileNameCommonWords = "commonWords.txt";
		File fileCommonWords = new File(fileNameCommonWords);
		BufferedReader readerCommonWords = null;
		try {
			readerCommonWords = new BufferedReader(new FileReader(fileCommonWords));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		String line = "";
		//EOF checks for the end of file.
		boolean EOF =false;
		int counter = 0;
		String commonWords[] = new String[1000];
		
		//Reads in commonWords.
		while (!EOF) {
			try {
				line = readerCommonWords.readLine();
				if (line == null) {
					EOF = true;
				}else {
					commonWords[counter] = line;
					counter++;
				}

			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Reads in the words to redact.
		String fileNameRedact = "redact.txt";
		File fileRedact = new File(fileNameRedact);
		BufferedReader inRedact = null;
		try {
			inRedact = new BufferedReader(new FileReader(fileRedact));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		line = "";

		String lineWords[] = new String[100];
		int wordCounter = 0;

		String words[] = new String[10000];

		//EOF checks for the end of file.
		EOF =false;
		counter = 0;

		//Reads in words to redact.
		while (!EOF) {
			try {
				line = inRedact.readLine();
				if (line == null) {
					EOF = true;
				}else {
					//Splits words by spaces.
					lineWords = splitSpace(line);
					while(lineLength > wordCounter) {
						words[redactedWordsCounter] = lineWords[wordCounter];

						wordCounter++;
						redactedWordsCounter++;
					}
					wordCounter = 0;
				}

			}catch (IOException e) {
				e.printStackTrace();
			}
		}

		//Runs the redact code twice to redact any words which were added later in the text.
		for (int p = 0; p<2; p++) {
			//Sets up to read from War and Peace.
			String fileName = "warAndPeace.txt";
			File file = new File(fileName);
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			line = "";

			//Sets up variables
			String lineArr[] = new String[1000];
			//EOF checks for the end of file.
			EOF =false;
			String[] onlyCharacters = new String[5];

			//Reads in War and Peace line by like.
			while (!EOF) {
				try {
					line = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (line == null) {
					EOF = true;
				}else {
					//Splits line by spaces.
					lineArr = splitSpace(line);
					//Loops through all words.
					for(int i = 0;i<lineLength;i++) {
						//Loops through all letters.
						for (int j =0;j<length(lineArr[i]);j++) {
							//As some checks add 1 to j and I don't want words 1 letter long the last letter is filtered out.
							if (j!= length(lineArr[i])-1) {
								//Splits the word and checks if the first element is a Roman numeral.
								onlyCharacters = splitPunctuation(lineArr[i]);
								//Also checks that the letter is a capital and the word is longer than one letter.
								if (((int)lineArr[i].charAt(j))>64 && ((int)lineArr[i].charAt(j))<91 && lineArr[i].charAt(j+1) != ' ' && lineArr[i].charAt(j+1) != '-' && (!isRomanNumeral(onlyCharacters[0]) || compare(onlyCharacters[0],"") ==0 )) {
									//Calls redact exact and sees if it reacted.
									boolean redactedWord = redactExact(words,lineArr,  i, j);
									if (!redactedWord) {
										//Calls redactWhole and sees if it reacted.
										redactedWord = redactFromWordsWhole(words,lineArr,  i, j);
									}
									onlyCharacters = splitPunctuation(lineArr[i], '’');
									if (!redactedWord) {
										//Calls redact from split and sees if it reacted.
										redactedWord = redactFromWordsSplit(onlyCharacters, words, lineArr,  i,  j);
									}
									//If non of these worked try redact normally.
									if (!redactedWord) {
										//To prevent redacting when certain cases like "Hello....." are present.
										if(j==0) {
											redactWordNormally(onlyCharacters, words,lineArr,punctuation,commonWords,  i,  j);
										}else {
											if (lineArr[i].charAt(j-1) == '’'|| lineArr[i].charAt(j-1) == '-') {
												redactWordNormally(onlyCharacters, words,lineArr,punctuation,commonWords,  i,  j);
											}
										}
									}
								}
							}
						}
						//Prints out.
						if(p==1) {
							System.out.print(lineArr[i] +" ");
						}

					}
					//Prints out.
					if(p==1) {
						System.out.println();
					}
				}
			}
			//Print to see all redacted words.
			/*
			for(int x = 0;x<redactedWordsCounter;x++) {
				System.out.println(words[x]);
			}
			System.out.println(redactedWordsCounter);
			*/
		}
	}
}
