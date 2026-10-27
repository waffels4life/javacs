package com.javacs.network.info;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Utility class that prints information about all network interfaces
 * available on the machine, including their IP addresses (IPv4 and IPv6).
 *
 * <p>Concept recap:
 * <ul>
 *   <li>A <b>NetworkInterface</b> is a physical or virtual network card
 *       (e.g. Wi-Fi, Ethernet, loopback, Docker bridge, VirtualBox host).</li>
 *   <li>Each interface can expose <b>multiple IP addresses</b> — typically
 *       one IPv4 and one or more IPv6 addresses.</li>
 *   <li>Java supports both <b>IPv4</b> (e.g. {@code 192.168.1.10}) and
 *       <b>IPv6</b> (e.g. {@code fe80::1}) transparently.</li>
 * </ul>
 *
 * <p>Note: not every printed address is a "real" public IP:
 * <ul>
 *   <li>{@code 127.0.0.1} / {@code ::1}  → loopback (this machine only)</li>
 *   <li>{@code fe80::...}               → IPv6 link-local (local network only)</li>
 *   <li>{@code 169.254.x.x}             → APIPA (DHCP failed)</li>
 *   <li>{@code 192.168.x.x}, {@code 10.x.x.x}, {@code 172.16-31.x.x} → private LAN</li>
 * </ul>
 */
public final class NetworkInfo {

    // Private constructor: this is a pure utility class and should never be instantiated.
    private NetworkInfo() {}

    /*
     * Device
     *  └── NetworkInterface (network card)
     *       ├── Name:         wlan0
     *       ├── DisplayName:  Intel Wi-Fi
     *       ├── isUp:         true
     *       └── InetAddress[] (multiple IPs)
     *            ├── 192.168.1.10   → IPv4  (private / LAN)
     *            ├── fe80::abcd     → IPv6  (link-local)
     *            └── 2001:db8::1    → IPv6  (global)
     */
    public static void main(String[] args) {
        try {
            // Fetch ALL network interfaces of this machine (physical + virtual).
            // This may include: lo, wlan0/eth0, docker0, vEthernet, virbr0, etc.
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            // Iterate over every interface the OS exposes.
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                System.out.println("----------------------------------------");
                System.out.println("Name:         " + networkInterface.getName());         // e.g. wlan0, eth0, lo
                System.out.println("Display Name: " + networkInterface.getDisplayName()); // human-readable label
                System.out.println("Active:       " + networkInterface.isUp());           // is the interface enabled?

                // Each interface may carry ZERO or MORE IP addresses.
                Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();

                // If an interface is down or has no configured address, skip the address loop.
                if (!addressEnumeration.hasMoreElements()) {
                    System.out.println("Address -> (none)");
                    continue;
                }

                // Iterate over every IP bound to this interface.
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = addressEnumeration.nextElement();
                    String ip = inetAddress.getHostAddress();

                    // Detect the IP family:
                    // - An IPv6 address always contains ':' (e.g. fe80::1)
                    // - An IPv4 address never contains ':' (e.g. 192.168.1.10)
                    // Using `instanceof Inet6Address` is a more explicit alternative.
                    String ipFamily = (inetAddress instanceof Inet6Address)
                            ? "IPv6"
                            : "IPv4";

                    System.out.println("  Address -> " + ip
                            + " [" + ipFamily + "]"
                    );

                    /*
                     * ┌────────────────────┬──────────┬─────────────────────────────────────┐
                     * │ Address            │ Family   │ Scope / Meaning                     │
                     * ├────────────────────┼──────────┼─────────────────────────────────────┤
                     * │ 127.0.0.1          │ IPv4     │ Loopback — this machine only        │
                     * │ ::1                │ IPv6     │ Loopback — this machine only        │
                     * │ fe80::abcd         │ IPv6     │ Link-local — same subnet only       │
                     * │ 169.254.x.x        │ IPv4     │ APIPA — DHCP failed, not usable     │
                     * │ 192.168.x.x        │ IPv4     │ Private LAN                         │
                     * │ 10.x.x.x           │ IPv4     │ Private LAN                         │
                     * │ 172.16–31.x.x      │ IPv4     │ Private LAN                         │
                     * │ 2001:db8::1        │ IPv6     │ Global (public)                     │
                     * └────────────────────┴──────────┴─────────────────────────────────────┘
                     */
                }
            }
        }
        catch (SocketException e) {
            // SocketException here means the OS refused or failed to enumerate interfaces.
            // Wrapping it in RuntimeException keeps the method signature clean
            // while still surfacing the original cause for debugging.
            throw new RuntimeException(
                    "Failed to read network interfaces",
                    e
            );
        }
    }
}