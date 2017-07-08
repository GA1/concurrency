
public class HelloWorld {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                System.out.println("Hello World");
            }
        });
        t1.run();
        t2.run();

    }

}