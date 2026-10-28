package com.javacs.network.Inetadress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class JInetAddress {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
        }
        catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }
}
