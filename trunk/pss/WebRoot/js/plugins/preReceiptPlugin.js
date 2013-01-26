// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.preReceiptInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var statusList = [{"value":"-1","text":"所有","selected":true},{"value":"1","text":"已审"},{"value":"0","text":"未审"}]; 
	  $('#status',queryContent).combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:150,
		data:statusList
	  })
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  nowrap:true,
		  striped: true,
		  collapsible:true,
		  rownumbers:true,
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onMulDelete()}},'-',
				{text:'已审',iconCls:'icon-info',handler:function(){onMulUpdateStatus(1)}},'-',
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}}
		  ],
		  columns:[[
		        {field:'preReceiptId',checkbox:true},
				{field:'preReceiptCode',title:'预收单号',width:150,align:"center"},
				{field:'customerName',title:'客户',width:90,align:"center"},
				{field:'preReceiptDate',title:'收款日期',width:80,align:"center"},
				{field:'amount',title:'付款金额',width:80,align:"center"},
				{field:'checkAmount',title:'已对账金额',width:80,align:"center"},
				{field:'balanceAmount',title:'余额',width:80,align:"center"},
				{field:'bankName',title:'银行',width:90,align:"center"},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'status',title:'状态',width:80,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else {
							return '<img src="style/v1/icons/info.png"/>';
						}
					}}
		  ]],
		  onDblClickRow:function(rowIndex, rowData){
				onUpdate();
		  },
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
				$(viewList).datagrid('unselectAll');
				$(viewList).datagrid('selectRow',selectIndex);
		  }
	 });
	 //点击查询按钮
	 $('#searchBtn',queryContent).click(function(){
		  var preReceiptCode = $('#preReceiptCode',queryContent).val();
		  var customerName = $('#customer',queryContent).val();
		  var beginDate = $('#beginDate',queryContent).val();
		  var endDate = $('#endDate',queryContent).val();
		  var status = $('#status',queryContent).combobox('getValue');
		  var url = 'finance/queryPreReceipt.do';
		  var queryParams ={preReceiptCode:preReceiptCode,'customer.customerName':customerName,beginDate:beginDate,endDate:endDate,status:status};
		  $(viewList).datagrid({
			url:url,
			queryParams:queryParams,
			pageNumber:1
		  })
	  })
	  //重置按钮
	  $('#resetBtn',queryContent).click(function(){
		  $('#preReceiptCode',queryContent).val('');
		  $('#customer',queryContent).val('');
		  $('#beginDate',queryContent).val('');
		  $('#endDate',queryContent).val('');
		  $('#status',queryContent).combobox('setValue',-1);
	  })
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑预收单',  
	    width:900,
	    height:400,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	$(editForm).form('clear');
			$(prepayDetail).datagrid({url:LYS.ClearUrl});
	    },
	    toolbar:[	
	 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
	 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
	 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
	 			{id:'pre'+id,text:'上一笔',iconCls:'icon-left',handler:function(){onOpenIndex(-1);}},'-',
	 			{id:'next'+id,text:'下一笔',iconCls:'icon-right',handler:function(){onOpenIndex(1);}},'-',
	 			{text:'退出',iconCls:'icon-cancel',handler:function(){
	 					$(editDialog).dialog('close');
	 				}
	 			}
	 	  ]
	}); 
	//新增时，按钮的状态
	var addBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#add'+id).linkbutton('disable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('disable');
		$('#pre'+id).linkbutton('disable');
		$('#next'+id).linkbutton('disable');
		$('#addReceive'+id).linkbutton('enable');
		$('#deleteReceive'+id).linkbutton('enable');
	}
	//审核通过按钮的状态
	var shBtnStatus = function(){
		$('#save'+id).linkbutton('disable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
	}
	//反审后的按钮状态
	var fsBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('enable');
		$('#sh'+id).linkbutton('enable');
		$('#fs'+id).linkbutton('disable');
	}
	//添加
	var onAdd = function(){
		$(viewList).datagrid('unselectAll');
		selectIndex==null
		selectRow==null
		$(editForm).form('clear');
		initChoose();
		addBtnStatus();
		$(editDialog).dialog('open');
	}
	
	var initChoose = function(){
		//客户下拉框
		 $('#customer',editForm).combogrid({rownumbers:true, pagination:true,panelWidth:480,
		 	idField:'customerId',textField:'customerName',mode:'remote',	//此处需要实时查询，要用remote
	        delay:1000,
	        url:'dict/queryCombogridCustomer.do',
			columns:[[	
					{field:'customerId',hidden:true}, 
					{field:'customerCode',title:'客户编号',width:80,sortable:true},  
		    		{field:'customerName',title:'客户名称',width:320,sortable:true}  
			]],
			filter:function(q,row){  if(row.name.toUpperCase().indexOf(q.toUpperCase())>=0)return true;  },//自定义的模糊查询	
			onSelect:function(rowIndex, rowData){
				$('#customerId',editForm).val(rowData.customerId);
		    }
	    });
		//银行
		$('#bankId',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:250,
			data:PSS.getBankList()
		})		  
		//经办人
		$('#employeeId',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:250,
			data:PSS.getEmployeeList()
		})
	}
	//保存前的赋值操作
	var setValue = function(){
		//客户
		var customerId = $('#customerId',editForm).val();
		if(customerId==''){
			$.messager.alert('提示','请选择客户','warning');
			return false;
		}
		//收款日期
		var preReceiptDate = $('#preReceiptDate',editForm).val();
		if(preReceiptDate==''){
			$.messager.alert('提示','请选择收款日期','warning');
			return false;
		}
		//付款金额
		var amount = $('#amount',editForm).numberbox('getValue');
		if(amount==0){
			$.messager.alert('提示','请填写付款金额','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employeeId',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'finance/savePreReceipt.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var data = result.data;
						var preReceiptId = $('#preReceiptId',editForm).val();
						if(preReceiptId==''){
							 $('#preReceiptId',editForm).val(data.preReceiptId);
							 $('#preReceiptCode',editForm).val(data.preReceiptCode);
							 $('#status',editForm).val('未审核');
							 fsBtnStatus();
						}
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	//修改
	var onUpdate = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		initChoose();
		onOpen(selectRow.preReceiptId);
		$(editDialog).dialog('open');
	 }
	//打开
	var onOpen = function(preReceiptId){
		var url = 'finance/initPreReceipt.do';
		var content ={preReceiptId:preReceiptId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var preDisable = false;
				var nextDisable = false;
				if(selectIndex==0||selectIndex==null){
					preDisable = true;
				}
				var rows = $(viewList).datagrid('getRows');
				if(selectIndex==rows.length-1||selectIndex==null){
					nextDisable = true;
				}
				if(preDisable){
					$('#pre'+id).linkbutton('disable');
				}else{
					$('#pre'+id).linkbutton('enable');
				}
				if(nextDisable){
					$('#next'+id).linkbutton('disable');
				}else{
					$('#next'+id).linkbutton('enable');
				}
				
				var data = result.data;
				var preReceiptData = eval("("+data.preReceiptData+")");
				$('#preReceiptId',editDialog).val(preReceiptData.preReceiptId);
				$('#preReceiptCode',editDialog).val(preReceiptData.preReceiptCode);
				$('#preReceiptDate',editDialog).val(preReceiptData.preReceiptDate);
				if(preReceiptData.status==1){
					$('#status',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				$('#customerId',editDialog).val(preReceiptData.customerId);
				$('#customer',editForm).combogrid('setText',preReceiptData.customerName);
				$('#bankId',editDialog).combobox('setValue',preReceiptData.bankId);
				$('#employeeId',editDialog).combobox('setValue',preReceiptData.employeeId);
				$('#note',editDialog).val(preReceiptData.note);
				$('#amount',editDialog).numberbox('setValue',preReceiptData.amount);
				
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//删除
	var onDelete = function(){
		var preReceiptId = $('#preReceiptId',editDialog).val();
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/deletePreReceipt.do';
				var content ={preReceiptId:preReceiptId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							selectRow = null;
							selectIndex = null;
							$(editDialog).dialog('close');
							search(true);
						}
						$.messager.alert('提示','删除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	 }
	//删除多个
	var onMulDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		var idArray = new Array();
		$(rows).each(function(index,row){
			idArray.push(row.preReceiptId);
		})
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/mulDelelePreReceipt.do';
				var content ={ids:idArray.join(LYS.join)};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							$('#searchBtn',queryContent).click();
						}
						$.messager.alert('提示','删除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//修改审核状态
	var onUpdateStatus = function(status){
		var preReceiptId = $('#preReceiptId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(preReceiptId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/updateStatusPreReceipt.do';
				var content ={preReceiptId:preReceiptId,status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							if(status==1){
								shBtnStatus();
								$('#status',editDialog).val('已审核');
							}else{
								fsBtnStatus();
								$('#status',editDialog).val('未审核'); 
							}
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	 }
	//修改多个审核状态
	var onMulUpdateStatus = function(status){
		var rows =  $(viewList).datagrid('getSelections');
		var msg = '';
		if(status==1){
			msg = '已审';
		}else{
			msg = '反审';
		}
		if(rows.length==0){
			$.messager.alert("提示","请选择要"+msg+"的数据行","warning");
			return;
		}
		var idArray = new Array();
		$(rows).each(function(index,row){
			idArray.push(row.preReceiptId);
		})
		$.messager.confirm("提示","确定要"+msg+"选中的记录"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/mulUpdateStatusPreReceipt.do';
				var content ={ids:idArray.join(LYS.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							$('#searchBtn',queryContent).click();
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//上下一笔
	var onOpenIndex = function(index){
		var rows = $(viewList).datagrid('getRows');
		selectIndex = selectIndex + index;
		selectRow = rows[selectIndex];
		onOpen(selectRow.preReceiptId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
  }
})(jQuery);   