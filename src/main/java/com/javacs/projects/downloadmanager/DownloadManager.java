package com.javacs.projects.downloadmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadManager {
    private final List<DownloadTask> downloadTaskList = new ArrayList<>();

    public DownloadManager() {
        downloadTaskList.add(new DownloadTask("photo.png"));
        downloadTaskList.add(new DownloadTask("video.mp4"));
        downloadTaskList.add(new DownloadTask("music.mp3"));
    }

    public void runnable() {
        try (ExecutorService executorService =
                     Executors.newFixedThreadPool(downloadTaskList.size())) {
            List<Future<?>> futures = new ArrayList<>();

            for (DownloadTask task : downloadTaskList) {
                futures.add(executorService.submit(task::download));
            }

            for (Future<?> future : futures) {
                future.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Download manager was interrupted", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("A download task failed", e.getCause());
        }
    }
}
