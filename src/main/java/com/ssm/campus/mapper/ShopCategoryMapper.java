package com.ssm.campus.mapper;

import com.ssm.campus.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryMapper {
    List<ShopCategory> listShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);
}
