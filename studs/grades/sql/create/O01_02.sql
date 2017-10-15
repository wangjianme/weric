/**
 * 角色表
 */
create table roles(
   role_id varchar(32) primary key,
   role_name varchar(30)
);

/**
 * 角色菜单关系表
 */
create table role_menu(
	rm_roleid varchar(32),
	rm_menuid varchar(32),
	constraint rm_pk primary key(rm_menuid,rm_roleid),
    constraint rm_fk1 foreign key(rm_menuid) references menus(menu_id),
    constraint rm_fk2 foreign key(rm_roleid) references roles(role_id)
);