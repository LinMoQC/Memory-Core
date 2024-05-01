ALTER DATABASE memory CHARACTER SET utf8;
use memory;
create table if not exists categories
(
    category_key   int unsigned auto_increment comment '唯一标识'
        primary key,
    category_title varchar(10)            not null comment '分类名',
    introduce      varchar(100)           not null comment '分类介绍',
    icon           varchar(20)            not null comment 'icon',
    color          char(8) default '#fff' not null comment '颜色',
    path_name      varchar(20)            not null,
    constraint categorie_title
        unique (category_title)
)
    comment '分类表';

create table if not exists friends
(
    friend_key  int unsigned auto_increment comment '唯一标识'
        primary key,
    site_name   varchar(20)   not null comment '网站名称',
    avatar      varchar(300)  not null comment '头像',
    site_url    varchar(50)   not null comment '友链',
    description varchar(50)   null comment '自我描述',
    status      int default 0 not null comment '友链状态',
    constraint friends_site_name_uindex
        unique (site_name),
    constraint friends_site_url_uindex
        unique (site_url)
)
    comment '友链表';

create table if not exists images
(
    image_key int unsigned auto_increment comment '唯一标识'
        primary key,
    image_url varchar(300) not null comment '图片链接'
)
    comment '图库表';

create table if not exists notes
(
    note_key      int unsigned auto_increment comment '唯一标识'
        primary key,
    note_title    varchar(50)                  not null comment '文章标题',
    note_content  text                         not null comment '内容',
    description   text                         not null comment '文章描述',
    cover         varchar(300)                 not null comment '封面',
    note_category int                 not null comment '文章分类key',
    note_tags     varchar(50)                  null comment '文章标签',
    status        varchar(10) default 'public' not null comment '发布状态',
    create_time   datetime                     not null comment '发布时间',
    update_time   datetime                     null,
    is_top        int         default 0        null,
    constraint title
        unique (note_title)
)
    comment '文章表';

create index categories_title
    on notes (note_category);

create table if not exists tag_level_1
(
    tag_key int unsigned auto_increment comment '唯一标识'
        primary key,
    title   varchar(20)               not null comment '标签名称',
    level   int     default 2         not null comment '一级标签',
    color   char(8) default '#ffffff' not null comment '标签颜色',
    constraint title
        unique (title)
)
    comment '一级标签表';

create table if not exists tag_level_2
(
    tag_key    int unsigned              not null comment '唯一标识'
        primary key,
    title      varchar(20)               not null comment '标签名称',
    level      int     default 2         not null comment '二级标签',
    color      char(8) default '#ffffff' not null comment '标签颜色',
    father_tag varchar(20)               not null comment '父标签',
    constraint title
        unique (title)
)
    comment '二级标签表';

create table if not exists talks
(
    talk_key    int unsigned auto_increment comment '唯一标识'
        primary key,
    talk_title  varchar(50) not null comment '说说标题',
    content     text        not null comment '说说内容',
    create_time datetime    not null comment '创建时间',
    update_time datetime    null comment '更新时间'
)
    comment '说说表';

create table if not exists user
(
    username varchar(100) not null comment '账号',
    password varchar(100) not null comment '密码',
    constraint password
        unique (password),
    constraint username
        unique (username)
)
    comment '用户';

create table if not exists web_info
(
    id                   int auto_increment
        primary key,
    blog_title           varchar(255) null,
    blog_author          varchar(255) null,
    blog_domain          varchar(255) null,
    blog_description     text         null,
    blog_icp             varchar(255) null,
    user_account         varchar(255) null,
    user_password        varchar(255) null,
    user_avatar          varchar(255) null,
    user_talk            text         null,
    social_github        varchar(255) null,
    social_email         varchar(255) null,
    social_bilibili      varchar(255) null,
    social_qq            varchar(255) null,
    social_netease_cloud varchar(255) null,
    openai_token         varchar(255) null,
    netease_cookies      varchar(255) null,
    github_token         varchar(255) null,
    constraint blog_title
        unique (blog_title)
);

