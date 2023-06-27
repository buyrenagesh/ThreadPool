package CustomThreadPool;

/**
 * Worker Thread class extending Thread class
 */
public class WorkerThread extends Thread {

    private final CustomThreadPool customThreadPool;

    public WorkerThread(CustomThreadPool customThreadPool) {
        this.customThreadPool = customThreadPool;
    }

    @Override
    public void run() {
        while (true) {
            Runnable task;
            synchronized (customThreadPool.taskQueue) {
                while (customThreadPool.taskQueue.isEmpty()) {
                    try {
                        customThreadPool.taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                task = customThreadPool.taskQueue.removeFirst();
            }

            try {
                task.run();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
