package com.weijunfeng.invest.model;

/**
 * Created by weijunfeng on 2017/9/30.
 * Email : weijunfeng@myhexin.com
 */

public class InvestEntry {
    private InvestType mType;
    private String name;
    private String code;

    public enum InvestType {
        FUND;
    }
}
