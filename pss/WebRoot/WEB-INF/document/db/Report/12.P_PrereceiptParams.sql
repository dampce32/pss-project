DROP PROCEDURE IF EXISTS `P_PrereceiptParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_PrereceiptParams`(
	IN prereceiptId varchar(32)
)
BEGIN
	select 
		a.prereceiptCode,
		a.prereceiptDate,
		a.amount,
		b.customerName,
		c.bankName,
		d.employeeName,
		a.note,
		e.companyName
	from t_prereceipt a
	left join t_customer b on a.customerId = b.customerId
	left join t_bank c on a.bankId = c.bankId
	left join t_employee d on a.employeeId = d.employeeId
	left join(select *
			from t_systemconfig) e on 1 = 1
	where a.prereceiptId = prereceiptId;
 
END
;;
DELIMITER ;

/*
select *
from t_Prereceipt

CALL P_PrereceiptParams('402880bb3ca975a4013ca9ab4807000b')

*/






