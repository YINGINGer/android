package com.example.system2.model;

import java.io.Serializable;

//自定义一个类
//实现序列化接口
public class User implements Serializable {
    private String account;
    private String password;

    public User(){

    }
    public User(String account,String password) {
        this.account=account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
