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
	  
	  var delSaleDetailIdArray = null;
	  
	  var queryContent = $('#queryContent',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  var selectDialog = $('#selectDialog',$this);
	  var viewList =  $('#viewList',$this);
	  var saleDetail = $('#saleDetail',editDialog);
	  var productList = $('#productList',selectDialog);
	  
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
		  var customerName = $('#customer',queryContent).val();
		  var beginDate = $('#beginDate',queryContent).val();
		  var endDate = $('#endDate',queryContent).val();
		  var status = $('#status',queryContent).combobox('getValue');
		  var url = 'outWarehouse/querySale.do';
		  var queryParams ={'customer.customerName':customerName,beginDate:beginDate,endDate:endDate,status:status};
		  $(viewList).datagrid({
			url:url,
			queryParams:queryParams,
			pageNumber:1
		  })
	  })
	  //重置按钮
	  $('#resetBtn',queryContent).click(function(){
		  $('#customer',queryContent).val('');
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
			width:150,
			data:PSS.getEmployeeList()
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
		var rows  = $(saleDetail).datagrid('getRows');
		if(rows.length!=0){
			$(saleDetail).datagrid({url:LYS.ClearUrl});
		}
		initChoose();
		$('#otherAmount',editForm).numberbox('setValue', 0.0);
		$('#amount',editForm).numberbox('setValue', 0.0);
		$('#receiptedAmount',editForm).numberbox('setValue', 0.0);
		addBtnStatus();
		delSaleDetailIdArray = new Array();
		$(editDialog).dialog('open');
	  }
	  //修改订单
	  var onUpdate = function(){
		  if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		initChoose();
		onOpen(selectRow.saleId);
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
					idArray.push(item.saleId);
				})
				var url = 'outWarehouse/mulDeleteSale.do';
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
					idArray.push(item.saleId);
				})
				var url = 'outWarehouse/mulUpdateStatusSale.do';
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
	  var onOpen = function(saleId){
		delSaleDetailIdArray = new Array();
		var url = 'outWarehouse/initSale.do';
		var content ={saleId:saleId};
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
				
				var data =  result.data;
				var saleData = eval("("+data.saleData+")");
				
				$('#saleId',editForm).val(saleData.saleId);
				$('#saleCode',editForm).val(saleData.saleCode);
				$('#sourceCode',editForm).val(saleData.sourceCode);
				$('#customer',editDialog).combogrid('setText',saleData.customerName);
				$('#customerId',editForm).val(saleData.customerId)
				$('#saleDate',editForm).val(saleData.saleDate);
				$('#deliverDate',editForm).val(saleData.deliverDate);
				$('#otherAmount',editForm).numberbox('setValue',saleData.otherAmount);
				$('#amount',editDialog).numberbox('setValue',saleData.amount);
				$('#receiptedAmount',editForm).numberbox('setValue',saleData.otherAmount);
				$('#bankId',editDialog).combobox('setValue',saleData.bankId);
				$('#employeeId',editDialog).combobox('setValue',saleData.employeeId);
				$('#note',editForm).val(saleData.note);
				
				if(saleData.status==1){
					$('#status',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				
				var detailData = eval("("+data.detailData+")");
				$(saleDetail).datagrid('loadData',detailData);
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
		        {field:'saleId',checkbox:true},
				{field:'saleCode',title:'订单单号',width:120,align:"center"},
				{field:'saleDate',title:'订单日期',width:80,align:"center"},
				{field:'deliverDate',title:'订单交期',width:80,align:"center"},
				{field:'sourceCode',title:'原始单号',width:120,align:"center"},
				{field:'customerName',title:'客户',width:200,align:"center"},
				{field:'otherAmount',title:'运费',width:80,align:"center"},
				{field:'amount',title:'应收金额',width:80,align:"center"},
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
	//保存前的赋值操作
	var setValue = function(){
		//客户
		var customerId = $('#customerId',editForm).val();
		if(customerId==''){
			$.messager.alert('提示','请选择客户','warning');
			return false;
		}
		//订单日期
		var saleDate = $('#saleDate',editForm).val();
		if(saleDate==''){
			$.messager.alert('提示','请选择订单日期','warning');
			return false;
		}
		//订单交期
		var deliverDate = $('#deliverDate',editForm).val();
		if(deliverDate==''){
			$.messager.alert('提示','请选择订单交期','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employeeId',editDialog).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		if(lastIndex!=null){
			$(saleDetail).datagrid('endEdit', lastIndex);
		}
		$(saleDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(saleDetail).datagrid('getRows');
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
		var saleDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var discountArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		$(rows).each(function(index,row){
			saleDetailIdArray.push(row.saleDetailId);
			productIdArray.push(row.productId);
			colorIdArray.push(row.colorId);
			qtyArray.push(row.qty);
			priceArray.push(row.price);
			discountArray.push(row.discount)
			note1Array.push(row.note1);
			note2Array.push(row.note2);
			note3Array.push(row.note3);
		})
		$('#saleDetailIds',editForm).val(saleDetailIdArray.join(LYS.join));
		$('#delSaleDetailIds',editForm).val(delSaleDetailIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#colorIds',editForm).val(colorIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		$('#prices',editForm).val(priceArray.join(LYS.join));
		$('#discounts',editForm).val(discountArray.join(LYS.join));
		$('#note1s',editForm).val(note1Array.join(LYS.join));
		$('#note2s',editForm).val(note2Array.join(LYS.join));
		$('#note3s',editForm).val(note3Array.join(LYS.join));
		
		return true;
	}
	var onSave = function(){
		var url = 'outWarehouse/addSale.do';
		if($('#saleId',editForm).val()!=''){
			url = 'outWarehouse/updateSale.do';
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
						//赋值saleId，并加载saleDetail
						onOpen(data.saleId);
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	var onDelete = function(){
		var saleId = $('#saleId',editDialog).val();
		$.messager.confirm("提示","确定要删除该订单吗?",function(t){ 
			if(t){
				var url = 'outWarehouse/deleteSale.do';
				var content ={saleId:saleId};
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
		var saleId = $('#saleId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(saleId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"该记录?"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'outWarehouse/updateStatusSale.do';
				var content ={saleId:saleId,status:status};
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
		onOpen(selectRow.saleId);
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
		print(selectRow.saleId);
	}
	//打印
	var print = function(saleId){
		window.open("printReport.jsp?report=sale&data=ReportServlet?saleId="+saleId);
	}
	//内层打印
	var onPrintIn = function(){
		var saleId = $('#saleId',editDialog).val();
		print(saleId);
	}
	var onExit = function(){
		$(editDialog).dialog('close');
		$(editForm).form('clear');
		lastIndex = null;
	}
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑订单',  
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
	$(saleDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}}
	  ],
	  columns:[[
		    {field:'saleDetailId',hidden:true},
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'colorId',title:'颜色',width:90,align:"center",editor:{type:'combobox',options:{valueField:'colorId',
				textField:'colorName',
				data:colors}},formatter:colorFormatter},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{min:0,precision:5}}},
		    {field:'discount',title:'折扣',width:90,align:"center",editor:{type:'numberbox',options:{min:0,max:1,precision:2}}},
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
				 var rows =  $(saleDetail).datagrid('getRows');
				 var row = $(saleDetail).datagrid('getSelected');
				 var rowIndex = $(saleDetail).datagrid('getRowIndex',row); 
				 
				 $(rows).each(function(index,item){
					 if(index!=rowIndex){
						 totalAmount += parseFloat(item.amount);
					 }
				 })
				totalAmount+=parseFloat(newValue); 
				var otherAmount = $('#otherAmount',editForm).numberbox('getValue');
				if(otherAmount==''){
					otherAmount=0;
				}
				totalAmount+=parseFloat(otherAmount); 
		    	$('#amount',editForm).numberbox('setValue',totalAmount);
		    }}}},
		    {field:'note1',title:'备注1',width:80,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:80,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:80,align:"center",editor:{type:'text'}},
		    {field:'hadSaleQty',title:'已出库',width:80,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}},
			{field:'unSaleQty',title:'未完成',width:80,align:"center",
				formatter: function(value,row,index){
					return row.qty-row.hadSaleQty;
				},editor:{type:'numberbox',options:{disabled:true,precision:2}}
		    }
	  ]],
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(saleDetail).datagrid('endEdit', lastIndex);
			$(saleDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  }
	 });
	var setEditing = function(rowIndex){  
	    var editors = $(saleDetail).datagrid('getEditors', rowIndex);  
	    var qtyEditor = editors[1];  
	    var priceEditor = editors[2];  
	    var discountEditor = editors[3];  
	    var amountEditor = editors[4];  
	    var hadSaleQtyQtyEditor = editors[8];  
	    var unSaleQtyQtyEditor = editors[9];  
	    qtyEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    priceEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    discountEditor.target.bind('change', function(){  
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
	        var dis = discountEditor.target.val();
	        if(dis!=''){
	        	cost = cost*parseFloat(dis);
	        }
	        $(amountEditor.target).numberbox('setValue',cost);
	        //更新未完成
	        $(unSaleQtyQtyEditor.target).numberbox('setValue', qtyEditor.target.val()- hadSaleQtyQtyEditor.target.val());
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
				{text:'新增',iconCls:'icon-add',handler:function(){onNewProduct()}},'-',
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
			 $(saleDetail).datagrid('appendRow',{
				 saleDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:0,
				 price:price,
				 discount:'',
				 amount:0,
				 note1:'',
				 note2:'',
				 note3:'',
				 hadSaleQty:0,
				 unSaleQty:0
			});
		 })
		 $(saleDetail).datagrid('endEdit', lastIndex);
		 $(saleDetail).datagrid('unselectAll');
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
		 var row = $(saleDetail).datagrid('getSelected');
		 delSaleDetailIdArray.push(row.saleDetailId);
		 var rowIndex = $(saleDetail).datagrid('getRowIndex',row);
		 $(saleDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(saleDetail).datagrid('getRows');
		 $(rows).each(function(index,item){
			 totalAmount += parseFloat(item.amount);
		 })
		var otherAmount = $('#otherAmount',editForm).numberbox('getValue');
		if(otherAmount==''){
			otherAmount = 0;
		}
		totalAmount =parseFloat(totalAmount)+parseFloat(otherAmount);
		$('#amount',editForm).numberbox('setValue',totalAmount);
	 }
	 //其他费用：运费发生改变
	 $('#otherAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 var amount =$('#amount',editForm).numberbox('getValue');
			 if(newValue==''){
				 newValue=0;
			 }
			 if(oldValue==''){
				 oldValue=0;
			 }
			 var totalAmount = parseFloat(amount)+parseFloat(newValue-oldValue);
			 $('#amount',editForm).numberbox('setValue',totalAmount);
		 }
	});
	//----新增商品s--------
		var newProductDialog = $('#newProductDialog',$this);
		var newProductForm = $('#newProductForm',newProductDialog);
		//编辑框
		$(newProductDialog).dialog({  
		    title: '新增商品信息',  
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    toolbar:[{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSaveNewProduct();}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(newProductDialog).dialog('close');
		 				}
		 			}]
		});
		//------默认商品组装--------
		var defaultPackagingList = $('#defaultPackagingList',newProductDialog);
		var lastIndexNewProduct=null;
		$(defaultPackagingList).datagrid({
		  title:'默认商品组装',
		  singleSelect:true,	
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:200,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:0}}},
			    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:5}}},
			    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProductNewProduct()}},'-',
				{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProductNewProduct()}}
		  ],
		  onBeforeLoad:function(){
				$(this).datagrid('rejectChanges');
		  },
		  onClickRow:function(rowIndex){
			if (lastIndexNewProduct != rowIndex){
				$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
				$(defaultPackagingList).datagrid('beginEdit', rowIndex);
				setEditingNewProduct(rowIndex);
			}
			lastIndexNewProduct = rowIndex;
		  }
		 });
		function setEditingNewProduct(rowIndex){  
		    var editors = $(defaultPackagingList).datagrid('getEditors', rowIndex);  
		    var qtyEditor = editors[0];  
		    var priceEditor = editors[1];  
		    var amountEditor = editors[2];  
		    qtyEditor.target.bind('change', function(){  
		        calculate(rowIndex);  
		    });  
		    function calculate(rowIndex){  
		    	if(qtyEditor.target.val()==''){
		    		$(qtyEditor.target).numberbox('setValue',0.00);
		    	}
		        var cost = qtyEditor.target.val() * priceEditor.target.val();  
		        $(amountEditor.target).numberbox('setValue',cost);
		    }  
		}  
		//初始化Combobox
		var initComboboxNewProduct = function(){
			//规格
		   $('#size',newProductDialog).combobox({
				valueField:'sizeId',
				textField:'sizeName',
				width:250,
				data:PSS.getSizeList()
		  })
		  //颜色
		   $('#color',newProductDialog).combobox({
				valueField:'colorId',
				textField:'colorName',
				width:250,
				data:PSS.getColorList()
		  })
		  //单位
		   $('#unit',newProductDialog).combobox({
				valueField:'unitId',
				textField:'unitName',
				width:250,
				data:PSS.getUnitList()
		  })
		}
		//初始化选择
		var initChooseNewProduct = function(){
			initComboboxNewProduct();
			  //商品类型
			  $('#productType',newProductDialog).combogrid({
				    panelWidth:480, 
					mode: 'remote',  
					url: 'dict/queryCombogridProductType.do',
					idField: 'productTypeId',  
					textField: 'productTypeName',  
					pagination:true,
					columns: [[  
					    {field:'productTypeName',title:'商品类型',width:150,sortable:true},  
					    {field:'productTypeCode',title:'商品类型编号',width:150,sortable:true}
					]]
				});
		}
		//新增商品
		var onNewProduct = function(){
			$(newProductDialog).form('clear');
			$('#uploadImgForm',newProductDialog).form('clear');
			initChooseNewProduct();
			$('#wholesalePrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#vipPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#memberPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#salePrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#buyingPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#status',newProductDialog).combobox('setValue',1);
			$(newProductDialog).dialog('open');
		}
		//编号双击 后台编号
		$('#productCode',newProductDialog).dblclick(function(){
			var obj = $(this);
			var productCode = $(obj).val();
			var url = 'dict/newCodeProduct.do';
			var content ={productCode:productCode};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					var data = result.data;
					$('#productCode',newProductDialog).val(data.productCode);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			})
			
		})
		//保存前的赋值操作
		var setValueNewProduct = function(){
			var productName = $.trim($('#productName',newProductForm).val());
			if(productName==''){
				$.messager.alert('提示','请填写商品名称','warning');
				return false;
			}
			//商品类别
			var productTypeId = $('#productType',newProductForm).combogrid('getValue');
			if(productTypeId==''){
				$.messager.alert('提示','请选择商品类别','warning');
				return false;
			}
			var productTypeName = $('#productType',newProductForm).combogrid('getText');
			$('#productTypeId',newProductForm).val(productTypeId);
			//商品单位
			var unitId = $('#unit',newProductForm).combobox('getValue');
			$('#unitId',newProductForm).val(unitId);
			//商品颜色
			var colorId = $('#color',newProductForm).combobox('getValue');
			$('#colorId',newProductForm).val(colorId);
			//商品尺码
			var sizeId = $('#size',newProductForm).combobox('getValue');
			$('#sizeId',newProductForm).val(sizeId);
			$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
			$(defaultPackagingList).datagrid('unselectAll');
			lastIndexNewProduct = null;
			var rows =  $(defaultPackagingList).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if(row.qty==0){
					var msg = '第'+(i+1)+'行商品的数量为0,请输入';
					$.messager.alert('提示',msg,'warning');
					return false;
				}
			}
			var defaultPackagingIdArray = new Array();
			var productIdArray = new Array();
			var qtyArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				defaultPackagingIdArray.push(rows[i].defaultPackagingId);
				productIdArray.push(rows[i].productId);
				qtyArray.push(rows[i].qty);
			}
			$('#defaultPackagingIds',newProductForm).val(defaultPackagingIdArray.join(LYS.join));
			$('#deleleIds',newProductForm).val('');
			$('#productIds',newProductForm).val(productIdArray.join(LYS.join));
			$('#qtys',newProductForm).val(qtyArray.join(LYS.join));
			return true;
		}
		//保存
		var onSaveNewProduct = function(){
			$(newProductForm).form('submit',{
				url: 'dict/saveProduct.do',
				onSubmit: function(){
					return setValueNewProduct();
				},
				success: function(data){
					var result = eval('('+data+')');
					if(result.isSuccess){
						var fn = function(){
							var data = result.data;
							if(data.isUnitChange){
								PSS.UnitList = null;
							}
							if(data.isSizeChange){
								PSS.SizeList = null;
							}
							if(data.isColorChange){
								PSS.ColorList = null;
							}
							$('#productId',newProductForm).val(data.productId);
						}
						$.messager.alert('提示','保存成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				}
			 });
		}
		//----选择商品---------
		var selectProductDialog = $('#selectProductDialog',$this);
		var productListSelectProductDialog = $('#productListSelectProductDialog',selectProductDialog);
		$(selectProductDialog).dialog({  
		    title: '选择商品',  
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    onClose:function(){
		    	var rows = $(productListSelectProductDialog).datagrid('getRows');
		    	if(rows.length!=0){
		    		$(productListSelectProductDialog).datagrid({url:LYS.ClearUrl});
		    	}
		    }
		});
		$(productListSelectProductDialog).datagrid({
			  fit:true,
			  cache: false, 
			  columns:[[
				    {field:'ck',checkbox:true},
				    {field:'productCode',title:'商品编号',width:90,align:"center"},
					{field:'productName',title:'商品名称',width:120,align:"center"},
				    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
				    {field:'unitName',title:'单位',width:90,align:"center"},
				    {field:'sizeName',title:'规格',width:90,align:"center"},
				    {field:'colorName',title:'颜色',width:90,align:"center"},
				    {field:'buyingPrice',title:'进价',width:90,align:"center"},
				    {field:'note',title:'备注',width:90,align:"center"}
			  ]],
			  rownumbers:true,
			  pagination:true,
			  toolbar:[	
					{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProductNewProduct()}},
					{text:'退出',iconCls:'icon-cancel',handler:function(){
						 $(selectProductDialog).dialog('close');
					}}
			  ]
		 });
		var onSelectProductNewProduct = function(){
			 $(selectProductDialog).form('clear');
			 $(selectProductDialog).dialog('open');
		 }
		//查询
		 $('#searchBtnSelectProductDialog',selectProductDialog).click(function(){
			 searchBtnProductSelect();
		 })
		 var searchBtnProductSelect = function(){
			var productCode = $('#productCodeSelectProductDialog',selectProductDialog).val();
			var productName = $('#productNameProductSelectDialog',selectProductDialog).val();
			var idArray = new Array();
			var rows = $(defaultPackagingList).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				idArray.push(row.productId);
			}
			var productId = $.trim($('#productId',newProductForm).val());
			if(productId!=''){
				idArray.push(productId);
			}
			var url = "dict/selectDefaultPackingProduct.do";
			var content = {productCode:productCode,productName:productName,ids:idArray.join(LYS.join)};
			var result = syncCallService(url,content);
			if(result.isSuccess){
				var data = result.data;
				$(productListSelectProductDialog).datagrid('loadData',eval("("+data.datagridData+")"));
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		 }
		//选择商品
		 var onSelectOKProductNewProduct = function(){
			 var rows = $(productListSelectProductDialog).datagrid('getSelections');
			 if(rows.length==0){
				 $.messager.alert('提示','请选择商品',"warning");
				 return;
			 }
			 for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				 $(defaultPackagingList).datagrid('appendRow',{
					 defaultPackagingId:'',
					 productId:row.productId,
					 productCode:row.productCode,
					 productName:row.productName,
					 unitName:row.unitName,
					 sizeName:row.sizeName,
					 qty:1,
					 price:row.buyingPrice,
					 amount:row.buyingPrice
				});
			}
			$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
			$(defaultPackagingList).datagrid('unselectAll');
			lastIndexNewProduct = null;
			$(selectProductDialog).dialog('close');
		 }
		//删除商品
		 var onDeleteProductNewProduct = function(){
			 var row = $(defaultPackagingList).datagrid('getSelected');
			 var rowIndex = $(defaultPackagingList).datagrid('getRowIndex',row);
			 $(defaultPackagingList).datagrid('deleteRow',rowIndex);
			 lastIndexNewProduct = null;
		 }
		//------商品照片--------
		 $('#uploadBtn',newProductDialog).click(function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 $.messager.alert('提示','先保存商品后上传照片','warning');
			 }else{
				 if(setValueUploadImg()){
					 $('#uploadImgForm',newProductDialog).ajaxSubmit({
							url:'dict/uploadImgProduct.do',
							type:'post',
							uploadFiles:true,
							dataType:'json',
							success:function(result){
								if(result.isSuccess){
									var fn = function(){
										showImg();
									}
									$.messager.alert('提示','上传成功','info',fn);
								}else{
									$.messager.alert('提示',result.message,'error');
								}
							}
					 });
				 }
			 }
		 })
		 //上传图片前检查
		 var setValueUploadImg = function(){
			 var file = $('#file',newProductDialog).val();
			 if(file==''){
				 $.messager.alert('提示','请选择上传文件','warning');
				 return false;
			 }
			 if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file)){
				 $.messager.alert('提示','图片类型必须是.gif,jpeg,jpg,png中的一种','warning');
				 return false;
			 }
			 var productId = $('#productId',newProductDialog).val();
			 $('#productIdUploadImgForm',newProductDialog).val(productId);
			 var pos = file.lastIndexOf("\\");
			 $('#fileName',newProductDialog).val(file.substring(pos+1));
			 return true;
		 }
		 //显示图片
		 var showImg = function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 return;
			 }
			 $('#imgPic').attr('src','dict/showImgProduct.do?productId='+productId);
		 }
		 $('#removeBtn',newProductDialog).click(function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 $.messager.alert('提示','先保存商品后上传照片','warning');
			 }else{
				var url = 'dict/deleteImgProduct.do';
				var content ={productId:productId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							$('#imgPic',newProductDialog).attr('src','#');
							$('#uploadImgForm',newProductDialog).form('clear');
						}
						$.messager.alert('提示','移除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			 }
		 });
		//----添加商品e--------
  }
})(jQuery);   