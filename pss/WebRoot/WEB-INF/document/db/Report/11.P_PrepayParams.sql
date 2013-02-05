DROP PROCEDURE IF EXISTS `P_PrepayParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_PrepayParams`(
	IN prepayId varchar(32)
)
BEGIN
	select 
		a.prepayCode,
		a.prepayDate,
		a.amount,
		b.supplierName,
		c.bankName,
		d.employeeName,
		a.note,
		e.companyName
	from t_prepay a
	left join t_supplier b on a.supplierId = b.supplierId
	left join t_bank c on a.bankId = c.bankId
	left join t_employee d on a.employeeId = d.employeeId
	left join(select *
			from t_systemconfig) e on 1 = 1
	where a.prepayId = prepayId;
 
END
;;
DELIMITER ;

/*
select *
from t_Prepay

CALL P_PrepayParams('402880bb3ca975a4013ca981eaba0006')

*/






