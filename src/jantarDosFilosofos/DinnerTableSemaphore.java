package jantarDosFilosofos;

import java.util.concurrent.Semaphore;

public class DinnerTableSemaphore implements DinnerTable {
	
	private int size;
	private PhilosopherState[] table;
	private Semaphore[] semaphores;
	private Semaphore mutex;
	
	
	public DinnerTableSemaphore(int size) {
		this.size = size;
		this.semaphores = new Semaphore[size];
		this.table = new PhilosopherState[size];
		this.mutex = new Semaphore(1);
		for (int i = 0; i < size; i ++) {
			this.semaphores[i] = new Semaphore(0);
			this.table[i] = PhilosopherState.THINKING;
		}
	}
	
	@Override
	public void takeForks(int philosopher) {
		try {
			mutex.acquire();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		this.table[philosopher] = PhilosopherState.HUNGRY;
		tryToEat(philosopher);
		this.mutex.release();
		try {
			semaphores[philosopher].acquire();
			System.out.println(String.format("Philosopher %d picked up the forks.", philosopher));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void putForks(int philosopher) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		table[philosopher] = PhilosopherState.THINKING;
		tryToEat(getLeft(philosopher));
		tryToEat(getRight(philosopher));
		
		mutex.release();
	}
	
	private void tryToEat(int philosopher) {
		if (table[philosopher] == PhilosopherState.HUNGRY 
				&& table[getLeft(philosopher)] != PhilosopherState.EATING 
				&& table[getRight(philosopher)] != PhilosopherState.EATING) {
			table[philosopher] = PhilosopherState.EATING;
			semaphores[philosopher].release();
		}
	}
	
	private int getLeft (int i) {
		return (i + size - 1) % size;
	}
	
	private int getRight (int i) {
		return (i + 1) % size;
	}
}
