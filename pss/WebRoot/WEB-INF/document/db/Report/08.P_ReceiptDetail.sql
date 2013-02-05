DROP PROCEDURE IF EXISTS `P_ReceiptDetail`;
DELIMITER ;;
CREATE  PROCEDURE `P_ReceiptDetail`(in receiptId varchar(32))
BEGIN
	select 
		a.sourceCode,
		a.receiptKind,
		a.sourceDate,
		a.amount,
		a.receiptedAmount,
		a.discountAmount,
		a.receiptAmount
  from t_receiptdetail a 
	where a.receiptId = receiptId;
 
END
;;
DELIMITER ;
/*
select *
from t_receipt

call P_ReceiptDetail('402880bb3ca7f528013ca8ee1c230013')

*/