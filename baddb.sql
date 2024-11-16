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
-- 5、城市邮编表
-- ----------------------------
drop table if exists sys_city_code;
CREATE TABLE sys_city_code
(
    city    VARCHAR(100) not null comment '城市中文名称', -- 存储城市名称
    zipcode VARCHAR(20)  not null comment '城市邮政编码'  -- 存储邮政编码
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '城市邮编表';

INSERT INTO sys_city_code (city, zipcode)
VALUES ('北京', '100000'), -- 北京
       ('上海', '200000'), -- 上海
       ('广州', '510000'), -- 广州
       ('深圳', '518000'), -- 深圳
       ('成都', '610000'), -- 成都
       ('杭州', '310000'), -- 杭州
       ('南京', '210000'), -- 南京
       ('武汉', '430000'), -- 武汉
       ('重庆', '400000'), -- 重庆
       ('苏州', '215000'), -- 苏州
       ('天津', '300000'), -- 天津
       ('长沙', '410000'), -- 长沙
       ('郑州', '450000'), -- 郑州
       ('沈阳', '110000'), -- 沈阳
       ('大连', '116000'), -- 大连
       ('青岛', '266000'), -- 青岛
       ('厦门', '361000'); -- 厦门


-- ----------------------------
-- 6、角色和城市关联表
-- ----------------------------
drop table if exists sys_role_city;
create table sys_role_city
(
   role_id   VARCHAR(32) not null comment '角色ID',
   zipcode   VARCHAR(20) default '000000' comment '城市邮政编码，000000为超级管理员童用户权限，可看所有城市'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '角色和城市关联表';

