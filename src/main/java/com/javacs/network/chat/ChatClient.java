package com.javacs.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public final class ChatClient {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream(),
                             StandardCharsets.UTF_8)
             );
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(
                             socket.getOutputStream(),
                             StandardCharsets.UTF_8),
                     true
             );
             BufferedReader console = new BufferedReader(
                     new InputStreamReader(System.in))
        ) {

            Thread reader = Thread.ofVirtual().start(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null)
                        System.out.println(line);
                }
                catch (IOException e) {
                    System.err.println("Connection closed: "
                            + e.getMessage()
                    );
                }
            });

            String line;
            while ((line = console.readLine()) != null)
                out.println(line);

            reader.join();
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
