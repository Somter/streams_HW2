package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();
        String randomString = main.generateRandomString(10);
        System.out.println("Random string: " + randomString);
    }


    static class RandomCharTask implements Callable<Character> {
        @Override
        public Character call() {
            Random random = new Random();
            return (char) ('a' + random.nextInt(26));
        }
    }

    public String generateRandomString(int length) {
        ExecutorService threadPool = Executors.newFixedThreadPool(6);
        List<Future<Character>> tasks = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            tasks.add(threadPool.submit(new RandomCharTask()));
        }

        StringBuilder sb = new StringBuilder();

        for (Future<Character> future : tasks) {
            try {
                sb.append(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        threadPool.shutdown();
        return sb.toString();
    }
}
