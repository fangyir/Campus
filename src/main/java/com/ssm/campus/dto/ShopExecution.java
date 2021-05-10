package com.ssm.campus.dto;

import com.ssm.campus.entity.Shop;
import com.ssm.campus.enum1.ShopStateEnum;

import java.util.List;

public class ShopExecution {
    //结果状态
    private int state;
    //状态标识,解释结果状态
    private String stateInfo;
    //店铺数量
    private int count;
    //操作的店铺（增删改店铺时用到）
    private Shop shop;
    //Shop列表（查询店铺的首使用）
    private List<Shop> shopList;

    public ShopExecution() {
        super();
    }
    //店铺操作失败的时候使用的构造器，也就是有问题的时候，使用返回信息的
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    //店铺操作成功的时候使用的构造器，返回当个对象
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }
    //店铺操作成功的时候使用的构造器，查询多个对象时
    public ShopExecution(ShopStateEnum stateEnum,List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }
}
