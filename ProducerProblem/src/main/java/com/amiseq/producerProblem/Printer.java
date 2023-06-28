package com.amiseq.producerProblem;

import java.util.LinkedList;


public class Printer extends Thread {

    private static final LinkedList<String> printQueue = new LinkedList<>();
    private final int printWindow;

    public Printer(int printWindow) {
        this.printWindow = printWindow;
    }

    public static synchronized void addToPrintQueue(String message) {
        printQueue.addFirst(message);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (printQueue) {
                if (!printQueue.isEmpty()) {
                    System.out.println("Printing: " + printQueue.removeFirst());
                }
            }

            try {
                Thread.sleep(printWindow);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}