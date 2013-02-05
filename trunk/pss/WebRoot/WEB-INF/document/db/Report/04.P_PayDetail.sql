DROP PROCEDURE IF EXISTS `P_PayDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_PayDetail`(in payId varchar(32))
BEGIN
	select 
		a.sourceCode,
		a.payKind,
		a.sourceDate,
		a.amount,
		a.payedAmount,
		a.discountAmount,
		a.payAmount
  from t_paydetail a 
	where a.payId = payId;
 
END
;;
DELIMITER ;
/*
select *
from t_pay

call P_PayDetail('402880bb3ca7f528013ca8174f510005')

*/