/**
 * 用户管理
 */
/*创建用户的表管理员表*/
create table users(
	user_id varchar(32) primary key,/*dddd*/
	user_name varchar(30) unique,
	user_pwd varchar(32),
	user_salt varchar(32),
	user_state char(1)/*0:可用，1：禁用*/
);
/**
 *用户与角色对应表
 */
create table user_role(
    ur_userid varchar(32),
    ur_roleid varchar(32),
    constraint ur_pk primary key(ur_userid,ur_roleid),
    constraint ur_fk1 foreign key(ur_userid) references users(user_id),
    constraint ur_fk2 foreign key(ur_roleid) references roles(role_id)
);