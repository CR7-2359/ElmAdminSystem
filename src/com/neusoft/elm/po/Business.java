package com.neusoft.elm.po;

/* 商家实体 */
public class Business {
    private int id; // 商家编号
    private String account; // 登录账号
    private String password; // 登录密码
    private String name; // 商家名称
    private String phone; // 联系电话
    private String address; // 商家地址
    private String description; // 商家描述

    /* 获取编号 */
    public int getId() {
        return id;
    }

    /* 设置编号 */
    public void setId(int id) {
        this.id = id;
    }

    /* 获取账号 */
    public String getAccount() {
        return account;
    }

    /* 设置账号 */
    public void setAccount(String account) {
        this.account = account;
    }

    /* 获取密码 */
    public String getPassword() {
        return password;
    }

    /* 设置密码 */
    public void setPassword(String password) {
        this.password = password;
    }

    /* 获取名称 */
    public String getName() {
        return name;
    }

    /* 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /* 获取电话 */
    public String getPhone() {
        return phone;
    }

    /* 设置电话 */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /* 获取地址 */
    public String getAddress() {
        return address;
    }

    /* 设置地址 */
    public void setAddress(String address) {
        this.address = address;
    }

    /* 获取描述 */
    public String getDescription() {
        return description;
    }

    /* 设置描述 */
    public void setDescription(String description) {
        this.description = description;
    }
}
