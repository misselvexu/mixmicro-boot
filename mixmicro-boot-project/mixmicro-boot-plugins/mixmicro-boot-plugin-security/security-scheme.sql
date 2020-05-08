CREATE TABLE `mixmicro_boot_user_info`
(
    `UI_ID`          int(11)   NOT NULL AUTO_INCREMENT COMMENT '用户编号，主键自增',
    `UI_USER_NAME`   varchar(30)    DEFAULT NULL COMMENT '用户名',
    `UI_NICK_NAME`   varchar(50)    DEFAULT NULL COMMENT '用户昵称',
    `UI_PASSWORD`    varchar(255)   DEFAULT NULL COMMENT '用户密码',
    `UI_EMAIL`       varchar(30)    DEFAULT NULL COMMENT '用户邮箱地址',
    `UI_AGE`         int(11)        DEFAULT NULL COMMENT '用户年龄',
    `UI_ADDRESS`     varchar(200)   DEFAULT NULL COMMENT '用户地址',
    `UI_IS_LOCKED`   char(1)        DEFAULT 'N' COMMENT '是否锁定',
    `UI_IS_ENABLED`  char(1)        DEFAULT 'Y' COMMENT '是否启用',
    `UI_STATUS`      char(1)        DEFAULT 'O' COMMENT 'O：正常，D：已删除',
    `UI_CREATE_TIME` timestamp NULL DEFAULT current_timestamp() COMMENT '用户创建时间',
    PRIMARY KEY (`UI_ID`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT ='默认的用户信息表';