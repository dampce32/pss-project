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
			b.supplierName
	from t_buy a
	left join t_supplier b on a.supplierId = b.supplierId
	where a.buyId = buyId;
END
;;
DELIMITER ;

/*
call P_BuyParams ('a')
*/