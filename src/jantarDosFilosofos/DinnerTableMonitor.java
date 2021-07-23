package jantarDosFilosofos;

public class DinnerTableMonitor implements DinnerTable {
	
	private int size;
	private PhilosopherState[] table;
	private Integer[] philosophers;
	
	public DinnerTableMonitor(int size) {
		this.size = size;
		this.table = new PhilosopherState[size];
		this.philosophers = new Integer[size];
		for (int i = 0; i < size; i ++) {
			this.table[i] = PhilosopherState.THINKING;
			this.philosophers[i] = 0;
		}
	}

	@Override
	public void takeForks(int philosopher) {
		synchronized (philosophers[philosopher]) {
			table[philosopher] = PhilosopherState.HUNGRY;
			tryToEat(philosopher);
			
			while (table[philosopher] != PhilosopherState.EATING) {
				try {
					this.philosophers[philosopher].wait();
				} catch (InterruptedException e) {
                   e.printStackTrace();
                }
			}
		}
	}
	
	@Override
	public void putForks(int philosopher) {
		synchronized (philosophers[philosopher]) {
			table[philosopher] = PhilosopherState.THINKING;
			tryToEat(getLeft(philosopher));
			tryToEat(getRight(philosopher));
		}
	}
	
	private void tryToEat(int philosopher) {
		if (table[philosopher] == PhilosopherState.HUNGRY 
				&& table[getLeft(philosopher)] != PhilosopherState.EATING 
				&& table[getRight(philosopher)] != PhilosopherState.EATING) {
			table[philosopher] = PhilosopherState.EATING;
			System.out.println(String.format("Philosopher %d picked up the forks.", philosopher));
			 synchronized (philosophers[philosopher]) {
				 philosophers[philosopher].notify();
	         }
		}
	}

	private int getLeft (int i) {
		return (i + size - 1) % size;
	}
	
	private int getRight (int i) {
		return (i + 1) % size;
	}
	
}
