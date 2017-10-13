package com.weijunfeng.mvp_lib.converter;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

class FastJsonRequestBodyConverter<T> implements Converter<T, ResponseBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=UTF-8");

    @Override
    public ResponseBody convert(T value) throws IOException {
        return ResponseBody.create(MEDIA_TYPE, JSON.toJSONBytes(value));
    }
}
