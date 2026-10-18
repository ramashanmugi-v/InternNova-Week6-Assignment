// Multithreading: saves data in background every few seconds
public class AutoSaveThread extends Thread {
    private final BankService service;
    private final String path;
    private volatile boolean running = true;

    public AutoSaveThread(BankService service, String path) {
        this.service = service;
        this.path = path;
        setDaemon(true);   // stops automatically when program exits
    }

    public void stopSaving() {
        running = false;
        interrupt();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(10000);   // every 10 seconds
            } catch (InterruptedException e) {
                break;
            }
            FileHandler.save(service.getAll(), path);
        }
    }
}
