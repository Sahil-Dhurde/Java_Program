class Practical20 extends Thread {
    private int start, end;

    public Practical20(int start, int end)
     {
        this.start = start;
        this.end = end;
    }

    public void run()
     {
        System.out.println("Primes from " + start + " to " + end + ":");
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++)
            if (num % i == 0)
                return false;
        return true;
    }

    public static void main(String[] args)
     {
        Practical20 t1 = new Practical20(1, 1000);
        Practical20 t2 = new Practical20(1001, 2000);
        Practical20 t3 = new Practical20(2001, 3000);

        t1.start();
        t2.start();
        t3.start();
    }
}
