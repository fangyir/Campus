package com.ssm.campus.dao;

import com.ssm.campus.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    //通过id查询店铺
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺，可输入条件有（店铺名，店铺状态，店铺类别，区域id，owner）
     * @param shopCondition
     * @param rowIndex 从第几行开始
     * @param pageSize 返回的数据条数
     *
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
