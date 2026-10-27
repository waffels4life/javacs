package com.javacs.network.Inetadress;

import java.net.InetAddress;
import java.net.UnknownHostException;

public final class JInetAddress {
    public void address() {
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException ex) {
            throw new RuntimeException(ex);
        }
    }
}
