/*
系统权限
select '	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , NULL , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is null
union all
select 'union	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , '''+ParentRightID+''' , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is not null
*/
insert into T_Right(rightId,rightName,rightUrl,parentRightId,isLeaf)
	  select '402881e53aa31698013aa31fa1980003' , '系统权限' , '' , NULL , 0
union select '402880c33b020bcc013b02476a450001' , '个人信息管理' , '' , '402881e53aa31698013aa31fa1980003' , 1
union select '402881e53b046217013b048ffca1000c' , '系统管理' , '' , '402881e53aa31698013aa31fa1980003' , 0
union select '402881e53b04bb98013b04caff8b0001' , '权限管理' , 'system/right.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53b04bb98013b04cb53a40002' , '角色管理' , 'system/role.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53b04bb98013b04cbc01c0003' , '用户管理' , 'system/user.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53baeee40013baef1c5e50001' , '基础数据' , '' , '402881e53aa31698013aa31fa1980003' , 0
union select '402881e53baeee40013baef4096f0002' , '商品颜色' , 'dict/color.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc2fd9b013bc32896e40001' , '商品规格' , 'dict/size.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc2fd9b013bc329500c0002' , '商品单位' , 'dict/unit.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc6b3d4013bc6b4a5c40001' , '商品类别' , 'dict/productType.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc82c91013bc832ecca0001' , '商品' , 'dict/product.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be485f3013be4c875990002' , '供应商' , 'dict/supplier.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be55e13013be563b45f0001' , '仓库' , 'dict/warehouse.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be58722013be58905b70001' , '银行' , 'dict/bank.do' , '402881e53baeee40013baef1c5e50001' , 1
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
	  select  '402881e53aa21d17013aa224b5ed0003','402880c33b020bcc013b02476a450001',0
union select  '402881e53aa21d17013aa224b5ed0003','402881e53aa31698013aa31fa1980003',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b046217013b048ffca1000c',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04caff8b0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04cb53a40002',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53b04bb98013b04cbc01c0003',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53baeee40013baef1c5e50001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53baeee40013baef4096f0002',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53bc2fd9b013bc32896e40001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53bc2fd9b013bc329500c0002',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53bc6b3d4013bc6b4a5c40001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53bc82c91013bc832ecca0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be485f3013be4c875990002',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be55e13013be563b45f0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be58722013be58905b70001',1
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
	
/*
商品类别
select 'union	select '''+productTypeID+''' , '''+productTypeCode+''' , '''+productTypeName+''',null'
from T_ProductType
where parentProductTypeID is null
*/
insert into T_ProductType(productTypeID,productTypeCode,productTypeName,parentProductTypeID)
	select 'fda922bf5f2847a89f9fb58727e99600' , '0','商品类别',null
