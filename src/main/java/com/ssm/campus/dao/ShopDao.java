package com.ssm.campus.dao;

import com.ssm.campus.entity.Shop;

/**
 * author fangyr
 * date 2018/12/14 12:30
 */
public interface ShopDao {
    /**
     * 新增店铺
     * @param shop
     * @return
     *  */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     *  */
    int updateShop(Shop shop);
}
