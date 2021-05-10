package com.ssm.campus.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * author fangyr
 * date 2018/12/14 14:00
 */
public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//时间格式化的格式
    private static final Random random = new Random();
    //处理缩略图
    public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            Thumbnails.of(thumbnail.getInputStream()).size(200,200).outputQuality(0.25f).toFile(dest);
        } catch (IOException e) {
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        return relativeAddr;
    }

    //创建目标路径所涉及到的目录，比如com/ssm/campus
    //让其自动创建com、ssm、campus三个文件夹
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    //获取输入文件流的扩展名
    private static String getFileExtension(CommonsMultipartFile cFile) {
        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    //生成随机文件名
    private static String getRandomFileName() {
        //生成随机文件名：当前年月日时分秒+五位随机数（为了在实际项目中防止文件同名而进行的处理）
        int rannum = (int) ((random.nextDouble() * (99999 - 10000 + 1)) + 10000);//获取随机数
        String nowTimeStr = sDateFormat.format(new Date());//当前时间
        return nowTimeStr + rannum;
    }

    public static void main(String[] args) throws IOException {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        Thumbnails.of(new File("E:\\file\\pic\\java\\6.jpg")).size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "\\123.png")),0.25f).outputQuality(0.8).toFile("E:\\file\\pic\\java\\123.jpg");
    }

    /*
     * 	如果storePath是文件路径则删除该文件
     * 	如果storePath是目录路径则删除该目录下所有文件
     */
    public static void deleteFileOrPath(String storePath) {
        File file = new File(PathUtil.getImgBasePath() + storePath);
        if (file.exists()) {
            if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            file.delete();
        }
    }

}
