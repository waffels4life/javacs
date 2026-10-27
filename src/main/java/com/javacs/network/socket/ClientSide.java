package com.javacs.network.socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class ClientSide {
    public void client() {
        try (Socket socket = new Socket("127.0.0.1", 5000);
             DataOutputStream outputStream = new DataOutputStream(
                     socket.getOutputStream()
             );
             BufferedReader bufferedReader = new BufferedReader(
                     new InputStreamReader(System.in)
             )) {
            System.out.println("Connected to server");
            String line;
            while (!(line = bufferedReader.readLine())
                    .equalsIgnoreCase("end"))
                outputStream.writeUTF(line);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
