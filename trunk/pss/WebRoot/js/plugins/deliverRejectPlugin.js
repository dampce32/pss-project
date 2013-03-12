// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.deliverRejectInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  var selectRow = null;
	  var selectIndex = null;

	  var delDeliverRejectDetailIdArray = null;
	  
	  var queryContent = $('#queryContent',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  var selectDialog = $('#selectDialog',$this);
	  var viewList =  $('#viewList',$this);
	  var deliverRejectDetail = $('#deliverRejectDetail',editDialog);
	  var productList = $('#productList',selectDialog);
	  
	  var isAdd = false;
	  
	  var statusList = [{"value":"-1","text":"所有","selected":true},{"value":"1","text":"已审"},{"value":"0","text":"未审"}]; 
	  $('#status',queryContent).combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:50,
		data:statusList
	  })
	  //点击查询按钮
	  $('#searchBtn',queryContent).click(function(){
		  var sourceCode = $('#sourceCode',queryContent).val();
		  var beginDate = $('#beginDate',queryContent).val();
		  var endDate = $('#endDate',queryContent).val();
		  var status = $('#status',queryContent).combobox('getValue');
		  var url = 'outWarehouse/queryDeliverReject.do';
		  var queryParams ={sourceCode:sourceCode,beginDate:beginDate,endDate:endDate,status:status};
		  $(viewList).datagrid({
			url:url,
			queryParams:queryParams,
			pageNumber:1
		  })
	  })
	  //重置按钮
	  $('#resetBtn',queryContent).click(function(){
		  $('#sourceCode',queryContent).val('');
		  $('#beginDate',queryContent).val('');
		  $('#endDate',queryContent).val('');
		  $('#status',queryContent).combobox('setValue',-1);
	  })
	  //初始化
	  var initChoose = function(){
		 //客户下拉框
		 $('#customer',editForm).combogrid({rownumbers:true, pagination:true,panelWidth:580,
		 	idField:'customerId',textField:'customerName',mode:'remote',	//此处需要实时查询，要用remote
	        delay:1000,
	        url:'dict/queryCombogridCustomer.do',
			columns:[[	
					{field:'customerId',hidden:true}, 
					{field:'customerCode',title:'客户编号',width:80,sortable:true},  
		    		{field:'customerName',title:'客户名称',width:320,sortable:true},
		    		{field:'priceLevel',title:'价格等级',width:100,align:"center",
					formatter: function(value,row,index){
						if (value=='wholesalePrice'){
							return '批发价格';
						}else if(value=='vipPrice'){
							return 'VIP价格';
						}else if(value=='memberPrice'){
							return '会员价格';
						}else if(value=='salePrice'){
							return '零售价格';
						}
					}
				}
			]],
			width:250,
			filter:function(q,row){  if(row.name.toUpperCase().indexOf(q.toUpperCase())>=0)return true;  },//自定义的模糊查询	
			onSelect:function(rowIndex, rowData){
				$('#customerId',editForm).val(rowData.customerId);
		    }
	    });
		 if(isAdd){
			 $('#customer',editForm).combogrid('enable');
		 }else{
			 $('#customer',editForm).combogrid('disable');
		 }
		 //仓库
		$('#warehouseId',editDialog).combobox({
			valueField:'warehouseId',
			textField:'warehouseName',
			width:150,
			data:PSS.getWarehouseList()
		})
		//银行
		$('#bankId',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:150,
			data:PSS.getBankList()
		})		  
		//经办人
		$('#employeeId',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:250,
			data:PSS.getEmployeeList()
		})
		//发票类型
		$('#invoiceTypeId',editDialog).combobox({
			valueField:'invoiceTypeId',
			textField:'invoiceTypeName',
			width:150,
			data:PSS.getInvoiceTypeList()
		})
	  }
	  //新增时，按钮的状态
	  var addBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#print'+id).linkbutton('disable');
		$('#add'+id).linkbutton('disable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('disable');
		$('#pre'+id).linkbutton('disable');
		$('#next'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('enable');
		$('#deleteProduct'+id).linkbutton('enable');
	  }
	  //审核通过按钮的状态
	  var shBtnStatus = function(){
		$('#save'+id).linkbutton('disable');
		$('#print'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
		$('#addProduct'+id).linkbutton('disable');
		$('#deleteProduct'+id).linkbutton('disable');
	  }
	  //反审后的按钮状态
	  var fsBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#print'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('enable');
		$('#sh'+id).linkbutton('enable');
		$('#fs'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('enable');
		$('#deleteProduct'+id).linkbutton('enable');
	  }
	  //新增订单
	  var onAdd = function(){
		$(viewList).datagrid('unselectAll');
		selectIndex==null
		selectRow==null
		$(editForm).form('clear');
		var rows  = $(deliverRejectDetail).datagrid('getRows');
		if(rows.length!=0){
			$(deliverRejectDetail).datagrid({url:LYS.ClearUrl});
		}
		isAdd = true;
		initChoose();
		$('#amount',editForm).numberbox('setValue', 0.0);
		$('#payedAmount',editForm).numberbox('setValue', 0.0);
		addBtnStatus();
		delDeliverRejectDetailIdArray = new Array();
		$(editDialog).dialog('open');
	  }
	  //修改订单
	  var onUpdate = function(){
		  if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		isAdd = false;
		initChoose();
		onOpen(selectRow.deliverRejectId);
		$(editDialog).dialog('open');
	  }
	  //批量删除订单
	  var onMulDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var idArray = new Array();
				$(rows).each(function(index,item){
					idArray.push(item.deliverRejectId);
				})
				var url = 'outWarehouse/mulDeleteDeliverReject.do';
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
	  //批量修改订单状态
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
		$.messager.confirm("提示","确定要"+msg+"选中的记录,"+msg+"后系统将进行库存和财务计算!!",function(t){ 
			if(t){
				var idArray = new Array();
				$(rows).each(function(index,item){
					idArray.push(item.deliverRejectId);
				})
				var url = 'outWarehouse/mulUpdateStatusDeliverReject.do';
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
	  //打开
	  var onOpen = function(deliverRejectId){
		delDeliverRejectDetailIdArray = new Array();
		var url = 'outWarehouse/initDeliverReject.do';
		var content ={deliverRejectId:deliverRejectId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				$('#customer',editForm).combogrid('disable');
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
				var deliverRejectData = eval("("+data.deliverRejectData+")");
				
				$('#deliverRejectId',editForm).val(deliverRejectData.deliverRejectId);
				$('#deliverRejectCode',editForm).val(deliverRejectData.deliverRejectCode);
				$('#sourceCode',editForm).val(deliverRejectData.sourceCode);
				$('#customer',editDialog).combogrid('setText',deliverRejectData.customerName);
				$('#customerId',editForm).val(deliverRejectData.customerId)
				$('#deliverRejectDate',editForm).val(deliverRejectData.deliverRejectDate);
				$('#amount',editDialog).numberbox('setValue',deliverRejectData.amount);
				$('#payedAmount',editForm).numberbox('setValue',deliverRejectData.payedAmount);
				
				$('#warehouseId',editDialog).combobox('setValue',deliverRejectData.warehouseId);
				$('#bankId',editDialog).combobox('setValue',deliverRejectData.bankId);
				$('#employeeId',editDialog).combobox('setValue',deliverRejectData.employeeId);
				$('#invoiceTypeId',editDialog).combobox('setValue',deliverRejectData.invoiceTypeId);
				$('#note',editForm).val(deliverRejectData.note);
				
				if(deliverRejectData.status==1){
					$('#status',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				
				var detailData = eval("("+data.detailData+")");
				$(deliverRejectDetail).datagrid('loadData',detailData);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
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
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}},'-',
				{text:'打印',iconCls:'icon-print',handler:function(){onPrint()}}
		  ],
		columns:[[
		        {field:'deliverRejectId',checkbox:true},
				{field:'deliverRejectCode',title:'退货单号',width:120,align:"center"},
				{field:'deliverRejectDate',title:'退货日期',width:80,align:"center"},
				{field:'sourceCode',title:'销售单号',width:120,align:"center"},
				{field:'customerName',title:'客户',width:200,align:"center"},
				{field:'warehouseName',title:'仓库',width:80,align:"center"},
				{field:'amount',title:'应退金额',width:80,align:"center"},
				{field:'payedAmount',title:'实退金额',width:80,align:"center"},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'invoiceTypeName',title:'发票',width:90,align:"center"},
				{field:'bankName',title:'银行',width:90,align:"center"},
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
	//保存前的赋值操作
	var setValue = function(){
		//客户
		var customerId = $('#customerId',editForm).val();
		if(customerId==''){
			$.messager.alert('提示','请选择客户','warning');
			return false;
		}
		//仓库
		var warehouseId = $('#warehouseId',editDialog).combobox('getValue');
		if(warehouseId==''){
			$.messager.alert('提示','请选择仓库','warning');
			return false;
		}
		//退货日期
		var deliverRejectDate = $('#deliverRejectDate',editForm).val();
		if(deliverRejectDate==''){
			$.messager.alert('提示','请选择退货日期','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employeeId',editDialog).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		if(lastIndex!=null){
			$(deliverRejectDetail).datagrid('endEdit', lastIndex);
		}
		$(deliverRejectDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(deliverRejectDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请选择添加商品','warning');
			return false;
		}
		$(rows).each(function(index,row){
			if(row.qty==0){
				var msg = '第'+(index+1)+'行商品的数量为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		})
		var deliverRejectDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		$(rows).each(function(index,row){
			deliverRejectDetailIdArray.push(row.deliverRejectDetailId);
			productIdArray.push(row.productId);
			colorIdArray.push(row.colorId);
			qtyArray.push(row.qty);
			priceArray.push(row.price);
			note1Array.push(row.note1);
			note2Array.push(row.note2);
			note3Array.push(row.note3);
		})
		$('#deliverRejectDetailIds',editForm).val(deliverRejectDetailIdArray.join(LYS.join));
		$('#delDeliverRejectDetailIds',editForm).val(delDeliverRejectDetailIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#colorIds',editForm).val(colorIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		$('#prices',editForm).val(priceArray.join(LYS.join));
		$('#note1s',editForm).val(note1Array.join(LYS.join));
		$('#note2s',editForm).val(note2Array.join(LYS.join));
		$('#note3s',editForm).val(note3Array.join(LYS.join));
		return true;
	}
	var onSave = function(){
		var url = 'outWarehouse/addDeliverReject.do';
		if($('#deliverRejectId',editForm).val()!=''){
			url = 'outWarehouse/updateDeliverReject.do';
		}
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
						//赋值deliverRejectId，并加载deliverRejectId
						onOpen(data.deliverRejectId);
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	var onDelete = function(){
		var deliverRejectId = $('#deliverRejectId',editDialog).val();
		$.messager.confirm("提示","确定要删除该出库吗?",function(t){ 
			if(t){
				var url = 'outWarehouse/deleteDeliverReject.do';
				var content ={deliverRejectId:deliverRejectId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							$('#searchBtn',queryContent).click();
							selectRow = null;
							selectIndex = null;
							$(editDialog).dialog('close');
						}
						$.messager.alert('提示','删除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	var onUpdateStatus = function(status){
		var deliverRejectId = $('#deliverRejectId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(deliverRejectId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"该记录?"+msg+"后系统将进行财务和库存计算!!",function(t){ 
			if(t){
				var url = 'outWarehouse/updateStatusDeliverReject.do';
				var content ={deliverRejectId:deliverRejectId,status:status};
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
	var onOpenIndex = function(index){
		var rows = $(viewList).datagrid('getRows');
		selectIndex = selectIndex + index;
		selectRow = rows[selectIndex];
		onOpen(selectRow.deliverRejectId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
	//打印 
	var onPrint = function(){
		if(selectRow ==null){
			$.messager.alert('提示','请选中要打印的记录行','warning');
    		return;
		}
		print(selectRow.deliverRejectId);
	}
	//打印
	var print = function(deliverRejectId){
		window.open("printReport.jsp?report=deliverReject&data=ReportServlet?deliverRejectId="+deliverRejectId);
	}
	//内层打印
	var onPrintIn = function(){
		var deliverRejectId = $('#deliverRejectId',editDialog).val();
		print(deliverRejectId);
	}
	var onExit = function(){
		$(editDialog).dialog('close');
		$(editForm).form('clear');
		lastIndex = null;
	}
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑销售退货单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[	
	 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	 			{id:'print'+id,text:'打印',iconCls:'icon-print',handler:function(){onPrintIn()}},'-',
	 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
	 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
	 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
	 			{id:'pre'+id,text:'上一笔',iconCls:'icon-left',handler:function(){onOpenIndex(-1);}},'-',
	 			{id:'next'+id,text:'下一笔',iconCls:'icon-right',handler:function(){onOpenIndex(1);}},'-',
	 			{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}}
	 	  ]
	});
	var colors=PSS.getColorList();
	function colorFormatter(value){
		if(value==null || value==''){
			return;
		}
		for(var i=0; i<colors.length; i++){
			if (colors[i].colorId == value) return colors[i].colorName;
		}
		return value;
	}
	var lastIndex = null;
	//明细
	$(deliverRejectDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}}
	  ],
	  columns:[[
		    {field:'deliverRejectDetailId',hidden:true},
		    {field:'productId',hidden:true},
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'colorId',title:'颜色',width:90,align:"center",editor:{type:'combobox',options:{valueField:'colorId',
				textField:'colorName',
				data:colors}},formatter:colorFormatter},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{min:0,precision:5}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2,
		    	onChange:function(newValue,oldValue){
		    	 if(newValue==''){
		    		 newValue=0;
		    	 }
		    	 if(oldValue==''){
		    		 oldValue=0;
		    	 }
		    	 if(parseFloat(newValue)==parseFloat(oldValue)){
		    		 return;
		    	 }
		    	 var totalAmount = 0 ;
				 var rows =  $(deliverRejectDetail).datagrid('getRows');
				 var row = $(deliverRejectDetail).datagrid('getSelected');
				 var rowIndex = $(deliverRejectDetail).datagrid('getRowIndex',row); 
				 
				 $(rows).each(function(index,item){
					 if(index!=rowIndex){
						 totalAmount += parseFloat(item.amount);
					 }
				 })
				totalAmount+=parseFloat(newValue); 
		    	$('#amount',editForm).numberbox('setValue',totalAmount);
		    }}}},
		    {field:'note1',title:'备注1',width:80,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:80,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:80,align:"center",editor:{type:'text'}}
	  ]],
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(deliverRejectDetail).datagrid('endEdit', lastIndex);
			$(deliverRejectDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  }
	 });
	var setEditing = function(rowIndex){  
	    var editors = $(deliverRejectDetail).datagrid('getEditors', rowIndex);  
	    var qtyEditor = editors[1];  
	    var priceEditor = editors[2];  
	    var amountEditor = editors[3];  
	    qtyEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    priceEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    function calculate(rowIndex){  
	    	var qty = qtyEditor.target.val();
	    	var price = priceEditor.target.val();
	    	if(qty==''){
	    		qty=0;
	    	}
	    	if(price==''){
	    		price=0;
	    	}
	        var cost = parseFloat(qty) *parseFloat(price);  
	        $(amountEditor.target).numberbox('setValue',cost);
	    }  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择商品',  
	    width:1000,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
	});
	$(productList).datagrid({
		  fit:true,
		  cache: false,
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'新增',iconCls:'icon-add',handler:function(){onAddProduct()}},'-',
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProduct()}},'-',
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectProduct();
				}}
		  ],
		  columns:[[
			    {field:'productId',checkbox:true},
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:120,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'colorName',title:'颜色',width:90,align:"center"},
			    {field:'wholesalePrice',title:'批发价格',width:120,align:"center"},
			    {field:'vipPrice',title:'VIP价格',width:120,align:"center"},
			    {field:'memberPrice',title:'会员价格',width:120,align:"center"},
			    {field:'salePrice',title:'零售价格',width:120,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]]
	 });
	 //选择商品页面打开
	 var onSelectProduct = function(){
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 //查询
	 var searchBtnSelect = function(){
		var productCode = $('#productCodeSelectDialog',selectDialog).val();
		var productName = $('#productNameSelectDialog',selectDialog).val();
		
		var options = $(productList).datagrid('options');
		var url = "dict/selectProduct.do";
		var content = {productCode:productCode,productName:productName,page:options.pageNumber,rows:options.pageSize};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(productList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	//分页条
	 $(productList).datagrid('getPager').pagination({   
	    onSelectPage: function(page, rows){
	    	searchBtnSelect();
	    }
	 });
	 //选择商品
	 var onSelectOKProduct = function(){
		 var rows = $(productList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择商品',"warning");
			 return;
		 }
		 var price = null;
		 var priceLevel = null;
		 var g = $('#customer',editDialog).combogrid('grid');	// get datagrid object
		 if(g!=null){
			 var r = g.datagrid('getSelected');	// get the selected row
			 if(r!=null){
				 priceLevel = r.priceLevel;
			 }
		 }
		 $(rows).each(function(index,row){
			 if(priceLevel=='wholesalePrice'){
				 price = row.wholesalePrice;
			 }else if(priceLevel=='vipPrice'){
				 price = row.vipPrice;
			 }else if(priceLevel=='memberPrice'){
				 price = row.memberPrice;
			 }else{
				 price = row.salePrice;
			 }
			 $(deliverRejectDetail).datagrid('appendRow',{
				 deliverRejectDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:0,
				 price:price,
				 amount:0,
				 note1:'',
				 note2:'',
				 note3:''
			});
		 })
		 $(deliverRejectDetail).datagrid('endEdit', lastIndex);
		 $(deliverRejectDetail).datagrid('unselectAll');
		 lastIndex = null;
		 onExitSelectProduct();
	 }
	 //退出选择商品界面
	 var onExitSelectProduct = function(){
		 $(selectDialog).dialog('close');
		 $(productList).datagrid({url:LYS.ClearUrl});
	 }
	 //删除商品
	 var onDeleteProduct = function(){
		 var row = $(deliverRejectDetail).datagrid('getSelected');
		 delDeliverRejectDetailIdArray.push(row.deliverRejectDetailId);
		 var rowIndex = $(deliverRejectDetail).datagrid('getRowIndex',row);
		 $(deliverRejectDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(deliverRejectDetail).datagrid('getRows');
		 $(rows).each(function(index,item){
			 totalAmount += parseFloat(item.amount);
		 })
		$('#amount',editForm).numberbox('setValue',totalAmount);
	 }
	 var addDialog = $('#addDialog',$this);
	 var addForm = $('#addForm',addDialog);
	 
	 var addData = null;
	 //添加
	 var onAddProduct = function(){
		if(addData==null){
			var url = 'dict/queryByDictionaryKindsDataDict.do';
			var content ={ids:'size,color,unit'};
			addData = syncCallService(url,content);
		}
		//规格
	   $('#size',addDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:addData.size,
			onSelect:function(record){
				$('#sizeId',addDialog).val(record.dataDictionaryId);
			}
	  })
	  //颜色
	   $('#color',addDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:addData.color,
			onSelect:function(record){
				$('#colorId',addDialog).val(record.dataDictionaryId);
			}
	  })
	  //单位
	   $('#unit',addDialog).combobox({
			valueField:'dataDictionaryId',
			textField:'dataDictionaryName',
			width:150,
			data:addData.unit,
			onSelect:function(record){
				$('#unitId',addDialog).val(record.dataDictionaryId);
			}
	  })
	  //商品类型
	  $('#productType',addDialog).combogrid({
		    panelWidth:480, 
			mode: 'remote',  
			url: 'dict/queryCombogridProductType.do',
			idField: 'productTypeId',  
			textField: 'productTypeName',  
			pagination:true,
			columns: [[  
			    {field:'productTypeName',title:'商品类型',width:80,sortable:true},  
			    {field:'productTypeCode',title:'商品类型编号',width:80,sortable:true}
			]],
			onSelect:function(rowIndex, rowData){
				$('#productTypeId',addDialog).val(rowData.productTypeId);
			}
		});
	   	$('#buyingPrice',addDialog).numberbox('setValue',0.0);
	   	$('#salePrice',addDialog).numberbox('setValue',0.0);
		$(addDialog).dialog('open');
	}
	//保存前的赋值操作
	var setAddValue = function(){
		var productCode = $.trim($('#productCode',addForm).val());
		if(productCode==''){
			$.messager.alert('提示','请填写商品编号','warning');
			return false;
		}
		var productName = $.trim($('#productName',addForm).val());
		if(productName==''){
			$.messager.alert('提示','请填写商品名称','warning');
			return false;
		}
		//商品类别
		var productTypeId = $('#productType',addForm).combogrid('getValue');
		var productTypeName = $('#productType',addForm).combogrid('getText');
		if(productTypeId==''){
			$.messager.alert('提示','请选择商品类别','warning');
			return false;
		}
		$('#productTypeId',addForm).val(productTypeId);
		$('#productTypeName',addForm).val(productTypeName);
		//商品单位
		var unitId = $('#unit',addForm).combobox('getValue');
		var unitName = $('#unit',addForm).combobox('getText');
		$('#unitId',addForm).val(unitId);
		$('#unitName',addForm).val(unitName);
		//商品颜色
		var colorId = $('#color',addForm).combobox('getValue');
		var colorName = $('#color',addForm).combobox('getText');
		$('#colorId',addForm).val(colorId);
		$('#colorName',addForm).val(colorName);
		//商品尺码
		var sizeId = $('#size',addForm).combobox('getValue');
		var sizeName = $('#size',addForm).combobox('getText');
		$('#sizeId',addForm).val(sizeId);
		$('#sizeName',addForm).val(sizeName);
		return true;
	}
	 //保存商品
	 var onAddSave = function(){
		var url = 'dict/saveProduct.do'
		$(addForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setAddValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						onAddExit();
						searchBtnSelect();
					}
					$.messager.alert('提示','保存成功','info',fn);
					
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	 }
	 //退出
	 var onAddExit = function(){
		 $(addForm).form('clear');
		 $(addDialog).dialog('close');
	 }
	 //编辑框
	 $(addDialog).dialog({  
	    title: '添加商品',  
	    width:500,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    	{text:'保存',iconCls:'icon-save',handler:function(){onAddSave();}},'-',
	    	{text:'退出',iconCls:'icon-cancel',handler:function(){onAddExit();}
		}]
	}); 
  }
})(jQuery);   