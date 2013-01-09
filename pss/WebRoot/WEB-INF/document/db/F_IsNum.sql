

--ÅÐ¶Ï×Ö·û´®ÊÇ·ñÕûÐÎ
if exists (select name FROM sysobjects where name = 'F_IsNum' AND type = 'FN') drop function F_IsNum
go
create function F_IsNum(@val nvarchar(100))
	returns bit
as       
begin
	declare @length int = len(@val), @i int = 1
	if @length = 0
		return 0
	else
	begin
		while @i <= @length
		begin
			if((select ascii(substring(@val,@i,1))) < 48 or (select ascii(substring(@val,@i,1)))>57)
				return 0
			set @i=@i+1
		end
	end     
	return 1
end
go
--select dbo.F_IsNum('1.1')