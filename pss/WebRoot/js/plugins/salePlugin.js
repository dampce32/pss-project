// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.saleInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var bankData = null;
	  var employeeData = null;
	  
	  var queryContent = $('#queryContent',$this);
	  var editDialog = $('#editDialog',$this);
	  
	  var viewList =  $('#viewList',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var statusList = [{"value":"-1","text":"所有","selected":true},{"value":"1","text":"已审"},{"value":"0","text":"未审"}]; 
	  $('#status',queryContent).combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:150,
		data:statusList
	  })
	 $('#customer',editForm).combogrid({rownumbers:true, pagination:true,panelWidth:480,
		 	idField:'customerId',textField:'customerName',mode:'remote',	//此处需要实时查询，要用remote
	        delay:1000,
			columns:[[	
					{field:'customerId',hidden:true}, 
					{field:'customerCode',title:'客户编号',width:80,sortable:true},  
		    		{field:'customerName',title:'客户名称',width:320,sortable:true}  
			]],
			width:150,
			filter:function(q,row){  if(row.name.toUpperCase().indexOf(q.toUpperCase())>=0)return true;  },//自定义的模糊查询	
			onSelect:function(rowIndex, rowData){
					var value = $('#YJXKDM',editDialog).val();
					if(value==rowData.YJXKDM){
						return;	
					}
		    		$('#YJXKDM',editDialog).val(rowData.YJXKDM);
		    		$('#YJXKMC',editDialog).val(rowData.YJXKMC);
		    		//清理二级学科信息
		    		$('#EJXKDM',editDialog).val('');
		    		$('#EJXKMC',editDialog).val('');
		    		$(ejxkCombogrid).combogrid('clear');
		    		asyncCallService('yjxk/getYJXK.do',{YJXKDM:rowData.YJXKDM},
					function(result){
						if(result.success){
							$('#ejxkForm',editDialog).form('load',result);
						}else{
							$.messager.alert('提示','获取数据失败','error');
						}
					})
		    		$(ejxkCombogrid).combogrid({url:'bzm/queryEJXKByPrefixPageBZM.do?LB=EJXK&DM='+rowData.YJXKDM,disabled:false});
		    }
			
		});
	  $('#searchBtn',queryContent).click(function(){
		  var saleCode = $('#saleCode',queryContent).val();
		  var customerName = $('#customer',queryContent).val();
		  var beginDate = $('#beginDate',queryContent).val();
		  var endDate = $('#endDate',queryContent).val();
		  var status = $('#status',queryContent).combobox('getVaule');
		  var url = 'outWarehouse/querySale.do';
		  var queryParams ={saleCode:saleCode,'customer.customerName':customerName,beginDate:beginDate,endDate:endDate,status:status};
		  
		  $(viewList).datagrid({
			url:url,
			queryParams:queryParams,
			pageNumber:1
		  })
	  })
	  var initChoose = function(){
		//供应商
		$('#customer',editForm).combogrid({  
		    panelWidth:450,  
		    idField:'supplierId',  
		    textField:'supplierName',  
		    url:'dict/queryCombogridCustomer.do',  
		    columns:[[  
		        {field:'supplierCode',title:'供应商编号',width:120},
		        {field:'supplierName',title:'供应商名称',width:120}
		    ]]  
		});
		//银行
		if(bankData==null){
			var url = 'dict/queryComboboxBank.do';
			bankData = syncCallService(url);
		}
		$('#bank',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:150,
			data:bankData
		})		  
		//经办人
		if(employeeData==null){
			var url = 'dict/queryComboboxEmployee.do';
			employeeData = syncCallService(url);
		}
		$('#employee',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:150,
			data:employeeData
		})
		//发票类型
		if(invoiceTypeData==null){
			var url = 'dict/queryComboboxInvoiceType.do';
			invoiceTypeData = syncCallService(url);
		}
		$('#invoiceType',editDialog).combobox({
			valueField:'invoiceTypeId',
			textField:'invoiceTypeName',
			width:150,
			data:invoiceTypeData
		})
	  }
	  //新增订单
	  var onAdd = function(){
		$(viewList).datagrid('unselectAll');
		selectIndex==null
		selectRow==null
		$(editForm).form('clear');
		var rows  = $('#saleDetail',editDialog).datagrid('getRows');
		if(rows.length!=0){
			$('#saleDetail',editDialog).datagrid({url:LYS.ClearUrl});
		}
		initChoose();
		$('#otherAmount',editForm).numberbox('setValue', 0.0);
		$('#amount',editForm).numberbox('setValue', 0.0);
		$('#payAmount',editForm).numberbox('setValue', 0.0);
		addBtnStatus();
		$(editDialog).dialog('open');
	  }
	  //修改订单
	  var onUpdate = function(){
		  
	  }
	  //批量删除订单
	  var onMulDelete = function(){
		  
	  }
	  //批量修改订单状态
	  var onMulUpdateStatus = function(status){
		  
	  }
	  //列表
	  $(viewList).datagrid({
		fit:true,
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		rownumbers:true,
		pagination:true,
		toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onMulDelete()}},'-',
				{text:'已审',iconCls:'icon-info',handler:function(){onMulUpdateStatus(1)}},'-',
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}}
		  ],
		columns:[[
		        {field:'saleId',checkbox:true},
				{field:'saleCode',title:'订单单号',width:120,align:"center"},
				{field:'saleDate',title:'订单日期',width:80,align:"center"},
				{field:'deliverDate',title:'订单交期',width:80,align:"center"},
				{field:'sourceCode',title:'原始单号',width:120,align:"center"},
				{field:'customerName',title:'供应商',width:90,align:"center"},
				{field:'otherAmount',title:'运费',width:80,align:"center"},
				{field:'amount',title:'应收定金',width:80,align:"center"},
				{field:'receiptedAmount',title:'实收定金',width:80,align:"center"},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'status',title:'状态',width:80,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else {
							return '<img src="style/v1/icons/info.png"/>';
						}
					}
				}
		  ]],
		onDblClickRow:function(rowIndex, rowData){
			onUpdate();
		  },
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow',selectIndex);
		}
	 });
	var onSave = function(){
		
	}
	var onAdd = function(){
		
	}
	var onDelete = function(){
		
	}
	var onUpdateStatus = function(status){
		
	}
	var onOpenIndex = function(index){
		
	}
	var onExit = function(){
		lastIndex = null;
	    $(editForm).form('clear');
		$('#saleDetail',editDialog).datagrid({url:LYS.ClearUrl});
	}
	//编辑框
	var lastIndex = null;
	$(editDialog).dialog({  
	    title: '编辑订单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){onExit();},
	    toolbar:[	
	 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
	 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
	 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
	 			{id:'pre'+id,text:'上一笔',iconCls:'icon-left',handler:function(){onOpenIndex(-1);}},'-',
	 			{id:'next'+id,text:'下一笔',iconCls:'icon-right',handler:function(){onOpenIndex(1);}},'-',
	 			{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}}
	 	  ]
	});
  }
})(jQuery);   