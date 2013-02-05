DROP PROCEDURE IF EXISTS `P_DeliverParams`;
DELIMITER ;;
CREATE  PROCEDURE `P_DeliverParams`(in deliverId varchar(32))
BEGIN
	select 
			a.deliverCode,
			a.deliverDate,
			b.customerName,
			b.phone customerPhone,
			b.fax customerFax,
			b.contacter customerContacter,
			c.warehouseName,
			d.employeeName,
			e.companyName,
			e.companyPhone,
			e.companyFax,
			e.companyAddr
	from t_deliver a
	left join t_customer b on a.customerId = b.customerId
	left join t_warehouse c on a.warehouseId = c.warehouseId
	left join t_employee d on a.employeeId = d.employeeId
	left join (select a.companyName,a.companyPhone,a.companyFax,a.companyAddr
		from t_systemConfig a) e on 1 = 1
  where a.deliverId = deliverId;

	
END
;;
DELIMITER ;

/*
select *
from t_deliver

call P_DeliverParams ('402880bb3ca7f528013ca8472a81000c')
*/