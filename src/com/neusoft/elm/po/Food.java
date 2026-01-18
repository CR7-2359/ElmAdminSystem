package com.neusoft.elm.po;

import java.math.BigDecimal;

/* 食品实体 */
public class Food {
    private int id; // 食品编号
    private int businessId; // 所属商家编号
    private String name; // 食品名称
    private BigDecimal price; // 食品价格
    private String description; // 食品描述
    private int status; // 食品状态

    /* 获取编号 */
    public int getId() {
        return id;
    }

    /* 设置编号 */
    public void setId(int id) {
        this.id = id;
    }

    /* 获取商家编号 */
    public int getBusinessId() {
        return businessId;
    }

    /* 设置商家编号 */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /* 获取名称 */
    public String getName() {
        return name;
    }

    /* 设置名称 */
    public void setName(String name) {
        this.name = name;
    }

    /* 获取价格 */
    public BigDecimal getPrice() {
        return price;
    }

    /* 设置价格 */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /* 获取描述 */
    public String getDescription() {
        return description;
    }

    /* 设置描述 */
    public void setDescription(String description) {
        this.description = description;
    }

    /* 获取状态 */
    public int getStatus() {
        return status;
    }

    /* 设置状态 */
    public void setStatus(int status) {
        this.status = status;
    }
}
