var PSS={};
//商品规格
PSS.SizeList = null;
PSS.getSizeList = function(){
	if(PSS.SizeList==null){
		var url = 'dict/queryByKindComboboxDataDict.do';
		var content ={kind:'size'};
		PSS.SizeList = syncCallService(url,content);
	}
	return PSS.SizeList;
}
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
//商品单位
PSS.UnitList = null;
PSS.getUnitList = function(){
	if(PSS.UnitList==null){
		var url = 'dict/queryByKindComboboxDataDict.do';
		var content ={kind:'unit'};
		PSS.UnitList = syncCallService(url,content);
	}
	return PSS.UnitList;
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
//仓库
PSS.WarehouseList = null;
PSS.getWarehouseList = function(){
	if(PSS.WarehouseList==null){
		var url = 'dict/queryComboboxWarehouse.do';
		PSS.WarehouseList = syncCallService(url);
	}
	return PSS.WarehouseList;
}
//快递
PSS.ExpressList = null;
PSS.getExpressList = function(){
	if(PSS.ExpressList==null){
		var url = 'dict/queryComboboxExpress.do';
		PSS.ExpressList = syncCallService(url);
	}
	return PSS.ExpressList;
}