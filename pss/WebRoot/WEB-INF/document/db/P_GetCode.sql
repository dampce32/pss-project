

if exists (select 1 from sysobjects where name = 'P_GetCode' and type = 'P') drop procedure P_GetCode
go
create procedure P_GetCode
	@ModuleName varchar(100),
	@CodeHeader nvarchar(100) = ''
as
	
	select @CodeHeader
go
--exec P_GetCode 'a','a'

Call P_GetCode('a','a')