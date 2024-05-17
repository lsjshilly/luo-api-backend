use planetcode;

create table if not exists tb_user
(
    id          bigint primary key auto_increment comment '主键ID',
    username    varchar(256) unique                    not null comment '用户名',
    password    varchar(256)                           null comment '密码',
    phone       varchar(32)                            null comment '手机',
    email       varchar(64)                            null comment '邮箱',
    state       tinyint                                null comment '状态',
    roles       varchar(64) default '["user::common"]' null comment '权限',
    nickname    varchar(256)                           not null comment '昵称',
    avatar      varchar(256)                           null comment '头像',
    gender      tinyint                                null comment '性别',
    tags        varchar(1024) comment '标签',
    city        varchar(128) comment '地址',
    description varchar(256) comment '个人介绍',
    birthday    date comment '生日',
    create_time datetime    default CURRENT_TIMESTAMP  null comment '创建时间',
    update_time datetime    default CURRENT_TIMESTAMP  null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint     default 1                  null comment '删除'

);

create table if not exists tb_tags
(
    id          bigint primary key auto_increment not null comment '主键id',
    name        varchar(256) unique               not null comment '标签名称',
    parent_id   bigint                            null comment '父标签id',
    is_parent   tinyint  default 0 comment '是否为父标签 0 非父标签',
    create_time datetime default CURRENT_TIMESTAMP comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 1                null comment '删除'
)


create table planetcode.tb_api_info
(
    id          bigint auto_increment primary key comment '主键ID',
    name        varchar(64)                        not null not null comment 'api名称',
    method      varchar(64)                        not null comment '请求方式',
    url         varchar(32)                        not null comment '接口地址',
    request     text                               null comment '请求信息',
    response    text                               null comment '响应信息',
    state       tinyint  default 0 comment '0 关闭 1 开启',
    user_id     bigint comment '用户ID',
    description varchar(256)                       null comment '描述信息',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     tinyint  default 1                 null comment '删除'
);
