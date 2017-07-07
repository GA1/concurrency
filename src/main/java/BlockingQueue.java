import java.util.LinkedList;
import java.util.List;


public class BlockingQueue {

	private int maxSize;
	private List<Integer> items = new LinkedList<>();
	
	public BlockingQueue(int maxSize) { 
		this.maxSize = maxSize;
	}
	
	public synchronized void enqueue(int i) throws InterruptedException { 
		while (items.size() == maxSize)
			wait();
		items.add(i);
		notifyAll();
	}
	
	public int size() { 
		return items.size();
	}
	
	public synchronized int dequeue() throws InterruptedException {
		while (items.size() == 0)
			wait();
		int result = items.remove(0);
		notifyAll();
		return result;
	}
	
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue bq = new BlockingQueue(2);
		Runnable firstRunnable = new Runnable() {
			public void run() {
				System.out.println("START FIRST");
				try {
					bq.enqueue(1);
					bq.enqueue(2);
					bq.enqueue(3);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("END FIRST");
			}
		};
		Runnable secondRunnable = new Runnable() {
			public void run() {
				System.out.println("START SECOND");
				try {
					Thread.sleep(2000);
					bq.dequeue();
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
		firstThread.join();
		secondThread.join();
		System.out.println(bq.size());
	}
}
