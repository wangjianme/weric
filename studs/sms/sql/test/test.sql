declare
begin
	for i in 1..23 loop
	   insert into users
values('U100'||i,'Mary'||i,'87e2a1e158d653c8063ce1de3744d65b',
'd22e18e2b82f4fef97541f5c18556a24',mod(i,2));
	end loop;
	
end;



select clazz_id,clazz_name,clazz_gradeid,grade_name,clazz_majorid,major_name
from clazz inner join grade on clazz_gradeid=grade_id
           inner join major on major_id=clazz_majorid;