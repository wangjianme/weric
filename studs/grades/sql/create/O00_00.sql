/**
 * 菜单表
 */
create table menus(
	menu_id varchar(32) primary key,
	menu_text varchar(50),
	menu_url varchar(100),
	menu_state varchar(10),
	menu_parent varchar(32)
);

 
/**
测试模块
*/
create table example(
   example_id varchar(32) primary key,
   example_name varchar(30),
   example_addr varchar(200),
   example_sex char(1)
);