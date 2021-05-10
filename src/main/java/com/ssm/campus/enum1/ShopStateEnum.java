package com.ssm.campus.enum1;

public enum ShopStateEnum {
    CHECK(0, "审核中"),
    OFFLINE(1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "通过认证"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOPID(-1002, "shopId为空"),
    NULL_SHOP(-1003,"shop信息为空");
    //结果状态
    private int state;
    //状态标识，解释结果状态
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    //外部查找state的枚举类型
    //根据传入的state返回相应的enum值
    public static  ShopStateEnum stateOf(int state) {
        //values()方法，是上面所有的说明（审核中、非法店铺、操作成功...）
        for (ShopStateEnum stateEnum:values()) {
            //值所对应的状态，是通过getState()获得的
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
