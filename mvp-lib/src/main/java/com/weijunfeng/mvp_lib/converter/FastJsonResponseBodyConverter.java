package com.weijunfeng.mvp_lib.converter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Type type;

    public FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource bufferedSource = Okio.buffer(value.source());
        String utf8 = bufferedSource.readUtf8();
        bufferedSource.close();
        try {
            return JSON.parseObject(utf8, type);
        } catch (Exception e) {
            JSONObject jsonObject = JSON.parseObject(utf8);
            Class<?> rawType = getRawType(type);
            if (rawType != null && HttpResult.class.equals(rawType.getSuperclass())) {
                HttpResult result = null;
                try {
                    result = parseHttpResult(jsonObject, (Class<? extends HttpResult>) rawType);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                if(result != null && !TextUtils.isEmpty(result.getErrorCode())){
                    return (T) result;
                }
            }
            throw e;
        }
    }

    private HttpResult parseHttpResult(JSONObject jsonObject, Class<? extends HttpResult> rawType) {
        HttpResult result = null;
        try {
            result = rawType.newInstance();
            result.setErrorCode(getFieldValue(jsonObject, "errorCode", rawType));
            result.setErrorInfo(getFieldValue(jsonObject, "errorInfo", rawType));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFieldValue(JSONObject jsonObject, String fieldName, Class<? extends HttpResult> rawType) throws NoSuchFieldException {
        Field field = rawType.getDeclaredField(fieldName);
        JSONField jsonField = field.getAnnotation(JSONField.class);
        String name = field.getName();
        if (jsonField != null) {
            name = jsonField.name();
        }
        return jsonObject.get(name).toString();
    }

    private Class<?> getRawType(Type type) {
        if (type == null) {
            return null;
        }
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
                return (Class<?>) rawType;
            }
            throw new IllegalArgumentException();
        }
        if (type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            Type genericComponentType = genericArrayType.getGenericComponentType();
            return Array.newInstance(getRawType(genericArrayType), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            return getRawType(wildcardType.getUpperBounds()[0]);
        }
        return null;
    }
}
