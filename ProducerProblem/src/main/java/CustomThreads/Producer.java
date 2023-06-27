package CustomThreads;

import CustomThreadPool.*;

public class Producer extends Thread {
    private final String name;
    private final int initialNumber;
    private final int delay;
    private final CustomThreadPool threadPool;

    public Producer(String name, int initialNumber, int delay, CustomThreadPool threadPool) {
        this.name = name;
        this.initialNumber = initialNumber;
        this.delay = delay;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        int currentNumber = initialNumber;

        while (true) {
            int finalCurrentNumber = currentNumber;
            threadPool.execute(() -> {
                System.out.println(name + " generated: " + finalCurrentNumber);
                Printer.addToPrintQueue(name + ": " + finalCurrentNumber);
            });

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            currentNumber += initialNumber;
        }
    }
}
