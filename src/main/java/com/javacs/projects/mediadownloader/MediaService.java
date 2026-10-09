package com.javacs.projects.mediadownloader;

import java.util.List;

public class MediaService {
    public static void main(String[] args) {
        List<String> files = List.of(
                "photo.png",
                "video.mp4",
                "music.mp3",
                "doc.pdf",
                "game.zip"
        );
        MediaDownloader mediaDownloader = new MediaDownloader(files);
        mediaDownloader.start();
    }
}
