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



-- START感谢使用黑鸣SQL数据生成工具 
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('1','福腊','GET','45.248.133.216','myrlvpc59eurgt6@fce4g.top','fautefcn@kkkd9g1.us','0','1','尚叔卡','2024-05-14 01:34:52','2024-05-12 10:20:44','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('3','史球','GET','116.204.111.107','pa6@k7uhnwiplu.io','g2o@in.biz','0','2','钱屏','2024-05-12 01:23:25','2024-05-16 00:27:13','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('2','乔忆翠','GET','103.73.247.252','kw@nk.pro','ukp@si7mmjf.mobi','0','3','轩辕常馁','2024-05-17 15:18:20','2024-05-17 18:13:56','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('4','从搁','GET','103.52.187.27','pz4u03iyyzst@wdso9.im','wea5ca8mbfifqk@67ogy9rjh8iabqt.cc','0','4','舒嗽处','2024-05-17 00:52:53','2024-05-14 20:12:53','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('6','石郎','GET','146.196.112.229','nbuoz@jvf.biz','wecjsoiyo@ym.biz','0','5','靳露映','2024-05-17 21:07:02','2024-05-15 00:53:09','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('5','冉寂','GET','43.243.131.62','pyupmb@ao.net','meye@yrt.biz','0','6','戈艘','2024-05-16 06:50:40','2024-05-12 16:29:57','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('7','柳译糕','GET','118.91.245.69','hfe4vpp1@ehp.com','tkbmzrdfec@ic3.cc','0','7','舒的','2024-05-14 15:44:54','2024-05-13 18:51:07','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('8','桓沏','GET','220.186.134.170','uh6k3e9q@1vqxcx.us','wygovhrt@we4j1g.us','0','8','裴诺','2024-05-12 20:31:03','2024-05-14 13:42:47','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('9','仉坠','GET','103.108.161.138','qccdwx@4v.mobi','uz@u875.top','0','9','茹蛋','2024-05-15 09:17:21','2024-05-13 20:08:23','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('10','爱乓撬','GET','103.59.148.88','qubbfbjdw3n0cw5@3z7a.info','tcz@2c9.us','0','10','冉涵柏','2024-05-15 06:49:42','2024-05-16 11:58:22','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('11','厍搅','GET','113.52.228.252','vnvwn86gwh@41alkyh.org','8ccb@dy.org','0','11','董柏曼','2024-05-10 23:42:50','2024-05-14 00:06:35','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('13','拓跋辞','GET','43.249.238.148','t53y7ra@fdbj.org','jn4qe3c@mid.mobi','0','12','康掠','2024-05-15 04:25:21','2024-05-12 07:29:43','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('12','容折','GET','103.237.69.146','ucddiwufscvq92m@c37kjj.pro','man5v0h@h0l.us','0','13','戎檀珐','2024-05-14 14:15:41','2024-05-13 02:50:55','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('14','郝海莲','GET','119.101.118.218','kheaxxpvaown4b@4l.top','ahtp8@l9lu0z.mobi','0','14','樊旅','2024-05-11 23:39:02','2024-05-13 13:43:12','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('15','闫涛魂','GET','119.165.134.29','sc@flierhquvse.top','ko@g5l.cc','0','15','蒯称','2024-05-17 04:59:10','2024-05-14 14:05:18','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('16','单于惩','GET','103.123.119.112','eosdmam717yvr@q2bu.tv','ia@x9.biz','0','16','巫锥','2024-05-16 08:09:11','2024-05-12 17:10:05','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('17','曹梁','GET','103.142.190.175','387ungua916@al1.biz','bvorksa@kjj.mobi','0','17','臧滓荷','2024-05-13 10:47:44','2024-05-11 07:28:51','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('18','阴咎碗','GET','117.51.217.248','qwot42xjs@i2tns9nsepym.cn','tfk9vezzwn4ps@aa.mobi','0','18','戴花','2024-05-13 04:49:45','2024-05-14 13:45:52','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('19','晋凄','GET','103.47.110.38','t5d@bqlr4xwci.pro','nfrxudnjpnf@ur.biz','0','19','费易','2024-05-14 21:46:36','2024-05-13 04:11:09','1');
INSERT INTO planetcode.tb_api_info (`id`,`name`,`method`,`url`,`request`,`response`,`state`,`user_id`,`description`,`create_time`,`update_time`,`deleted`) VALUES ('20','呼延奎疤','GET','103.31.148.37','ab@gc.biz','yl@tv.us','0','20','奚儿易','2024-05-15 10:34:29','2024-05-14 03:35:03','1');
-- END感谢使用黑鸣SQL数据生成工具
