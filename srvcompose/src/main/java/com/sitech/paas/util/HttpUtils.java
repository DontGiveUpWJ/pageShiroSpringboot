package com.sitech.paas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


/**
 * 
 * @类描述：Http工具类
 * @项目名称：srvcompose
 * @包名： com.sitech.paas.util
 * @类名称：HttpUtils
 * @创建人：wangjun_paas
 * @创建时间：2018年9月26日上午11:06:01
 * @修改人：wangjun_paas
 * @修改时间：2018年9月26日上午11:06:01
 * @修改备注：
 * @version v1.0
 * @see 
 * @bug 
 * @Copyright 
 * @mail
 */
public class HttpUtils {

    /**
     * 发送POST请求
     * @param url 请求url
     * @param data 请求数据
     * @return 结果
     */
	/**
	 * 
	 * @描述:POST请求(方法的注解意思是压制警告)
	 * @方法名: doPost
	 * @param url
	 * @param data
	 * @return
	 * @返回类型 String
	 * @创建人 wangjun_paas
	 * @创建时间 2018年9月26日上午11:06:27
	 * @修改人 wangjun_paas
	 * @修改时间 2018年9月26日上午11:06:27
	 * @修改备注
	 * @since
	 * @throws
	 */
    @SuppressWarnings("deprecation")
    public static String doPost(String url, String data) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(10000).setConnectTimeout(20000)
                .setConnectionRequestTimeout(10000).build();
        httpPost.setConfig(requestConfig);
        String context = StringUtils.EMPTY;
        if (!StringUtils.isEmpty(data)) {
            StringEntity body = new StringEntity(data, "utf-8");
            httpPost.setEntity(body);
        }
        // 设置回调接口接收的消息头
        httpPost.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            context = EntityUtils.toString(entity, HTTP.UTF_8);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                response.close();
                httpPost.abort();
                httpClient.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return context;
    }

  
    /**
     * 
     * @描述:模拟发送get请求
     * @方法名: doGet
     * @param url
     * @return
     * @返回类型 String
     * @创建人 wangjun_paas
     * @创建时间 2018年9月28日上午10:00:40
     * @修改人 wangjun_paas
     * @修改时间 2018年9月28日上午10:00:40
     * @修改备注
     * @since
     * @throws
     */
    @SuppressWarnings("deprecation")
	public static String doGet(String url) {
    	  CloseableHttpClient httpClient = HttpClients.createDefault();
          HttpGet httpGet = new HttpGet(url);
          RequestConfig requestConfig = RequestConfig.custom()
                  .setSocketTimeout(10000).setConnectTimeout(20000)
                  .setConnectionRequestTimeout(10000).build();
          httpGet.setConfig(requestConfig);
          String context = StringUtils.EMPTY;
         
          // 设置回调接口接收的消息头
          httpGet.addHeader("Content-Type", "application/json");
          CloseableHttpResponse response = null;
          try {
              response = httpClient.execute(httpGet);
              HttpEntity entity = response.getEntity();
              context = EntityUtils.toString(entity, HTTP.UTF_8);
          } catch (Exception e) {
              e.getStackTrace();
          } finally {
              try {
                  response.close();
                  httpGet.abort();
                  httpClient.close();
              } catch (Exception e) {
                  e.getStackTrace();
              }
          }
          return context;
    }
    
    /**
     * 
     * @描述:模拟发送soap请求
     * @方法名: doPostSoap
     * @param postUrl
     * @param soapXml
     * @param soapAction
     * @return
     * @返回类型 String
     * @创建人 wangjun_paas
     * @创建时间 2018年9月28日上午10:01:14
     * @修改人 wangjun_paas
     * @修改时间 2018年9月28日上午10:01:14
     * @修改备注
     * @since
     * @throws
     */
    public static String doPostSoapAction(String postUrl, String soapXml,
			String soapAction) {
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
                //  设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(10000)
                .setConnectionRequestTimeout(20000)
				.setConnectTimeout(20000).build();
		httpPost.setConfig(requestConfig);
		try {
			httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
			httpPost.setHeader("SOAPAction", soapAction);
			StringEntity data = new StringEntity(soapXml,
					Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient
					.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
			}
                response.close();
			// 释放资源
			closeableHttpClient.close();
		} catch (Exception e) {
		}
		return retStr;
	}
 
    

    /**
     * 
     * @描述:解析出url参数中的键值对
     * @方法名: getRequestParam
     * @param url
     * @return 键值对
     * @返回类型 Map<String,String>
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年9月26日上午11:07:40
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年9月26日上午11:07:40
     * @修改备注
     * @since
     * @throws
     */
    public static Map<String, String> getRequestParam(String url) {

        Map<String, String> map = new HashMap<String, String>();
        String[] arrSplit = null;

        // 每个键值为一组
        arrSplit = url.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            // 解析出键值
            if (arrSplitEqual.length > 1) {
                // 正确解析
                map.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if ("".equals( arrSplitEqual[0])) {
                    map.put(arrSplitEqual[0], "");
                }
            }
        }
        return map;
    }

    /**
     * 
     * @描述:
     * @方法名: doPostSoap
     * @param postUrl
     * @param xmlFile
     * @return
     * @throws Exception
     * @返回类型 String
     * @创建人 NeverGiveUp-WJ
     * @创建时间 2018年9月28日上午11:08:56
     * @修改人 NeverGiveUp-WJ
     * @修改时间 2018年9月28日上午11:08:56
     * @修改备注
     * @since
     * @throws
     */
    @SuppressWarnings({ "unused" })
	public static String doPostSoap(String postUrl, String xmlFile) {
		StringBuffer sb;
		try {
			URL url = new URL(postUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			byte[] bytes = xmlFile.getBytes();
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			OutputStream out = httpConn.getOutputStream();
			out.write(bytes);
			out.close();
			InputStream inputStream = httpConn.getInputStream();
			InputStreamReader is = new InputStreamReader(httpConn.getInputStream(),
					"utf-8");
			BufferedReader in = new BufferedReader(is);
			String inputLine;
			sb = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
				sb.append("\n");
			}
			in.close();
			httpConn.disconnect();
			return sb.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
    	
    }
    
    @SuppressWarnings({ "resource", "unused" })
   	public static String doPostSoapFile(String postUrl, String xmlFile) throws Exception {
		URL url = new URL(postUrl);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		File fileToSend = new File(xmlFile);
		byte[] buf = new byte[(int) fileToSend.length()];// 用于存放文件数据的数组
		int read = new FileInputStream(xmlFile).read(buf);
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		httpConn.setRequestMethod("POST");
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();
		out.write(buf);
		out.close();
		InputStream inputStream = httpConn.getInputStream();
		InputStreamReader is = new InputStreamReader(httpConn.getInputStream(),
				"utf-8");
		BufferedReader in = new BufferedReader(is);
		String inputLine;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			sb.append(inputLine);
			sb.append("\n");
		}
		in.close();
		httpConn.disconnect();
	   	return sb.toString();
    }
    
    public static void main(String[] args) throws Exception {
    	/*String a = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
    			"<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n" + 
    			"  <soap:Body>\r\n" + 
    			"    <getWeatherbyCityName xmlns=\"http://WebXml.com.cn/\">\r\n" + 
    			"      <theCityName>上海</theCityName>\r\n" + 
    			"    </getWeatherbyCityName>\r\n" + 
    			"  </soap:Body>\r\n" + 
    			"</soap:Envelope>";
		String doPostSoap = HttpUtils.doPostSoapFile("http://www.webxml.com.cn/WebServices/WeatherWebService.asmx", "test.xml");
		System.out.println(doPostSoap);*/
		String doPost = HttpUtils.doPost("http://localhost:8089/users", "{\"id\":\"1\"}");
		System.out.println(doPost);
		
	}
}