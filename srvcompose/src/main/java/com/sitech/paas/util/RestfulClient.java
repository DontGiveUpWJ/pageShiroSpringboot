package com.sitech.paas.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RestfulClient {
	//com_sitech_custsvc_comp_inter_ITranCalledCoSvc_init
	//
	private static final String targetURL = "http://192.168.95.217:28075/crmpfcore/ws/csf/com_sitech_custsvc_comp_inter_ISpamMessagesMgrCoSvc_qryDelayType/";
	private static Date now = new Date();
	
	public static void main(String[] args) throws Exception {
		String json = getRequest("C:\\Users\\Administrator\\Desktop\\req.txt");
		String result = doGet(targetURL,json);
		System.out.println(result);
	}

	public static String doGet(String url, String json) throws Exception {
		String sUrl = url + URLEncoder.encode(json, "utf-8");
		URL getUrl = new URL(sUrl);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection httpConnection = (HttpURLConnection) getUrl
				.openConnection();
		String sn = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(now);
		httpConnection.setRequestProperty("X-Trans-Id", sn);
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setRequestProperty("X-Channel-Id", "CRMPF");
		
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		httpConnection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				httpConnection.getInputStream(), "utf-8"));// 设置编码,否则中文乱码
		String lines = reader.readLine();
		reader.close();
		// 断开连接
		httpConnection.disconnect();
		return lines;
	}

	public static String doPost(String url, String json) {
		BufferedReader responseBuffer = null;
		String output = null;
		try {

			URL targetUrl = new URL(url);

			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl
					.openConnection();
			String sn = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(now);
			httpConnection.setRequestProperty("X-Trans-Id", sn);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type","application/json");
			httpConnection.setRequestProperty("X-Channel-Id", "CRMPF");
			

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(json.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			}

			responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));

			output = responseBuffer.readLine();

			httpConnection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

		return output;

	}
	
	public static String getRequest(String filePath) throws IOException {
		StringBuilder req = new StringBuilder();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					req.append((lineTxt));
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return req.toString().replaceAll(" ", "");
	}
}
