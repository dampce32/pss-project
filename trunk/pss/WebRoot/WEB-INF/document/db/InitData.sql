/*
系统权限
select 'union	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , NULL , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is null
union all
select 'union	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , '''+ParentRightID+''' , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is not null
*/
insert into T_Right(RightID,RightName,RightURL,ParentRightID,IsLeaf)
	  select '402881e53aa31698013aa31fa1980003' , '系统权限' , '' , NULL , 1
/*
角色
select 'union	select '''+RoleId+''' , '''+RoleName+''''
from T_Role
*/
insert into T_Role(RoleId,RoleName) 
	  select '402881e53aa21d17013aa224b5ed0003' , '超级管理员'
/*
角色权限
select 'union	select  '''+RoleID+''','''+RightID+''','+cast(state as varchar)
from T_RoleRight
*/	
insert into T_RoleRight(RoleID,RightID,state)
	  select  '402881e53aa21d17013aa224b5ed0003','402881e53aa31698013aa31fa1980003',0
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b046217013b048ffca1000c',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04caff8b0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04cb53a40002',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04cbc01c0003',1
/*
用户信息
select 'union	select '''+userId+''' , '''+userCode+''' , '''+userName+''' , '''+userPwd+''''
from T_User
*/
insert into T_User(userId,userCode,userName,userPwd)
    select '402881e53b046217013b0490d440000d' , 'admin' , '超级管理员' , '21232f297a57a5a743894a0e4a801fc3'
/*
用户角色
select 'union	select '''+userId+''' , '''+roleId+''''
from T_UserRole
*/
insert into T_UserRole(userId,roleId)
	select '402881e53b046217013b0490d440000d' , '402881e53aa21d17013aa224b5ed0003'