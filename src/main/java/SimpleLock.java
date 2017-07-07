public class SimpleLock {

	private boolean isLocked;

	public synchronized void lock() throws InterruptedException {
		while (isLocked)
			wait();
		isLocked = true;
		notifyAll();
	}

	public synchronized void unlock() throws InterruptedException {
		while (!isLocked)
			wait();
		isLocked = false;
		notify();  // no need to wake up more than oneg thread
	}

	public static void main(String[] args) throws InterruptedException {
		SimpleLock simpleLock = new SimpleLock();
		Runnable firstRunnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("START FIRST");
				try {
					simpleLock.lock();
					simpleLock.lock();
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
					Thread.sleep(2000);
					simpleLock.unlock();
					Thread.sleep(2000);
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
