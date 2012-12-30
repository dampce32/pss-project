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