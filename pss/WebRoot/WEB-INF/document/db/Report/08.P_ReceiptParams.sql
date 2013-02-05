DROP PROCEDURE IF EXISTS `P_ReceiptParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiptParams`(in receiptId varchar(32))
BEGIN
	select 
			a.receiptCode,
			a.receiptDate,
			a.receiptway,
			d.bankName,
			b.customerName,
			c.companyName,
			e.employeeName
	from t_receipt a
	left join t_customer b on a.customerId = b.customerId
	left join (select a.companyName
			from t_systemConfig a) c on 1 = 1
  left join t_bank d on a.bankId = d.bankId
  left join t_employee e on a.employeeId = e.employeeId
	where a.receiptId = receiptId;
END
;;
DELIMITER ;

/*
select *
from t_receipt


call P_ReceiptParams ('402880bb3ca7f528013ca8ee1c230013')

*/

