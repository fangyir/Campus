package com.ssm.campus.service.impl;

import com.ssm.campus.dao.ShopCategoryDao;
import com.ssm.campus.entity.ShopCategory;
import com.ssm.campus.mapper.ShopCategoryMapper;
import com.ssm.campus.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategory) {
        List<ShopCategory> list = shopCategoryDao.queryShopCategory(shopCategory);
        return list;
    }
}