-- spring参数配置表 ，可以将 application 中的一些配置添加到数据库。
-- auto-generated definition
create table if not exists sys_config
(
    id           int auto_increment comment 'id'
        primary key,
    config_name  varchar(128) not null comment '名称',
    config_key   varchar(64)  not null comment 'spring配置属性',
    config_value varchar(256) null comment '值',
    is_private_flag  char         not null comment '1 私用 0 公开'
)comment 'spring参数配置表';
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (1, 'QQ邮箱号', 'spring.mail.username', '', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (2, 'QQ邮箱授权码', 'spring.mail.password', '', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (6, '本地存储启用状态', 'local.enable', 'true', '0');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (7, '阿里云存储启用状态', 'ali.enable', 'false', '0');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (10, '阿里云-accessKey', 'ali.accessKey', '', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (11, '阿里云-secretKey', 'ali.secretKey', '', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (12, '阿里云-bucket', 'ali.bucket', 'wkq-img', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (13, '阿里云-endpoint', 'ali.endpoint', 'oss-cn-chengdu.aliyuncs.com', '0');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (16, '阿里云-上传路径', 'ali.uploadPath', 'blog/pic/', '0');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (17, '本地存储-上传路径', 'local.uploadDir', 'upload-dir', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (19, 'JWT-key(设置复杂一点，否则会报错)', 'jwt.key', 'sdfasdfasdfq2w2easdfajsiodfhasuidhfasopidfhasiopdfuasidfasdfasdf', '1');
INSERT INTO sys_config (id, config_name, config_key, config_value, is_private_flag) VALUES (21, 'JWT-过期时间(毫秒)', 'jwt.expire', '86400000', '0');



INSERT INTO categories (category_title, introduce, icon, color, path_name)
VALUES ('default', 'This is the introduction for the default category', 'default', '#fff', 'default');

INSERT INTO tag_level_1 (title)
VALUES ('default');

INSERT INTO web_info (user_account, user_password, blog_title,blog_author, blog_domain,blog_icp,blog_description,user_avatar)
VALUES ('admin', '123456', 'Memory', 'Memory','127.0.0.1','粤ICP备XXXXXXXX号-1','这里是Memory','https://cdn.jsdelivr.net/gh/LinMoQC/gitTest@master/Cache_4d1f4da2034512f3..jpg');



# admin 123456
insert into user (username, password) values ('8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
                                              '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');

INSERT INTO notes (note_title, note_content, description, cover, note_category, note_tags, status, create_time, update_time, is_top) VALUES ('Hi！Memory', '## Memory\r\n\r\n欢迎使用 **Memory** 个人主题博客。本项目采用 **React + SpringBoot** 前后端分离，以优美的动画和可爱的画风为主题，由作者**林陌青川**维护。目前已支持使用 Docker 上线。\r\n\r\n## 创作初心\r\n\r\n作为我的处女作品，我对 **Memory** 投入了许多时间。从 23 年年底开始一直到 24 年的清明节才算是正式完工。也许 **Memory** 并不完美，但也是我的呕心沥血之作。想起创作期间无数个凌晨，碰到 bug 一写就是一晚上。现在回头望去，留给我的已经是珍贵的回忆。\r\n\r\n## 为什么起名 Memory?\r\n\r\n关于 **Memory** 的背后其实有一段悲伤的故事。24 年年初因为一些琐事，我没能回去贵州，而我的外祖母在今年的 3 月初不幸意外离世，而我却没有见到她最后一面……\r\n\r\n## 作者寄语\r\n\r\n如果你觉得 **Memory** 并不完美，那么就去构建一个属于你自己的 **\"Memory\"** 吧。本项目将在 **V2** 版本重构采用 **NextJS**，作者再次承诺后续所有版本皆是开源。\r\n\r\n## Star History \r\n\r\n![star](https://api.star-history.com/svg?repos=LinMoQC/Memory-Blog&type=Date&theme=dark)\r\n\r\n', '给所有用户的一封信', 'http://8.210.172.244:8080/upload/540a301a-50fd-472a-bac8-020fac650d08.jpg', '1', '1', 'public', '2024-04-10 05:31:00', '2024-04-10 05:48:59', '0');
