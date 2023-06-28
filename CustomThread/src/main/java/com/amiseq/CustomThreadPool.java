package com.amiseq;

import java.util.LinkedList;
import java.util.List;

/**
 * Custom Thread Pool without using Java TP library
 */
public class CustomThreadPool
{
    /**
     * workerThreads field
     */
    private final List<WorkerThread> workerThreads;

    /**
     * task Queue field
     */
    public final LinkedList<Runnable> taskQueue;

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

    /**
     * Worker Thread class extending Thread class
    */
    private class WorkerThread extends Thread {

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
}
