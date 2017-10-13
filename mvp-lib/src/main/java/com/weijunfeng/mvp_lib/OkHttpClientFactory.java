package com.weijunfeng.mvp_lib;

import android.content.Context;
import android.support.annotation.RawRes;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class OkHttpClientFactory {
    private static final Map<String, Integer> CERTIFICATES = new ArrayMap<>();

    public static void addSSLCertificate(String alias, @RawRes int rawRes) {
        if (!TextUtils.isEmpty(alias)) {
            CERTIFICATES.put(alias, rawRes);
        }
    }

    public static OkHttpClient create(Context context, int timeout, Interceptor netWorkInterceptor, List<Interceptor> interceptors, boolean isSafe, CookieJar cookieJar) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        if (netWorkInterceptor != null) {
            builder.addNetworkInterceptor(netWorkInterceptor);
        }
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS);
        if (cookieJar != null) {
            builder.cookieJar(cookieJar);
        }
        SSLSocketFactory sslSocketFactory;
        if (isSafe) {
            sslSocketFactory = getSafeSslSocketFactory(context);
        } else {
            sslSocketFactory = getUnSafeSslSocketFactory(context);
        }
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(sslSocketFactory).hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }
        return builder.build();
    }

    private static SSLSocketFactory getUnSafeSslSocketFactory(Context context) {
        TrustManager[] trustManagers = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagers, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static SSLSocketFactory getSafeSslSocketFactory(Context context) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            for (String s : CERTIFICATES.keySet()) {
                InputStream inputStream = context.getResources().openRawResource(CERTIFICATES.get(s));
                keyStore.setCertificateEntry(s, certificateFactory.generateCertificate(inputStream));
                if (inputStream != null) {
                    inputStream.close();
                }
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(keyStore);
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
                return sslContext.getSocketFactory();
            }
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
}
