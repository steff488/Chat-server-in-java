
public class NewThread extends Thread {
    private static boolean running;
    public void run() {
        running = true;
        int i = 0;
        while (running) {
            System.out.println(this.getName() + ": New Thread is running..." + i++);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stop_thread(){
        System.out.println("The client 69 has disconnected.");
        running = false;
    }
}

