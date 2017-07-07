public class ReentrantLock {

	private boolean isLocked;
	private int counter = 0;
	private Thread lockedBy = null;
	
	

	public synchronized void lock() throws InterruptedException {
		while (isLocked && Thread.currentThread() != lockedBy) 
			wait();
		lockedBy = Thread.currentThread();
		isLocked = true;
		counter++;
		notifyAll();
	}

	public synchronized void unlock() throws InterruptedException {
		while (Thread.currentThread() != lockedBy)
			wait();
		counter--;
		if (counter == 0) {
			lockedBy = null;
			isLocked = false;
			notify();  // no need to wake up more than one thread
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ReentrantLock reentrantLock = new ReentrantLock();
		Runnable firstRunnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("START FIRST");
				try {
					reentrantLock.lock();
					reentrantLock.lock();
					Thread.sleep(3000);
					reentrantLock.unlock();
					reentrantLock.unlock();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("END FIRST");
			}
		};
		Runnable secondRunnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("START SECOND");
				try {
					Thread.sleep(1500);
					reentrantLock.lock();
					reentrantLock.lock();
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("END SECOND");
			}
		};
		Thread firstThread = new Thread(firstRunnable);
		Thread secondThread = new Thread(secondRunnable);
		firstThread.start();
		secondThread.start();
	}
	
}
