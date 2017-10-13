package com.weijunfeng.mvp_lib.converter;

/**
 * Created by weijunfeng on 2017/10/13.
 * Email : weijunfeng@myhexin.com
 */

interface HttpResult {
    void setErrorInfo(String errorInfo);

    CharSequence getErrorCode();

    void setErrorCode(String errorCode);
}
