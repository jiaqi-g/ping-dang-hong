package game;


public class Test {
	public static void main(String[] args) {
		increase();
		decrease();
		
	}

	private static void increase() {
		for (int i = 0; i < 100; i++) {
			if (i % 10 == 0) System.out.println();
			
			float x = 0;
			if (i<5) {
				x=(float) 7.7;
			}
			else if (i<10) {
				x=(float) 7.5;
			}
			else if (i<15) {
				x=(float) 7.3;
			}
			else if (i<20) {
				x=(float) 6.7;
			}
			else if (i<25) {
				x=(float) 6.4;
			}
			else if (i<30) {
				x=(float) 6.1;
			}
			else if (i<35) {
				x=(float) 5.5;
			}
			else if (i<40) {
				x=(float) 4.9;
			}
			else if (i<45) {
				x=(float) 4.4;
			}
			else if (i<50) {
				x=(float) 3.9;
			}
			else if (i<55) {
				x=(float) 3.3;
			}
			else if (i<60) {
				x=(float) 3.1;
			}
			else if (i<65) {
				x=(float) 2.9;
			}
			else if (i<70) {
				x=(float) 2.3;
			}
			else if (i<75) {
				x=(float) 1.9;
			}
			else if (i<80) {
				x=(float) 1.4;
			}
			else if (i<85) {
				x=(float) 1.2;
			}
			else if (i<90) {
				x=(float) 0.8;
			}
			else if (i<95) {
				x=(float) 0.4;
			}
			else if (i<100) {
				x=(float) 0.2;
			}

			System.out.print(x+",");
		}
	}

	private static void decrease() {
		for (int i = 0; i < 100; i++) {
			if (i % 10 == 0) System.out.println();
			
			float x = 0;
			if (i > 94) {
				x=(float) 7.7;
			}
			else if (i > 89) {
				x=(float) 7.5;
			}
			else if (i > 84) {
				x=(float) 7.3;
			}
			else if (i > 79) {
				x=(float) 6.7;
			}
			else if (i > 74) {
				x=(float) 6.4;
			}
			else if (i > 69) {
				x=(float) 6.1;
			}
			else if (i > 64) {
				x=(float) 5.5;
			}
			else if (i > 59) {
				x=(float) 4.9;
			}
			else if (i > 54) {
				x=(float) 4.4;
			}
			else if (i > 49) {
				x=(float) 3.9;
			}
			else if (i > 44) {
				x=(float) 3.3;
			}
			else if (i > 39) {
				x=(float) 3.1;
			}
			else if (i > 34) {
				x=(float) 2.9;
			}
			else if (i > 29) {
				x=(float) 2.3;
			}
			else if (i > 24) {
				x=(float) 1.9;
			}
			else if (i > 19) {
				x=(float) 1.4;
			}
			else if (i > 14) {
				x=(float) 1.2;
			}
			else if (i > 9) {
				x=(float) 0.8;
			}
			else if (i > 4) {
				x=(float) 0.4;
			}
			else if (i > -1) {
				x=(float) 0.2;
			}

			System.out.print(x+",");
		}
	}
}
