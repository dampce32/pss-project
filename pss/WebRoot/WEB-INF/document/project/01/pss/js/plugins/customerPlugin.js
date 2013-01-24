// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.customerInit = function() {
	  var $this = $(this);
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var statusList = [{"value":"-1","text":"所有","selected":true},{"value":"1","text":"有效"},{"value":"0","text":"无效"}]; 
	  $('#status',queryContent).combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:150,
		data:statusList
	  })
	  
	   $('#customerTypeID',editForm).combobox({
		url:'dict/queryComboboxCustomerType.do',
		editable:false,
		valueField:'customerTypeID',
		textField:'customerTypeName',
		width:250
	  })
	  $('#searchBtn',queryContent).click(function(){
		  var status =$('#status',queryContent).combobox('getValue');
		  var customerCode = $('#customerCodeSearch',queryContent).val();
		  var customerName = $('#customerNameSearch',queryContent).val();
		  var url ='dict/queryCustomer.do';
		  var content = {customerCode:customerCode,customerName:customerName,status:status};
		  $(viewList).datagrid({
			url:url,
			queryParams:content,
			pageNumber:1
		  })
	  })
	  //新增
	  var onAdd = function(){
		  $(editDialog).dialog('open');
	  }
	  	//修改
	  var onUpdate = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	  }
	  //修改状态
	  var onStatus = function(text,status){
		  if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		 }
		  if(selectRow.status==status){
			$.messager.alert("提示","该客户已经是"+text+'状态,不能重复修改',"warning");
			return;
		  }
		 $.messager.confirm('确认','确认要将选中客户修改为'+text+'状态吗?',function(r){
			 if(r){
				 var url = 'dict/updateStatusCustomer.do';
				 var content ={customerId:selectRow.customerId,status:status}
				 asyncCallService(url,content,function(result){
					 if(result.isSuccess){
						 selectRow.status = status;
						 $(viewList).datagrid('updateRow',{index:selectIndex,row:selectRow});
					 }
				 })
			 }
		 })
	  }
	  //列表
	  $(viewList).datagrid({
		  singleSelect:true,
		  fit:true,
		  rownumbers:true,
		  pagination:true,
		  pageSize:30,
		  pageList:[30,40,50],
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},
				{text:'启用',iconCls:'icon-ok',handler:function(){onStatus('启用',1)}},
				{text:'禁用',iconCls:'icon-cancel',handler:function(){onStatus('禁用',0)}}
		  ],
		  columns:[[
			    {field:'customerCode',title:'客户编号',width:100,align:"center"},
				{field:'customerName',title:'客户名称',width:200,align:"center"},
				{field:'customerTypeName',title:'客户类型',width:150,align:"center"},
				{field:'contacter',title:'联系人',width:100,align:"center"},
				{field:'phone',title:'联系电话',width:150,align:"center"},
				{field:'status',title:'状态',width:100,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/cancel.png"/>';
						} else {
							return '<img src="style/v1/icons/ok.png"/>';
						}
					}
				},
			    {field:'note',title:'备注',width:300,align:"center"}
		  ]],
		  onDblClickRow:function(rowIndex, rowData){
				onUpdate();
		  },
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
		  }
	 });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑客户',  
	    width:600,
	    height:500,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    	{text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	    	{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}}
	    	]
	});    
	//保存前的赋值操作
	var setValue = function(){
		var customerCode = $.trim($('#customerCode',editForm).val());
		if(customerCode==''){
			$.messager.alert('提示','请填写客户编号','warning');
			return false;
		}
		var customerName = $.trim($('#customerName',editForm).val());
		if(customerName==''){
			$.messager.alert('提示','请填写客户名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveCustomer.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						 $(viewList).datagrid('reload');
						 onExit();
					}
					$.messager.alert('提示','保存成功','info',fn);
					
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	var onExit = function(){
		$(editDialog).dialog('close');
		$(editForm).form('clear');
	}
  }
})(jQuery);   