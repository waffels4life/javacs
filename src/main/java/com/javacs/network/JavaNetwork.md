# Java Networking — Introduction & Core Protocols

This section covers the foundational concepts of Java networking:
what it is, which package provides it, and the two core transport
protocols (TCP and UDP) that virtually every network application
is built on.

---

## 1. Introduction

**Java Networking** is the ability of two or more computing devices
to communicate and exchange data over a network. Java provides this
capability mainly through the `java.net` package.

Key points:

- **Protocol support**: Java networking APIs support communication
  using protocols such as **TCP** and **UDP**.
- **Resource APIs**: The package provides APIs for working with
  **sockets**, **IP addresses**, **URLs**, and other network
  resources.


---

## 2. Common Network Protocols

Java networking revolves around two dominant transport protocols:
**TCP** and **UDP**. They differ fundamentally in reliability,
ordering, and speed.

### 2.1. TCP (Transmission Control Protocol)

TCP is a **connection-oriented** protocol that guarantees data is
delivered **accurately** and **in the correct order**. A connection
is established before any data is exchanged, and every transmission
is acknowledged.

Characteristics:

- **Reliable, error-checked** data transmission.
- **Ordered delivery**, maintained through acknowledgements (ACKs).
- **Connection setup required** before communication (three-way handshake).

Typical use cases:

- Web applications (HTTP/HTTPS)
- File transfer (FTP, SFTP)
- Email (SMTP, IMAP, POP3)

### 2.2. UDP (User Datagram Protocol)

UDP is a **connectionless** protocol designed for **fast**
transmission **without delivery guarantees**. Each datagram is sent
independently, making it suitable for real-time communication where
occasional loss is acceptable.

Characteristics:

- **Faster** than TCP, but **unreliable** (best-effort delivery).
- **No connection setup** and **no acknowledgements**.
- **No ordering guarantee** — packets may arrive out of order.

Typical use cases:

- Streaming (audio/video)
- Online gaming
- Video conferencing
- DNS lookups


---

### Summary Table

| Aspect        | TCP                          | UDP                          |
|---------------|------------------------------|------------------------------|
| Connection    | Required (handshake)         | None                         |
| Reliability   | Guaranteed (ACK + retransmit)| Best-effort (no retransmit)  |
| Ordering      | Preserved                    | Not preserved                |
| Speed         | Slower (overhead)            | Faster (minimal overhead)    |
| Header size   | Larger (20+ bytes)           | Smaller (8 bytes)            |
| Use cases     | Web, File Transfer, Email    | Streaming, Gaming, VoIP      |

---

## 4. Takeaway

- Java provides networking through the **`java.net`** package.
- **TCP** is the right choice when **reliability and order matter**
  more than raw speed (web, files, email).
- **UDP** is the right choice when **speed and low latency matter**
  more than guaranteed delivery (streaming, games, conferencing).
- Both protocols are the foundation for everything else in Java
  networking — sockets, servers, and higher-level APIs like the
  modern `java.net.http` client.