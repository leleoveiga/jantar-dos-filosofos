package jantarDosFilosofos;

public class MonitorMain {

	public static void main(String[] args) {
		int size = 5;
		DinnerTable dinnerTable = new DinnerTableMonitor(size);
		
		for (int i = 0; i < size; i ++) {
			new Philosopher(i, dinnerTable);
		}
	}

}
