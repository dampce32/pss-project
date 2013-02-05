DROP PROCEDURE IF EXISTS `P_DeliverRejectParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_DeliverRejectParams`(in deliverRejectId varchar(32))
BEGIN
	select 
			a.deliverRejectCode,
			a.deliverRejectDate,
			a.note,
			b.customerName,
			c.warehouseName,
			d.employeeName,
			e.companyName
	from t_deliverReject a
	left join t_customer b on a.customerId = b.customerId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_employee d on a.employeeId = d.employeeId
	left join (select a.companyName
		from t_systemConfig a) e on 1 = 1
  where a.deliverRejectId = deliverRejectId;

	
END
;;
DELIMITER ;

/*
select *
from t_deliverReject



call P_DeliverRejectParams ('402880bb3ca7f528013ca91f5fa30017')
*/