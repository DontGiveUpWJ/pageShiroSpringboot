
DROP TABLE IF EXISTS composite_serviceInfo;
CREATE TABLE composite_serviceInfo(
  com_info_id 		INT			   NOT NULL UNIQUE PRIMARY KEY auto_increment ,
  com_srv_id		VARCHAR (32)   NOT NULL UNIQUE comment '服务唯一ID',
  com_srv_name 		VARCHAR (64) 			comment '服务链路名字',
  com_srv_url 		VARCHAR (128)  NOT NULL comment '对外接口名字',
  com_srv_method 	VARCHAR (16) 			comment '调用方式put 、get、post、delete',
  com_tab_id 		VARCHAR (20)   NOT NULL comment 'tab的id',
  com_tab_name 		VARCHAR(64)    NOT NULL comment '流程名字',
  com_wires 		VARCHAR(256) 			comment '流程下一个节点属性',
  is_valid 			tinyint(1) 				comment '是否有效',
  com_childs_info 	TEXT 					comment '编排服务中所有的子节点信息'
)engine=InnoDB  DEFAULT charset=utf8;

DROP TABLE IF EXISTS atom_com_rel;
CREATE TABLE atom_com_rel(
  rel_id 		INT 		 NOT NULL PRIMARY KEY AUTO_INCREMENT,
  com_srv_id 	VARCHAR(20)  NOT NULL ,
  atom_srv_sign VARCHAR(128) NOT NULL
)engine=InnoDB  AUTO_INCREMENT=1 DEFAULT charset=utf8;

DROP TABLE IF EXISTS atom_serviceInfo;
CREATE TABLE atom_serviceInfo(
  srv_id 				  	int not null primary key auto_increment,
  srv_name 			  		varchar(128) not null comment '服务名称',
  atom_srv_sign 			varchar(128) not null comment '服务标识',
  srv_ch_name  				varchar(128) 		 comment '服务中文名称',
  srv_app_name 				varchar(128)		 comment '应用名称',
  srv_src_name 				varchar(128) 		 comment '源服务名称',
  srv_url 				  	varchar(128) 		 comment '服务地址（URL）',
  srv_register 				varchar(128)  		 comment '注册中心地址',
  srv_loadbalance_list  	varchar(256)  		 comment '负载均衡地址',
  srv_type 			  		int(2)   			 comment '原子服务类型 0：rest，1：hsf',
  srv_version 				varchar(12)  		 comment '服务版本',
  srv_group 			  	varchar(64)   		 comment '服务分组',
  srv_timeout 				int			 		 comment '服务超时时间',
  srv_loadbalance  			varchar(20)   		 comment '负载均衡',
  srv_failover 				int(3)   			 comment '集群容错',
  srv_input 				varchar(128) 		 comment '服务入参',
  srv_output 				varchar(128) 		 comment '服务出参',
  srv_success_desc 			varchar(128) 		 comment '正常码说明',
  srv_error_desc 			varchar(128) 		 comment '异常码说明',
  srv_source_info 			varchar(64)   		 comment '服务来源',
  srv_system				varchar(64)  		 comment '服务归属系统',
  srv_bus_center			varchar(64)  		 comment '服务归属业务中心',
  srv_create				varchar(32)  		 comment '服务创建人',
  srv_update		 		varchar(32)  		 comment '服务更新人',
  create_time				timestamp NULL DEFAULT CURRENT_TIMESTAMP comment '服务创建时间',
  update_time				timestamp NULL DEFAULT CURRENT_TIMESTAMP comment '服务更新时间',
  srv_init_type 			tinyint(1)  			 comment '手工录入/自动同步 0：手动，1：自动', 
  srv_update_type       	tinyint(1)   		 comment '数据修改 手工录入/自动同步  0：手动，1：自动',
  is_valid					tinyint(1)			 comment '是否有效 0：无效 ，1：有效',
  srv_method  				VARCHAR(64)   		 comment '方法名rest的post、get 及hsf的调用方法',
  srv_desc  				VARCHAR(128)   		 comment '服务描述'
)engine=InnoDB  AUTO_INCREMENT=1 DEFAULT charset=utf8;
