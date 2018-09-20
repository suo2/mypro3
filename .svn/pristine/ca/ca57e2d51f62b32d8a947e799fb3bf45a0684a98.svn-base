package com.huawei.solarsafe;

import com.huawei.solarsafe.bean.GlobalConstants;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by jWX531163 on 2018/2/1.
 */

class MyHttpsUtils {


    private static  MyHttpsUtils instance ;
    private  static SSLParams  sslParams ;


    public static  MyHttpsUtils getInstance(){
        if (instance==null){
            instance = new MyHttpsUtils();
        }
        return instance;
    }

    private MyHttpsUtils(){
        sslParams = new SSLParams();
    }

    static class SSLParams
    {
         MySSLSocketFactory sSLSocketFactory;
         X509TrustManager trustManager;
    }

    SSLParams getSslSocketFactory()
    {
        try {
            X509TrustManager trustManager = new MyTrustManager();
            sslParams.sSLSocketFactory = new MySSLSocketFactory(trustManager);
            sslParams.trustManager = trustManager;
            return sslParams;
        } catch (NoSuchAlgorithmException|KeyStoreException e) {
            throw new AssertionError(e);
        }
    }



    private  X509TrustManager chooseTrustManager(TrustManager[] trustManagers)
    {
        for (TrustManager trustManager : trustManagers)
        {
            if (trustManager instanceof X509TrustManager)
            {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }




    private  class MyTrustManager implements X509TrustManager
    {
        private X509TrustManager defaultTrustManager;

         MyTrustManager()throws NoSuchAlgorithmException, KeyStoreException
        {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) { }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            if (defaultTrustManager!=null&&GlobalConstants.isNeedCheck){
                defaultTrustManager.checkServerTrusted(chain, authType);
            }
        }
        @Override
        public X509Certificate[] getAcceptedIssuers()
        {
            return new X509Certificate[0];
        }
    }
}
