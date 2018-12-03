package com.sitech.paas.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version v1.0
 * @类描述：
 * @项目名称：composer-admin
 * @包名： com.sitech.util
 * @类名称：ProcessUtils
 * @创建人：guoqq_paas
 * @创建时间：2018/11/6 16:18
 * @修改人：guoqq_paas
 * @修改时间：2018/11/6 16:18
 * @修改备注：
 * @bug
 * @Copyright
 * @mail
 * @see
 */
public class ProcessUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProcessUtils.class);

    public static void pm2Start(String appName,String userPath,String redPath) throws InterruptedException {

        String cmd = "pm2 start " + redPath + " --name " + appName + " -- -userDir " + userPath;

        List<String> result = executeLinuxCmd(cmd);

        if (result != null && result.size() > 0) {
            for (String line:result) {
                if (line.indexOf(appName) > 0 && line.indexOf("online") > 0) {
                    logger.info(appName + "已启动");
                    return;
                }
            }
        }
        logger.info("启动失败");
    }


    /**
     *  通过pm2，查询正在运行的用户实例列表
     *
     * @return node-red的应用名列表
     * @throws InterruptedException
     */
    public static List<String> pm2List() throws InterruptedException {

        List<String> result = executeLinuxCmd("pm2 list");
        String pattern = "(\\S+-\\d+)";
        Pattern p = Pattern.compile(pattern);

        List<String> list = new ArrayList<>();
        if (result != null && result.size() > 0) {
            for (String line:result) {
                if (line.indexOf("online") > 0) {
                    Matcher matcher = p.matcher(line);
                    if (matcher.find()) {
                        list.add(matcher.group(1));
                    }
                }
            }
        }
        return list;
    }

    public static boolean pm2List(String appName) throws InterruptedException {

        List<String> result = executeLinuxCmd("pm2 list");

        if (result != null && result.size() > 0) {
            for (String line:result) {
                if (line.indexOf(appName) > 0 && line.indexOf("online") > 0) {
                    logger.info(appName + "已启动");
                    return true;
                } else if (line.indexOf(appName) > 0 && line.indexOf("stopped") > 0) {
                    logger.info("已停止");
                    return false;
                }
            }
        }

        return false;
    }


    private static List<String> executeLinuxCmd(String cmd) throws InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        try {
            logger.debug("exec:" + cmd);
            process = runtime.exec(new String[] {"/bin/sh", "-c", cmd});
            process.waitFor();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> list = new ArrayList<>();
            String result = null;

            while ((result = bufferedReader.readLine()) != null) {
//                System.out.println(result);
                list.add(result);
            }

            return list;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    /**
     *  通过pm2，批量关闭node-red实例
     *
     * @param appNames node-red的应用名列表
     * @throws InterruptedException
     */
    public static void pm2Stop(List<String> appNames) throws InterruptedException {

        if (appNames != null && appNames.size() > 0) {

            StringBuilder cmd = new StringBuilder();
            cmd.append("pm2").append(" ").append("stop").append(" ");
            for (String appName :appNames) {
                cmd.append(appName).append(" ");
            }
            executeLinuxCmd(cmd.toString());
        }
    }

    public static void pm2Stop(String appName) throws InterruptedException {

        List<String> result = executeLinuxCmd("pm2 stop " + appName);

        if (result != null && result.size() > 0) {
            for (String line:result) {
                if (line.indexOf(appName) > 0 && line.indexOf("stopped") > 0) {
                    logger.info("关闭成功！");

                }
            }
        }
    }
}
