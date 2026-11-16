package com.javacs.projects.Omail;

import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class SmtpClient {

    private final String host;
    private final int port;

    public SmtpClient(String host) {
        this(host, 465);
    }

    public SmtpClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void createSMTP() throws IOException {
        /*
         * factory for creating secure sockets
         *
         * SocketFactory        <- parent (general type)
         *    ^
         * SSLSocketFactory     <- child (our exact type)
         */
        SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        /*
         * using the factory to create a secure socket, then implement the socket with data.
         * s: domain
         * i: port
         */
        try (SSLSocket socket = (SSLSocket) sslSocketFactory.createSocket(host, port)) {
            SSLParameters sslParameters = socket.getSSLParameters();
            sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
            socket.setSSLParameters(sslParameters);

            socket.startHandshake();

            /*
             * Input/Output buffers
             */
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8)
            );
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8)
            );

            readReply(bufferedReader);
        }
    }

    private @NotNull String readReply(@NotNull BufferedReader bufferedReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println("S: " + line);
            stringBuilder.append(line).append("\n");

            if (line.length() < 4 || line.charAt(3) == ' ')
                break;
        }

        return stringBuilder.toString();
    }
}
