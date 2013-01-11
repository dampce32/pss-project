--²âÊÔÊý¾Ý
use pss

/*
ÑÕÉ«
select '	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , NULL , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is null
union all
select 'union	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , '''+ParentRightID+''' , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is not null
*/