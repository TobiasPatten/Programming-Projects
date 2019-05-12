
public class problem8 {
	
	/**
	 * Main method, calls calculate.
	 */
	public static void main(String[] args) {
		System.out.println(calculate(1001));
	}
	
	/**
	 * Uses a formula to calculate the sum of the diagonals.
	 */
	private static long calculate(long size) {	
		//Calcuates the distance from centre to edge and stores it in x.
		long x = 0;
		long answer = 0;
		x = (size-1)/2;
		//Formula that is derived by seeing the uniform increase in the rate of increase.
		//The rate of which it increases is 32 more each ring is added. Increases in triangular numbers.
		//The base increase rate of increase is 20. Increases in sum of all previous numbers.
		//The base rate of increase starts at 24. Increases linearly.
		//This does not account for the 1 in the middle so +1 is necessary. 
		answer = 1+ x*24 + ((x*(x-1))/2)*20 + (((x-1)*(x)*(x+1))/6)*32;
		return answer;		
	}
}
