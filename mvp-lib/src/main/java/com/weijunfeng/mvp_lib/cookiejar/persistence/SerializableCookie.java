package com.weijunfeng.mvp_lib.cookiejar.persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

public class SerializableCookie implements Serializable {

    private static final long serialVersionUID = 647299727195918773L;
    private transient Cookie mCookie;

    public String encode(Cookie cookie) {
        mCookie = cookie;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return byteArrayToHexString(byteArrayOutputStream.toByteArray());
    }

    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            int v = aByte & 0xff;
            if (v < 16) {
                stringBuilder.append('0');
            }
            stringBuilder.append(Integer.toHexString(v));
        }
        return stringBuilder.toString();
    }

    public Cookie decode(String encodedCookie) {
        byte[] bytes = hexStringToByteArray(encodedCookie);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableCookie) objectInputStream.readObject()).mCookie;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cookie;
    }

    private byte[] hexStringToByteArray(String encodedCookie) {
        int len = encodedCookie.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i++) {
            data[i / 2] = (byte) (Character.digit(encodedCookie.charAt(i), 16) << 4 + Character.digit(encodedCookie.charAt(i + 1), 16));
        }
        return data;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeObject(mCookie.name());
        outputStream.writeObject(mCookie.value());
        outputStream.writeLong(mCookie.persistent() ? mCookie.expiresAt() : -1L);
        outputStream.writeObject(mCookie.domain());
        outputStream.writeObject(mCookie.path());
        outputStream.writeBoolean(mCookie.secure());
        outputStream.writeBoolean(mCookie.httpOnly());
        outputStream.writeBoolean(mCookie.hostOnly());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Cookie.Builder builder = new Cookie.Builder();
        builder.name((String) objectInputStream.readObject())
                .value((String) objectInputStream.readObject());
        long expiresAt = objectInputStream.readLong();
        if (expiresAt != -1L) {
            builder.expiresAt(expiresAt);
        }
        String domain = (String) objectInputStream.readObject();
        builder.domain(domain)
                .path((String) objectInputStream.readObject());
        if (objectInputStream.readBoolean()) {
            builder.secure();
        }
        if (objectInputStream.readBoolean()) {
            builder.httpOnly();
        }
        if (objectInputStream.readBoolean()) {
            builder.hostOnlyDomain(domain);
        }
        mCookie = builder.build();
    }
}
