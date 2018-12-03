package com.sitech.paas.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.sitech.paas.entity.Microservice;
import com.sitech.paas.entity.RequestParam;
import com.sitech.paas.mapper.MicroserviceMapper;
import com.sitech.paas.mapper.MircsFlowMapper;
import com.sitech.paas.service.IMicroseviceServer;

/**
 * 
 * @类描述：
 * 
 * @项目名称：srvcompose @包名： com.sitech.paas.service.impl
 * @类名称：MicroseviceServerImp
 * @创建人：dongkw
 * @创建时间：2018年10月24日下午2:13:57
 * @修改人：dongkw
 * @修改时间：2018年10月24日下午2:13:57 @修改备注：
 * @version v1.0
 * @see
 * @bug
 * @Copyright
 * @mail
 */
@Service
public class MicroseviceServerImp implements IMicroseviceServer {

	private static final Logger log = LoggerFactory.getLogger(MicroseviceServerImp.class);

	@Autowired
	private MicroserviceMapper microserviceMapper;

	@Autowired
	private MircsFlowMapper mircsFlowMapper;

	@Override
	public List<Microservice> searchMicrosevice(RequestParam param) throws Exception {
		int offset = param.getOffset();
		int rows = param.getRows() == 0 ? 10 : param.getRows();
		PageHelper.startPage(offset, rows);
		return microserviceMapper.searchMicrs(param);
	}

	@Transactional
	@Override
	public void deleteMicrosevice(List<String> uniqueId) throws Exception {
		if (uniqueId != null && uniqueId.size() > 0) {
			microserviceMapper.deleteBatch(uniqueId);
			mircsFlowMapper.deleteBatch(uniqueId);
		}
	}

	@Override
	public void updateMircosevice(Microservice mics) {
		microserviceMapper.updateByPrimaryKeySelective(mics);
	}

	@Transactional
	@Override
	public void saveMircosevice(Microservice mics) throws Exception {
		microserviceMapper.insert(mics);
	}

	@Override
	public Microservice getMicroserviceByUniqueId(String uniqueId) {
		Microservice microservice = null;
		if (uniqueId != null) {
			microservice = microserviceMapper.getMicroserviceByUniqueId(uniqueId);
		}
		return microservice;
	}

	@Override
	public List<Microservice> getMicroserviceAll() {

		return microserviceMapper.getMicroserviceAll();
	}

	@Override
	public int getCountByUniqueId(String uniqueId) {
		if (!StringUtils.isEmpty(uniqueId)) {
			return microserviceMapper.getCountByUniqueId(uniqueId);
		}
		return -1;
	}

	@Override
	public void syncMicroserviceFormZookeeper(String ip) throws Exception {
		List<Microservice> hsfProvider = getHsfProvider(ip);
		if (hsfProvider != null && hsfProvider.size() > 0) {
			for (Microservice m : hsfProvider) {
				String serverUniqueid = m.getServerUniqueid();
				if (null != serverUniqueid && !"".equals(serverUniqueid)) {
					Integer count = microserviceMapper.getCountByUniqueId(serverUniqueid);
					if (count > 0) {
						microserviceMapper.updateByPrimaryKeySelective(m);
					} else {
						microserviceMapper.insert(m);
					}
				}
			}
		}
	}

	@Override
	public Microservice selectSyncMicros(RequestParam param) throws Exception {
		Microservice microservice = null;
		if (param != null) {
			if (!"".equals(param.getName())) {
				if ("16".equals(param.getType())) {
					microservice = microserviceMapper.queryServerFormEsbHsf(param.getName());
					microservice.setServerUniqueid(microservice.getServerName());
					String serverRegister = microservice.getServerRegister();
					if (serverRegister != null && !"".equals(serverRegister)) {
						serverRegister = serverRegister.replace("http://", "");
						microservice.setServerRegister(serverRegister);
						microservice.setServerAuthor("ESB");
						microservice.setServerType(1);
						microservice.setServerFrom("ESB");
						String serverName = microservice.getServerName();
						String source = serverName.replace("_", ".");
						int index = source.lastIndexOf(".");
						String method = source.substring(index + 1);
						String api = source.substring(0, index);
						microservice.setServerType(0);
						microservice.setServerMethod(method);
						microservice.setServerOrginName(api);
					}
				} else if ("5".equals(param.getType())) {
					microservice = microserviceMapper.queryServerFormEsbHttp(param.getName());
					microservice.setServerUniqueid(microservice.getServerName());
					microservice.setServerType(0);
					String serverUrl = microservice.getServerUrl();
					// 符号处理
					serverUrl = serverUrl.replace(",", microservice.getServerOrginName() + ",");
					if (!serverUrl.endsWith(",")) {
						serverUrl = serverUrl + microservice.getServerOrginName();
					}
					microservice.setServerUrl(serverUrl);
					microservice.setServerBalanceloadUrl(serverUrl);
				} else {
					log.info("原子服务类型不支持.........");
				}
			}
		}
		return microservice;
	}

	@Override
	public List<Microservice> queryServerFormEsbList(RequestParam param) throws Exception {
		return microserviceMapper.queryServerFormEsbList(param);
	}

	private List<Microservice> getHsfProvider(String ip) throws Exception {
		ZkClient zkClient = new ZkClient(ip,10000);
		List<Microservice> microsAll = null;
		if (zkClient.exists("/dubbo")) {
			List<String> children = zkClient.getChildren("/dubbo");
			microsAll = new ArrayList<>();
			for (String name : children) {
				if (zkClient.exists("/dubbo/" + name + "/providers")) {
					List<String> children1 = zkClient.getChildren("/dubbo/" + name + "/providers");
					for (String node : children1) {
						String url = URLDecoder.decode(node);
						Microservice microservice = new Microservice();
						if (!"".equals(url)) {
							Map<String, String> paramsMap = URLRequest(url);
							if (null != paramsMap.get("interface") && !"".equals(paramsMap.get("interface"))) {
								microservice.setServerName(paramsMap.get("interface"));
								microservice.setServerUniqueid(paramsMap.get("interface"));
								microservice.setServerRegister(ip);
								microservice.setServerOrginName(paramsMap.get("interface"));
								microservice.setServerVersion(paramsMap.get("version"));
								if(paramsMap.containsKey("default.timeout")){
									microservice.setServerTimeout(Integer.parseInt(paramsMap.get("default.timeout")));
								}
								//microservice.setServerMethod(paramsMap.get("methods"));
								microservice.setServerGroup(paramsMap.get("group"));
								microservice.setServerApp(paramsMap.get("application"));
								microservice.setServerType(1);
								microservice.setServerSystem("zookeeper");
								microservice.setServerAuthor("system_sync");
								microservice.setServerDatainitMode(false);
								microservice.setServerCtime(new Date());
								microservice.setServerFrom("zookeeper");
								microsAll.add(microservice);
								log.info("<<<****************" + paramsMap + "******************>>>");
							}
						}
					}
				}
			}
		}
		zkClient.close();
		return microsAll;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 *
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 *
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

}
