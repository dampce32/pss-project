DELIMITER ;

DROP PROCEDURE IF EXISTS `p_buyParams`;
DELIMITER ;;
CREATE  PROCEDURE `p_buyParams`(IN buyId_in varchar(32))
BEGIN
	select 
			a.buyCode,
			a.sourceCode,
			a.buyDate,
			a.receiveDate,
			b.supplierName,
			c.employeeName,
			a.note
	from t_buy a
  left join t_supplier b on a.supplierId = b.supplierId
  left join t_employee c on a.employeeId = c.employeeId
	where a.buyId = buyId_in;
END
;;
DELIMITER ;