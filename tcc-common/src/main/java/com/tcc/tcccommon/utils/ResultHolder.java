package com.tcc.tcccommon.utils;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class ResultHolder {
    private final RedisUtil redisUtil;

    public void setResult(Class<?> actionClass, String xid, String v) {
        String key = actionClass.getName();
        redisUtil.set(key + ":" + xid, v);
    }

    public String getResult(Class<?> actionClass, String xid) {
        String key = actionClass.getName() + ":" + xid;
        return redisUtil.get(key);
    }

    public void removeResult(Class<?> actionClass, String xid) {
        String key = actionClass.getName() + ":" + xid;
        redisUtil.remove(key);
    }

}
