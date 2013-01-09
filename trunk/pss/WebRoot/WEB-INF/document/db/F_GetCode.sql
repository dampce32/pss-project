

--获取编号
if exists (select name FROM sysobjects where name = 'F_GetCode' and type = 'FN') drop function F_GetCode
go
create function F_GetCode(@ModuleName varchar(100), @CodeHeader nvarchar(100) = '')
	returns nvarchar(100)
as
begin
	declare @Code nvarchar(100) = '', @SerialNumber char(4)
	if @ModuleName = 'Receive'
	begin--获取后缀
		set @SerialNumber = (select max(substring(ReceiveCode, len(@CodeHeader)+1, len(ReceiveCode))) from T_Receive
							where ReceiveCode like @CodeHeader+'%'
							and len(ReceiveCode) = len(@CodeHeader)+4
							and dbo.F_IsNum(substring(ReceiveCode, len(@CodeHeader)+1, len(ReceiveCode))) = 1)
		
	end
	
	if @SerialNumber is null
		set @Code = upper(@CodeHeader)+'0001'
	else
		set @Code = upper(@CodeHeader)+right('000'+cast((@SerialNumber+1) as varchar),4)
	return @Code
end
go
/*
select dbo.F_GetCode('Receive', 'AHREG002') ReceiveCode
select dbo.F_GetCode('Buy', 'a') BuyCode
select dbo.F_GetCode('QCCode', 'a') QC
select dbo.F_GetCode('MaterialCostCode', 'bbb') materialCost
select dbo.F_GetCode('Work', 'a') WorkCode
select dbo.F_GetCode('Store', 'a') StoreCode
select dbo.F_GetCode('PayCheck', 'a') PayCheckCode
select dbo.F_GetCode('ReceiptCheck', 'a') ReceiptCheckCode
select dbo.F_GetCode('Pay', 'a') PayCode
select dbo.F_GetCode('Receipt', 'a') ReceiptCode
select dbo.F_GetCode('InWarehouse', 'UNTITLE') InWarehouseCode 
select dbo.F_GetCode('PreReceipt', 'UNTITLE') DeliverCode 

*/

select max(substring(ReceiveCode, len('abc')+1, len(ReceiveCode))) from T_Receive