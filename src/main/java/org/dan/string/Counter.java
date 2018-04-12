package org.dan.string;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Counter {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(new Arena("contest:totalNum", "天龙八部"));
        executor.submit(new Arena("contest:totalNum", "射雕英雄传"));
        executor.submit(new Arena("contest:totalNum", "鹿鼎记"));
        executor.submit(new Announcer("contest:totalNum"));
    }
}
