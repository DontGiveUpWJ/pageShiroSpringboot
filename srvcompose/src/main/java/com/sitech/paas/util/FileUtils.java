package com.sitech.paas.util;

import java.io.*;

import org.springframework.core.io.ClassPathResource;

/**
 * Created by 72707 on 2018/9/6.
 */
public class FileUtils {


    /**
     * 把一个字符串写到指定文件中
     * @param str  要写入文件中的字符串内容
     * @param path 文件夹路径
     * @param fileName 文件名称
     */
    public static void writeStringToFile(String str,String path,String fileName) throws IOException {
        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.mkdirs();
        }
        File file = new File(path+fileName);
        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file,true);
        fw.write(str);
        fw.flush();
        fw.close();
    }

    /**
     * 获得指定文件的byte数组
     */
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 从文件中加载内容到内存中
     * @param  path 路径
     * @return
     * @throws IOException
     */
    public static String readerFile(String path) throws IOException{
        //File file = ResourceUtils.getFile(path);
        //InputStream resourceAsStream = FileUtils.class.getClassLoader().getResourceAsStream(path);
        InputStream inputStream =  new ClassPathResource(path).getInputStream();
        //InputStream inputStream = new FileInputStream(file);
        Reader reader = new InputStreamReader(inputStream, "utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        reader.close();
        String jsonString = sb.toString();
        return jsonString;
    }

    /**
     * 创建文件目录
     * @param path 文件夹路径
     * @param fileName 文件夹下的文件
     * @return 返回文件
     * @throws IOException
     */
    public static File createFile(String path,String fileName) throws IOException {
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();//创建目录
        }
        File file = new File(path, fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 把字符串写进文件中
     * @param path 文件路径
     * @param fileName 文件名字
     * @param nJson 内容
     * @throws IOException
     */
    public static void writer(String path,String fileName,String nJson) throws IOException {
        File file = FileUtils.createFile(path,fileName);
        FileOutputStream out = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(out);
        BufferedWriter buf = new BufferedWriter(writer);
        buf.write(nJson,0,nJson.length());
        buf.close();
        writer.close();
        out.close();
    }
    public static void main(String arg[]){
        File dir = new File("aaa/bbb");
        dir.mkdir();
    }
}
