package com.imm.model.protoc;

/**
 * Created by Misnearzhang on 2017/3/28.
 */
public class HandShakeMessage {

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public HandShakeMessage setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public HandShakeMessage setPassword(String password) {
        this.password = password;
        return this;
    }
}
