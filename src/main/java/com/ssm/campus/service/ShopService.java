package com.ssm.campus.service;

import com.ssm.campus.dto.ShopExecution;
import com.ssm.campus.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;

public interface ShopService {
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg);

    //查询shop
    Shop getByShopId(long shopId);

    ShopExecution modifyShop(Shop shop, InputStream inputStream, String originalFilename);

    ShopExecution getShopList(Shop shopCondition, int i, int i1);
}
