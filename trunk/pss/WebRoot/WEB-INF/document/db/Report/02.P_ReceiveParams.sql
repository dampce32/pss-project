DROP PROCEDURE IF EXISTS `P_ReceiveParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiveParams`(in receiveId varchar(32))
BEGIN
	select 
			a.ReceiveCode,
			a.receiveDate,
			a.deliverCode,
			a.note,
			b.supplierName,
			c.warehouseName,
			d.employeeName,
			e.companyName
	from t_Receive a
	left join t_supplier b on a.supplierId = b.supplierId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_employee d on a.employeeId = d.employeeId
	left join (select a.companyName
		from t_systemConfig a) e on 1 = 1
  where a.receiveId = receiveId;

	
END
;;
DELIMITER ;

/*
call P_ReceiveParams ('402880bb3c8542f0013c857b66580007')
*/