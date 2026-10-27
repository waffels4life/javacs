package com.javacs.network.socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public final class ServerSide {

    public static final int PORT = 5000;

    public void server() {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket socket = serverSocket.accept();
             DataInputStream inputStream = new DataInputStream(
                     socket.getInputStream()
             )) {
            System.out.println("Server has started; waiting for client...");

            String line;
            while (!(line = inputStream.readUTF())
                    .equalsIgnoreCase("end"))
                System.out.println("Client: " + line);
        }
        catch (IOException e) {
            StackTraceElement[] stackTraceElement = e.getStackTrace();

            System.err.println("Error: " + e.getMessage());
            System.err.println("Trace: " + Arrays.toString(stackTraceElement));
        }
    }
}
