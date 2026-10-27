package com.javacs.network.info;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class NetworkInfo {

    private NetworkInfo() {}

    public static void main(String[] args) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                System.out.println("----------------------------------------");
                System.out.println("Name:         " + networkInterface.getName());
                System.out.println("Display Name: " + networkInterface.getDisplayName());
                System.out.println("Active:       " + networkInterface.isUp());

                Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();

                if (!addressEnumeration.hasMoreElements()) {
                    System.out.println("Address -> (none)");
                    continue;
                }

                while (addressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = addressEnumeration.nextElement();
                    String ip = inetAddress.getHostAddress();
                    String ipFamily = (inetAddress instanceof Inet6Address)
                            ? "IPv6"
                            : "IPv4";

                    System.out.println("  Address -> " + ip
                            + " [" + ipFamily + "]"
                    );
                }
            }
        }
        catch (SocketException e) {
            throw new RuntimeException(
                    "Failed to read network interfaces",
                    e
            );
        }
    }
}