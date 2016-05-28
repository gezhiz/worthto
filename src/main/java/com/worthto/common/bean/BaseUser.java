package com.worthto.common.bean;

/**
 * Created by wenjie on 16/4/5.
 */
public class BaseUser extends BaseBean implements IUser {
    private String user;
    private String passwd;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getUser() {
        return user;
    }
}
