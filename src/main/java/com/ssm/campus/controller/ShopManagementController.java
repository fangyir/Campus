package com.ssm.campus.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssm.campus.dto.ShopExecution;
import com.ssm.campus.entity.Area;
import com.ssm.campus.entity.PersonInfo;
import com.ssm.campus.entity.Shop;
import com.ssm.campus.entity.ShopCategory;
import com.ssm.campus.enum1.ShopStateEnum;
import com.ssm.campus.service.AreaService;
import com.ssm.campus.service.ShopCategoryService;
import com.ssm.campus.service.ShopService;
import com.ssm.campus.util.CodeUtil;
import com.ssm.campus.util.HttpServletRequestUtil;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopAdmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @RequestMapping(value = "/registerShop",method = {RequestMethod.POST})
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request) throws IOException {
        Map<String,Object> modelMap = new HashMap<>();
        //1.接收并转换相应的参数
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        }catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        //获取前端传递的文件流（图片）
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断上传的request是否含有图片，是就将图片赋值给shopImg
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest httpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) httpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            //UserId是session传过来的
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution shopExecution = shopService.addShop(shop,shopImg,getInputStream(),shopImg.getOriginalFilename());
            if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", shopExecution.getStateInfo());
            }
        }
        else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
        //3.返回结果
        return modelMap;

    }

    private Map<String,Object> getShopInitInfo() {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            //获取全部Category对象，所以传入参数为一个空的shopCategory对象
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            //获取全部Area对象
            areaList = areaService.getAreaList();
            modelMap.put("success",true);
            modelMap.put("areaList",areaList);
            modelMap.put("shopCategoryList",shopCategoryList);
        }catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    //更改店铺
    @RequestMapping(value = "/registerShop",method = {RequestMethod.POST})
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        //判断验证码是否正确
        System.out.println(CodeUtil.checkVerifyCode(request));
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码错误");
            return modelMap;
        }
        //1.接收并转换相应的参数
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            //这是将字符串shopStr转换成Shop.Class类对的JSON数据格式
            shop = mapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
            return modelMap;
        }
        //火球前端传递的文件流（图片）
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断上传的request是否含有图片，是就将图片赋值给shopImg
        if (commonsMultipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest httpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) httpServletRequest.getFile("shopImg");
        }else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }
        //2.修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution;
            try {
                if (shopImg == null) {
                    shopExecution = shopService.modifyShop(shop,null,null);
                }else {
                    shopExecution = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                    if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                        modelMap.put("success",true);
                    }else {
                        modelMap.put("success",false);
                        modelMap.put("essMsg",shopExecution.getStateInfo());
                    }
                }
            }catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("essMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("essMsg","请输入店铺信息");
            return modelMap;
        }
        //3.返回结果
        return modelMap;
    }

    //根据用户信息查询用户创建的店铺
    @RequestMapping(value = "/getShopList",method = {RequestMethod.POST})
    @ResponseBody
    private Map<String,Object> getShopList(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        //采用session来模拟用户登录
        request.getSession().setAttribute("user",user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        System.out.println(user);
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService.getShopList(shopCondition,0,100);
            modelMap.put("shopList",shopExecution.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg",e.getMessage());
        }
        return modelMap;
    }

    //店铺管理，根据输入的信息做跳转
    @RequestMapping(value = "/getShopManagementInfo", method = {RequestMethod.POST})
    private Map<String,Object> getShopManageMentInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //如果前台没有传过来shopId
        if (shopId < 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            //检查session中是否含有shopId
            if (currentShopObj == null) {
                modelMap.put("redirect", true);
                modelMap.put("url", "/campus/shop/shopList");
            }else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }
        }
        //前台传过来了shopId
        else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;
    }
}
