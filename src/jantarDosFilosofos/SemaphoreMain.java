package jantarDosFilosofos;

public class SemaphoreMain {

	public static void main(String[] args) {
		int size = 5;
		DinnerTable dinnerTable = new DinnerTableSemaphore(size);
		
		for (int i = 0; i < size; i ++) {
			new Philosopher(i, dinnerTable);
		}

	}

}
