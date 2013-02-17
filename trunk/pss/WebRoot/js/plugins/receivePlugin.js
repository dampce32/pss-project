// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.receiveInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	  var searchBtn = $('#searchBtn',$this);
	  var mulSearchBtn = $('#mulSearchBtn',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var pager = $('#pager',$this);
	  var pageNumber = 1;
	  var pageSize = 10;
	  
	  var changeSearch = false;
	  
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  nowrap:true,
		  striped: true,
		  collapsible:true,
		  rownumbers:true,
		  columns:[[
		        {field:'ck',title:'选择',checkbox:true},
				{field:'receiveCode',title:'入库单号',width:130,align:"center"},
				{field:'receiveDate',title:'入库日期',width:80,align:"center"},
				{field:'deliverCode',title:'送货单号',width:120,align:"center"},
				{field:'warehouseName',title:'存入仓库',width:90,align:"center"},
				{field:'supplierName',title:'供应商',width:90,align:"center"},
				{field:'amount',title:'应付款',width:80,align:"center"},
				{field:'payAmount',title:'已付款',width:80,align:"center"},
				{field:'discountAmount',title:'优惠',width:80,align:"center"},
				{field:'checkAmount',title:'已对账金额',width:80,align:"center"},
				{field:'noPayAmount',title:'欠款',width:80,align:"center",
					formatter: function(value,row,index){
						return row.amount-row.payAmount-row.discountAmount-row.checkAmount;
					}
				},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'status',title:'状态',width:80,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else {
							return '<img src="style/v1/icons/info.png"/>';
						}
					}},
				{field:'isPay',title:'付款',width:80,align:"center",
						formatter: function(value,row,index){
							if(row.isPay==0){
								return '未付';
							}else if(row.isPay==1){
								return '已付';
							}
						}},
				{field:'invoiceTypeName',title:'开票信息',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onMulDelete()}},'-',
				{text:'已审',iconCls:'icon-info',handler:function(){onMulUpdateStatus(1)}},'-',
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}},'-',
				{text:'清款',iconCls:'icon-clear',handler:function(){onMulUpdateIsPay()}},'-',
				{text:'打印',iconCls:'icon-print',handler:function(){onPrint()}}
				
		  ],
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
	//分页条
	$(pager).pagination({   
	    onSelectPage: function(page, rows){
			pageNumber = page;
			pageSize = rows;
			search();
	    }
	});
	//高级查询
	$(mulSearchBtn).click(function(){
		if(changeSearch){
			$('#mulSearchPanel',$this).panel('close');
			$('#'+id).layout('panel','north').panel('resize',{
				height: 32
			});
			$('#'+id).layout('resize');
			changeSearch = false;
		}else{
			$('#mulSearchPanel',$this).panel('open');
			$('#'+id).layout('panel','north').panel('resize',{
				height: 140
			});
			$('#'+id).layout('resize');
			changeSearch = true;
		}
	})
	
	$("#mulSearch",$this).click(function(){
		var receiveCode = $('#receiveCodeMulSearch',$this).val();
		
		var url = "inWarehouse/queryReceive.do";
		var content = {receiveCode:receiveCode,page:pageNumber,rows:pageSize};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(viewList).datagrid('loadData',eval("("+data.datagridData+")"));
			//需要重新重新分页信息
			if(flag){
				getTotal(content);
			}
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	})
	
	$("#mulSearchClear",$this).click(function(){
		$("#mulSearchForm",$this).form('clear');
		return false;
	});
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		var receiveCode = $('#receiveCodeSearch',queryContent).val();
		
		var url = "inWarehouse/queryReceive.do";
		var content = {receiveCode:receiveCode,page:pageNumber,rows:pageSize};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(viewList).datagrid('loadData',eval("("+data.datagridData+")"));
			//需要重新重新分页信息
			if(flag){
				getTotal(content);
			}
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	} 
	//统计总数
	var getTotal = function(content){
		var url = "inWarehouse/getTotalCountReceive.do";
		asyncCallService(url,content,
		function(result){
			if(result.isSuccess){
				var data = result.data;
				$(pager).pagination({  
					pageNumber:1,
					total:data.total
				});
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		})
	}	
		
	//编辑框
	$(editDialog).dialog({  
	    title: '采购入库单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	$(receiveDetail).datagrid({url:LYS.ClearUrl});
	    	$(editForm).form('clear');
	    },
	    toolbar:[	
		 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
		 			{id:'print'+id,text:'打印',iconCls:'icon-print',handler:function(){onPrintIn()}},'-',
		 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
		 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
		 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
		 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
		 			{id:'isPay'+id,text:'清款',iconCls:'icon-clear',handler:function(){onUpdateIsPay();}},'-',
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
		$('#print'+id).linkbutton('disable');
		$('#add'+id).linkbutton('disable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('disable');
		$('#isPay'+id).linkbutton('disable');
		$('#pre'+id).linkbutton('disable');
		$('#next'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('enable');
		$('#addBuyProduct'+id).linkbutton('enable');
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
		$('#addBuyProduct'+id).linkbutton('disable');
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
		$('#addBuyProduct'+id).linkbutton('enable');
		$('#deleteProduct'+id).linkbutton('enable');
	}
	//清款后的状态
	var qkBtnStatus = function(){
		$('#save'+id).linkbutton('disable');
		$('#print'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
		$('#isPay'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('disable');
		$('#addBuyProduct'+id).linkbutton('disable');
		$('#deleteProduct'+id).linkbutton('disable');
	}
	//添加
	var onAdd = function(){
		$(viewList).datagrid('unselectAll');
		selectIndex=null;
		selectIndex==null;
		addBtnStatus();
    	$(editForm).form('clear');
    	var rows  = $(receiveDetail).datagrid('getRows');
		if(rows.length!=0){
			$(receiveDetail).datagrid({url:LYS.ClearUrl});
		}
		initChoose();
		$('#otherAmount',editForm).numberbox('setValue', 0.0);
		$('#amount',editForm).numberbox('setValue', 0.0);
		$('#discountAmount',editForm).numberbox('setValue', 0.0);
		$('#payAmount',editForm).numberbox('setValue', 0.0);
		
		$(editDialog).dialog('open');
	}
	//初始化选择项
	var initChoose = function(){
		//供应商
		$('#supplier',editForm).combogrid({  
		    panelWidth:450,  
		    idField:'supplierId',  
		    textField:'supplierName',  
		    url:'dict/queryCombogridSupplier.do',  
		    columns:[[  
		        {field:'supplierCode',title:'供应商编号',width:120},
		        {field:'supplierName',title:'供应商名称',width:120}
		    ]]  
		});
		//仓库
		$('#warehouse',editDialog).combobox({
			valueField:'warehouseId',
			textField:'warehouseName',
			width:150,
			data:PSS.getWarehouseList()
		})
		//银行
		$('#bank',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:150,
			data:PSS.getBankList()
		})		  
		//经办人
		$('#employee',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:150,
			data:PSS.getEmployeeList()
		})
		//发票类型
		$('#invoiceType',editDialog).combobox({
			valueField:'invoiceTypeId',
			textField:'invoiceTypeName',
			width:150,
			data:PSS.getInvoiceTypeList()
		})
	}
	//保存前的赋值操作
	var setValue = function(){
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return false;
		}
		$('#supplierId',editForm).val(supplierId);
		//存放仓库
		var warehouseId = $('#warehouse',editForm).combobox('getValue');
		if(warehouseId==''){
			$.messager.alert('提示','请选择存放仓库','warning');
			return false;
		}
		$('#warehouseId',editForm).val(warehouseId);
		//收货日期
		var receiveDate = $('#receiveDate',editForm).val();
		if(receiveDate==''){
			$.messager.alert('提示','请选择收货日期','warning');
			return false;
		}
		//银行
		var bankId = $('#bank',editForm).combobox('getValue');
		if(bankId==''){
			$.messager.alert('提示','请选择银行','warning');
			return false;
		}
		$('#bankId',editForm).val(bankId);
		//经办人
		var employeeId = $('#employee',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		$('#employeeId',editForm).val(employeeId);
		//发票
		var invoiceTypeId = $('#invoiceType',editForm).combobox('getValue');
		if(invoiceTypeId==''){
			$.messager.alert('提示','请选择发票','warning');
			return false;
		}
		$('#invoiceTypeId',editForm).val(invoiceTypeId);
		//验证添加的商品行
		$(receiveDetail).datagrid('endEdit', lastIndex);
		$(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(receiveDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请选择添加商品','warning');
			return false;
		}
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(row.qty==0){
				var msg = '第'+(i+1)+'行商品的数量为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		}
		var receiveDetailIdArray = new Array();
		var buyDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		for ( var i = 0; i < rows.length; i++) {
			buyDetailIdArray.push(rows[i].buyDetailId);
			receiveDetailIdArray.push(rows[i].receiveDetailId);
			productIdArray.push(rows[i].productId);
			colorIdArray.push(rows[i].colorId);
			qtyArray.push(rows[i].qty);
			priceArray.push(rows[i].price);
			note1Array.push(rows[i].note1);
			note2Array.push(rows[i].note2);
			note3Array.push(rows[i].note3);
		}
		delReceiveDetailIdArray = new Array();
		//统计原记录中被删除的记录
		for ( var i = 0; i < oldReceiveDetailIdArray.length; i++) {
			var haveDel = true;
			for(var j=0;j<rows.length;j++){
				if(oldReceiveDetailIdArray[i]==rows[j].receiveDetailId){
					haveDel = false;
					break;
				}
			}
			if(haveDel){
				delReceiveDetailIdArray.push(oldReceiveDetailIdArray[i]);
			}
		}
		$('#receiveDetailIds',editForm).val(receiveDetailIdArray.join(LYS.join));
		$('#buyDetailIds',editForm).val(buyDetailIdArray.join(LYS.join));
		$('#delReceiveDetailIds',editForm).val(delReceiveDetailIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#colorIds',editForm).val(colorIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		$('#prices',editForm).val(priceArray.join(LYS.join));
		$('#note1s',editForm).val(note1Array.join(LYS.join));
		$('#note2s',editForm).val(note2Array.join(LYS.join));
		$('#note3s',editForm).val(note3Array.join(LYS.join));
		$(editDialog).mask({maskMsg:'正在保存'});
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'inWarehouse/saveReceive.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				$(editDialog).mask('hide');
				if(result.isSuccess){
					var fn = function(){
						var data = result.data;
						//赋值receiveId，并加载receiveDetail
						onOpen(data.receiveId);
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
				
			}
		 });
	}
	//打开
	var onOpen = function(receiveId){
		var url = 'inWarehouse/initReceive.do';
		var content ={receiveId:receiveId};
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
				var receiveData = eval("("+data.receiveData+")");
				$('#receiveId',editDialog).val(receiveData.receiveId);
				$('#receiveCode',editDialog).val(receiveData.receiveCode);
				$('#receiveDate',editDialog).val(receiveData.receiveDate);
				$('#deliverCode',editDialog).val(receiveData.deliverCode);
				$('#isPay'+id).linkbutton('disable');
				if(receiveData.status==1){
					$('#status',editDialog).val('已审核');
					if(receiveData.isPay==0){
						$('#isPay'+id).linkbutton('enable');
					}
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				
				$('#supplier',editDialog).combogrid('setValue',receiveData.supplierId);
				$('#warehouse',editDialog).combobox('setValue',receiveData.warehouseId);
				
				$('#otherAmount',editDialog).numberbox('setValue',receiveData.otherAmount);
				$('#amount',editDialog).numberbox('setValue',receiveData.amount);
				$('#discountAmount',editDialog).numberbox('setValue',receiveData.discountAmount);
				$('#payAmount',editDialog).numberbox('setValue',receiveData.payAmount);
				
				$('#bank',editDialog).combobox('setValue',receiveData.bankId);
				$('#employee',editDialog).combobox('setValue',receiveData.employeeId);
				$('#invoiceType',editDialog).combobox('setValue',receiveData.invoiceTypeId);
				$('#note',editDialog).val(receiveData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(receiveDetail).datagrid('loadData',detailData);
			}else{
				$.messager.alert('提示',result.message,'error');
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
		onOpen(selectRow.receiveId);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var receiveId = $('#receiveId',editDialog).val();
		$.messager.confirm("提示","确定删除记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/deleteReceive.do';
				var content ={receiveId:receiveId};
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
	//批量删除
	var onMulDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].receiveId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/mulDeleteReceive.do';
				var content ={ids:idArray.join(LYS.join)};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							selectRow = null;
							selectIndex = null;
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
	//修改审核状态
	var onUpdateStatus = function(status){
		var receiveId = $('#receiveId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(receiveId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"记录?"+msg+"后系统将进行库存计算和财务计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/updateStatusReceive.do';
				var content ={receiveId:receiveId,status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							if(status==1){
								shBtnStatus();
								onOpen(receiveId);
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
	//修改审核状态
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
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].receiveId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?"+msg+"后系统将进行库存和财务计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/mulUpdateStatusReceive.do';
				var content ={ids:idArray.join(LYS.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//批量清款
	var onMulUpdateIsPay = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要清款的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].receiveId);
		}
		$.messager.confirm("提示","确定要清款选中的记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/mulUpdateIsPayReceive.do';
				var content ={ids:idArray.join(LYS.join),isPay:1};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						}
						$.messager.alert('提示','清款成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//清款单笔
	var onUpdateIsPay = function(){
		var receiveId = $('#receiveId',editDialog).val();
		if(''==receiveId){
			$.messager.alert('提示','请选择要清款的记录','warning');
			return;
		}
		$.messager.confirm("提示","确定要清款该记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/updateIsPayReceive.do';
				var content ={receiveId:receiveId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							qkBtnStatus();
						}
						$.messager.alert('提示','清款成功','info',fn);
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
		onOpen(selectRow.receiveId);
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
		print(selectRow.receiveId);
	}
	//打印
	var print = function(receiveId){
		window.open("printReport.jsp?report=receive&data=ReportServlet?receiveId="+receiveId);
	}
	//内层打印
	var onPrintIn = function(){
		var receiveId = $('#receiveId',editDialog).val();
		print(receiveId);
	}
	//-----收货明细----------
	var receiveDetail = $('#receiveDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var productList = $('#productList');
	var lastIndex=null;
	var colors=PSS.getColorList();
	function colorFormatter(value){
		for(var i=0; i<colors.length; i++){
			if (colors[i].colorId == value) return colors[i].colorName;
		}
		return value;
	}
	var oldReceiveDetailIdArray = new Array();
	$(receiveDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'colorId',title:'颜色',width:90,align:"center",editor:{type:'combobox',options:{valueField:'colorId',
				textField:'colorName',
				data:colors}},formatter:colorFormatter},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{precision:5}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2,onChange:function(newValue,oldValue){
		    	 var totalAmount = 0 ;
				 var rows =  $(receiveDetail).datagrid('getRows');
				 var row = $(receiveDetail).datagrid('getSelected');
				 var rowIndex = $(receiveDetail).datagrid('getRowIndex',row); 
				 for ( var i = 0; i < rows.length; i++) {
					 if(i!=rowIndex){
						row = rows[i];
						totalAmount += parseFloat(row.amount);
					 }
				}
				totalAmount+=parseFloat(newValue); 
				var otherAmount = $('#otherAmount',editForm).numberbox('getValue');
				totalAmount+=parseFloat(otherAmount); 
		    	$('#amount',editForm).numberbox('setValue',totalAmount);
		    }}}},
		    {field:'buyCode',title:'采购单号',width:120,align:"center"},
		    {field:'note1',title:'备注1',width:120,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:120,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:120,align:"center",editor:{type:'text'}},
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addBuyProduct'+id,text:'从采购单添加商品',iconCls:'icon-add',handler:function(){onSelectBuyProduct()}},'-',
			{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}},'-'	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(receiveDetail).datagrid('endEdit', lastIndex);
			$(receiveDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  },onLoadSuccess:function(data){
			var rows = data.rows;
			oldReceiveDetailIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				oldReceiveDetailIdArray.push(rows[i].receiveDetailId);
			}
		}
	 });
	function setEditing(rowIndex){  
	    var editors = $(receiveDetail).datagrid('getEditors', rowIndex);  
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
	    	if(qtyEditor.target.val()==''){
	    		$(qtyEditor.target).numberbox('setValue',0.00);
	    	}
	    	if(priceEditor.target.val()==''){
	    		$(priceEditor.target).numberbox('setValue',0.00);
	    	}
	        var cost = qtyEditor.target.val() * priceEditor.target.val();  
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
		  columns:[[
			    {field:'ck',checkbox:true},
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:120,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'colorName',title:'颜色',width:90,align:"center"},
			    {field:'buyingPrice',title:'预设进价',width:90,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
		        {text:'新增',iconCls:'icon-add',handler:function(){onAddProduct()}},'-',
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProduct()}},'-',
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectProduct();
				}}
		  ]
	 });
	 var onSelectProduct = function(){
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 var searchBtnSelect = function(){
		var productCode = $('#productCodeSelectDialog',selectDialog).val();
		var productName = $('#productNameSelectDialog',selectDialog).val();
		
		var url = "dict/selectProduct.do";
		var content = {productCode:productCode,productName:productName};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(productList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	 //选择商品
	 var onSelectOKProduct = function(){
		 var rows = $(productList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择商品',"warning");
			 return;
		 }
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			 $(receiveDetail).datagrid('appendRow',{
				 receiveDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:0,
				 price:row.buyingPrice,
				 amount:0,
				 buyCode:'',
				 note1:'',
				 note2:'',
				 note3:''
			});
		}
		 $(receiveDetail).datagrid('endEdit', lastIndex);
		 $(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		onExitSelectProduct();
	 }
	 //退出选择商品界面
	 var onExitSelectProduct = function(){
		 $(selectDialog).dialog('close');
		 $(productList).datagrid('loadData',LYS.ClearData);
	 }
	 //删除商品
	 var onDeleteProduct = function(){
		 var row = $(receiveDetail).datagrid('getSelected');
		 var rowIndex = $(receiveDetail).datagrid('getRowIndex',row);
		 $(receiveDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(receiveDetail).datagrid('getRows');
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			totalAmount += parseFloat(row.amount);
		}
		$('#amount',editForm).numberbox('setValue',totalAmount);
	 }
	 //应付金额发生改变
	 $('#amount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 var discountAmount = $('#discountAmount',editForm).numberbox('getValue');
			 $('#payAmount',editForm).numberbox('setValue',newValue-parseFloat(discountAmount));
		 }
	});
	//优惠金额发生改变
	 $('#discountAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 if(newValue==''){
				 $('#discountAmount',editForm).numberbox('setValue',0.00);
				 newValue = 0.00;
			 }
			 var amount = $('#amount',editForm).numberbox('getValue');
			 $('#payAmount',editForm).numberbox('setValue',amount-newValue);
		 }
	});
	//运费发生改变
	 $('#otherAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 if(newValue==''){
				 $('#otherAmount',editForm).numberbox('setValue',0.00);
				 newValue = 0.00;
			 }
			var amount = $('#amount',editForm).numberbox('getValue');
			var totalAmount=parseFloat(amount)+parseFloat(newValue-oldValue); 
			 $('#amount',editForm).numberbox('setValue',totalAmount);
		 }
	});
	 
	 //实付金额
	 $('#payAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 if(newValue==''){
				 $('#payAmount',editForm).numberbox('setValue',0.00);
				 newValue = 0.00;
			 }
		 }
	});
	
	//-----------从采购单选择添加商品--------------
	var selectBuyDialog = $('#selectBuyDialog',$this);
	var buyList = $('#buyList',selectBuyDialog);
	//编辑框
	$(selectBuyDialog).dialog({  
	    title: '选择采购单',  
	    width:1000,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[	
					{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKBuy()}},
					{text:'退出',iconCls:'icon-cancel',handler:function(){$(selectBuyDialog).dialog('close');}}
			  ],
	    onClose:function(){
	    	$(buyList).datagrid('loadData',LYS.ClearData);
	    	$(selectBuyDialog).dialog('clear');
	    }
	});
	$(buyList).datagrid({
		  fit:true,
		  cache: false, 
		  columns:[[
			    {field:'buyId',checkbox:true},
			    {field:'buyCode',title:'采购单编号',width:150,align:"center"},
			    {field:'sourceCode',title:'原始单号',width:150,align:"center"},
			    {field:'buyDate',title:'采购日期',width:90,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true
		  
	 });
	//从采购单添加商品
	var onSelectBuyProduct = function(){
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return;
		}
		$(selectBuyDialog).dialog('open');
	}
	//查询
	 $('#searchBtnSelectBuyDialog',selectBuyDialog).click(function(){
		 searchBtnSelectBuy();
	 })
	 var searchBtnSelectBuy = function(){
		var beginDate = $('#beginDate',selectBuyDialog).val();
		var endDate = $('#endDate',selectBuyDialog).val();
		var rows = $(receiveDetail).datagrid('getRows');
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(''!=row.buyDetailId){
				idArray.push(row.buyDetailId);
			}
		}
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		var buyCode = $('#buyCodeSelectBuyDialog',selectBuyDialog).val();
		
		var url = "inWarehouse/queryReceiveBuy.do";
		var content = {beginDate:beginDate,endDate:endDate,ids:idArray.join(LYS.join),'supplier.supplierId':supplierId,buyCode:buyCode};
		$(buyList).datagrid({url:url,queryParams:content,pageNumber:1})
	 }
	 //选择采购单
	 var onSelectOKBuy = function(){
		 var rows = $(buyList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择采购单','warning');
			 return;
		 }
		 var ids = null;
		 var ids2 = null;
		 var idArray = new Array();
		 var idArray2 = new Array();
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			idArray.push(row.buyId);
		 }
		 ids = idArray.join(LYS.join);
		 rows = $(receiveDetail).datagrid('getRows');
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(''!=row.buyDetailId){
				idArray2.push(row.buyDetailId);
			}
		}
		ids2 = idArray2.join(LYS.join);
		
		var url = "inWarehouse/querySelectBuyDetailReceive.do";
		var content = {ids:ids,ids2:ids2};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			var datagridData = eval("("+data.datagridData+")");
			for ( var i = 0; i < datagridData.length; i++) {
				var row = datagridData[i];
				 $(receiveDetail).datagrid('appendRow',{
					 receiveDetailId:'',
					 productId:row.productId,
					 productCode:row.productCode,
					 productName:row.productName,
					 unitName:row.unitName,
					 sizeName:row.sizeName,
					 colorId:row.colorId,
					 qty:row.qty,
					 price:row.price,
					 amount:row.qty*row.price,
					 buyCode:row.buyCode,
					 buyDetailId:row.buyDetailId,
					 note1:row.note1,
					 note2:row.note2,
					 note3:row.note3
				});
			}
			//重新计算应付金额
			onCalAmount();
			$(selectBuyDialog).dialog('close');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	 //重新计算应付金额
	 var onCalAmount = function(){
		 var totalAmount = 0 ;
		 var rows =  $(receiveDetail).datagrid('getRows');
		 for ( var i = 0; i < rows.length; i++) {
			row = rows[i];
			totalAmount += parseFloat(row.amount);
		}
		var discountAmount = $('#discountAmount',editForm).numberbox('getValue'); 
		totalAmount+=parseFloat(discountAmount); 
		 $('#amount',editForm).numberbox('setValue',totalAmount);  
	 }
	 
	//-----------添加商品-------
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
			    {field:'productTypeName',title:'商品类型',width:120,sortable:true},  
			    {field:'productTypeCode',title:'商品类型编号',width:120,sortable:true}
			]]
		});
	   	$('#buyingPrice',addDialog).numberbox('setValue',0.0);
	   	$('#salePrice',addDialog).numberbox('setValue',0.0);
		$(addDialog).dialog('open');
	}
	//保存前的赋值操作
	var setAddValue = function(){
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
						if(result.isUnitChange){
							PSS.UnitList = null;
						}
						if(result.isSizeChange){
							PSS.SizeList = null;
						}
						if(result.isColorChange){
							PSS.ColorList = null;
						}
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
	 $(this).mask('hide');
  }
})(jQuery);   