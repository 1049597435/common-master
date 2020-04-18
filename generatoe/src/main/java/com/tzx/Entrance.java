package com.tzx;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: generator
 * @Package: PACKAGE_NAME
 * @ClassName: com.tzx.Entrance
 * @Description: 代码生成文件入口
 * @Author: 唐志翔
 * @Date: 2020/4/8 0008 10:07
 * @Version: 1.0
 * 注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的
 */
public class Entrance extends KeyLibrary{

    private static Map<String, String> propertiesJumpMap(String filePath) {
        File file = new File(filePath);
        if (file.exists() && file.length() > 0){
            Map<String, String> result = new HashMap<>();
            FileInputStream inputStream = null;
            BufferedReader reader = null;
            try {
                inputStream = new FileInputStream(file);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String str = null;
                while ((str = reader.readLine()) != null){
                    str = str.trim();
                    if(!"".equals(str) && !str.startsWith("#") && str.indexOf("=") != -1){
                        String[] ss = str.split("=");
                        result.put(ss[0],ss[1]);
                    }
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    reader.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static void isFolder(String path){
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static void modelFile(String path,Map<String, String> map){
        String pack = map.get(PACKAGE_MODEL);
        String packs = packageUtil(pack);
        String[] beans = map.get(BEAN).split(",");
        for (String bean : beans) {
            String itemPath = path + packs + "\\item";
            String searchPath = path + packs + "\\search";
            fileUtil(itemPath,itemPath + "\\"  + bean + "Item.java",contentPack(bean,pack,Templet.ITEM));
            fileUtil(searchPath,searchPath + "\\"  + bean + "Search.java",contentPack(bean,pack,Templet.SEARCH));
        }
    }

    private static void fileUtil(String folderName,String fileName,String content){
        isFolder(folderName);
        File file = new File(fileName);
        FileOutputStream outputStream = null;
        BufferedWriter  writer = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            writer = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            writer.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                writer.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void serviceFile(String path,Map<String, String> map){
        String pack = map.get(PACKAGE_SERVICE);
        String beanPack = map.get(PACKAGE_MODEL);
        String daoPack = map.get(PACKAGE_DAO);
        String packs = packageUtil(pack);
        String intefa = path + packs;
        String impl = path + packs + "\\impl";
        String[] beans = map.get(BEAN).split(",");
        for (String bean : beans) {
            String content = contentPack(bean,pack,Templet.INTEFACE);
            content =  content.replaceAll(MY_BEAN_PACK,beanPack);
            String implContent = contentPack(bean,pack,Templet.IMPL);
            implContent =  implContent.replaceAll(MY_BEAN_PACK,beanPack);
            implContent =  implContent.replaceAll(MY_DAO_PACK,daoPack);
            fileUtil(intefa,intefa + "\\"  + bean + "Service.java",content);
            fileUtil(impl,impl + "\\"  + bean + "ServiceImpl.java",implContent);
        }
    }

    private static void controllerFile(String path,Map<String, String> map){
        String[] beans = map.get(BEAN).split(",");
        String folderName = path + packageUtil(map.get(PACKAGE_CONTROLLER));
        for (String bean : beans) {
            String fileName = folderName + "\\" + bean + "Controller.java";
            String content = contentPack(bean,map.get(PACKAGE_CONTROLLER),Templet.CONTROLLER);
            content = content.replaceAll(MY_BEAN_PACK,map.get(PACKAGE_MODEL));
            content = content.replaceAll(MY_COMMONS_PACK,map.get(PACKAGE_COMMONS));
            content = content.replaceAll(MY_SERVICE_PACK,map.get(PACKAGE_SERVICE));
            fileUtil(folderName,fileName,content);
        }
    }

    private static String contentPack(String bean,String pack,String templet){
        templet = templet.replaceAll(DEMO_MAX,bean);
        templet = templet.replaceAll(DEMO_Min,beanJump(bean));
        return String.format(templet, pack,pack);
    }

    private static String beanJump(String s){
        String first = s.substring(0,1);
        return first.toLowerCase() + s.substring(1);
    }

    private static String packageUtil(String pack){
        return pack.replaceAll("\\.","/");
    }

    private static boolean isNull(String s){
        if(s == null) {
            return false;
        }
        if("".equals(s.trim())) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        //当前文件路径
        String current =  System.getProperty("user.dir");
        String path = current + "\\src\\";
        String propertiesPath = current + "\\config.properties";
        Map<String, String> map = propertiesJumpMap(propertiesPath);
        if(map == null){
            System.out.println("配置文件解析失败!");
            return;
        }
        if(!isNull(map.get(BEAN))){
            System.out.println("没有配置Bean的名字!");
            return;
        }
        if(!isNull(map.get(PACKAGE_MODEL))){
            System.out.println("没有配置MODEL的包路径! 例 > modelPackage=com.gzwl.pojo");
            return;
        }
        modelFile(path,map);
        if(!isNull(map.get(PACKAGE_DAO))){
            System.out.println("没有配置DAO的包路径,不能继续生成! 例 > daoPackage=com.gzwl.dao");
            return;
        }
        if(!isNull(map.get(PACKAGE_SERVICE))){
            System.out.println("没有配置SERVICE的包路径,不能继续生成! 例 > servicePackage=com.gzwl.service");
            return;
        }
        serviceFile(path,map);
        if(!isNull(map.get(PACKAGE_COMMONS))){
            System.out.println("没有配置COMMONS的包路径,不能继续生成! 例 > commonsPackage=com.gzwl.commons");
            return;
        }
        if(!isNull(map.get(PACKAGE_CONTROLLER))){
            System.out.println("没有配置CONTROLLER的包路径,不能继续生成! 例 > controllerPackage=com.gzwl.controller");
            return;
        }
        controllerFile(path,map);
        System.out.println("SUCCESS!");
    }
}
