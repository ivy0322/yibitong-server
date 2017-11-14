package com.yibitong.utils.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;


/**
 * 包      名：  com.newzqxq.utils.oss
 * 创 建 人：   寻欢 · 李
 * 创建时间：  2017/7/15 14:44
 * 修 改 人：
 * 修改日期：
 *
 * 阿里云对象存储OSS工具类
 */
public class AliyunOSSClientUtils {

    protected static Logger logger = LoggerFactory.getLogger(AliyunOSSClientUtils.class);

    public static final String URL_PREFIX="http://finances.oss-cn-beijing.aliyuncs.com/";  // 外网域名：阿里云默认
//    public static final String URL_PREFIX="http://oss.newzqxq.com/";  // 外网域名：自定义
    private static final String endpoint = "http://oss-cn-beijing.aliyuncs.com";//内网域名：http://oss-cn-shanghai-internal.aliyuncs.com
    private static final String accessKeyId = "LTAIMQz9fLWQJU0d";
    private static final String accessKeySecret = "4EdByiToiAWgojWElSseP3ijGHvDnk";
    private static final String bucketName = "finances";

    /**
     * 获取OSSClient对象
     * @return
     */
    public static OSSClient getOSSClient() {
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        if (ossClient.doesBucketExist(bucketName)) {
            logger.info("您已经创建Bucket：" + bucketName + "。");
        } else {
            logger.info("您的Bucket不存在，创建Bucket：" + bucketName + "。");
            ossClient.createBucket(bucketName);
        }
        BucketInfo bucketInfo = ossClient.getBucketInfo(bucketName);
        logger.info("Bucket " + bucketName + "的信息如下：");
        logger.info("\t数据中心：" + bucketInfo.getBucket().getLocation());
        logger.info("\t创建时间：" + bucketInfo.getBucket().getCreationDate());
        logger.info("\t用户标志：" + bucketInfo.getBucket().getOwner());
        return ossClient;
    }

    /**
     * 上传文件
     * @param file
     * @return
     */
    public static String putOSSObject(File file) {
        OSSClient ossClient = getOSSClient();
        String fileKey = UUID.randomUUID().toString()+".jpg";
        ObjectMetadata om = new ObjectMetadata();
        om.setContentType("image/jpeg");
        ossClient.putObject(bucketName, fileKey, file,om);
        logger.info("Object：" + file.getName() + "存入OSS成功,fileKey:" + fileKey);
        return fileKey;
    }


    /**
     * 上传文件（springMVC）
     * @param multipartFile
     * @return
     */
    public static String putOSSObject(MultipartFile multipartFile) {
        OSSClient ossClient = getOSSClient();
        String fileKey = UUID.randomUUID().toString()+".jpg";
        try {
            ObjectMetadata om = new ObjectMetadata();
            om.setContentType("image/jpeg");
            om.setContentType(multipartFile.getContentType());
            ossClient.putObject(bucketName, fileKey, multipartFile.getInputStream(), om);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Object：" + multipartFile.getName() + "存入OSS成功,fileKey:" + fileKey);
        return fileKey;
    }

    /**
     * 上传字符串对象
     * @param str
     * @return
     */
    public static String putOSSObject(String str) {
        OSSClient ossClient = getOSSClient();
        InputStream is = new ByteArrayInputStream(str.getBytes());
        String strKey = UUID.randomUUID().toString();
        ossClient.putObject(bucketName, strKey, is);
        logger.info("Object：" + str + "存入OSS成功,strKey:" + strKey);
        return strKey;
    }

    /**
     * 根据key获取OSS文件对象，并存放到指定路径
     * @param fileKey
     * @param path 存放路径
     * @param postfix 文件后缀名
     * @return
     */
    public static File getOSSObjectFile(String fileKey, String path, String postfix) {
        OSSClient ossClient = getOSSClient();
        OSSObject ossObject = ossClient.getObject(bucketName, fileKey);
        File file = null;
        try {
            InputStream in = ossObject.getObjectContent();
            file = new File(path, fileKey + postfix);
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = readInputStream(in);
            FileOutputStream out = new FileOutputStream(file);
            out.write(data);//写入数据
            out.close();//关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 输入流转换为字节数组
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream in) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=in.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            out.write(buffer, 0, len);
        }
        //关闭输入流
        in.close();
        //把outStream里的数据写入内存
        return out.toByteArray();
    }

    /**
     * 根据key删除OSS对象
     * @param key
     */
    public static void deleteOSSObject(String key) {
        OSSClient ossClient = getOSSClient();
        ossClient.deleteObject(bucketName, key);
        logger.info("删除Object：" + key + "成功。");
    }

    /**
     * 列出所有OSS对象
     * @return
     */
    public static List<OSSObjectSummary> listOSSObject() {
        OSSClient ossClient = getOSSClient();
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        List<OSSObjectSummary> objectSummaryList = objectListing.getObjectSummaries();
        return objectSummaryList;
    }

    public static void main(String[] args) {
        String fileKey = putOSSObject(new File("D://1.jpg"));
        logger.info("文件url：" + URL_PREFIX + fileKey);
//          File file = getOSSObjectFile("baac47e9-b443-437b-8fda-ae0094qweasd", "D://", ".jpg");
//        deleteOSSObject("a3b0d4b0-4153-46d2-8dbd-4e9475463752");
    }
}
