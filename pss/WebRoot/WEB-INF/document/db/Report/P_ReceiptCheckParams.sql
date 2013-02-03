DROP PROCEDURE IF EXISTS `P_ReceiptCheckParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiptCheckParams`(
	IN customerId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select a.customerName,b.warehouseName
  from(select *
		from t_customer a
		where a.customerId = customerId)a
  left join(select *
		from t_warehouse a 
		where a.warehouseId = warehouseId)b on 1 = 1;
END
;;
DELIMITER ;

/*
CALL P_ReceiptCheckParams('402880bb3c85143a013c852448ea001e','402880bb3c85143a013c852342d80015')

*/


