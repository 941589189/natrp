drop database natrp_main;
create DATABASE natrp_main;
use natrp_main;

-- ----------------------------
-- 病人信息表 --
-- NULL可以为空 DEFAULT NULL默认为空
DROP TABLE IF EXISTS `l_patientinfo`;
CREATE TABLE `l_patientinfo`  (
      `patient_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '患者ID',
      `patient_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '患者姓名',
      `sex` int(4) NOT NULL COMMENT '患者性别 1 男/ 2 女',
      `age` int(4)  COMMENT '年龄',
      `patient_no` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '患者身份证号码',
      `serial_num` bigint(20) null  DEFAULT NULL COMMENT '患者编号',
      `phone_num` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '患者手机号码',
      `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '患者所在省',
      `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '患者所在市',
      `town` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '患者所在区/县',
      `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
      `del_flag` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
      `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
      PRIMARY KEY (`patient_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '患者信息表' ROW_FORMAT = Dynamic;
INSERT INTO `l_patientinfo` VALUES (1, '刘凯', 1 , 18 ,'34142219850228605X', 202105111113216615, '18888888888', '安徽省', '宿州市', '埇桥区', '长江中路18号', '0', '');
INSERT INTO `l_patientinfo` VALUES (2, '周俊永', 1 , 18 ,'341422198502286210', 202105111113214444, '18888888888', '安徽省', '六安市', '裕安区', '龙河西路', '0', '');
INSERT INTO `l_patientinfo` VALUES (00002021, '吴安庄', 1, 54 ,'412728196901045559', null, '15088498888', '浙江省', '宁波市', '鄞州区', '下应街道', '0', '');
INSERT INTO `l_patientinfo` VALUES (00000213, '钟雪梅', 2, 39, null,null,19855321125,"浙江省",'宁波市','鄞州区','民望街道',null,null);
INSERT INTO `l_patientinfo` VALUES (91247405, '陈时斌', 1, 59 ,'412728196403081549', null, '1508849****', '浙江省', '宁波市', '鄞州区', '下应街道', '0', '');

-- 医院检验条码信息表--
DROP TABLE IF EXISTS `L_JYTMXX`;
create table L_JYTMXX(
     `doctadviseno`     bigint(20) not null COMMENT '报告单ID',
     `sampleno`         varchar(15) COMMENT '实验样本号',
     `requester`        varchar(20) COMMENT '申请者/开单医生',
     `requesttime`      datetime(0) NULL DEFAULT NULL COMMENT '开单时间',
     `section`          varchar(10) COMMENT '申请单位/科室',
     `bed_no`           varchar(10) COMMENT '床号',
     `stayhospitalmode` int(2) 	 default 1 not null  COMMENT '在院类型 1门诊2住院3体检',
     `brxz`             varchar(10) COMMENT '病人性质',
     `patientid`        bigint(20) COMMENT '门诊号（就诊卡号）/住院号/病人ID',
     `patientname`      varchar(50) COMMENT '病人姓名',
     `sex`              int(2)     COMMENT '性别',
     `age`              int(4)  COMMENT '年龄',
     `examinaim`	     varchar(100) COMMENT '检验目的',
     `sampletype`       varchar(2)  default 'C' COMMENT '样本类型',
     `result`           varchar(40) COMMENT '结果',
     `SAMPLESTATUS`     CHAR(1) NULL DEFAULT '0' COMMENT '(0 未审核 / 1 已审核 /2 已打印 /3 作废)',
     `CHECKTIME`	     datetime(0) NULL DEFAULT NULL COMMENT '报告时间',
     `checktor`         varchar(20) NULL DEFAULT NULL COMMENT '审核者',
     PRIMARY KEY (`doctadviseno`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '医院检验条码信息' ROW_FORMAT = Dynamic;
insert into L_JYTMXX values(202911111002370,'20201015WSZ1002','王殷玲','2021-12-15 9:24:36','1296','821',2,'2',00000213,'钟雪梅',2,39,'新型冠状病毒抗体检测','Q',
                            '阴性(-)','1',null,null);
insert into L_JYTMXX values(202911111205723,'20211126WWX0016','陈会会','2021-11-16 14:59:57','1311','',1,'3',91247405,'陈时斌',1,38,'新型冠状病毒核酸检测（院前筛查）','Q',
                            '阴性(-)','1',null,null);
insert into L_JYTMXX values(202911111209290,'20211116HSX0022','张宇航','2021-11-26 12:12:22','1020','',1,'3',00002021,'吴安庄',1,52,'新型冠状病毒核酸检测（院前筛查）','Q'
                           ,null,'0',null,null);
insert into L_JYTMXX values(202911111234567,'20211116HSX0012','张宇航','2021-11-27 9:12:32','1052','',1,'3',1,'刘凯',1,18,'新型冠状病毒核酸检测（院前筛查）','Q'
                           ,null,'0',null,null);
insert into L_JYTMXX values(202911112345678,'20211116HSX0013','陈会会','2021-11-27 9:12:32','1053','',1,'3',2,'周俊永',1,18,'新型冠状病毒核酸检测（院前筛查）','Q'
                           ,null,'0',null,null);

-- 报告单 --
DROP TABLE IF EXISTS `l_Report`;
create table `l_Report` (
    `report_id` bigint(20) NOT NULL COMMENT '报告单ID',
    `patient_id` bigint(20) NOT NULL COMMENT '患者ID',
    `create_time`  datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
    `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
    `status` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '报告单状态(0 未审核 / 1 已审核 /2 已打印 /3 作废)',
    `item` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医嘱项目',
    `result` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检验结果',
    `sample_type` CHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '样本类型(0 咽拭子)',
    `receive_time` datetime(0) null default null  COMMENT '接收者',
    `check_time`  datetime(0) NULL DEFAULT NULL COMMENT '报告时间',
    `checktor` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '审核者',
    `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`report_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报告单主业务表' ROW_FORMAT = Dynamic;
INSERT INTO `l_Report` VALUES(123456789,1,'2021-11-30 10:27:26','魔法少女郭德纲','0','新型冠状病毒抗体检测','阴性(-)','Q',NULL,NULL,NULL,'测试数据');
INSERT INTO `l_Report` VALUES(223456789,2,'2021-11-30 10:27:45','魔法少女郭德纲','0','新型冠状病毒抗体检测','阴性(-)','Q',NULL,NULL,NULL,'测试数据');



-- user 用户表 --
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
     `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
     `login_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
     `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户昵称',
     `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '密码',
     `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '00' COMMENT '用户类型（00系统用户 01注册用户）',
     `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '盐加密',
     `status` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
     `del_flag` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
     PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;
INSERT INTO `sys_user` VALUES (1, 'admin', '管理员', '08a9c8816a2e0ca3cd9f6cb68428dad5', '00','QL1CH5','1', '0');
INSERT INTO `sys_user` VALUES (2, 'lzx', 'IT工作人员', '2aaa0baaefcd57338a8682251b2fb472', '00','xNLSNF','1', '0');  -- 123456
INSERT INTO `sys_user` VALUES (100, 'visitor', '访客', '07250c28ed35dcc98b558329d278f77f', '00','kU4Job','1', '0');  -- 123456

-- 告用户书 --
DROP TABLE IF EXISTS `sys_notice`;
create table sys_notice(
   `notice_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章ID',
   `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT "文章内容",
   `user` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '作者',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '文章时间',
   PRIMARY KEY (`notice_id`) USING BTREE
)ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;
insert into sys_notice values(1,"新型冠状病毒肺炎（Corona Virus Disease 2019 简称COVID-19），简称“新冠肺炎”，世界卫生组织命名为“2019冠状病毒病”  ，是指2019新型冠状病毒感染导致的肺炎。2019年12月以来，湖北省武汉市部分医院陆续发现了多例有华南海鲜市场暴露史的不明原因肺炎病例，证实为2019新型冠状病毒感染引起的急性呼吸道传染病",
                              "管理员",SYSDATE());

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
create table `sys_role`(
   `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
   `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
   `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
   `role_sort` int(4) NOT NULL COMMENT '显示顺序',
   `data_scope` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
   `status` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色状态（0正常 1停用）',
   `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
   `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
   `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
   PRIMARY KEY (`role_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', '0', 'admin', '2021-12-10 14:51:00', '超级管理员');
INSERT INTO `sys_role` VALUES (2, '工作人员', 'worker', 2, '2', '0', 'admin', '2021-12-10 14:51:00', '系统工作人员');
INSERT INTO `sys_role` VALUES (3, '普通用户', 'visitor', 3, '3', '0', 'admin', '2021-12-10 14:51:00', '访问者');

-- 用户角色关联表
DROP TABLE IF EXISTS `sys_user_role`;
create table `sys_user_role`(
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (1, 2);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (100, 3);

-- 菜单表（权限表）
DROP TABLE IF EXISTS `sys_menu`;
create table `sys_menu`(
   `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
   `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
   `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
   `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
   `menu_type` CHAR(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
   `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '请求地址',
   `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
   `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
   PRIMARY KEY (`menu_id`) USING BTREE
)ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 1, 'M' ,'#', '',  '系统管理目录');
INSERT INTO `sys_menu` VALUES (2, '报告单管理', 0, 2, 'M' ,'#', '',  '报告单管理目录');

INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'C', '/system/user', 'system:user',  '用户管理目录');
INSERT INTO `sys_menu` VALUES (102, '用户书', 1, 3,  'C', '/system/notice', 'system:notice',  '用户书目录');

INSERT INTO `sys_menu` VALUES (200, '本地报告单', 2, 1, 'C', '/report', 'report',  '本地报告单');
INSERT INTO `sys_menu` VALUES (201, '外入报告单', 2, 2, 'C', '/reportin', 'reportin',  '外入报告单');

INSERT INTO `sys_menu` VALUES (1000, '新增用户', 100, 1, 'F' ,'/system/user/add', 'system:user:add', '系统用户新增');
INSERT INTO `sys_menu` VALUES (1001, '删除用户', 100, 2, 'F' ,'/system/user/delete', 'system:user:delete', '系统用户删除');
INSERT INTO `sys_menu` VALUES (1002, '修改用户', 100, 3, 'F' ,'/system/user/update', 'system:user:update', '系统用户信息更新');
INSERT INTO `sys_menu` VALUES (1003, '用户修改密码', 100, 4, 'F' ,'/system/user/password', 'system:user:password', '系统用户密码修改');
INSERT INTO `sys_menu` VALUES (1020, '用户书显示', 102, 1, 'F' ,'/system/notice/open', 'system:notice:open', '用户书导出');
INSERT INTO `sys_menu` VALUES (1021, '用户书录入', 102, 2, 'F' ,'/system/notice/innotice', 'system:notice:innotice', '用户书录入');

INSERT INTO `sys_menu` VALUES (2001, '报告单录入', 200, 1, 'F', '/report/insert', 'report:insert', '报告单录入');
INSERT INTO `sys_menu` VALUES (2002, '报告单审核', 200, 2, 'F', '/report/check', 'report:check', '报告单审核');
INSERT INTO `sys_menu` VALUES (2003, '报告单状态', 200, 3, 'F', '/report/status', 'report:status', '报告单状态修改,审核报告单');
INSERT INTO `sys_menu` VALUES (2004, '报告单导出excel', 200, 4, 'F','/report/export', 'report:export', '报告单导出excel');
INSERT INTO `sys_menu` VALUES (2005, '报告单导出PDF', 200, 5,'F', '/report/pdf/download', 'report:pdf:download', '报告单导出excel');
INSERT INTO `sys_menu` VALUES (2006, '查询当天报告单', 200, 6, 'F','/report/receive/today', 'report:receive:today', '查询当天报告单');


INSERT INTO `sys_menu` VALUES (2011, '外入报告单主业务', 201, 1, 'F', '/reportin/main', 'reportin:main', '外入报告单主业务');
INSERT INTO `sys_menu` VALUES (2012, '外入报告单查询', 201, 2, 'F', '/reportin/list', 'reportin:list', '外入报告单list');
INSERT INTO `sys_menu` VALUES (2013, '外入报告单导入', 201, 2, 'F', '/reportin/excel', 'reportin:excel', '外入报告单导入');

-- 角色 菜单(权限）关联表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (2, 100);
INSERT INTO `sys_role_menu` VALUES (2, 102);
INSERT INTO `sys_role_menu` VALUES (2, 200);
INSERT INTO `sys_role_menu` VALUES (2, 201);
INSERT INTO `sys_role_menu` VALUES (2, 1002);
INSERT INTO `sys_role_menu` VALUES (2, 1003);
INSERT INTO `sys_role_menu` VALUES (2, 1020);
INSERT INTO `sys_role_menu` VALUES (2, 2001);
INSERT INTO `sys_role_menu` VALUES (2, 2002);
INSERT INTO `sys_role_menu` VALUES (2, 2003);
INSERT INTO `sys_role_menu` VALUES (2, 2004);
INSERT INTO `sys_role_menu` VALUES (2, 2005);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 100);
INSERT INTO `sys_role_menu` VALUES (3, 102);
INSERT INTO `sys_role_menu` VALUES (3, 200);
INSERT INTO `sys_role_menu` VALUES (3, 1002);
INSERT INTO `sys_role_menu` VALUES (3, 1003);
INSERT INTO `sys_role_menu` VALUES (3, 1020);
INSERT INTO `sys_role_menu` VALUES (3, 2005);