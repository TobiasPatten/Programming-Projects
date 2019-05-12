import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class problem4 {

	/**
	 * A global variable to keep track of the number of names in the arr(XOR list)
	 */
	static int counter = 0;
	
	/**
	 * A global variable for the XOR linked list.
	 */
	static String arr[][] = new String[10000][2];

	
	/**
	 * A global variable for the list of free addresses in the section of the XOR list currently bring used.
	 */
	static Integer[] freedAddresses = new Integer[10000];
	
	/**
	 * A global variable to keep track of the top of freedAddresses
	 */
	static int freeAddressesTop = 0;
	
	/**
	 * Main function which calls each of the other functions.
	 */
	public static void main(String[] args) {
		//Reads in from file.
		readIn();
		//Inserts WINNII after HAI.
		insertAfter("HAI\0","WINNII\0");
		//Inserts TOBIII before HAI.
		insertBefore("HAI\0","TOBIII\0");
		//Removes name after TOBIAS.
		System.out.println(removeAfter("TOBIAS�\0"));
		//Removes name before ELDEN.
		System.out.println(removeBefore("ELDEN\0"));
		//Inserts WINNII after HAI.
		insertAfter("CAROL\0","PETERPAN\0");

		//Writes to the file and prints out list.
		write();
	}
	
	/**
	 * This compares 2 strings. Returns 1 if string1 is alphabetically later than string2 else returns 0 if equal and -1 else.
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
	 * This inserts a string into the list after a given name.
	 */
	public static String intToString(int input) {

		//Stores the value being built up.
		String value = "";
		//Stores the multiplier used for % and * values;
		int multiplier = 10;
		//Stores the total value of % before.
		int totalLeftOver = 0;
		//Stores the current value left after %; 
		int currentLeftOver = 0;
		//Loops untill the int has been rebuilt.
		while (totalLeftOver != input) {
			//Isolate current digit
			currentLeftOver = input % multiplier;
			currentLeftOver -= totalLeftOver;
			//Get rid of following 0's.
			currentLeftOver = (currentLeftOver/(multiplier/10));
			//Add it to string.
			value = (char)(currentLeftOver+48) + value;
			//Build back up to form total left over.
			currentLeftOver *= (multiplier/10);
			totalLeftOver +=currentLeftOver;
			//Increase multiplier to go though int.
			multiplier *= 10;
		}
		return value;
	}
	
	/**
	 * This inserts a string into the list after a given name.
	 */
	public static int parseInt(String string) {
		int value = 0;
		int i = 0;
		string += '\0';
		//Loops through string.
		while (string.charAt(i)!='\0') {
			value *=10;
			//Add current char to int.
			value += (int)string.charAt(i)-48;
			i++;
		}
		return value;
	}
	
	/**
	 * This inserts a string into the list after a given name.
	 */
	public static void insertAfter(String afterString, String newString) {
		//Ints used to navigate the list.
		int previous = 0;
		int current = 0;
		int holder = 0;
		
		//Placeholders for swapping values around.
		int swapHolder1 = 0;
		int swapHolder2 = 0;
		int newAddress = 0;
		
		//This loops through all names. counter holds number of names currently being held.
		for(int p =0; p<counter+1;p++) {
			//When name is found.
			if (compare(arr[current][1],afterString) == 0) {
				//This looks if there are any freed addresses within the area array being used so to fill all the space.
				if (freeAddressesTop==0) {
					newAddress = counter+1;
				}else {
					newAddress = freedAddresses[freeAddressesTop-1];
					freedAddresses[freeAddressesTop-1] = null;
					freeAddressesTop--;
				}
				//Increments number of total names.
				counter++;
				
				//Holds the XOR of previous and new as this will need to be stored in current.
				swapHolder1 = (previous^newAddress);
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;

				//Stores XOR of previous and current as this will need to be stored in new.
				swapHolder2 = (previous^current);
				//As previous and current have been moved along, previous now takes the value stored earlier.
				arr[previous][0] = intToString(swapHolder1);
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;

				//Sets new value of previous's XOR.
				arr[previous][0] = intToString((newAddress^current));
				//Sets new's XOR that was stored earlier.
				arr[newAddress][0] =intToString(swapHolder2);
				//Sets new's name.
				arr[newAddress][1] = newString;
				break;

			}else {
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;
			}
		}
	}

	/**
	 * This inserts a string into the list before a given name.
	 */
	public static void insertBefore(String beforeString , String newString) {
		//Ints used to navigate the list.
		int previous = 0;
		int current = 0;
		int holder = 0;
		//Placeholders for swapping values around.
		int swapHolder1 = 0;
		int swapHolder2 = 0;
		
		int newAddress = 0;
		
		//This loops through all names. counter holds number of names currently being held.
		for(int p =0; p<counter+1;p++) {
			//When name is found.
			if (compare(arr[previous][1],beforeString) == 0) {
				//This looks if there are any freed addresses within the area array being used so to fill all the space.
				if (freeAddressesTop==0) {
					newAddress = counter+1;
				}else {
					newAddress = freedAddresses[freeAddressesTop-1];
					freedAddresses[freeAddressesTop-1] = null;
					freeAddressesTop--;
				}
				//Increments number of total names.
				counter++;

				//Holds the XOR of current and new as this will need to be stored in previous.
				swapHolder1 = (current^newAddress);
				//Moves current and previous back.
				holder = previous;
				previous = current^parseInt(arr[previous][0]);
				current = holder;

				//Holds the XOR of previous and current as this will need to be stored in new.
				swapHolder2 = (previous^current);
				//As previous and current have been moved back, current now takes the value stored earlier.
				arr[current][0] = intToString(swapHolder1);
				
				//Moves current and previous back.
				holder = previous;
				previous = current^parseInt(arr[previous][0]);
				current = holder;

				//Sets current's new XOR value.
				arr[current][0] = intToString((newAddress^previous));
				//Sets new's XOR that was stored earlier.
				arr[newAddress][0] =intToString(swapHolder2);
				//Sets new's name.
				arr[newAddress][1] = newString;
				break;

			}else {
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;
			}
		}
	}
	
	/**
	 * This removes a string from the list after a given name.
	 */
	public static String removeAfter(String afterString) {
		//Ints used to navigate the list.
		int previous = 0;
		int current = 0;
		int holder = 0;
		
		//Placeholders for swapping values around.
		int holder1 = 0;
		int holder2 = 0;
		int holder3 = 0;
		
		String outputString = "";
		//This loops through all names. counter holds number of names currently being held.
		for(int p =0; p<counter+1;p++) {
			//When name is found.
			if (compare(arr[previous][1],afterString) == 0) {
				//Sets the output string to the string being removed.
				outputString = arr[current][1];

				//Holder1 holds the address of name before the afterString so afterString's XOR can be changed.
				holder1 = current^parseInt(arr[previous][0]);
				
				//Holder2 holds the address of the name after the one being removed.
				holder2 = previous^parseInt(arr[current][0]);
				
				//Holder3 holds the address of the name after holder2 (2 after the one being removed) so XOR of Holder2 can be changed.
				holder3 = current^parseInt(arr[holder2][0]);
				
				//Changes previous's XOR to now not use the removed address.
				arr[previous][0] = intToString(holder1^holder2);
				
				//Changes holder2's XOR to now not use the removed address.
				arr[holder2][0] = intToString(previous^holder3);

				//Decrease number of total names.
				counter--;
				
				//Add removed address to free addresses.
				freedAddresses[freeAddressesTop] = current;
				freeAddressesTop++;
				
				return "SUCCESS REMOVED "+outputString;

			}else {
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;

			}
		}

	
		return "FAIL NOT FOUND";

	}
	
	/**
	 * This removes a string from the list before a given name.
	 */
	public static String removeBefore(String beforeString) {
		//Ints used to navigate the list.
		int previous = 0;
		int current = 0;
		int holder = 0;
		
		//Placeholders for swapping values around.
		int holder1 = 0;
		int holder2 = 0;
		int holder3 = 0;
		
		String outputString = "";
		//This loops through all names. counter holds number of names currently being held.
		for(int p =0; p<counter+1;p++) {
			//When name is found.
			if (compare(arr[current][1],beforeString) == 0) {
				//Sets the output string to the string being removed.
				outputString = arr[previous][1];
				
				//Holder3 holds the address of the name after beforeString so it's XOR value can be changed.
				holder3 = previous^parseInt(arr[current][0]);
				//Holder2 holds the address of the name before the one being removed.
				holder2 = current^parseInt(arr[previous][0]);
				//Holder1 holds the address of name 2 before the name being removed so the XOR of name before the one being removed can be changed.
				holder1 = previous^parseInt(arr[holder2][0]);
				
				//Changes the XOR value of beforeString to now not use the removed address.
				arr[current][0] = intToString(holder3^holder2);
				//Changes the XOR value of holder2 to now not use the removed address.
				arr[holder2][0] = intToString(current^holder1);

				//Decrease number of total names.
				counter--;
				
				//Add removed address to free addresses.
				freedAddresses[freeAddressesTop] = previous;
				freeAddressesTop++;
				
				return "SUCCESS REMOVED "+outputString;

			}else {
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;

			}
		}

		return "FAIL NOT FOUND";
	}
	
	/**
	 * Splits up a given string and adds each word to the list.
	 */
	private static void split(String line) {
		//charCounter increments through each char in the given string.
		int charCounter = 0;
		
		//Word holds the current word that is building.
		String word = "";
		
		//These ints help increment through the array storing appropriate XOR values to form the list.
		int prev = 0;
		int next = 1;
		
		//Loops untill an end of string indicator is found.
		while(line.charAt(charCounter) !='\0') {
			//If a '"' is not found it checks if it is a comma and adds it to the current word if it is not a comma.
			if (line.charAt(charCounter)!= '"') {
				if (line.charAt(charCounter)!= ',') {
					word +=line.charAt(charCounter);
				}else {
					//If a '"' is found it calculates the XOR value and adds that and the name to the current index.
					arr[counter][0] = intToString((prev^next));
					word +='\0';
					arr[counter][1] = word;
					
					//Resets the word.
					word = "";
					
					//Moves on the ints.
					prev = counter;
					next = counter+2;	
					
					//Moves to next index.
					counter++;
				}
			}
			charCounter++;
		}
		//Adds the final word to the list.
		arr[counter][1] = word;
		arr[counter][0] = intToString(prev-1^0);
	}

	/**
	 * Writes to the file.
	 */
	private static void write() {
		try {
			File fileWrite = new File("nameList.txt");

			//Creates a new file.
			fileWrite.delete();
			fileWrite.createNewFile();

			FileWriter fileWriter = new FileWriter(fileWrite);
			BufferedWriter fileOut = new BufferedWriter(fileWriter);

			//Ints used to navigate the list.
			int previous = 0;
			int current = 0;
			int holder = 0;

			for(int p =0; p<counter+1;p++) {
				//Writes to file and prints out current item.
				System.out.println(arr[current][1]);
				fileOut.append("\""+arr[current][1]+"\"");
				if (p !=counter) {
					fileOut.append(',');
				}
				//Moves previous and current along.
				holder = current;
				current = previous^parseInt(arr[current][0]);
				previous = holder;
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
