package com.javacs.network.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/*
 * AccessHttp
 * ----------
 * A minimal, modern Java example that performs an HTTP GET request
 * to https://oracle.com/ and prints the resulting status code and
 * final URI (after any redirects).
 *
 * Flow overview:
 *
 *   +---------------------+     +----------------------+     +----------------------+
 *   |  Build HttpClient   | --> |   Build HttpRequest  | --> |  send() and receive  |
 *   | (follow redirects)  |     | (URI, timeout, GET)  |     |  HttpResponse<String>|
 *   +---------------------+     +----------------------+     +----------------------+
 *                                                                       |
 *                                                                       v
 *                                                        +-----------------------------+
 *                                                        |  Print status + final URI   |
 *                                                        +-----------------------------+
 *
 * Notes:
 *  - InterruptedException is handled separately so the interrupt flag is
 *    restored only when it actually makes sense.
 */
public final class AccessHttp {

    public static void main(String[] args) {

        // try-with-resources:
        //   Ensures httpClient.close() is called automatically when the
        //   block exits — whether normally or because of an exception.
        try (HttpClient httpClient = HttpClient.newBuilder()
                // NORMAL: follow 301/302/303/307/308, but NOT 305 and NOT
                // HTTPS -> HTTP redirects. Without this, a request to
                // https://oracle.com/ typically returns 301 and stops.
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()) {

            // Build an immutable HTTP request.
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    // Target URI. Must start with http:// or https://.
                    // URI.create() throws IllegalArgumentException on bad syntax.
                    .uri(URI.create("https://oracle.com/"))
                    // Overall timeout (connect + send + receive the response).
                    // On expiry, HttpTimeoutException (a subtype of IOException)
                    // is thrown.
                    .timeout(Duration.ofSeconds(10))
                    // HTTP method. GET is also the default if omitted.
                    .GET()
                    // Finalize the request (HttpRequest is immutable).
                    .build();

            // send(...) is blocking: it waits until a response arrives.
            //   - returns HttpResponse on success
            //   - throws IOException            on network/protocol failure
            //   - throws InterruptedException   if the thread is interrupted
            //
            // BodyHandlers.ofString() tells Java how to consume the body:
            //   ofString()   -> String        (used here)
            //   ofByteArray()-> byte[]
            //   ofFile(Path) -> writes to file
            //   discarding() -> ignores body (status/headers only)
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString()
            );

            // Response summary.
            System.out.println("Status Code: " + httpResponse.statusCode());
            System.out.println("Final URI:   " + httpResponse.uri());

            // InterruptedException must be caught separately: restore the
            // interrupt flag so callers higher up the stack can react to it.
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);

            // IOException: network error, timeout, connection reset, etc.
            // No interrupt flag handling needed here.
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}