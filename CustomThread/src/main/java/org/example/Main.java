package org.example;


import com.amiseq.*;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        CustomThreadPool threadPool = new CustomThreadPool(5);
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            threadPool.execute(() -> {
                System.out.println("Task " + taskId + " started");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " completed");
            });
        }
    }
}