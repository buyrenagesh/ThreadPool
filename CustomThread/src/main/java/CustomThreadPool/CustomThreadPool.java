package CustomThreadPool;

import java.util.LinkedList;
import java.util.List;

/**
 * Custom Thread Pool without using Java TP library
 */
public class CustomThreadPool
{
    //region Fields
    /**
     * workerThreads field
     */
    private final List<WorkerThread> workerThreads;

    /**
     * task Queue field
     */
    public final LinkedList<Runnable> taskQueue;
    //endregion

    //region Constructor
    /**
     * Constructor
     * @param maxThreads
     */
    public CustomThreadPool(int maxThreads) {
        workerThreads = new LinkedList<>();
        taskQueue = new LinkedList<>();

        for (int i = 0; i < maxThreads; i++) {
            WorkerThread workerThread = new WorkerThread(this);
            workerThreads.add(workerThread);
            workerThread.start();
        }
    }
    //endregion

    //region Public Method
    /**
     * execute Method
     * @param task
     */
    public void execute(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.addLast(task);
            taskQueue.notify(); // Notify a waiting thread that a task is available
        }
    }
    //endregion

}
