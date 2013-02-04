DROP PROCEDURE IF EXISTS `P_BuyParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_BuyParams`(in buyId varchar(32))
BEGIN
	select 
			a.buyCode,
			a.buyDate,
			a.sourceCode,
			a.receiveDate,
			a.note,
			b.supplierName,
			c.companyName
	from t_buy a
	left join t_supplier b on a.supplierId = b.supplierId
	left join (select a.companyName
			from t_systemConfig a) c on 1 = 1
	where a.buyId = buyId;
END
;;
DELIMITER ;

/*
call P_BuyParams ('402880bb3c8542f0013c854975af0001')
*/

