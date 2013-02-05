DROP PROCEDURE IF EXISTS `P_RejectParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_RejectParams`(in rejectId varchar(32))
BEGIN
	select 
			a.rejectCode,
			a.rejectDate,
			a.note,
			b.supplierName,
			c.warehouseName,
			d.employeeName,
			e.companyName
	from t_reject a
	left join t_supplier b on a.supplierId = b.supplierId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_employee d on a.employeeId = d.employeeId
	left join (select a.companyName
		from t_systemConfig a) e on 1 = 1
  where a.rejectId = rejectId;

	
END
;;
DELIMITER ;

/*
select *
from t_reject



call P_RejectParams ('402880bb3c8a3478013c8a39fa9e000b')
*/