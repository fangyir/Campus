package com.ssm.campus.service.impl;

import com.ssm.campus.dao.ShopDao;
import com.ssm.campus.dto.ShopExecution;
import com.ssm.campus.entity.Shop;
import com.ssm.campus.enum1.ShopStateEnum;
import com.ssm.campus.exception.ShopOperationException;
import com.ssm.campus.service.ShopService;
import com.ssm.campus.util.ImageUtil;
import com.ssm.campus.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;
    @Override
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) {
        //验证店铺是否为空
        if (shop == null) {
            return  new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺创建失败");
            }
            else {
                if (shopImg != null) {
                    //存储图片
                    try {
                        addShopImg(shop,shopImg);
                    } catch (Exception e) {
                        throw new RuntimeException("addShopImg error:" + e.getMessage());
                    }
                    //更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }
        }catch(Exception e) {
            throw new RuntimeException("addShop error:" + e.getMessage());
        }
        return null;
    }

    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        //获取shop图片目录的相对路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        //修改shop中的img地址
        shop.setShopImg(shopImgAddr);
    }

    //查询shop
    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    //修改shop
    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        //更新之前都会先进行查询，如果查询到的shop为空
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                //判断是否需要处理图片
                if (shopImgInputStream != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(tempShop,shopImgInputStream,fileName);
                }
                //更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum < 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            }catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    //分页查询店铺
    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopExecution = new ShopExecution();
        if (shopList != null) {
            shopExecution.setShopList(shopList);
            shopExecution.setCount(count);
        }else {
            shopExecution.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return shopExecution;
    }


}
