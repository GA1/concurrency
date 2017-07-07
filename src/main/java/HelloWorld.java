
public class HelloWorld {

    public static void main(String[] args) {
        final Object lock = new Object();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        });
        t1.run();

    }

}