package com.ssm.campus.util;

import java.io.File;

public class PathUtil {
    private static String seperator = System.getProperty("file.separator");
    //返回项目图片的根路径
    public static String getImgBasePath(){
        //获取操作系统名称
        String os = System.getProperty("os.name");
        String basePath = "";
        //如果是windows系统
        if (os.toLowerCase().startsWith("win")){
            basePath = "E:/file/java/pic/";
        }
        else {
            basePath = "/home/java/image/";
        }
        //为了是符号/符号各个操作系统，蒋/替换成操作系统指定的分隔符
        basePath = basePath.replace("/",seperator);
        return basePath;
    }
    //返回项目图片的子路径
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop" + shopId + "/";
        return imagePath.replace("/",seperator);
    }
}
