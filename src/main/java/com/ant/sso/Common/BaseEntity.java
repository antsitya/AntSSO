package com.ant.sso.Common;

public class BaseEntity<T> {
    public T notNull(){
        if(this==null) throw new AntException(AntResponseCode.EMPTY_EXCEPTION);
        return (T) this;
    }
}
