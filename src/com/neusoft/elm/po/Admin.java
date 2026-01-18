package com.neusoft.elm.po;

/* 管理员实体 */
public class Admin {
    private int id; // 管理员编号
    private String name; // 管理员名称
    private String password; // 管理员密码

    /* 获取编号 */
    public int getId() {
        return id;
    }

    /* 设置编号 */
    public void setId(int id) {
        this.id = id;
    }

    /* 获取名称 */
    public String getName() {
        return name;
    }

    /* 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /* 获取密码 */
    public String getPassword() {
        return password;
    }

    /* 设置密码 */
    public void setPassword(String password) {
        this.password = password;
    }
}
