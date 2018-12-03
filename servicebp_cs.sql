/*
Navicat MySQL Data Transfer

Source Server         : 172.18.231.40_8066
Source Server Version : 50720
Source Host           : 172.18.231.40:8066
Source Database       : servicebp_cs

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-12-03 16:11:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for atom_com_rel
-- ----------------------------
DROP TABLE IF EXISTS `atom_com_rel`;
CREATE TABLE `atom_com_rel` (
  `rel_id` int(11) NOT NULL AUTO_INCREMENT,
  `com_srv_id` varchar(20) NOT NULL,
  `atom_srv_sign` varchar(128) NOT NULL,
  PRIMARY KEY (`rel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of atom_com_rel
-- ----------------------------
INSERT INTO `atom_com_rel` VALUES ('1', 'eb4f335e.14b0d', 'http://172.21.224.233:8767/user/1');
INSERT INTO `atom_com_rel` VALUES ('2', '73c4a354.5a1c4c', 'http://172.21.214.157:8080/find');
INSERT INTO `atom_com_rel` VALUES ('3', 'efa0e814.397bb8', 'http://172.21.12.228:8763/product/query');
INSERT INTO `atom_com_rel` VALUES ('4', 'efa0e814.397bb8', 'http://172.21.12.228:8762/order/add');
INSERT INTO `atom_com_rel` VALUES ('5', 'efa0e814.397bb8', 'http://172.21.12.228:8764/user/query');
INSERT INTO `atom_com_rel` VALUES ('6', 'f2466d43.efcff', 'http://172.21.12.228:8763/product/query');
INSERT INTO `atom_com_rel` VALUES ('7', '8bf1c473.035398', 'http://172.21.12.228:8764/user/query');
INSERT INTO `atom_com_rel` VALUES ('8', '82d21825.493f6', 'http://172.21.10.30:53000/esbWS/rest/test_com_rest_string');
INSERT INTO `atom_com_rel` VALUES ('9', '88df0474.7cbf5', 'http://172.21.11.172:8763/product/query');
INSERT INTO `atom_com_rel` VALUES ('10', '88df0474.7cbf5', 'http://172.21.11.172:8762/order/add');
INSERT INTO `atom_com_rel` VALUES ('11', '88df0474.7cbf5', 'http://172.21.11.172:8764/user/query');
INSERT INTO `atom_com_rel` VALUES ('12', '2f3f7d95.e82ebe', 'http://172.21.11.172:8763/product/query');
INSERT INTO `atom_com_rel` VALUES ('13', '2f3f7d95.e82ebe', 'http://172.21.11.172:8762/order/add');

-- ----------------------------
-- Table structure for atom_serviceinfo
-- ----------------------------
DROP TABLE IF EXISTS `atom_serviceinfo`;
CREATE TABLE `atom_serviceinfo` (
  `srv_id` int(11) NOT NULL AUTO_INCREMENT,
  `srv_name` varchar(128) NOT NULL COMMENT '服务名称',
  `atom_srv_sign` varchar(128) NOT NULL COMMENT '服务标识',
  `srv_ch_name` varchar(128) DEFAULT NULL COMMENT '服务中文名称',
  `srv_app_name` varchar(128) DEFAULT NULL COMMENT '应用名称',
  `srv_src_name` varchar(128) DEFAULT NULL COMMENT '源服务名称',
  `srv_url` varchar(128) DEFAULT NULL COMMENT '服务地址（URL）',
  `srv_register` varchar(128) DEFAULT NULL COMMENT '注册中心地址',
  `srv_loadbalance_list` varchar(256) DEFAULT NULL COMMENT '负载均衡地址',
  `srv_type` int(2) DEFAULT NULL COMMENT '原子服务类型 0：rest，1：hsf',
  `srv_version` varchar(12) DEFAULT NULL COMMENT '服务版本',
  `srv_group` varchar(64) DEFAULT NULL COMMENT '服务分组',
  `srv_timeout` int(11) DEFAULT NULL COMMENT '服务超时时间',
  `srv_loadbalance` varchar(20) DEFAULT NULL COMMENT '负载均衡',
  `srv_failover` int(3) DEFAULT NULL COMMENT '集群容错',
  `srv_input` varchar(128) DEFAULT NULL COMMENT '服务入参',
  `srv_output` varchar(128) DEFAULT NULL COMMENT '服务出参',
  `srv_success_desc` varchar(128) DEFAULT NULL COMMENT '正常码说明',
  `srv_error_desc` varchar(128) DEFAULT NULL COMMENT '异常码说明',
  `srv_source_info` varchar(64) DEFAULT NULL COMMENT '服务来源',
  `srv_system` varchar(64) DEFAULT NULL COMMENT '服务归属系统',
  `srv_bus_center` varchar(64) DEFAULT NULL COMMENT '服务归属业务中心',
  `srv_create` varchar(32) DEFAULT NULL COMMENT '服务创建人',
  `srv_update` varchar(32) DEFAULT NULL COMMENT '服务更新人',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务更新时间',
  `srv_init_type` tinyint(1) DEFAULT NULL COMMENT '手工录入/自动同步 0：手动，1：自动',
  `srv_update_type` tinyint(1) DEFAULT NULL COMMENT '数据修改 手工录入/自动同步  0：手动，1：自动',
  `is_valid` tinyint(1) DEFAULT NULL COMMENT '是否有效 0：无效 ，1：有效',
  `srv_method` varchar(64) DEFAULT NULL COMMENT '方法名rest的post、get 及hsf的调用方法',
  `srv_desc` varchar(128) DEFAULT NULL COMMENT '服务描述',
  PRIMARY KEY (`srv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of atom_serviceinfo
-- ----------------------------
INSERT INTO `atom_serviceinfo` VALUES ('1', '商品服务', 'http://172.21.12.228:8763/product/query', '', null, null, 'http://172.21.12.228:8763/product/query', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('2', '用户服务', 'http://172.21.12.228:8764/user/query', '', null, null, 'http://172.21.12.228:8764/user/query', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('3', '', 'http://172.21.224.233:8767/user/1', '', null, null, 'http://172.21.224.233:8767/user/1', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'GET', null);
INSERT INTO `atom_serviceinfo` VALUES ('4', '订单信息', 'http://172.21.12.228:8762/order/add', '', null, null, 'http://172.21.12.228:8762/order/add', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('5', '', 'http://172.21.10.30:53000/esbWS/rest/test_com_rest_string', '', null, null, 'http://172.21.10.30:53000/esbWS/rest/test_com_rest_string', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('6', 'sayHello', 'com.sitech.hsf.Interface.ServerA.sayHello', 'sayHello', 'consumer-demo', 'com.sitech.hsf.Interface.ServerA', null, '172.21.10.130:2181', null, '1', '1.0.0', '', '10000', null, null, null, null, null, null, '', 'zookeeper', null, 'system', 'system', '2018-12-03 16:00:00', '2018-12-03 16:00:00', '0', '0', '0', 'sayHello', null);
INSERT INTO `atom_serviceinfo` VALUES ('7', '', 'http://172.21.214.157:8080/find', '', null, null, 'http://172.21.214.157:8080/find', null, null, '0', null, null, '3000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'GET', null);
INSERT INTO `atom_serviceinfo` VALUES ('8', '商品服务', 'http://172.21.11.172:8763/product/query', '', null, null, 'http://172.21.11.172:8763/product/query', null, null, '0', null, null, '2000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('9', '用户服务', 'http://172.21.11.172:8764/user/query', '', null, null, 'http://172.21.11.172:8764/user/query', null, null, '0', null, null, '2000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:00', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('10', '订单信息', 'http://172.21.11.172:8762/order/add', '', null, null, 'http://172.21.11.172:8762/order/add', null, null, '0', null, null, '2000', null, null, null, null, null, null, null, '', null, null, 'system', null, '2018-12-03 16:00:01', null, '0', '0', 'POST', null);
INSERT INTO `atom_serviceinfo` VALUES ('11', 'asdfsadf', 'adsfasdfsafd', null, null, null, 'http://127.0.0.1:11111/query', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '2018-11-30 18:59:27', null, '1', null, '0', 'POST', null);

-- ----------------------------
-- Table structure for cluster_info
-- ----------------------------
DROP TABLE IF EXISTS `cluster_info`;
CREATE TABLE `cluster_info` (
  `cluster_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cluster_ip` varchar(20) NOT NULL COMMENT 'ip地址',
  `cluster_port` varchar(20) NOT NULL COMMENT 'sftp端口',
  `user_name` varchar(128) DEFAULT NULL COMMENT '用户名',
  `user_pwd` varchar(128) DEFAULT NULL COMMENT '密码',
  `file_name` varchar(64) DEFAULT NULL COMMENT '流程文件名',
  `file_path` varchar(128) DEFAULT NULL COMMENT '文件路径',
  `view_url` varchar(128) NOT NULL COMMENT '访问地址',
  `type` tinyint(2) NOT NULL COMMENT '类型（0 管理节点;1 运行节点）',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态（0 正常;1 异常）',
  `is_valid` tinyint(2) NOT NULL COMMENT '是否有效（0 有效;1 无效）',
  `res_create` varchar(128) DEFAULT NULL COMMENT '创建人',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`cluster_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cluster_info
-- ----------------------------
INSERT INTO `cluster_info` VALUES ('1', '172.18.231.92', '22', 'chnesb', 'Y2huZXNi', 'flows_host-172-18-231-92.json', '/paas/chnesb/servicebp/node-red/node-red-0.18.7/userDir', 'http://172.18.231.92:2880', '1', '0', '0', 'admin', '2018-11-28 10:23:23', '2018-11-28 14:47:14');
INSERT INTO `cluster_info` VALUES ('2', '172.18.231.92', '22', 'chnesb', 'Y2huZXNi', 'flows_host-172-18-231-92.json', '/paas/chnesb/.node-red', 'http://172.18.231.92:1880', '0', null, '0', 'admin', '2018-11-27 16:35:48', '2018-11-28 14:45:42');

-- ----------------------------
-- Table structure for composite_serviceinfo
-- ----------------------------
DROP TABLE IF EXISTS `composite_serviceinfo`;
CREATE TABLE `composite_serviceinfo` (
  `com_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `com_srv_id` varchar(32) NOT NULL COMMENT '服务唯一ID',
  `com_srv_name` varchar(64) DEFAULT NULL COMMENT '服务链路名字',
  `com_srv_url` varchar(128) NOT NULL COMMENT '对外接口名字',
  `com_srv_method` varchar(16) DEFAULT NULL COMMENT '调用方式put 、get、post、delete',
  `com_tab_id` varchar(20) NOT NULL COMMENT 'tab的id',
  `com_tab_name` varchar(64) NOT NULL COMMENT '流程名字',
  `com_wires` varchar(256) DEFAULT NULL COMMENT '流程下一个节点属性',
  `is_valid` tinyint(1) DEFAULT NULL COMMENT '是否有效',
  `com_childs_info` text COMMENT '编排服务中所有的子节点信息',
  PRIMARY KEY (`com_info_id`),
  UNIQUE KEY `com_info_id` (`com_info_id`),
  UNIQUE KEY `com_srv_id` (`com_srv_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of composite_serviceinfo
-- ----------------------------
INSERT INTO `composite_serviceinfo` VALUES ('1', 'eb4f335e.14b0d', '/test1', '/test1', 'get', 'f4cfd7d9.b679d8', '流程1', '[[\"6b93e8b1.946c18\"]]', '0', '[{\"id\":\"eb4f335e.14b0d\",\"type\":\"http in\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"url\":\"/test1\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":100,\"y\":180,\"wires\":[[\"6b93e8b1.946c18\"]]},{\"id\":\"6b93e8b1.946c18\",\"type\":\"http request\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"method\":\"GET\",\"ret\":\"txt\",\"url\":\"http://172.21.224.233:8767/user/1\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.224.233:8767/user/1\",\"tls\":\"\",\"x\":260,\"y\":180,\"wires\":[[\"909d99ae.6f6268\"]]},{\"id\":\"909d99ae.6f6268\",\"type\":\"http response\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"statusCode\":\"\",\"headers\":{},\"x\":770,\"y\":200,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('2', '73c4a354.5a1c4c', '/ww', '/ww', 'get', 'f4cfd7d9.b679d8', '流程1', '[[\"f70e674.f8a4998\"]]', '0', '[{\"id\":\"73c4a354.5a1c4c\",\"type\":\"http in\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"url\":\"/ww\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":200,\"y\":1420,\"wires\":[[\"f70e674.f8a4998\"]]},{\"id\":\"f70e674.f8a4998\",\"type\":\"http request\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"method\":\"GET\",\"ret\":\"txt\",\"url\":\"http://172.21.214.157:8080/find\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.214.157:8080/find\",\"tls\":\"\",\"x\":540,\"y\":1420,\"wires\":[[\"3c4ceb9f.0b9b94\",\"5de835f7.2b4794\"]]},{\"id\":\"3c4ceb9f.0b9b94\",\"type\":\"debug\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"dubugname\",\"active\":false,\"tosidebar\":true,\"console\":false,\"tostatus\":false,\"complete\":\"payload\",\"x\":750,\"y\":1340,\"wires\":[]},{\"id\":\"5de835f7.2b4794\",\"type\":\"http response\",\"z\":\"f4cfd7d9.b679d8\",\"name\":\"\",\"statusCode\":\"\",\"headers\":{},\"x\":760,\"y\":1460,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('3', 'efa0e814.397bb8', '/create/order', '/create/order', 'get', '2b9c963d.446dfa', '流程2', '[[\"b0ff73e9.1883\",\"fc8ab3bb.290ae\"]]', '0', '[{\"id\":\"efa0e814.397bb8\",\"type\":\"http in\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"url\":\"/create/order\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":140,\"y\":380,\"wires\":[[\"b0ff73e9.1883\",\"fc8ab3bb.290ae\"]]},{\"id\":\"b0ff73e9.1883\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"获取商品ID\",\"func\":\"var productId = msg.payload.productId;\\n\\nmsg.payload={\\n    \\\"id\\\":productId\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":330,\"y\":300,\"wires\":[[\"7ef58cbc.33a934\",\"a94946c.3664cb8\"]]},{\"id\":\"7ef58cbc.33a934\",\"type\":\"http request\",\"z\":\"2b9c963d.446dfa\",\"name\":\"商品服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.12.228:8763/product/query\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.12.228:8763/product/query\",\"tls\":\"\",\"x\":500,\"y\":300,\"wires\":[[\"ff6c3949.4a0418\"]]},{\"id\":\"ff6c3949.4a0418\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"获取商品信息\",\"func\":\"var product=msg.payload.name;\\n\\nmsg.payload={\\n    \\\"product\\\":product\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":668,\"y\":300,\"wires\":[[\"5ce61590.ba1fcc\",\"2aab309a.9811c\"]]},{\"id\":\"5ce61590.ba1fcc\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":660,\"y\":160,\"wires\":[]},{\"id\":\"2aab309a.9811c\",\"type\":\"split\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"splt\":\":\",\"spltType\":\"str\",\"arraySplt\":1,\"arraySpltType\":\"len\",\"stream\":false,\"addname\":\"topic\",\"x\":890,\"y\":240,\"wires\":[[\"617dda97.cd4ff4\"]]},{\"id\":\"617dda97.cd4ff4\",\"type\":\"join\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"mode\":\"custom\",\"build\":\"object\",\"property\":\"payload\",\"propertyType\":\"msg\",\"key\":\"topic\",\"joiner\":\"\\\\n\",\"joinerType\":\"str\",\"accumulate\":true,\"timeout\":\"3\",\"count\":\"2\",\"reduceRight\":false,\"reduceExp\":\"\",\"reduceInit\":\"\",\"reduceInitType\":\"\",\"reduceFixup\":\"\",\"x\":970,\"y\":360,\"wires\":[[\"fa5a9555.e7b578\",\"83532d96.1b8bf\"]]},{\"id\":\"fa5a9555.e7b578\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":1180,\"y\":280,\"wires\":[]},{\"id\":\"83532d96.1b8bf\",\"type\":\"http request\",\"z\":\"2b9c963d.446dfa\",\"name\":\"订单信息\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.12.228:8762/order/add\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.12.228:8762/order/add\",\"tls\":\"\",\"x\":1200,\"y\":360,\"wires\":[[\"d8a18c9.2ebd57\",\"78f60855.e70c68\"]]},{\"id\":\"d8a18c9.2ebd57\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":1400,\"y\":280,\"wires\":[]},{\"id\":\"78f60855.e70c68\",\"type\":\"http response\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"statusCode\":\"200\",\"headers\":{\"content-type\":\"application/json\"},\"x\":1440,\"y\":360,\"wires\":[]},{\"id\":\"a94946c.3664cb8\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"true\",\"x\":310,\"y\":160,\"wires\":[]},{\"id\":\"fc8ab3bb.290ae\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"获取用户ID\",\"func\":\"var userId = msg.payload.userId;\\n\\nmsg.payload={\\n    \\\"id\\\": userId\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":330,\"y\":420,\"wires\":[[\"daf651a7.63ba\",\"3b24bc01.045224\"]]},{\"id\":\"daf651a7.63ba\",\"type\":\"http request\",\"z\":\"2b9c963d.446dfa\",\"name\":\"用户服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.12.228:8764/user/query\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.12.228:8764/user/query\",\"tls\":\"\",\"x\":480,\"y\":420,\"wires\":[[\"33413ddb.21d902\"]]},{\"id\":\"33413ddb.21d902\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"获取用户信息\",\"func\":\"var user=msg.payload.name;\\n\\nmsg.payload={\\n    \\\"user\\\":user\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":670,\"y\":420,\"wires\":[[\"6b23e06e.35008\",\"c329d65b.06ef28\"]]},{\"id\":\"6b23e06e.35008\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":680,\"y\":540,\"wires\":[]},{\"id\":\"c329d65b.06ef28\",\"type\":\"split\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"splt\":\":\",\"spltType\":\"str\",\"arraySplt\":1,\"arraySpltType\":\"len\",\"stream\":false,\"addname\":\"topic\",\"x\":850,\"y\":420,\"wires\":[[\"617dda97.cd4ff4\"]]},{\"id\":\"3b24bc01.045224\",\"type\":\"debug\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":340,\"y\":540,\"wires\":[],\"inputLabels\":[\"dsadasdsadsad\"]}]');
INSERT INTO `composite_serviceinfo` VALUES ('4', 'f2466d43.efcff', '/product', '/product', 'get', '2b9c963d.446dfa', '流程2', '[[\"28eb1ef6.4064f2\"]]', '0', '[{\"id\":\"f2466d43.efcff\",\"type\":\"http in\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"url\":\"/product\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":130,\"y\":680,\"wires\":[[\"28eb1ef6.4064f2\"]]},{\"id\":\"28eb1ef6.4064f2\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"func\":\"var id = msg.payload.id;\\n\\nmsg.payload={\\n    \\\"id\\\": id\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":330,\"y\":680,\"wires\":[[\"5fec1091.cf8bc\"]]},{\"id\":\"5fec1091.cf8bc\",\"type\":\"http request\",\"z\":\"2b9c963d.446dfa\",\"name\":\"商品服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.12.228:8763/product/query\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.12.228:8763/product/query\",\"tls\":\"\",\"x\":520,\"y\":680,\"wires\":[[\"9c4e94f8.e9d9f8\"]]},{\"id\":\"9c4e94f8.e9d9f8\",\"type\":\"http response\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"statusCode\":\"200\",\"headers\":{\"content-type\":\"application/json\"},\"x\":740,\"y\":680,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('5', '8bf1c473.035398', '/user', '/user', 'get', '2b9c963d.446dfa', '流程2', '[[\"ab235956.6e57d8\"]]', '0', '[{\"id\":\"8bf1c473.035398\",\"type\":\"http in\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"url\":\"/user\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":120,\"y\":760,\"wires\":[[\"ab235956.6e57d8\"]]},{\"id\":\"ab235956.6e57d8\",\"type\":\"function\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"func\":\"var id = msg.payload.id;\\n\\nmsg.payload={\\n    \\\"id\\\": id\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":330,\"y\":760,\"wires\":[[\"7fc61dde.440db4\"]]},{\"id\":\"7fc61dde.440db4\",\"type\":\"http request\",\"z\":\"2b9c963d.446dfa\",\"name\":\"用户服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.12.228:8764/user/query\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.12.228:8764/user/query\",\"tls\":\"\",\"x\":520,\"y\":760,\"wires\":[[\"ff1216d.9ac9be8\"]]},{\"id\":\"ff1216d.9ac9be8\",\"type\":\"http response\",\"z\":\"2b9c963d.446dfa\",\"name\":\"\",\"statusCode\":\"200\",\"headers\":{\"content-type\":\"application/json\"},\"x\":740,\"y\":760,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('6', '633b57c.f1f2da8', '/hello-cookie', '/hello-cookie', 'get', 'f41a409d.230a4', '流程3', '[[\"e4ae1a62.2c7368\"]]', '0', '[{\"id\":\"633b57c.f1f2da8\",\"type\":\"http in\",\"z\":\"f41a409d.230a4\",\"name\":\"\",\"url\":\"/hello-cookie\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":290,\"y\":140,\"wires\":[[\"e4ae1a62.2c7368\"]]},{\"id\":\"e4ae1a62.2c7368\",\"type\":\"function\",\"z\":\"f41a409d.230a4\",\"name\":\"Format cookies\",\"func\":\"msg.payload = JSON.stringify(msg.req.cookies,null,4);\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":540,\"y\":140,\"wires\":[[\"e390d9f9.848678\"]]},{\"id\":\"e390d9f9.848678\",\"type\":\"template\",\"z\":\"f41a409d.230a4\",\"name\":\"page\",\"field\":\"payload\",\"fieldType\":\"msg\",\"format\":\"handlebars\",\"syntax\":\"mustache\",\"template\":\"<html>\\n    <head></head>\\n    <body>\\n        <h1>Cookies</h1>\\n        <p></p><a href=\\\"hello-cookie/add\\\">Add a cookie</a> &bull; <a href=\\\"hello-cookie/clear\\\">Clear cookies</a></p>\\n        <pre>{{ payload }}</pre>\\n    </body>\\n</html>\",\"output\":\"str\",\"x\":810,\"y\":140,\"wires\":[[\"1b6f43cc.a940cc\"]]},{\"id\":\"1b6f43cc.a940cc\",\"type\":\"http response\",\"z\":\"f41a409d.230a4\",\"name\":\"\",\"x\":1050,\"y\":140,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('7', '151731b3.f2050e', '/hello-cookie/add', '/hello-cookie/add', 'get', 'f41a409d.230a4', '流程3', '[[\"9ea27461.205508\"]]', '0', '[{\"id\":\"151731b3.f2050e\",\"type\":\"http in\",\"z\":\"f41a409d.230a4\",\"name\":\"\",\"url\":\"/hello-cookie/add\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":300,\"y\":200,\"wires\":[[\"9ea27461.205508\"]]},{\"id\":\"9ea27461.205508\",\"type\":\"function\",\"z\":\"f41a409d.230a4\",\"name\":\"Add a cookie\",\"func\":\"msg.cookies = { };\\nmsg.cookies[\\\"demo-\\\"+(Math.floor(Math.random()*1000))] = Date.now();\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":550,\"y\":200,\"wires\":[[\"6745d15f.6eccc\"]]},{\"id\":\"6745d15f.6eccc\",\"type\":\"change\",\"z\":\"f41a409d.230a4\",\"name\":\"Redirect to /hello-cookie\",\"rules\":[{\"t\":\"set\",\"p\":\"statusCode\",\"pt\":\"msg\",\"to\":\"302\",\"tot\":\"num\"},{\"t\":\"set\",\"p\":\"headers\",\"pt\":\"msg\",\"to\":\"{}\",\"tot\":\"json\"},{\"t\":\"set\",\"p\":\"headers.location\",\"pt\":\"msg\",\"to\":\"/hello-cookie\",\"tot\":\"str\"}],\"action\":\"\",\"property\":\"\",\"from\":\"\",\"to\":\"\",\"reg\":false,\"x\":830,\"y\":220,\"wires\":[[\"1b6f43cc.a940cc\"]]},{\"id\":\"1b6f43cc.a940cc\",\"type\":\"http response\",\"z\":\"f41a409d.230a4\",\"name\":\"\",\"x\":1050,\"y\":140,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('8', 'c7e9e984.9cb7b8', '/hello-cookie/clear', '/hello-cookie/clear', 'get', 'f41a409d.230a4', '流程3', '[[\"8ecffb4a.113d28\"]]', '0', '[{\"id\":\"c7e9e984.9cb7b8\",\"type\":\"http in\",\"z\":\"f41a409d.230a4\",\"name\":\"\",\"url\":\"/hello-cookie/clear\",\"method\":\"get\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":300,\"y\":260,\"wires\":[[\"8ecffb4a.113d28\"]]},{\"id\":\"8ecffb4a.113d28\",\"type\":\"function\",\"z\":\"f41a409d.230a4\",\"name\":\"Clear cookies\",\"func\":\"// Find demo cookies and clear them\\nvar cookieNames = Object.keys(msg.req.cookies).filter(function(cookieName) { return /^demo-/.test(cookieName);});\\nmsg.cookies = {};\\n\\ncookieNames.forEach(function(cookieName) {\\n    msg.cookies[cookieName] = null;\\n});\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":540,\"y\":260,\"wires\":[[\"6745d15f.6eccc\"]]}]');
INSERT INTO `composite_serviceinfo` VALUES ('9', '82d21825.493f6', '/esbTest', '/esbTest', 'post', '360aff99.34f32', '流程4', '[[\"f8a0779a.3def58\"]]', '0', '[{\"id\":\"82d21825.493f6\",\"type\":\"http in\",\"z\":\"360aff99.34f32\",\"name\":\"\",\"url\":\"/esbTest\",\"method\":\"post\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":290,\"y\":260,\"wires\":[[\"f8a0779a.3def58\"]]},{\"id\":\"f8a0779a.3def58\",\"type\":\"http request\",\"z\":\"360aff99.34f32\",\"name\":\"\",\"method\":\"POST\",\"ret\":\"txt\",\"url\":\"http://172.21.10.30:53000/esbWS/rest/test_com_rest_string\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.10.30:53000/esbWS/rest/test_com_rest_string\",\"tls\":\"\",\"x\":520,\"y\":260,\"wires\":[[\"b0e85295.90333\",\"be695f46.d6a05\"]]},{\"id\":\"b0e85295.90333\",\"type\":\"http response\",\"z\":\"360aff99.34f32\",\"name\":\"\",\"statusCode\":\"\",\"headers\":{},\"x\":750,\"y\":260,\"wires\":[]},{\"id\":\"be695f46.d6a05\",\"type\":\"debug\",\"z\":\"360aff99.34f32\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":false,\"tostatus\":false,\"complete\":\"payload\",\"x\":770,\"y\":320,\"wires\":[]}]');
INSERT INTO `composite_serviceinfo` VALUES ('10', '88df0474.7cbf5', '创建订单【组合服务】', '/create/order11', 'post', 'e5aacac3.9bf018', '流程6', '[[\"8bd56a4b.87bc8\",\"c84aeb6e.599418\"]]', '0', '[{\"id\":\"88df0474.7cbf5\",\"type\":\"http in\",\"z\":\"e5aacac3.9bf018\",\"name\":\"创建订单【组合服务】\",\"url\":\"/create/order11\",\"method\":\"post\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":140,\"y\":380,\"wires\":[[\"8bd56a4b.87bc8\",\"c84aeb6e.599418\"]]},{\"id\":\"8bd56a4b.87bc8\",\"type\":\"function\",\"z\":\"e5aacac3.9bf018\",\"name\":\"获取商品ID\",\"func\":\"var productId = msg.payload.productId;\\n\\nmsg.payload={\\n    \\\"id\\\":productId\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":350,\"y\":300,\"wires\":[[\"8d80f44.10404c8\",\"1b261e3e.9d9cba\"]]},{\"id\":\"8d80f44.10404c8\",\"type\":\"http request\",\"z\":\"e5aacac3.9bf018\",\"name\":\"商品服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.11.172:8763/product/query\",\"timeout\":\"2000\",\"uniqueId\":\"http://172.21.11.172:8763/product/query\",\"tls\":\"\",\"x\":520,\"y\":300,\"wires\":[[\"bec2fce5.21b16\"]]},{\"id\":\"bec2fce5.21b16\",\"type\":\"function\",\"z\":\"e5aacac3.9bf018\",\"name\":\"获取商品信息\",\"func\":\"var product=msg.payload.name;\\n\\nmsg.payload={\\n    \\\"product\\\":product\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":688,\"y\":300,\"wires\":[[\"af3f7b84.08fcb8\",\"44782dcf.f505f8\"]]},{\"id\":\"af3f7b84.08fcb8\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":680,\"y\":160,\"wires\":[]},{\"id\":\"44782dcf.f505f8\",\"type\":\"join\",\"z\":\"e5aacac3.9bf018\",\"name\":\"组装参数\",\"mode\":\"custom\",\"build\":\"object\",\"property\":\"payload\",\"propertyType\":\"msg\",\"key\":\"topic\",\"joiner\":\"\\\\n\",\"joinerType\":\"str\",\"accumulate\":true,\"timeout\":\"3\",\"count\":\"2\",\"reduceRight\":false,\"reduceExp\":\"\",\"reduceInit\":\"\",\"reduceInitType\":\"\",\"reduceFixup\":\"\",\"x\":1000,\"y\":360,\"wires\":[[\"c3cc03e4.000028\",\"d32e4b79.61982\"]]},{\"id\":\"c3cc03e4.000028\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":1200,\"y\":280,\"wires\":[]},{\"id\":\"d32e4b79.61982\",\"type\":\"http request\",\"z\":\"e5aacac3.9bf018\",\"name\":\"订单信息\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.11.172:8762/order/add\",\"timeout\":\"2000\",\"uniqueId\":\"http://172.21.11.172:8762/order/add\",\"tls\":\"\",\"x\":1220,\"y\":360,\"wires\":[[\"7cceec73.0417d4\",\"8d809059.ea101\"]]},{\"id\":\"7cceec73.0417d4\",\"type\":\"http response\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"statusCode\":\"200\",\"headers\":{\"content-type\":\"application/json\"},\"x\":1460,\"y\":360,\"wires\":[]},{\"id\":\"8d809059.ea101\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":1420,\"y\":280,\"wires\":[]},{\"id\":\"1b261e3e.9d9cba\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"true\",\"x\":330,\"y\":160,\"wires\":[]},{\"id\":\"c84aeb6e.599418\",\"type\":\"function\",\"z\":\"e5aacac3.9bf018\",\"name\":\"获取用户ID\",\"func\":\"var userId = msg.payload.userId;\\n\\nmsg.payload={\\n    \\\"id\\\": userId\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":350,\"y\":420,\"wires\":[[\"89e4db76.db53a\",\"ef83cea2.d85a\"]]},{\"id\":\"89e4db76.db53a\",\"type\":\"http request\",\"z\":\"e5aacac3.9bf018\",\"name\":\"用户服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.11.172:8764/user/query\",\"timeout\":\"2000\",\"uniqueId\":\"http://172.21.11.172:8764/user/query\",\"tls\":\"\",\"x\":520,\"y\":420,\"wires\":[[\"f09d1dac.7b6348\"]]},{\"id\":\"f09d1dac.7b6348\",\"type\":\"function\",\"z\":\"e5aacac3.9bf018\",\"name\":\"获取用户信息\",\"func\":\"var user=msg.payload.name;\\n\\nmsg.payload={\\n    \\\"user\\\":user\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":690,\"y\":420,\"wires\":[[\"fac49e99.b20c8\",\"44782dcf.f505f8\"]]},{\"id\":\"fac49e99.b20c8\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":700,\"y\":540,\"wires\":[]},{\"id\":\"ef83cea2.d85a\",\"type\":\"debug\",\"z\":\"e5aacac3.9bf018\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":360,\"y\":540,\"wires\":[],\"inputLabels\":[\"dsadasdsadsad\"]}]');
INSERT INTO `composite_serviceinfo` VALUES ('11', '2f3f7d95.e82ebe', '创建订单【组合服务】', '/create/order', 'post', '13c3b8cc.1182d7', '流程7', '[[\"20a82e9b.853286\"]]', '0', '[{\"id\":\"2f3f7d95.e82ebe\",\"type\":\"http in\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"创建订单【组合服务】\",\"url\":\"/create/order\",\"method\":\"post\",\"upload\":false,\"swaggerDoc\":\"\",\"x\":160,\"y\":100,\"wires\":[[\"20a82e9b.853286\"]]},{\"id\":\"20a82e9b.853286\",\"type\":\"function\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"拆分请求\",\"func\":\"var productId = msg.payload.productId;\\nvar userId = msg.payload.userId;\\n\\nmsg.payload=[{\\\"id\\\":productId},{\\\"id\\\":userId}];\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":238,\"y\":200,\"wires\":[[\"2838f633.f7c976\"]]},{\"id\":\"2838f633.f7c976\",\"type\":\"split\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"按规则拆分\",\"splt\":\"\\\\n\",\"spltType\":\"str\",\"arraySplt\":1,\"arraySpltType\":\"len\",\"stream\":false,\"addname\":\"\",\"x\":228,\"y\":280,\"wires\":[[\"a5559c28.7c9678\"]]},{\"id\":\"a5559c28.7c9678\",\"type\":\"switch\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"分服务调用\",\"property\":\"parts.index\",\"propertyType\":\"msg\",\"rules\":[{\"t\":\"eq\",\"v\":\"0\",\"vt\":\"str\"},{\"t\":\"eq\",\"v\":\"1\",\"vt\":\"str\"}],\"checkall\":\"true\",\"repair\":false,\"outputs\":2,\"x\":228,\"y\":380,\"wires\":[[\"ad958b9d.388448\",\"57518694.10a87\"],[\"8e0ebebf.65687\",\"bf3df492.7c8758\"]]},{\"id\":\"ad958b9d.388448\",\"type\":\"http request\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"商品服务\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.11.172:8763/product/query\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.11.172:8763/product/query\",\"tls\":\"\",\"x\":560,\"y\":340,\"wires\":[[\"bd4be0d9.c2634\"]]},{\"id\":\"bd4be0d9.c2634\",\"type\":\"function\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"获取商品信息\",\"func\":\"var product=msg.payload.name;\\n\\nmsg.payload={\\n    \\\"product\\\":product\\n};\\n\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":800,\"y\":340,\"wires\":[[\"903dc8a0.633ce8\",\"21ee56d0.39c5be\"]]},{\"id\":\"903dc8a0.633ce8\",\"type\":\"debug\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":900,\"y\":140,\"wires\":[]},{\"id\":\"21ee56d0.39c5be\",\"type\":\"join\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"组装参数\",\"mode\":\"auto\",\"build\":\"object\",\"property\":\"payload\",\"propertyType\":\"msg\",\"key\":\"topic\",\"joiner\":\"\\\\n\",\"joinerType\":\"str\",\"accumulate\":false,\"timeout\":\"3\",\"count\":\"2\",\"reduceRight\":false,\"reduceExp\":\"\",\"reduceInit\":\"\",\"reduceInitType\":\"\",\"reduceFixup\":\"\",\"x\":1020,\"y\":340,\"wires\":[[\"7b71aab7.ed3304\"]]},{\"id\":\"7b71aab7.ed3304\",\"type\":\"function\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"组装服务入参\",\"func\":\"var product = msg.payload[0].product;\\nvar user = msg.payload[1].user;\\n\\nmsg.payload = {\\n    \\\"product\\\": product,\\n    \\\"user\\\": user\\n}\\nreturn msg;\",\"outputs\":1,\"noerr\":0,\"x\":1220,\"y\":340,\"wires\":[[\"64375597.79d43\"]]},{\"id\":\"64375597.79d43\",\"type\":\"http request\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"订单信息\",\"method\":\"POST\",\"ret\":\"obj\",\"url\":\"http://172.21.11.172:8762/order/add\",\"timeout\":\"3000\",\"uniqueId\":\"http://172.21.11.172:8762/order/add\",\"tls\":\"\",\"x\":1400,\"y\":340,\"wires\":[[\"5393c237.dd97e8\",\"998eeb5a.8616f8\"]]},{\"id\":\"5393c237.dd97e8\",\"type\":\"http response\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"\",\"statusCode\":\"200\",\"headers\":{\"content-type\":\"application/json\"},\"x\":1620,\"y\":340,\"wires\":[]},{\"id\":\"998eeb5a.8616f8\",\"type\":\"debug\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"\",\"active\":true,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"payload\",\"x\":1620,\"y\":240,\"wires\":[]},{\"id\":\"57518694.10a87\",\"type\":\"debug\",\"z\":\"13c3b8cc.1182d7\",\"name\":\"\",\"active\":false,\"tosidebar\":true,\"console\":true,\"tostatus\":false,\"complete\":\"true\",\"x\":570,\"y\":240,\"wires\":[]}]');

-- ----------------------------
-- Table structure for http2in
-- ----------------------------
DROP TABLE IF EXISTS `http2in`;
CREATE TABLE `http2in` (
  `id` varchar(20) NOT NULL,
  `NAME` varchar(64) DEFAULT NULL COMMENT '服务链路名字',
  `url` varchar(128) NOT NULL COMMENT '对外接口名字',
  `method` varchar(16) DEFAULT NULL COMMENT '调用方式put 、get、post、delete',
  `z` varchar(20) NOT NULL COMMENT 'tab的id',
  `tabName` varchar(64) NOT NULL COMMENT '流程名字',
  `wires` varchar(256) DEFAULT NULL COMMENT '流程下一个节点id',
  `childId` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of http2in
-- ----------------------------

-- ----------------------------
-- Table structure for microservice
-- ----------------------------
DROP TABLE IF EXISTS `microservice`;
CREATE TABLE `microservice` (
  `server_id` int(11) NOT NULL AUTO_INCREMENT,
  `server_name` varchar(128) NOT NULL COMMENT '服务名称',
  `server_uniqueId` varchar(128) NOT NULL COMMENT '服务标识',
  `server_ch_name` varchar(128) DEFAULT NULL COMMENT '服务中文名称',
  `server_app` varchar(128) DEFAULT NULL COMMENT '应用名称',
  `server_orgin_name` varchar(128) DEFAULT NULL COMMENT '源服务名称',
  `server_url` varchar(128) DEFAULT NULL COMMENT '服务地址（URL）',
  `server_register` varchar(128) DEFAULT NULL COMMENT '注册中心地址',
  `server_balanceLoad_url` varchar(256) DEFAULT NULL COMMENT '负载均衡地址',
  `server_type` int(2) DEFAULT NULL COMMENT '服务类型 原子服务、编排服务、流程服务',
  `server_version` varchar(12) DEFAULT NULL COMMENT '服务版本',
  `server_group` varchar(20) DEFAULT NULL COMMENT '服务分组',
  `server_timeout` int(11) DEFAULT NULL COMMENT '服务超时时间',
  `server_balanceLoad` int(3) DEFAULT NULL COMMENT '负载均衡',
  `server_failover` int(3) DEFAULT NULL COMMENT '集群容错',
  `server_params_in` varchar(128) DEFAULT NULL COMMENT '服务入参',
  `server_params_out` varchar(128) DEFAULT NULL COMMENT '服务出参',
  `server_status_normal` varchar(128) DEFAULT NULL COMMENT '正常码说明',
  `server_status_error` varchar(128) DEFAULT NULL COMMENT '异常码说明',
  `server_from` varchar(64) DEFAULT NULL COMMENT '服务来源',
  `server_system` varchar(64) DEFAULT NULL COMMENT '服务归属系统',
  `server_business` varchar(64) DEFAULT NULL COMMENT '服务归属业务中心',
  `server_author` varchar(32) DEFAULT NULL COMMENT '服务创建人',
  `server_update_author` varchar(32) DEFAULT NULL COMMENT '服务更新人',
  `server_cTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务创建时间',
  `server_updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务更新时间',
  `server_dataInit_mode` tinyint(1) DEFAULT NULL COMMENT '手工录入/自动同步',
  `server_dataMod_mode` tinyint(1) DEFAULT NULL COMMENT '数据修改 手工录入/自动同步',
  `server_method` varchar(64) DEFAULT NULL COMMENT 'hsf的调用方法名，或rest的调用方式',
  PRIMARY KEY (`server_id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of microservice
-- ----------------------------

-- ----------------------------
-- Table structure for mircs_flow
-- ----------------------------
DROP TABLE IF EXISTS `mircs_flow`;
CREATE TABLE `mircs_flow` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `httpInId` varchar(20) NOT NULL,
  `uniqueId` varchar(128) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=799 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mircs_flow
-- ----------------------------

-- ----------------------------
-- Table structure for optionrecord_201811
-- ----------------------------
DROP TABLE IF EXISTS `optionrecord_201811`;
CREATE TABLE `optionrecord_201811` (
  `opt_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作流水',
  `opt_model` varchar(255) DEFAULT NULL COMMENT '操作模块',
  `opt_login` varchar(255) DEFAULT NULL COMMENT '操作工号',
  `opt_time` varchar(255) DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`opt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of optionrecord_201811
-- ----------------------------
INSERT INTO `optionrecord_201811` VALUES ('1', '添加用户', 'admin', '');
INSERT INTO `optionrecord_201811` VALUES ('2', '添加用户', 'admin', '2018-11-13 14:28:16');
INSERT INTO `optionrecord_201811` VALUES ('3', '添加角色', 'admin', '2018-11-13 14:50:32');
INSERT INTO `optionrecord_201811` VALUES ('4', '删除用户', 'admin', '2018-11-13 14:51:26');
INSERT INTO `optionrecord_201811` VALUES ('5', '更新用户', 'admin', '2018-11-13 14:55:00');
INSERT INTO `optionrecord_201811` VALUES ('6', '删除用户', 'user1', '2018-11-13 14:56:23');
INSERT INTO `optionrecord_201811` VALUES ('7', '添加角色', 'admin', '2018-11-13 15:08:27');
INSERT INTO `optionrecord_201811` VALUES ('8', '更新角色', 'admin', '2018-11-13 15:08:41');
INSERT INTO `optionrecord_201811` VALUES ('9', '分配资源', 'admin', '2018-11-13 15:08:50');
INSERT INTO `optionrecord_201811` VALUES ('10', '删除角色', 'admin', '2018-11-13 15:08:58');
INSERT INTO `optionrecord_201811` VALUES ('11', '添加资源', 'admin', '2018-11-13 15:13:20');
INSERT INTO `optionrecord_201811` VALUES ('12', '更新资源', 'admin', '2018-11-13 15:13:31');
INSERT INTO `optionrecord_201811` VALUES ('13', '删除资源', 'admin', '2018-11-13 15:13:39');
INSERT INTO `optionrecord_201811` VALUES ('14', '更新资源', 'admin', '2018-11-13 15:14:46');
INSERT INTO `optionrecord_201811` VALUES ('15', '修改静态node', 'admin', '2018-11-15 11:22:45');
INSERT INTO `optionrecord_201811` VALUES ('16', '添加静态node', 'admin', '2018-11-15 11:24:23');
INSERT INTO `optionrecord_201811` VALUES ('17', '删除静态node', 'admin', '2018-11-15 11:24:38');
INSERT INTO `optionrecord_201811` VALUES ('18', '添加角色', 'admin', '2018-11-15 14:18:35');
INSERT INTO `optionrecord_201811` VALUES ('19', '更新角色', 'admin', '2018-11-15 14:40:56');
INSERT INTO `optionrecord_201811` VALUES ('20', '添加用户', 'admin', '2018-11-15 15:49:41');
INSERT INTO `optionrecord_201811` VALUES ('21', '改变用户状态', 'admin', '2018-11-15 16:10:38');
INSERT INTO `optionrecord_201811` VALUES ('22', '添加用户', 'admin', '2018-11-15 16:18:20');
INSERT INTO `optionrecord_201811` VALUES ('23', '添加用户', 'admin', '2018-11-15 17:22:25');
INSERT INTO `optionrecord_201811` VALUES ('24', '添加用户', 'admin', '2018-11-16 16:56:30');
INSERT INTO `optionrecord_201811` VALUES ('25', '分配资源', 'admin', '2018-11-16 17:12:55');
INSERT INTO `optionrecord_201811` VALUES ('26', '分配资源', 'admin', '2018-11-16 17:13:15');
INSERT INTO `optionrecord_201811` VALUES ('27', '添加用户', 'admin', '2018-11-21 10:39:55');
INSERT INTO `optionrecord_201811` VALUES ('28', '删除用户', 'admin', '2018-11-21 10:41:47');
INSERT INTO `optionrecord_201811` VALUES ('29', '改变用户状态', 'admin', '2018-11-21 11:13:43');
INSERT INTO `optionrecord_201811` VALUES ('30', '改变用户状态', 'admin', '2018-11-21 11:13:48');
INSERT INTO `optionrecord_201811` VALUES ('31', '添加角色', 'admin', '2018-11-21 11:13:58');
INSERT INTO `optionrecord_201811` VALUES ('32', '添加角色', 'admin', '2018-11-21 13:56:48');
INSERT INTO `optionrecord_201811` VALUES ('33', '更新角色', 'admin', '2018-11-21 13:56:55');
INSERT INTO `optionrecord_201811` VALUES ('34', '删除角色', 'admin', '2018-11-21 13:57:16');
INSERT INTO `optionrecord_201811` VALUES ('35', '分配资源', 'admin', '2018-11-21 14:09:44');
INSERT INTO `optionrecord_201811` VALUES ('36', '添加资源', 'admin', '2018-11-21 15:38:43');
INSERT INTO `optionrecord_201811` VALUES ('37', '更新资源', 'admin', '2018-11-21 15:41:45');
INSERT INTO `optionrecord_201811` VALUES ('38', '删除资源', 'admin', '2018-11-21 15:41:51');
INSERT INTO `optionrecord_201811` VALUES ('39', '分配资源', 'admin', '2018-11-21 15:48:00');
INSERT INTO `optionrecord_201811` VALUES ('40', '分配资源', 'admin', '2018-11-21 15:57:43');
INSERT INTO `optionrecord_201811` VALUES ('41', '分配资源', 'admin', '2018-11-21 15:58:00');
INSERT INTO `optionrecord_201811` VALUES ('42', '改变用户状态', 'admin', '2018-11-21 15:58:23');
INSERT INTO `optionrecord_201811` VALUES ('43', '改变用户状态', 'admin', '2018-11-21 15:58:28');
INSERT INTO `optionrecord_201811` VALUES ('44', '添加角色', 'admin', '2018-11-21 16:01:16');
INSERT INTO `optionrecord_201811` VALUES ('45', '添加用户', 'admin', '2018-11-21 16:01:31');
INSERT INTO `optionrecord_201811` VALUES ('46', '删除用户', 'admin', '2018-11-21 16:02:10');
INSERT INTO `optionrecord_201811` VALUES ('47', '添加角色', 'admin', '2018-11-21 16:02:28');
INSERT INTO `optionrecord_201811` VALUES ('48', '更新角色', 'admin', '2018-11-21 16:02:32');
INSERT INTO `optionrecord_201811` VALUES ('49', '分配资源', 'admin', '2018-11-21 16:02:37');
INSERT INTO `optionrecord_201811` VALUES ('50', '分配资源', 'admin', '2018-11-21 16:02:43');
INSERT INTO `optionrecord_201811` VALUES ('51', '分配资源', 'admin', '2018-11-21 16:03:27');
INSERT INTO `optionrecord_201811` VALUES ('52', '分配资源', 'admin', '2018-11-21 16:03:42');
INSERT INTO `optionrecord_201811` VALUES ('53', '添加用户', 'admin', '2018-11-22 14:06:39');
INSERT INTO `optionrecord_201811` VALUES ('54', '删除角色', 'admin', '2018-11-22 14:10:07');
INSERT INTO `optionrecord_201811` VALUES ('55', '添加角色', 'admin', '2018-11-23 09:25:01');
INSERT INTO `optionrecord_201811` VALUES ('56', '添加用户', 'admin', '2018-11-29 18:04:49');
INSERT INTO `optionrecord_201811` VALUES ('57', '删除用户信息', 'admin', '2018-11-29 19:57:51');
INSERT INTO `optionrecord_201811` VALUES ('58', '更新用户角色信息', 'admin', '2018-11-29 19:59:11');
INSERT INTO `optionrecord_201811` VALUES ('59', '更改用户状态', 'admin', '2018-11-29 19:59:32');
INSERT INTO `optionrecord_201811` VALUES ('60', '添加角色信息', 'admin', '2018-11-29 19:59:47');
INSERT INTO `optionrecord_201811` VALUES ('61', '更新角色信息', 'admin', '2018-11-29 19:59:56');
INSERT INTO `optionrecord_201811` VALUES ('62', '添加用户', 'admin', '2018-11-29 20:01:37');
INSERT INTO `optionrecord_201811` VALUES ('63', '修改用户信息', 'admin', '2018-11-29 20:03:11');
INSERT INTO `optionrecord_201811` VALUES ('64', '更改角色资源信息', 'admin', '2018-11-29 20:03:28');
INSERT INTO `optionrecord_201811` VALUES ('65', '删除角色信息', 'admin', '2018-11-29 20:03:41');
INSERT INTO `optionrecord_201811` VALUES ('66', '添加资源信息', 'admin', '2018-11-29 20:03:51');
INSERT INTO `optionrecord_201811` VALUES ('67', '更新资源信息', 'admin', '2018-11-29 20:04:05');
INSERT INTO `optionrecord_201811` VALUES ('68', '删除资源信息', 'admin', '2018-11-29 20:04:22');
INSERT INTO `optionrecord_201811` VALUES ('69', '添加角色信息', 'admin', '2018-11-30 15:15:49');
INSERT INTO `optionrecord_201811` VALUES ('70', '删除角色信息', 'admin', '2018-11-30 15:15:54');
INSERT INTO `optionrecord_201811` VALUES ('71', '添加角色信息', 'admin', '2018-11-30 15:17:23');
INSERT INTO `optionrecord_201811` VALUES ('72', '删除角色信息', 'admin', '2018-11-30 15:17:27');
INSERT INTO `optionrecord_201811` VALUES ('73', '添加角色信息', 'admin', '2018-11-30 15:18:08');
INSERT INTO `optionrecord_201811` VALUES ('74', '删除角色信息', 'admin', '2018-11-30 15:18:11');
INSERT INTO `optionrecord_201811` VALUES ('75', '更改角色资源信息', 'admin', '2018-11-30 15:18:20');
INSERT INTO `optionrecord_201811` VALUES ('76', '添加资源信息', 'admin', '2018-11-30 15:18:40');
INSERT INTO `optionrecord_201811` VALUES ('77', '删除资源信息', 'admin', '2018-11-30 15:18:49');
INSERT INTO `optionrecord_201811` VALUES ('78', '添加资源信息', 'admin', '2018-11-30 15:21:15');
INSERT INTO `optionrecord_201811` VALUES ('79', '删除资源信息', 'admin', '2018-11-30 15:21:21');
INSERT INTO `optionrecord_201811` VALUES ('80', '添加静态node信息', 'admin', '2018-11-30 15:24:53');
INSERT INTO `optionrecord_201811` VALUES ('81', '更新资源信息', 'admin', '2018-11-30 16:05:14');
INSERT INTO `optionrecord_201811` VALUES ('82', '1', null, null);
INSERT INTO `optionrecord_201811` VALUES ('83', '1', null, null);
INSERT INTO `optionrecord_201811` VALUES ('84', '1', null, null);
INSERT INTO `optionrecord_201811` VALUES ('85', '1', null, null);
INSERT INTO `optionrecord_201811` VALUES ('86', '1', null, null);
INSERT INTO `optionrecord_201811` VALUES ('87', '更新资源信息', 'admin', '2018-11-30 17:24:50');
INSERT INTO `optionrecord_201811` VALUES ('88', '更新资源信息', 'admin', '2018-11-30 17:25:59');
INSERT INTO `optionrecord_201811` VALUES ('89', '更新资源信息', 'admin', '2018-11-30 18:56:47');
INSERT INTO `optionrecord_201811` VALUES ('90', '更新资源信息', 'admin', '2018-11-30 19:24:06');
INSERT INTO `optionrecord_201811` VALUES ('91', '添加用户', 'admin', '2018-12-03 11:12:40');
INSERT INTO `optionrecord_201811` VALUES ('92', '更改用户状态', 'admin', '2018-12-03 11:12:51');
INSERT INTO `optionrecord_201811` VALUES ('93', '修改用户信息', 'admin', '2018-12-03 11:15:56');
INSERT INTO `optionrecord_201811` VALUES ('94', '修改用户信息', 'admin', '2018-12-03 11:20:20');
INSERT INTO `optionrecord_201811` VALUES ('95', '更新用户角色信息', 'admin', '2018-12-03 11:20:29');
INSERT INTO `optionrecord_201811` VALUES ('96', '删除用户信息', 'admin', '2018-12-03 11:20:38');
INSERT INTO `optionrecord_201811` VALUES ('97', '添加角色信息', 'admin', '2018-12-03 11:21:01');
INSERT INTO `optionrecord_201811` VALUES ('98', '更改角色资源信息', 'admin', '2018-12-03 11:21:08');
INSERT INTO `optionrecord_201811` VALUES ('99', '删除角色信息', 'admin', '2018-12-03 11:21:21');
INSERT INTO `optionrecord_201811` VALUES ('100', '添加资源信息', 'admin', '2018-12-03 11:22:04');

-- ----------------------------
-- Table structure for resourceinfo
-- ----------------------------
DROP TABLE IF EXISTS `resourceinfo`;
CREATE TABLE `resourceinfo` (
  `res_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `res_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `res_path` varchar(255) DEFAULT NULL COMMENT '资源url',
  `res_type` int(11) DEFAULT NULL COMMENT '资源类型   1:一级菜单    2：二级菜单 3：三级菜单',
  `res_parent` int(11) DEFAULT NULL COMMENT '父资源',
  `res_desc` varchar(255) DEFAULT NULL COMMENT '资源描述',
  `res_group` varchar(255) DEFAULT NULL COMMENT '资源归属',
  `is_valid` int(11) DEFAULT NULL COMMENT '是否有效',
  `res_create` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`res_id`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resourceinfo
-- ----------------------------
INSERT INTO `resourceinfo` VALUES ('1', '系统设置', '/system', '0', '0', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('2', '用户管理', '用户管理.html', '2', '27', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('3', '角色管理', '角色管理.html', '2', '27', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('4', '资源管理', '资源管理.html', '2', '27', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('24', 'swagger', '/swagger-ui.html', null, '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('27', '平台管理', '', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('28', '在线帮助', '静态node.html', '2', '27', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('29', '平台部署', '操作监控.html', '2', '27', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('32', '服务编排', '/allServer/all', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('33', '可视化编排', 'node-red.html', '2', '32', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('34', '服务管理', '/allServer/all', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('35', '服务目录', '', '2', '34', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('36', '服务关系', '', '2', '34', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('37', '服务管控', '', '2', '34', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('38', '原子服务信息', '/原子服务信息.html', '3', '35', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('39', '编排服务信息', '/流程服务.html', '3', '35', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('40', '服务信息同步', '服务信息同步.html', '3', '35', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('41', '服务全景', '/allServer/all', '3', '36', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('42', '原子服务', '原子服务信息.html', '3', '37', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('43', '编排服务', '/allServer/all', '3', '37', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('44', '能力提供', '/allServer/all', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('45', '服务发布', 'flows-deploy.html', '2', '44', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('46', '集群管理', '', '2', '44', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('47', '集群配置', 'instance-config.html', '3', '46', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('48', '实例监控', '/flow', '3', '46', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('49', '监控分析', '', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('50', '运营分析', '', '2', '49', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('51', '调用链路展示', '/allServer/all', '3', '50', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('52', '日调用分析', '/allServer/all', '3', '50', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('53', '月调用分析', '/allServer/all', '3', '50', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('54', '调用趋势分析', '/allServer/all', '3', '50', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('68', '测试1128', '用户管理.html', '1', '1', null, null, null, null, null, null);
INSERT INTO `resourceinfo` VALUES ('72', '集群监控', 'instance-monitor.html', '3', '46', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for roleinfo
-- ----------------------------
DROP TABLE IF EXISTS `roleinfo`;
CREATE TABLE `roleinfo` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `role_group` varchar(255) DEFAULT NULL COMMENT '角色归属',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `role_parent` varchar(255) DEFAULT NULL COMMENT '上级角色',
  `is_valid` int(11) DEFAULT NULL,
  `role_create` varchar(255) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(255) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roleinfo
-- ----------------------------
INSERT INTO `roleinfo` VALUES ('1', '管理员', null, null, null, null, null, null, null);
INSERT INTO `roleinfo` VALUES ('2', '普通用户', null, null, null, null, null, null, null);
INSERT INTO `roleinfo` VALUES ('3', '超级管理员', null, null, null, null, null, null, null);
INSERT INTO `roleinfo` VALUES ('17', 'test', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for role_res
-- ----------------------------
DROP TABLE IF EXISTS `role_res`;
CREATE TABLE `role_res` (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `res_id` int(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`role_id`,`res_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_res
-- ----------------------------
INSERT INTO `role_res` VALUES ('1', '2');
INSERT INTO `role_res` VALUES ('1', '3');
INSERT INTO `role_res` VALUES ('1', '4');
INSERT INTO `role_res` VALUES ('1', '24');
INSERT INTO `role_res` VALUES ('1', '27');
INSERT INTO `role_res` VALUES ('1', '28');
INSERT INTO `role_res` VALUES ('1', '29');
INSERT INTO `role_res` VALUES ('1', '32');
INSERT INTO `role_res` VALUES ('1', '33');
INSERT INTO `role_res` VALUES ('1', '34');
INSERT INTO `role_res` VALUES ('1', '35');
INSERT INTO `role_res` VALUES ('1', '36');
INSERT INTO `role_res` VALUES ('1', '37');
INSERT INTO `role_res` VALUES ('1', '38');
INSERT INTO `role_res` VALUES ('1', '39');
INSERT INTO `role_res` VALUES ('1', '40');
INSERT INTO `role_res` VALUES ('1', '41');
INSERT INTO `role_res` VALUES ('1', '42');
INSERT INTO `role_res` VALUES ('1', '43');
INSERT INTO `role_res` VALUES ('1', '44');
INSERT INTO `role_res` VALUES ('1', '45');
INSERT INTO `role_res` VALUES ('1', '46');
INSERT INTO `role_res` VALUES ('1', '47');
INSERT INTO `role_res` VALUES ('1', '49');
INSERT INTO `role_res` VALUES ('1', '50');
INSERT INTO `role_res` VALUES ('1', '51');
INSERT INTO `role_res` VALUES ('1', '52');
INSERT INTO `role_res` VALUES ('1', '53');
INSERT INTO `role_res` VALUES ('1', '54');
INSERT INTO `role_res` VALUES ('1', '72');
INSERT INTO `role_res` VALUES ('2', '2');
INSERT INTO `role_res` VALUES ('2', '3');
INSERT INTO `role_res` VALUES ('2', '4');
INSERT INTO `role_res` VALUES ('2', '27');
INSERT INTO `role_res` VALUES ('2', '28');
INSERT INTO `role_res` VALUES ('2', '32');
INSERT INTO `role_res` VALUES ('2', '33');
INSERT INTO `role_res` VALUES ('2', '34');
INSERT INTO `role_res` VALUES ('2', '35');
INSERT INTO `role_res` VALUES ('2', '36');
INSERT INTO `role_res` VALUES ('2', '37');
INSERT INTO `role_res` VALUES ('2', '38');
INSERT INTO `role_res` VALUES ('2', '39');
INSERT INTO `role_res` VALUES ('2', '40');
INSERT INTO `role_res` VALUES ('2', '41');
INSERT INTO `role_res` VALUES ('2', '42');
INSERT INTO `role_res` VALUES ('2', '43');
INSERT INTO `role_res` VALUES ('3', '2');
INSERT INTO `role_res` VALUES ('3', '3');
INSERT INTO `role_res` VALUES ('3', '4');
INSERT INTO `role_res` VALUES ('3', '5');
INSERT INTO `role_res` VALUES ('3', '6');
INSERT INTO `role_res` VALUES ('3', '7');
INSERT INTO `role_res` VALUES ('3', '8');
INSERT INTO `role_res` VALUES ('3', '9');
INSERT INTO `role_res` VALUES ('3', '10');
INSERT INTO `role_res` VALUES ('3', '14');
INSERT INTO `role_res` VALUES ('3', '15');
INSERT INTO `role_res` VALUES ('3', '16');
INSERT INTO `role_res` VALUES ('41', '2');
INSERT INTO `role_res` VALUES ('41', '3');

-- ----------------------------
-- Table structure for staticflow
-- ----------------------------
DROP TABLE IF EXISTS `staticflow`;
CREATE TABLE `staticflow` (
  `nid` int(11) NOT NULL AUTO_INCREMENT,
  `noname` varchar(255) DEFAULT NULL,
  `nodesc` text,
  `flowdata` text,
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of staticflow
-- ----------------------------
INSERT INTO `staticflow` VALUES ('1', '话费业务', '话费报销流程', '{\"title\":\"手机上网套餐办理\",\"nodes\":{\"demo_node_1\":{\"name\":\"2子树入口4\",\"left\":295,\"top\":0,\"type\":\"start round\",\"width\":26,\"height\":26,\"alt\":true},\"demo_node_2\":{\"name\":\"广告语\",\"left\":256,\"top\":79,\"type\":\"state\",\"width\":104,\"height\":26},\"demo_node_3\":{\"name\":\"菜单\",\"left\":265,\"top\":146,\"type\":\"join\",\"width\":104,\"height\":26},\"demo_node_7\":{\"name\":\"流量叠加包\",\"left\":433,\"top\":149,\"type\":\"join\",\"width\":109,\"height\":26},\"demo_node_8\":{\"name\":\"手机上网套餐\",\"left\":247,\"top\":246,\"type\":\"join\",\"width\":117,\"height\":26},\"demo_node_9\":{\"name\":\"夜猫套餐\",\"left\":64,\"top\":253,\"type\":\"join\",\"width\":104,\"height\":26},\"demo_node_15\":{\"name\":\"套餐及上网流量剩余查询\",\"left\":93,\"top\":140,\"type\":\"node\",\"width\":119,\"height\":36},\"demo_node_17\":{\"name\":\"10元夜猫\",\"left\":106,\"top\":358,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_18\":{\"name\":\"5元夜猫\",\"left\":2,\"top\":363,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_21\":{\"name\":\"2元叠加包1\",\"left\":604,\"top\":7,\"type\":\"node\",\"width\":104,\"height\":26,\"alt\":true},\"demo_node_22\":{\"name\":\"5元叠加包\",\"left\":601,\"top\":44,\"type\":\"node\",\"width\":104,\"height\":26,\"alt\":true},\"demo_node_23\":{\"name\":\"10元叠加包1\",\"left\":684,\"top\":92,\"type\":\"node\",\"width\":104,\"height\":26,\"alt\":true},\"demo_node_24\":{\"name\":\"20元叠加包\",\"left\":606,\"top\":147,\"type\":\"node\",\"width\":106,\"height\":26},\"demo_node_25\":{\"name\":\"30元叠加包\",\"left\":597,\"top\":224,\"type\":\"node\",\"width\":113,\"height\":26},\"demo_node_26\":{\"name\":\"5元套餐\",\"left\":87,\"top\":448,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_27\":{\"name\":\"8元套餐\",\"left\":210,\"top\":405,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_28\":{\"name\":\"10元套餐\",\"left\":272,\"top\":451,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_29\":{\"name\":\"20元套餐\",\"left\":341,\"top\":412,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_30\":{\"name\":\"30元套餐\",\"left\":460,\"top\":437,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_31\":{\"name\":\"50元套餐\",\"left\":580,\"top\":464,\"type\":\"node\",\"width\":104,\"height\":26},\"demo_node_32\":{\"name\":\"GPRS功能办理\",\"left\":683,\"top\":464,\"type\":\"node\",\"width\":130,\"height\":26}},\"lines\":{\"demo_line_4\":{\"type\":\"sl\",\"from\":\"demo_node_1\",\"to\":\"demo_node_2\",\"name\":\"\"},\"demo_line_5\":{\"type\":\"sl\",\"from\":\"demo_node_2\",\"to\":\"demo_node_3\",\"name\":\"\"},\"demo_line_11\":{\"type\":\"sl\",\"from\":\"demo_node_3\",\"to\":\"demo_node_7\",\"name\":\"2\"},\"demo_line_12\":{\"type\":\"sl\",\"from\":\"demo_node_3\",\"to\":\"demo_node_8\",\"name\":\"1\"},\"demo_line_13\":{\"type\":\"sl\",\"from\":\"demo_node_3\",\"to\":\"demo_node_9\",\"name\":\"3\"},\"demo_line_16\":{\"type\":\"sl\",\"from\":\"demo_node_3\",\"to\":\"demo_node_15\",\"name\":\"4\"},\"demo_line_19\":{\"type\":\"sl\",\"from\":\"demo_node_9\",\"to\":\"demo_node_17\",\"name\":\"2\"},\"demo_line_20\":{\"type\":\"sl\",\"from\":\"demo_node_9\",\"to\":\"demo_node_18\",\"name\":\"1\"},\"demo_line_33\":{\"type\":\"sl\",\"from\":\"demo_node_7\",\"to\":\"demo_node_21\",\"name\":\"1\"},\"demo_line_34\":{\"type\":\"sl\",\"from\":\"demo_node_7\",\"to\":\"demo_node_22\",\"name\":\"2\"},\"demo_line_35\":{\"type\":\"sl\",\"from\":\"demo_node_7\",\"to\":\"demo_node_23\",\"name\":\"3\"},\"demo_line_36\":{\"type\":\"sl\",\"from\":\"demo_node_7\",\"to\":\"demo_node_24\",\"name\":\"4\"},\"demo_line_37\":{\"type\":\"sl\",\"from\":\"demo_node_7\",\"to\":\"demo_node_25\",\"name\":\"5\"},\"demo_line_39\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_26\",\"name\":\"1\"},\"demo_line_42\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_29\",\"name\":\"4\"},\"demo_line_43\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_30\",\"name\":\"5\"},\"demo_line_44\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_31\",\"name\":\"6\"},\"demo_line_45\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_32\",\"name\":\"7\"},\"demo_line_46\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_27\",\"name\":\"2\"},\"demo_line_47\":{\"type\":\"sl\",\"from\":\"demo_node_8\",\"to\":\"demo_node_28\",\"name\":\"3\"}},\"areas\":{},\"initNum\":48}');
INSERT INTO `staticflow` VALUES ('2', '订单信息流程', '订单信息流程图', '{\"title\":\"手机上网套餐办理\",\"nodes\":{\"1542001000640\":{\"name\":\"get/creat\",\"left\":139,\"top\":238,\"type\":\"wj\",\"width\":104,\"height\":26,\"alt\":true},\"1542080410528\":{\"name\":\"获取商品id\",\"left\":332,\"top\":171,\"type\":\"func\",\"width\":101.008404,\"height\":24.008404,\"alt\":true},\"1542080462857\":{\"name\":\"获取用户id\",\"left\":335,\"top\":305,\"type\":\"func\",\"width\":104,\"height\":26,\"alt\":true},\"1542080495065\":{\"name\":\"商品服务\",\"left\":515,\"top\":170,\"type\":\"req\",\"width\":104,\"height\":26,\"alt\":true},\"1542080500975\":{\"name\":\"用户服务\",\"left\":514,\"top\":310,\"type\":\"req\",\"width\":104,\"height\":26,\"alt\":true},\"1542080526662\":{\"name\":\"获取商品信息\",\"left\":701,\"top\":161,\"type\":\"func\",\"width\":128,\"height\":43,\"alt\":true},\"1542080538850\":{\"name\":\"获取用户信息\",\"left\":703,\"top\":300,\"type\":\"func\",\"width\":127,\"height\":43,\"alt\":true},\"1542080565386\":{\"name\":\"split\",\"left\":907,\"top\":168,\"type\":\"split\",\"width\":104,\"height\":26,\"alt\":true},\"1542080615871\":{\"name\":\"split\",\"left\":908,\"top\":309,\"type\":\"split\",\"width\":104,\"height\":26,\"alt\":true},\"1542080639780\":{\"name\":\"join\",\"left\":1063,\"top\":234,\"type\":\"join1\",\"width\":104,\"height\":26,\"alt\":true},\"1542080692853\":{\"name\":\"订单信息\",\"left\":1196,\"top\":234,\"type\":\"res\",\"width\":104,\"height\":26,\"alt\":true},\"1542080798361\":{\"name\":\"响应201\",\"left\":1322,\"top\":233,\"type\":\"res\",\"width\":104,\"height\":26,\"alt\":true}},\"lines\":{\"1542080426011\":{\"type\":\"lr\",\"M\":284.97479248046875,\"from\":\"1542001000640\",\"to\":\"1542080410528\",\"name\":\"\"},\"1542080478441\":{\"type\":\"lr\",\"M\":285.24090576171875,\"from\":\"1542001000640\",\"to\":\"1542080462857\",\"name\":\"\"},\"1542080655129\":{\"type\":\"sl\",\"from\":\"1542080410528\",\"to\":\"1542080495065\",\"name\":\"\"},\"1542080656680\":{\"type\":\"sl\",\"from\":\"1542080462857\",\"to\":\"1542080500975\",\"name\":\"\"},\"1542080658157\":{\"type\":\"sl\",\"from\":\"1542080495065\",\"to\":\"1542080526662\",\"name\":\"\"},\"1542080659075\":{\"type\":\"sl\",\"from\":\"1542080500975\",\"to\":\"1542080538850\",\"name\":\"\"},\"1542080660192\":{\"type\":\"sl\",\"from\":\"1542080526662\",\"to\":\"1542080565386\",\"name\":\"\"},\"1542080661509\":{\"type\":\"sl\",\"from\":\"1542080538850\",\"to\":\"1542080615871\",\"name\":\"\"},\"1542080662672\":{\"type\":\"lr\",\"M\":1035.755602,\"from\":\"1542080565386\",\"to\":\"1542080639780\",\"name\":\"\"},\"1542080663736\":{\"type\":\"lr\",\"M\":1037.005602,\"from\":\"1542080615871\",\"to\":\"1542080639780\",\"name\":\"\"},\"1542080778477\":{\"type\":\"sl\",\"from\":\"1542080639780\",\"to\":\"1542080692853\",\"name\":\"\"},\"1542080824425\":{\"type\":\"sl\",\"from\":\"1542080692853\",\"to\":\"1542080798361\",\"name\":\"\"}},\"areas\":{},\"initNum\":98}');
INSERT INTO `staticflow` VALUES ('5', '10', '20', '{\"title\":\"newFlow_1\",\"nodes\":{\"1542079886490\":{\"name\":\"node_2\",\"left\":152,\"top\":182,\"type\":\"wj\",\"width\":104,\"height\":26,\"alt\":true},\"1542079889406\":{\"name\":\"node_3\",\"left\":338,\"top\":183,\"type\":\"func\",\"width\":104,\"height\":26,\"alt\":true}},\"lines\":{\"1542079901602\":{\"type\":\"sl\",\"from\":\"1542079886490\",\"to\":\"1542079889406\",\"name\":\"\",\"alt\":true}},\"areas\":{},\"initNum\":5}');
INSERT INTO `staticflow` VALUES ('7', '0', '2', '{\"title\":\"newFlow_1\",\"nodes\":{\"1542007434195\":{\"name\":\"inject\",\"left\":274,\"top\":244,\"type\":\"wj\",\"width\":101.9832,\"height\":22.9832,\"alt\":true},\"1542007441541\":{\"name\":\"debug\",\"left\":527,\"top\":257,\"type\":\"http\",\"width\":101.9832,\"height\":22.9832,\"alt\":true}},\"lines\":{},\"areas\":{},\"initNum\":3}');
INSERT INTO `staticflow` VALUES ('8', '0', '3', '{\"title\":\"newFlow_1\",\"nodes\":{\"1543404975425\":{\"name\":\"node_1\",\"left\":177,\"top\":141,\"type\":\"wj\",\"width\":104,\"height\":26,\"alt\":true}},\"lines\":{},\"areas\":{},\"initNum\":2}');
INSERT INTO `staticflow` VALUES ('9', '4', '4', '{}');
INSERT INTO `staticflow` VALUES ('10', '流程1', '流程描述', '{}');
INSERT INTO `staticflow` VALUES ('11', '流程2', '流程描述2', '{}');
INSERT INTO `staticflow` VALUES ('12', '流程3', '流程描述3', '{}');
INSERT INTO `staticflow` VALUES ('15', '1128', '1128', '{}');
INSERT INTO `staticflow` VALUES ('16', '1', '2', '{}');
INSERT INTO `staticflow` VALUES ('17', '12', '12', '{}');
INSERT INTO `staticflow` VALUES ('18', '11', '111', '{\"title\":\"newFlow_1\",\"nodes\":{\"1543480894913\":{\"name\":\"node_1\",\"left\":104,\"top\":134,\"type\":\"wj\",\"width\":104,\"height\":26,\"alt\":true}},\"lines\":{},\"areas\":{},\"initNum\":2}');
INSERT INTO `staticflow` VALUES ('19', '2', '2', '{}');

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(33) DEFAULT NULL COMMENT '用户名称',
  `user_pwd` varchar(50) DEFAULT NULL COMMENT '用户密码',
  `is_valid` int(10) DEFAULT '1' COMMENT '是否启用',
  `user_group` varchar(20) DEFAULT NULL COMMENT '用户归属',
  `user_desc` varchar(50) DEFAULT NULL COMMENT '用户描述',
  `user_create` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(50) DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(50) DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('1', 'admin', '3ef7164d1f6167cb9f2658c07d3c2f0a', '1', '1', null, null, null, null);
INSERT INTO `userinfo` VALUES ('3', 'user2', '121', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('4', 'user3', 'eeb15f98f67fbcc5c8458dca61243e20', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('5', 'user4', 'user4', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('6', 'user5', 'user5', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('7', 'user6', 'user6', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('8', 'user7', 'user7', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('9', 'user8', 'user8', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('14', 'user13', 'user13', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('15', 'user14', 'user14', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('16', 'user15', 'user15', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('17', 'user16', 'user16', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('18', 'user17', 'user17', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('19', 'user18', 'user18', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('21', 'user20', 'user20', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('23', 'java', '83e13fa32725ed2eaf9ee4c3f0ce74fc', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('26', 'java1', '8eb418609ad8fa0373154636332710a3', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('27', 'java2', '4908786a283847e9549d4c694ad95e59', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('28', 'java3', 'c87dc8e545d7832e070cdea0310eaa8e', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('29', 'java5', '05752ff82b4899ada1ef8f52041f47a3', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('30', 'java6', '67b83a7611c30d32aff3cd1a417bf570', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('31', 'java7', '9aa5b1ad3a8790cc93f4091dc4831a57', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('32', 'admin116', '52a698d5eaa2ea105acad627d6454fcb', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('33', 'guest', 'ec6accdccaa07c48553791cc20426c10', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('36', 'guest2', '3e2138e1517d472cb9a47b18fe300662', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('37', 'guest3', '09f243ad1ebfe75137fb1ddf417c78ec', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('41', '修改', '992e63b8c9f4b0c878146302e0bf3830', '0', '01', null, null, null, null);
INSERT INTO `userinfo` VALUES ('42', '1121', '79a62331bb34455364f9f044a5deee65', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('43', 't1123', '599fd3ba1953dd136b25194801eb4847', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('44', '1127', 'be4c36e267e58809c54a91c648fc8337', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('45', '1128', 'bc08623f66d2ced2b7316e3f7871ee00', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('46', '1129', '3ebc72cee342253eeffc94cd829b386a', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('47', '1130', '6e79a5ee1477be4ae891fdb25ba27114', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('48', '1131', '04804455e3e4a0b2e3b2dd3338a49096', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('49', '1132', '4925dee6f33298523e9e214b9ad618cf', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('50', '1133', '249f7b988c643d53daf36a69d53f2aac', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('53', 'admin111', '4258eb39b852cc2fa622a00396446440', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('54', '222', 'd3db819453cadc702ea57097a1ad5ed9', '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('57', '33342', '32719877f8df25a5b44ee4f18fdb1884', '0', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('58', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('59', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('60', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('61', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('62', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('63', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('64', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('65', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('66', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('67', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('68', '2', null, '1', null, null, null, null, null);
INSERT INTO `userinfo` VALUES ('69', '2', null, '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('23', '2');
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('33', '2');
INSERT INTO `user_role` VALUES ('36', '2');
INSERT INTO `user_role` VALUES ('37', '1');
INSERT INTO `user_role` VALUES ('37', '2');
INSERT INTO `user_role` VALUES ('37', '3');
INSERT INTO `user_role` VALUES ('41', '1');
INSERT INTO `user_role` VALUES ('41', '2');
INSERT INTO `user_role` VALUES ('42', '1');
INSERT INTO `user_role` VALUES ('42', '2');
INSERT INTO `user_role` VALUES ('4', '2');
INSERT INTO `user_role` VALUES ('3', '1');
INSERT INTO `user_role` VALUES ('3', '2');

-- ----------------------------
-- Procedure structure for create_table_recharge_gain_every_mon
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_table_recharge_gain_every_mon`;
DELIMITER ;;
CREATE DEFINER=`mysql`@`%` PROCEDURE `create_table_recharge_gain_every_mon`()
BEGIN DECLARE `@suffix` VARCHAR(15); DECLARE `@sqlstr` VARCHAR(2560); SET `@suffix` = DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 MONTH),'%Y_%m'); SET @sqlstr = CONCAT( "CREATE TABLE optionRecord_", `@suffix`, "(   `opt_id` int(11) NOT NULL AUTO_INCREMENT,   `opt_login` varchar(255) DEFAULT NULL,   `opt_model` varchar(255) DEFAULT NULL,   `opt_time` varchar(255) DEFAULT NULL,   PRIMARY KEY (`opt_id`) ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;" ); PREPARE stmt FROM @sqlstr; EXECUTE stmt; END
;;
DELIMITER ;
