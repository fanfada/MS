-- ----------------------------
-- 1、用户信息表
-- ----------------------------
drop table if exists sys_user;
create table sys_user
(
    user_id     varchar(32) not null comment '用户ID',
    user_name   varchar(30) not null comment '用户账号',
    nick_name   varchar(30) not null comment '用户昵称',
    user_type   varchar(2)   default '00' comment '用户类型（00系统用户）',
    email       varchar(50)  default '' comment '用户邮箱',
    phonenumber varchar(11)  default '' comment '手机号码',
    sex         char(1)      default '0' comment '用户性别（0男 1女 2未知）',
    password    varchar(100) default '' comment '密码',
    status      TINYINT(1) default '0' comment '帐号状态（0正常 1停用）',
    del_flag    char(1)      default '0' comment '删除标志（0代表存在 2代表删除）',
    login_date  datetime comment '最后登录时间',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(500) default null comment '备注',
    primary key (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '用户信息表';

alter table sys_user add role_id  varchar(32) not null comment '角色ID';

-- ----------------------------
-- 2、房屋信息表
-- ----------------------------
drop table if exists sys_room;
CREATE TABLE sys_room
(
    room_id     INT          NOT NULL AUTO_INCREMENT COMMENT '房屋ID',
    color       VARCHAR(10)  NOT NULL COMMENT '红黑榜：推荐、不推荐、还行',
    room_type   VARCHAR(50)  DEFAULT NULL COMMENT '房屋类型：合租、整租',
    city        VARCHAR(50)  DEFAULT NULL COMMENT '城市',
    address     VARCHAR(100) NOT NULL COMMENT '房屋地址',
    status      TINYINT(1) DEFAULT 0 COMMENT '状态（0正常 1停用）',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(50)  DEFAULT NULL COMMENT '更新者',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '备注',
    owner_id    int          NOT NULL COMMENT '房东ID',
    PRIMARY KEY (room_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '房屋信息表';

INSERT INTO `baddb`.`sys_room` (`color`, `room_type`, `country`, `province`, `city`, `address`, `status`, `create_time`,
                                `update_by`, `update_time`, `remark`, `owner_id`)
VALUES ('推荐', '合租', '中国', '江苏', '苏州', '龙惠花苑1000', 0, '2024-11-06 16:07:09', NULL, '2024-11-06 16:07:09', '推荐', 2);


select count(1) from sys_room;

-- ----------------------------
-- 3、房东信息表
-- ----------------------------
drop table if exists sys_owner;
CREATE TABLE sys_owner
(
    owner_id    INT         NOT NULL AUTO_INCREMENT COMMENT '房东ID',
    phone       VARCHAR(15)  DEFAULT NULL COMMENT '联系方式',
    owner_type  VARCHAR(50)  DEFAULT NULL COMMENT '房东类型：个人房东、二房东',
    color       VARCHAR(50) NOT NULL COMMENT '红黑榜：推荐、不推荐、还行',
    status      TINYINT(1) DEFAULT 0 COMMENT '状态（0正常 1停用）',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by   VARCHAR(50)  DEFAULT NULL COMMENT '更新者',
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    remark      VARCHAR(500) DEFAULT NULL COMMENT '推荐或者不推荐理由',
    PRIMARY KEY (owner_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '房东信息表';


INSERT INTO `baddb`.`sys_owner` (`phone`, `owner_type`, `color`, `status`, `create_time`, `update_by`, `update_time`,
                                 `remark`)
VALUES ('19999999999', '二房东', '不推荐', 0, '2024-11-06 16:07:38', NULL, '2024-11-06 16:07:38',
        '二房东，太坑了，不推荐');


select * from sys_owner;

-- ----------------------------
-- 4、角色信息表
-- ----------------------------
drop table if exists sys_role;
create table sys_role
(
    role_id     varchar(32) not null comment '角色ID',
    role_name   varchar(30) not null comment '角色名称',
    role_key    varchar(20) not null comment '角色权限字符串',
    data_scope  char(1)      default '1' comment '数据范围（1：全部数据权限 2：自定数据权限）',
    status      TINYINT(1) not null comment '角色状态（0正常 1停用）',
    create_by   varchar(64)  default '' comment '创建者',
    create_time datetime comment '创建时间',
    update_by   varchar(64)  default '' comment '更新者',
    update_time datetime comment '更新时间',
    remark      varchar(500) default null comment '备注',
    primary key (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色信息表';

-- ----------------------------
-- 初始化-角色信息表数据
-- ----------------------------
insert into sys_role values ('admin', '超级管理员', 'admin', '1', 0, 'admin', sysdate(), '', null, '超级管理员');


-- ----------------------------
-- 6、角色和城市关联表
-- ----------------------------
drop table if exists sys_role_auth;
create table sys_role_auth
(
   role_id   VARCHAR(32) not null comment '角色ID',
   zipcode   VARCHAR(20) default '000000' comment '城市邮政编码，000000为超级管理员用户权限，可看所有城市'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色和城市关联表';

-- ----------------------------
-- 7、系统访问记录
-- ----------------------------
drop table if exists sys_login_info;
create table sys_login_info (
     info_id        bigint(20)     not null auto_increment   comment '访问ID',
     user_name      varchar(50)    default ''                comment '用户账号',
     ipaddr         varchar(128)   default ''                comment '登录IP地址',
     login_location varchar(255)   default ''                comment '登录地点',
     browser        varchar(50)    default ''                comment '浏览器类型',
     os             varchar(50)    default ''                comment '操作系统',
     status         char(1)        default '0'               comment '登录状态（0成功 1失败）',
     msg            varchar(255)   default ''                comment '提示消息',
     login_time     datetime                                 comment '访问时间',
     primary key (info_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '系统访问记录';


-- ----------------------------
-- 8、操作日志记录
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
      oper_id           bigint(20)      not null auto_increment    comment '日志主键',
      title             varchar(50)     default ''                 comment '模块标题',
      business_type     int(2)          default 0                  comment '业务类型（0其它 1新增 2修改 3删除）',
      method            varchar(100)    default ''                 comment '方法名称',
      request_method    varchar(10)     default ''                 comment '请求方式',
      operator_type     int(1)          default 0                  comment '操作类别（0其它 1后台用户 2手机端用户）',
      oper_name         varchar(50)     default ''                 comment '操作人员',
      oper_url          varchar(255)    default ''                 comment '请求URL',
      oper_ip           varchar(128)    default ''                 comment '主机地址',
      oper_location     varchar(255)    default ''                 comment '操作地点',
      oper_param        varchar(2000)   default ''                 comment '请求参数',
      json_result       varchar(2000)   default ''                 comment '返回参数',
      status            int(1)          default 0                  comment '操作状态（0正常 1异常）',
      error_msg         varchar(2000)   default ''                 comment '错误消息',
      oper_time         datetime                                   comment '操作时间',
      cost_time         bigint(20)      default 0                  comment '消耗时间',
      primary key (oper_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '操作日志记录';


-- ----------------------------
-- 9、权限字典表
-- ----------------------------
drop table if exists sys_auth_dict;
create table sys_auth_dict
(
    dict_code        int             not null auto_increment    comment '字典编码',
    dict_label       varchar(100)    default ''                 comment '字典标签',
    dict_value       varchar(100)    default ''                 comment '字典键值',
    status           char(1)         default '0'                comment '状态（0正常 1停用）',
    create_by        varchar(64)     default ''                 comment '创建者',
    create_time      datetime                                   comment '创建时间',
    update_by        varchar(64)     default ''                 comment '更新者',
    update_time      datetime                                   comment '更新时间',
    remark           varchar(255)    default null               comment '备注',
    primary key (dict_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '字典数据表';

INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('room', 'room:all', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('room', 'room:query', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('room', 'room:add', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('room', 'room:edit', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('room', 'room:delete', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('owner', 'owner:all', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('owner', 'owner:query', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('owner', 'owner:add', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('owner', 'owner:edit', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
INSERT INTO sys_auth_dict (dict_label, dict_value, status, create_by, create_time, update_by, update_time, remark) VALUES ('owner', 'owner:delete', '0', 'super', '2024-11-23 22:39:43', 'super', '2024-11-23 22:39:49', '内置权限字符');
