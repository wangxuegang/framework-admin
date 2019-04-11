/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/4/11 16:50:42                           */
/*==============================================================*/


drop procedure if exists nextval;

drop table if exists sys_dept;

drop table if exists sys_dict;

drop table if exists sys_log;

drop table if exists sys_menu;

drop table if exists sys_role;

drop table if exists sys_role_dept;

drop table if exists sys_role_menu;

drop table if exists sys_seq;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   dept_id              int(20) not null auto_increment comment '部门ID',
   parent_id            int(20) comment '上级部门ID，一级部门为0',
   name                 varchar(50) comment '部门名称',
   order_num            int(11) comment '排序',
   del_flag             int(4) comment '是否删除  -1：已删除  0：正常',
   primary key (dept_id)
);

alter table sys_dept comment '部门表';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   int(20) not null auto_increment,
   name                 varchar(100) comment '字典名称',
   type                 varchar(100) comment '字典类型',
   code                 varchar(100) comment '字典码',
   value                varchar(1000) comment '字典值',
   order_num            int(11) comment '排序',
   remark               varchar(255) comment '备注',
   del_flag             int(4) comment '删除标记',
   primary key (id),
   unique key type (type)
);

alter table sys_dict comment '字典表';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   int(20) not null auto_increment,
   username             varchar(50) comment '用户名',
   operation            varchar(50) comment '用户操作',
   method               varchar(200) comment '请求方法',
   params               varchar(5000) comment '请求参数',
   time                 int(20) comment '执行时长',
   ip                   varchar(64) comment 'IP地址',
   create_date          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (id)
);

alter table sys_log comment '日志表';

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   menu_id              int(20) not null auto_increment comment '菜单ID',
   parent_id            int(20) comment '父菜单ID，一级菜单为0',
   name                 varchar(50) comment '菜单名称',
   url                  varchar(200) comment '菜单URL',
   perms                varchar(500) comment '授权(多个用逗号分隔，如：user:list,user:create)',
   type                 int(11) comment '类型   0：目录   1：菜单   2：按钮',
   icon                 varchar(50) comment '菜单图标',
   order_num            int(11) comment '排序',
   primary key (menu_id)
);

alter table sys_menu comment '菜单表';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   role_id              int(20) not null auto_increment comment '角色ID',
   role_name            varchar(100) comment '角色名称',
   remark               varchar(100) comment '备注',
   dept_id              int(20) comment '部门ID',
   create_time          datetime default CURRENT_TIMESTAMP comment '创建时间',
   primary key (role_id)
);

alter table sys_role comment '角色';

/*==============================================================*/
/* Table: sys_role_dept                                         */
/*==============================================================*/
create table sys_role_dept
(
   id                   int(20) not null auto_increment,
   role_id              int(20) comment '角色ID',
   dept_id              int(20) comment '部门ID',
   primary key (id)
);

alter table sys_role_dept comment '角色部门表';

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   int(20) not null auto_increment,
   role_id              int(20) comment '角色ID',
   menu_id              int(20) comment '菜单ID',
   primary key (id)
);

alter table sys_role_menu comment '角色菜单表';

/*==============================================================*/
/* Table: sys_seq                                               */
/*==============================================================*/
create table sys_seq
(
   name                 varchar(50) not null,
   start_value          int(11),
   increment_value      int(11),
   remark               varchar(255) comment '备注',
   primary key (name)
);

alter table sys_seq comment '序列表';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   user_id              int(20) not null auto_increment comment '用户ID',
   username             varchar(50) not null comment '用户名',
   password             varchar(100) not null comment '密码',
   salt                 varchar(20) not null comment '盐',
   email                varchar(100) comment '邮箱',
   mobile               varchar(100) comment '手机号',
   status               int(4) comment '状态  0：禁用   1：正常',
   dept_id              int(20) not null comment '部门ID',
   create_time          datetime not null default CURRENT_TIMESTAMP comment '创建时间',
   primary key (user_id),
   unique key username (username)
);

alter table sys_user comment '用户表';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   id                   int(20) not null auto_increment,
   user_id              int(20) comment '用户ID',
   role_id              int(20) comment '角色ID',
   primary key (id)
);

alter table sys_user_role comment '用户角色表';


CREATE FUNCTION nextval(str varchar(50)) RETURNS int(11)  
begin  
    declare i int;  
    set i=(select start_value from sys_seq where name=str);  
    update sys_seq  
        set start_value=i+increment_value  
    where name=str;  
return i;  
end;

INSERT INTO `framework`.`sys_dept` (`dept_id`, `parent_id`, `name`, `order_num`, `del_flag`) VALUES ('1', '0', '总公司', NULL, NULL);
INSERT INTO `framework`.`sys_dept` (`dept_id`, `parent_id`, `name`, `order_num`, `del_flag`) VALUES ('2', '1', '上海分公司', NULL, NULL);


INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('1', '0', '系统管理', NULL, NULL, '0', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('2', '1', '用户管理', NULL, NULL, '1', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('3', '1', '角色管理', NULL, NULL, '1', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('4', '2', '添加用户', NULL, NULL, '2', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('5', '2', '修改用户', NULL, NULL, '2', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('6', '2', '删除用户', NULL, NULL, '2', NULL, NULL);
INSERT INTO `framework`.`sys_menu` (`menu_id`, `parent_id`, `name`, `url`, `perms`, `type`, `icon`, `order_num`) VALUES ('7', '2', '重置密码', NULL, NULL, '2', NULL, NULL);


INSERT INTO `framework`.`sys_role` (`role_id`, `role_name`, `remark`, `dept_id`, `create_time`) VALUES ('1', '超级管理员', 'root', '1', '2019-04-11 17:00:40');


INSERT INTO `framework`.`sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES ('1', '1', '1');


INSERT INTO `framework`.`sys_user` (`user_id`, `username`, `password`, `salt`, `email`, `mobile`, `status`, `dept_id`, `create_time`) VALUES ('1', 'root', '86aed45403064ce6a1587b4242e8f40ead54b749c0e879a98a7fd9670f8cd54f', 'IiAjgVbktztxLGJwTcFj', 'qq@163.com', '136996234324', '1', '1', '2019-04-11 17:32:22');
