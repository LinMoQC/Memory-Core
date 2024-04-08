CREATE DATABASE IF NOT EXISTS linmoblog;
use linmoblog;
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
    note_category varchar(10)                  not null comment '文章分类',
    note_tags     varchar(50)                  null comment '文章标签',
    status        varchar(10) default 'public' not null comment '发布状态',
    create_time   datetime                     not null comment '发布时间',
    update_time   datetime                     null,
    is_top        int         default 0        null,
    constraint title
        unique (note_title),
    constraint notes_ibfk_1
        foreign key (note_category) references categories (category_title)
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
        unique (title),
    constraint fk_father_key
        foreign key (father_tag) references tag_level_1 (title)
            on update cascade on delete cascade
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

INSERT IGNORE INTO categories (category_title, introduce, icon, color, path_name)
VALUES ('default', 'This is the introduction for the default category', 'default', '#fff', 'default');

INSERT INTO tag_level_1 (title)
VALUES ('default');

# admin 123456
insert into user (username, password) values ('8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',
                                              '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92');

insert into web_info (user_account,user_password,blog_title,blog_icp,blog_author,blog_description,blog_domain) values ('admin','123456','Memory','粤ICP备XXXXXXXX号-1','Memory','这里是Memory','127.0.0.1');




