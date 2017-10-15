select cls_id as clsId,cls_name as clsName,teac_name as teacName from cls inner join major on cls_major=major_id inner join edusys on major_edusys=edusys_id inner join teac on cls_head=teac_id where 1=1

select * from cls;


select cls_id as clsId,cls_name as clsName,cls_btime as clsBtime,
cls_etime as clsEtime,cls_state as clsState,cls_head as clsHead,
teac_name as teacName,cls_major as clsMajor,major_name as majorName,
edusys_id as edusysId,edusys_name as edusysName 
from cls inner join teac on cls_head=teac_id 
inner join major on cls_major=major_id 
inner join edusys on major_edusys=edusys_id where 1=1
