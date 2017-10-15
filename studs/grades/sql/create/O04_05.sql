/**
 * 职称表
 */
CREATE TABLE title(
     title_id VARCHAR(32) PRIMARY KEY,
     title_name VARCHAR(30) UNIQUE,
     title_desc varchar(500)
);
