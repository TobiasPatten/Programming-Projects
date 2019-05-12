import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class bubbleSort {

	/**
	 * Array that holds all the names.
	 */
	static String[] list = new String[10000];
	
	/**
	 * Int that holds the top of the array.
	 */
	static int listTop = 0;


	/**
	 * Main function which calls each of the other functions.
	 */
	public static void main(String[] args) {
		readIn();
		sort();
		write();
	}
	/**
	 * Splits up a given string and adds each word to the list.
	 */
	private static void split(String line) {
		int i = 0;
		String word = "";
		while(line.charAt(i) !='\0') {
			if (line.charAt(i)!= '"') {
				if (line.charAt(i)!= ',') {
					word +=line.charAt(i);
				}else {
					word +='\0';
					list[listTop] = word;
					listTop++;
					word = "";
				}
			}
			i++;

		}
	}

	
	/**
	 * This compares 2 strings. Returns 1 if string1 is alphabetically later than string2 else returns -1.
	 */
	private static int compare(String string1,String string2) {

		int value1 = 0;
		int value2 = 0;

		int i = 0;
		
		while(true) {
			//If string1 has reached its end it means it is the same as the other string or before in the alphabet so returns -1.
			if (string1.charAt(i) !='\0') {
				//Sets value1 to the current char being checked.
				value1 = (int)string1.charAt(i);
			}else {
				return -1;
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
				if (value1>value2) {
					return 1;
				}else {
					return -1;
				}
			}
			i++;

		}


	}


	/**
	 * This bubble sorts the list by passing through the list till a swap isn't made.
	 */
	private static void sort() {
		//Sorted holds whether or not the list is sorted.
		Boolean sorted = false;
		//Holds whether or not a swap has been made.
		Boolean swap = false;
		//Holder used for swapping elements.
		String holder =null;
		
		//Loops till it passes through the list without making a swap.
		while (!sorted) {
			for(int i=0;i<listTop-1;i++) {
				//compares 2 adjacent elements and swaps if necessary.
				if (compare(list[i], list[i+1])>0){
					holder = list[i];
					list[i] = list[i+1];
					list[i+1] = holder;
					//Sets swap to true as a swap has been made.
					swap = true;
				}
			}
			//Checks if a swap has been made.
			if (!swap) {
				sorted = true;
			}
			swap =false;
		}
	}
	
	/**
	 * Writes to the file.
	 */
	private static void write() {
		try {
			File fileWrite = new File("sortedNames.txt");
			
			//Creates a new file.
			fileWrite.delete();
			fileWrite.createNewFile();

			FileWriter fileWriter = new FileWriter(fileWrite);
			BufferedWriter fileOut = new BufferedWriter(fileWriter);


			//Writes names out in the same standard format.
			for(int i=0;i<listTop;i++) {
				fileOut.write("\"" +list[i] +"\"");
				System.out.print("\"" +list[i] +"\"");
				if (i!=listTop-1) {
					fileOut.write(",");
					System.out.print(",");
				}
			}
			
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads from file.
	 */
	private static void readIn() {
		try {
			
			File file = new File("names.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader fileIn = new BufferedReader(fileReader);
			String line;


			while ((line = fileIn.readLine()) != null) {   
				//Adds an end of file character to the end.
				line +='\0';
				//Splits the line into the list.
				split(line);
			}
			fileIn.close();

		}catch (IOException ex) {
			ex.printStackTrace();
		}
		


	}
}
