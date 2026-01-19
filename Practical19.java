class MyThread extends Thread {
    String name;

    MyThread(String name) {
        this.name = name;
    }

    public void run() {
        System.out.println(name + " started with priority: " + getPriority());
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + " - Count: " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class Practical19 {
    public static void main(String[] args) {
        MyThread high = new MyThread("High Priority Thread");
        MyThread low = new MyThread("Low Priority Thread");

        high.setPriority(Thread.MAX_PRIORITY);
        low.setPriority(Thread.MIN_PRIORITY);

        high.start();
        low.start();

        try {
            high.join();
            low.join();
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        System.out.println("Main thread done.");
    }
}
