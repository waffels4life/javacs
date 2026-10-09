package com.javacs.projects.downloadmanager;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadTask {
    private final String fileName;
    private volatile boolean downloadComplete;
    private final AtomicInteger downloadPercentage = new AtomicInteger();

    public DownloadTask(String fileName) {
        this.fileName = Objects.requireNonNull(fileName, "fileName");

        if (fileName.isBlank()) {
            throw new IllegalArgumentException("fileName must not be blank");
        }
    }

    public void download() {
        Thread downloadThread = new Thread(() -> {
            while (!downloadComplete) {
                int percentage = downloadPercentage.getAndIncrement();
                if (percentage > 100) {
                    downloadComplete = true;
                    break;
                }

                System.out.println(fileName + ": %" + percentage);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (downloadComplete) {
                System.out.println(fileName + ": [Download Completed]");
            }
        }, "download-" + fileName);

        downloadThread.start();
        try {
            downloadThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for " + fileName, e);
        }
    }
}
