package com.javacs.projects.mediadownloader;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MediaDownloader {

    private final String[] fileNames;
    private final List<?> listOfFiles;
    private final CountDownLatch countDownLatch;
    private final Semaphore semaphore = new Semaphore(3);

    public MediaDownloader(@NotNull List<?> listOfFiles) {

        if (listOfFiles.isEmpty())
            throw new RuntimeException("Empty");

        for (Object o : listOfFiles)
            if (o == null)
                throw new RuntimeException("ContainNull");

        fileNames = new String[listOfFiles.size()];
        countDownLatch = new CountDownLatch(fileNames.length);
        this.listOfFiles = listOfFiles;
    }

    public void start() {
        int setUpIndex = 0;
        for (Object file : listOfFiles) {
            fileNames[setUpIndex] = file.toString();
            setUpIndex++;
        }
        calculateDistribution(fileNames);
    }

    public void calculateDistribution(String[] list) {
        /*
         * int size = fileNames.length;
         * int forEachThread = (int) Math.ceil((double)size / 3);
         */
        for (int i = 0; i < list.length; i++) {
            switch (i % 3) {
                case 0 -> downloadPipeline1(i);
                case 1 -> downloadPipeline2(i);
                case 2 -> downloadPipeline3(i);
            }
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void downloadPipeline1(int index) {
        downloadFile(index);
    }
    public void downloadPipeline2(int index) {
        downloadFile(index);
    }
    public void downloadPipeline3(int index) {
        downloadFile(index);
    }

    public void downloadFile(int index) {
        Thread downloadPipeline = new Thread(() ->  {
            AtomicInteger downloadPercentage = new AtomicInteger(0);
            AtomicBoolean isComplete = new AtomicBoolean(false);
            try {
                semaphore.acquire();
                try {
                    while (!isComplete.get()) {
                        System.out.println(fileNames[index]
                                + ": %"
                                + downloadPercentage.getAndIncrement()
                        );
                        Thread.sleep(150);
                        if (downloadPercentage.get() >= 100)
                            isComplete.set(true);
                    }
                    System.out.println(fileNames[index] + " is Done");
                    countDownLatch.countDown();
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    semaphore.release();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        downloadPipeline.start();
    }
}
