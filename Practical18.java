public class Practical18 extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 200; i++) 
        {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println(i);
                try
                 {
                    Thread.sleep(1000);  // 1 second delay
                } 
                catch (InterruptedException e)
                 {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Practical18 t1 = new Practical18();
        t1.start(); 
    }
}

