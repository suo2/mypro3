package com.huawei.solarsafe;

import android.net.SSLCertificateSocketFactory;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jWX531163 on 2018/2/1.
 */

 class MySSLSocketFactory extends SSLCertificateSocketFactory {

    private static String protocols[] = null, cipherSuites[] = null;
    private SSLSocketFactory defaultFactory;

    static {
        SSLSocket socket = null;
        try {
            socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket();
            if (socket != null) {
                MySSLSocketFactory.protocols = new String[]{"TLSv1.1","TLSv1.2"};
                List<String> allowedCiphers = Arrays.asList(
                        // TLS 1.2
                        "TLS_RSA_WITH_AES_128_CBC_SHA",
                        "TLS_RSA_WITH_AES_128_CBC_SHA256",
                        "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
                        "TLS_ECHDE_RSA_WITH_AES_128_CBC_SHA",
                        "TLS_RSA_WITH_AES_256_CBC_SHA",
                        "TLS_RSA_WITH_AES_256_CBC_SHA256");
                List<String> availableCiphers = Arrays.asList(socket.getSupportedCipherSuites());
                HashSet<String> preferredCiphers = new HashSet<>(allowedCiphers);
                preferredCiphers.retainAll(availableCiphers);
                MySSLSocketFactory.cipherSuites = preferredCiphers.toArray(new String[preferredCiphers.size()]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e("MySSLSocketFactory", "static field : " + e.getMessage());
                }
            }
        }

    }

    MySSLSocketFactory(X509TrustManager tm)  {
        super(600000);
        SSLContext tls;
        try {
            tls = SSLContext.getInstance("TLS");
            tls.init(null,new TrustManager[]{tm},null);
            defaultFactory = tls.getSocketFactory();
        } catch (NoSuchAlgorithmException|KeyManagementException e) {
            e.printStackTrace();
        }
    }


    private void upgradeTLS(SSLSocket ssl) {
        if (protocols != null) {
            ssl.setEnabledProtocols(protocols);
        }
        if (cipherSuites != null) {
            ssl.setEnabledCipherSuites(cipherSuites);
        }
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return cipherSuites;
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return cipherSuites;
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        Socket ssl = defaultFactory.createSocket(s, host, port, autoClose);
        if (ssl instanceof SSLSocket)
            upgradeTLS((SSLSocket) ssl);
        return ssl;
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException{
        Socket ssl = defaultFactory.createSocket(host, port);
        if (ssl instanceof SSLSocket)
            upgradeTLS((SSLSocket) ssl);
        return ssl;
    }
}
