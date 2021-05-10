package com.ssm.campus.mapper;

import com.ssm.campus.entity.Shop;

/**
 * 注册店铺
 */
public interface ShopMapper {
    int insertShop(Shop shop);
    int updateShop(Shop shop);
}
