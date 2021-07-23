package jantarDosFilosofos;

public class Philosopher implements Runnable {
	
	private int id;
	private DinnerTable dinnerTable;
	
	Philosopher (int id, DinnerTable dinnerTable) {
		this.id = id;
		this.dinnerTable = dinnerTable;
        new Thread(this, "Philosopher " + id).start();
	}

	@Override
	public void run() {
		while (true) {
			think();
			takeForks();
			eat();
			putForks();
		}
		
	}

	private void think() {
		sleep(100);
	}

	private void takeForks() {
		dinnerTable.takeForks(id);
	}
	
	private void eat() {
		System.out.println(String.format("Philosopher %d is eating.", id));
		sleep(200);
	}
	
	private void putForks() {
		System.out.println(String.format("Philosopher %d released the forks.", id));
		dinnerTable.putForks(id);
	}
	
	private void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
