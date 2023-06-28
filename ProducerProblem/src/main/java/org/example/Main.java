package org.example;


import com.amiseq.CustomThreadPool;
import com.amiseq.producerProblem.*;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CustomThreadPool threadPool = new CustomThreadPool(5);
        Random random = new Random();

        Producer producer1 = new Producer("Producer 1", 3, random.nextInt(1000) + 1000, threadPool);
        Producer producer2 = new Producer("Producer 2", 5, random.nextInt(1000) + 1000, threadPool);
        Printer printer = new Printer(random.nextInt(1000) + 1000);

        producer1.start();
        producer2.start();
        printer.start();
    }
}