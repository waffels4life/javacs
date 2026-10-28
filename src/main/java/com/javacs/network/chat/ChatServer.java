package com.javacs.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.locks.ReentrantLock;

public final class ChatServer {

    private static final int PORT = 8080;
    private static final Set<PrintWriter> clients = ConcurrentHashMap.newKeySet();
    private static final ReentrantLock clientsLock = new ReentrantLock();

    @SuppressWarnings("all")
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()
        ) {
            System.out.println("Chat server listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    executorService.submit(() -> handleClient(socket));
                }
                catch (RejectedExecutionException e) {
                    socket.close();
                    throw e;
                }
            }
        }
    }

    private static void handleClient(Socket socket) {
        Objects.requireNonNull(socket, "socket");

        String clientName = socket.getRemoteSocketAddress().toString();
        System.out.println("Connected: " + clientName);

        PrintWriter printWriter = null;

        try (socket;
             BufferedReader bufferedReader = new BufferedReader(
                     new InputStreamReader(
                             socket.getInputStream(),
                             StandardCharsets.UTF_8
                     ))) {

            printWriter = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(),
                            StandardCharsets.UTF_8),
                    true
            );

            clients.add(printWriter);
            broadcast("*** " + clientName + " joined ***");

            String line;
            while ((line = bufferedReader.readLine()) != null)
                broadcast("[" + clientName + "] " + line);

        }
        catch (IOException e) {
            System.err.println("Client error ("
                    + clientName
                    + "): "
                    + e.getMessage()
            );
        }
        finally {
            if (printWriter != null) {
                clients.remove(printWriter);
                printWriter.close();
            }
            broadcast("*** " + clientName + " left ***");
            System.out.println("Disconnected: " + clientName);
        }
    }

    private static void broadcast(String message) {
        clientsLock.lock();
        try {
            for (PrintWriter pw : clients) {
                pw.println(message);
            }
        }
        finally {
            clientsLock.unlock();
        }
    }
}
