public class Progress {
	
	public void printToPre(int num) {
		for(int i = 0; i < num; i++) {
			System.out.print("\b");
		}
	}
	
	public void printToNext(int num) {
		for(int i = 0; i < num; i++) {
			System.out.print(">");
		}
	}
	
	public void print() {
		String progress = "Progress:___%[----------]";
		System.out.print(progress);
		int i = 0; 
		int j = 0;
		printToPre(11);
		while (i < 101) {
			if (i < 10) {
				printToPre(3);
				System.out.print(i + "%[");
				printToNext(j);
				printToPre(j);
			} else if (i >= 10 && i <= 99) {
				printToPre(4);
				System.out.print(i + "%[");
				printToNext(j);
				printToPre(j);
			} else {
				printToPre(5);
				System.out.print(i + "%[");
				printToNext(j);
				printToPre(j);
			}
			i++;
			if (i % 10 == 0) {
				j++;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new Progress().print();
		//System.out.print("ssss");
		//System.out.print("\b\b");
	}
	
}
