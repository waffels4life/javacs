package com.javacs.network.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public final class AccessHttp {
    public static void main(String[] args) {
        try (HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()) {

            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://oracle.com/"))
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            System.out.println("Status Code: " + httpResponse.statusCode());
            System.out.println("Final URI: " + httpResponse.uri());
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}