var PSS={};
//商品颜色
PSS.ColorList = null;
PSS.getColorList = function(){
	if(PSS.ColorList==null){
		var url = 'dict/queryByKindComboboxDataDict.do';
		var content ={kind:'color'};
		PSS.ColorList = syncCallService(url,content);
	}
	return PSS.ColorList;
}
//银行
PSS.BankList = null;
PSS.getBankList = function(){
	if(PSS.BankList==null){
		var url = 'dict/queryComboboxBank.do';
		PSS.BankList = syncCallService(url);
	}
	return PSS.BankList;
}
//经办人
PSS.EmployeeList = null;
PSS.getEmployeeList = function(){
	if(PSS.EmployeeList==null){
		var url = 'dict/queryComboboxEmployee.do';
		PSS.EmployeeList = syncCallService(url);
	}
	return PSS.EmployeeList;
}

//发票类型
PSS.InvoiceTypeList = null;
PSS.getInvoiceTypeList = function(){
	if(PSS.InvoiceTypeList==null){
		var url = 'dict/queryComboboxInvoiceType.do';
		PSS.InvoiceTypeList = syncCallService(url);
	}
	return PSS.InvoiceTypeList;
}