DROP PROCEDURE IF EXISTS `P_PayCheckParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_PayCheckParams`(
	IN supplierId varchar(32),
	IN warehouseId varchar(32)
)
BEGIN
	select a.supplierName,b.warehouseName
  from(select *
		from t_supplier a
		where a.supplierId = supplierId)a
  left join(select *
		from t_warehouse a 
		where a.warehouseId = warehouseId)b on 1 = 1;
END
;;
DELIMITER ;

/*
CALL P_PayCheckParams('402880bb3c85143a013c8522deb90012','402880bb3c85143a013c852342d80015')
*/


