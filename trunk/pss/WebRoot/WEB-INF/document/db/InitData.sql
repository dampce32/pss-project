use pss
/*
ϵͳȨ��
select '	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , NULL , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is null
union all
select 'union	select '''+RightID+''' , '''+RightName+''' , '''+isnull(RightURL,'')+''' , '''+ParentRightID+''' , '+cast(isLeaf as varchar) 
from T_Right
where parentRightID is not null
*/
insert into T_Right(rightId,rightName,rightUrl,parentRightId,isLeaf)
	   select '402881e53aa31698013aa31fa1980003' , 'ϵͳȨ��' , '' , NULL , 0
union select '402880bb3c27a904013c27e25e860001' , '�ɹ��˻�' , 'outWarehouse/reject.do' , '402881e53be5d01c013be6347b6c0007' , 1
union select '402880c33b020bcc013b02476a450001' , '������Ϣ����' , '' , '402881e53aa31698013aa31fa1980003' , 1
union select '402881e53b046217013b048ffca1000c' , 'ϵͳ����' , '' , '402881e53aa31698013aa31fa1980003' , 0
union select '402881e53b04bb98013b04caff8b0001' , 'Ȩ�޹���' , 'system/right.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53b04bb98013b04cb53a40002' , '��ɫ����' , 'system/role.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53b04bb98013b04cbc01c0003' , '�û�����' , 'system/user.do' , '402881e53b046217013b048ffca1000c' , 1
union select '402881e53baeee40013baef1c5e50001' , '��������' , '' , '402881e53aa31698013aa31fa1980003' , 0
union select '402881e53baeee40013baef4096f0002' , '��Ʒ��ɫ' , 'dict/color.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc2fd9b013bc32896e40001' , '��Ʒ���' , 'dict/size.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc2fd9b013bc329500c0002' , '��Ʒ��λ' , 'dict/unit.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc6b3d4013bc6b4a5c40001' , '��Ʒ���' , 'dict/productType.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53bc82c91013bc832ecca0001' , '��Ʒ' , 'dict/product.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be485f3013be4c875990002' , '��Ӧ��' , 'dict/supplier.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be55e13013be563b45f0001' , '�ֿ�' , 'dict/warehouse.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be58722013be58905b70001' , '����' , 'dict/bank.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be5af4f013be5b04d3a0001' , 'Ա��' , 'dict/employee.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be5d01c013be5d1602e0001' , '��Ʊ����' , 'dict/invoiceType.do' , '402881e53baeee40013baef1c5e50001' , 1
union select '402881e53be5d01c013be6347b6c0007' , '������' , '' , '402881e53aa31698013aa31fa1980003' , 0
union select '402881e53be5d01c013be637f3380008' , '�ɹ����' , 'inWarehouse/receive.do' , '402881e53be5d01c013be6347b6c0007' , 1
union select '402881e53c200d7f013c20164cb60001' , '��ǰ���' , 'inWarehouse/productStore.do' , '402881e53be5d01c013be6347b6c0007' , 1
union select '402881e53c240327013c24ccc6730001' , '�������' , 'inWarehouse/receiveOther.do' , '402881e53be5d01c013be6347b6c0007' , 1
/*
��ɫ
select 'union	select '''+RoleId+''' , '''+RoleName+''''
from T_Role
*/
insert into T_Role(RoleId,RoleName) 
	  select '402881e53aa21d17013aa224b5ed0003' , '��������Ա'
/*
��ɫȨ��
select 'union	select  '''+RoleID+''','''+RightID+''','+cast(state as varchar)
from T_RoleRight
*/	
insert into T_RoleRight(RoleID,RightID,state)
	  select  '402881e53aa21d17013aa224b5ed0003','402880bb3c27a904013c27e25e860001',1
union select  '402881e53aa21d17013aa224b5ed0003','402880c33b020bcc013b02476a450001',0
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
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be5af4f013be5b04d3a0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be5d01c013be5d1602e0001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be5d01c013be6347b6c0007',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53be5d01c013be637f3380008',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53c200d7f013c20164cb60001',1
union select  '402881e53aa21d17013aa224b5ed0003','402881e53c240327013c24ccc6730001',1
/*
�û���Ϣ
select 'union	select '''+userId+''' , '''+userCode+''' , '''+userName+''' , '''+userPwd+''''
from T_User
*/
insert into T_User(userId,userCode,userName,userPwd)
    select '402881e53b046217013b0490d440000d' , 'admin' , '��������Ա' , '21232f297a57a5a743894a0e4a801fc3'
/*
�û���ɫ
select 'union	select '''+userId+''' , '''+roleId+''''
from T_UserRole
*/
insert into T_UserRole(userId,roleId)
	select '402881e53b046217013b0490d440000d' , '402881e53aa21d17013aa224b5ed0003'
	
/*
��Ʒ���
*/
insert into T_ProductType(productTypeID,productTypeCode,productTypeName,parentProductTypeID,isLeaf)
	select 'fda922bf5f2847a89f9fb58727e99600' , '0','��Ʒ���',null,1
/*
��Ʊ����
select 'union	select '''+invoiceTypeId+''' , '''+invoiceTypeName+''''
from T_InvoiceType
*/
insert into T_InvoiceType(invoiceTypeId,invoiceTypeName)
	  select '402881e53be5d01c013be5d1b6b70002' , '����Ʊ'
union select '402881e53be5d01c013be5d1ec720003' , '�ѿ��վ�'
union select '402881e53be5d01c013be5d21c630004' , '��Ʊ3%'
union select '402881e53be5d01c013be5d2465f0005' , '��Ʊ17%'


	
	