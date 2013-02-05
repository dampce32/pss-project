DROP PROCEDURE IF EXISTS `P_PayParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_PayParams`(in payId varchar(32))
BEGIN
	select 
			a.payCode,
			a.payDate,
			a.payway,
			d.bankName,
			b.supplierName,
			c.companyName
	from t_pay a
	left join t_supplier b on a.supplierId = b.supplierId
	left join (select a.companyName
			from t_systemConfig a) c on 1 = 1
  left join t_bank d on a.bankId = d.bankId
	where a.payId = payId;
END
;;
DELIMITER ;

/*
select *
from t_pay


call P_PayParams ('402880bb3ca7f528013ca8174f510005')
*/

