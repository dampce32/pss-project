// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.rejectInit = function() {
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
	  
	  var warehouseData = null;
	  var bankData = null;
	  var employeeData = null;
	  var invoiceTypeData = null;
	  
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
				{field:'rejectCode',title:'退货单号',width:120,align:"center"},
				{field:'rejectDate',title:'退货日期',width:80,align:"center"},
				{field:'warehouseName',title:'退货仓库',width:90,align:"center"},
				{field:'supplierName',title:'供应商',width:90,align:"center"},
				{field:'amount',title:'应退金额',width:80,align:"center"},
				{field:'payAmount',title:'实退金额',width:80,align:"center"},
				{field:'checkAmount',title:'对账金额',width:80,align:"center"},
				{field:'noPayAmount',title:'欠款',width:80,align:"center",
					formatter: function(value,row,index){
						return row.amount-row.payAmount-row.checkAmount;
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
				}}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onMulDelete()}},'-',
				{text:'已审',iconCls:'icon-info',handler:function(){onMulUpdateStatus(1)}},'-',
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}},'-',
				{text:'打印',iconCls:'icon-print',handler:function(){onPrint()}}
		  ],
		  onDblClickRow:function(rowIndex, rowData){
				onUpdate();
		  },
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
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
		var rejectCode = $('#rejectCodeMulSearch',$this).val();
		
		var url = "inWarehouse/queryReject.do";
		var content = {rejectCode:rejectCode,page:pageNumber,rows:pageSize};
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
	
	$("#mulSearchClear").click(function(){
		$("#mulSearchForm").form('clear');
		return false;
	});
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		var rejectCode = $('#rejectCodeSearch',queryContent).val();
		
		var url = "inWarehouse/queryReject.do";
		var content = {rejectCode:rejectCode,page:pageNumber,rows:pageSize};
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
		var url = "inWarehouse/getTotalCountReject.do";
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
	    title: '采购退货单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    },
	    toolbar:[	
		 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
		 			{id:'print'+id,text:'打印',iconCls:'icon-print',handler:function(){onPrintIn()}},'-',
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
	//添加
	var onAdd = function(){
		var rows  = $(rejectDetail).datagrid('getRows');
		if(rows.length!=0){
			$(rejectDetail).datagrid({url:LYS.ClearUrl});
		}
		$(viewList).datagrid('unselectAll');
		selectIndex=null;
		selectIndex=null;
		addBtnStatus();
		
		$(editForm).form('clear');
		initChoose();
		$('#amount',editForm).numberbox('setValue',0);
		$('#payAmount',editForm).numberbox('setValue',0);
		$(editDialog).dialog('open');
	}
	
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
		//退货仓库
		var warehouseId = $('#warehouse',editForm).combobox('getValue');
		if(warehouseId==''){
			$.messager.alert('提示','请选择退货仓库','warning');
			return false;
		}
		$('#warehouseId',editForm).val(warehouseId);
		//退货日期
		var rejectDate = $('#rejectDate',editForm).val();
		if(rejectDate==''){
			$.messager.alert('提示','请选择退货日期','warning');
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
		//验证添加的商品行
		$(rejectDetail).datagrid('endEdit', lastIndex);
		 $(rejectDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(rejectDetail).datagrid('getRows');
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
		var rejectDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		for ( var i = 0; i < rows.length; i++) {
			rejectDetailIdArray.push(rows[i].rejectDetailId);
			productIdArray.push(rows[i].productId);
			colorIdArray.push(rows[i].colorId);
			qtyArray.push(rows[i].qty);
			priceArray.push(rows[i].price);
			note1Array.push(rows[i].note1);
			note2Array.push(rows[i].note2);
			note3Array.push(rows[i].note3);
		}
		
		delRejectDetailIdArray = new Array();
		//统计原记录中被删除的记录
		for ( var i = 0; i < oldRejectDetailIdArray.length; i++) {
			var haveDel = true;
			for(var j=0;j<rows.length;j++){
				if(oldRejectDetailIdArray[i]==rows[j].rejectDetailId){
					haveDel = false;
					break;
				}
			}
			if(haveDel){
				delRejectDetailIdArray.push(oldRejectDetailIdArray[i]);
			}
		}
		$('#rejectDetailIds',editForm).val(rejectDetailIdArray.join(LYS.join));
		$('#delRejectDetailIds',editForm).val(delRejectDetailIdArray.join(LYS.join));
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
		var url = 'inWarehouse/saveReject.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				$(editDialog).mask('hide');
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var data = result.data;
						//赋值rejectId，并加载rejectDetail
						onOpen(data.rejectId);
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
		onOpen(selectRow.rejectId);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var rejectId = $('#rejectId',editDialog).val();
		$.messager.confirm("提示","确定删除记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/deleteReject.do';
				var content ={rejectId:rejectId};
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
			idArray.push(rows[i].rejectId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/mulDeleteReject.do';
				var content ={ids:idArray.join(LYS.join)};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
							selectRow = null;
							selectIndex = null;
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
		var rejectId = $('#rejectId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(rejectId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行库存计算和财务计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/updateStatusReject.do';
				var content ={rejectId:rejectId,status:status};
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
		if(shzt==1){
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
			idArray.push(rows[i].rejectId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?审核后单据不能再修改和删除，系统将进行库存和财务计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/mulUpdateStatusReject.do';
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
	//打开
	var onOpen = function(rejectId){
		var url = 'inWarehouse/initReject.do';
		var content ={rejectId:rejectId};
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
				var rejectData = eval("("+data.rejectData+")");
				$('#rejectId',editDialog).val(rejectData.rejectId);
				$('#rejectCode',editDialog).val(rejectData.rejectCode);
				$('#rejectDate',editDialog).val(rejectData.rejectDate);
				$('#buyCode',editDialog).val(rejectData.buyCode);
				if(rejectData.status==1){
					$('#shzt',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#shzt',editDialog).val('未审核');
				}
				
				$('#supplier',editDialog).combogrid('setValue',rejectData.supplierId);
				$('#warehouse',editDialog).combobox('setValue',rejectData.warehouseId);
				
				$('#amount',editDialog).val(rejectData.amount);
				$('#payAmount',editDialog).val(rejectData.payAmount);
				
				$('#bank',editDialog).combobox('setValue',rejectData.bankId);
				$('#employee',editDialog).combobox('setValue',rejectData.employeeId);
				$('#note',editDialog).val(rejectData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(rejectDetail).datagrid('loadData',detailData);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//上下一笔
	var onOpenIndex = function(index){
		var rows = $(viewList).datagrid('getRows');
		selectIndex = selectIndex + index;
		selectRow = rows[selectIndex];
		onOpen(selectRow.rejectId);
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
		print(selectRow.rejectId);
	}
	//打印
	var print = function(rejectId){
		window.open("printReport.jsp?report=reject&data=ReportServlet?rejectId="+rejectId);
	}
	//内层打印
	var onPrintIn = function(){
		var rejectId = $('#rejectId',editDialog).val();
		print(rejectId);
	}
	//-----退货明细----------
	var rejectDetail = $('#rejectDetail',editDialog);
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
	var oldRejectDetailIdArray = new Array();
	$(rejectDetail).datagrid({
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
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2,onChange:function(newValue,oldValue){
		    	 var totalAmount = 0 ;
				 var rows =  $(rejectDetail).datagrid('getRows');
				 var row = $(rejectDetail).datagrid('getSelected');
				 var rowIndex = $(rejectDetail).datagrid('getRowIndex',row); 
				 for ( var i = 0; i < rows.length; i++) {
					 if(i!=rowIndex){
						row = rows[i];
						totalAmount += parseFloat(row.amount);
					 }
				}
				totalAmount+=parseFloat(newValue); 
		    	$('#amount',editForm).numberbox('setValue',totalAmount);
		    }}}},
		    {field:'note1',title:'备注1',width:120,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:120,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:120,align:"center",editor:{type:'text'}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}}
	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(rejectDetail).datagrid('endEdit', lastIndex);
			$(rejectDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  },onLoadSuccess:function(data){
			var rows = data.rows;
			oldRejectDetailIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				oldRejectDetailIdArray.push(rows[i].rejectDetailId);
			}
		}
	 });
	function setEditing(rowIndex){  
	    var editors = $(rejectDetail).datagrid('getEditors', rowIndex);  
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
			    {field:'qty',title:'库存数量',width:100,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProduct()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectProduct();
				}}
		  ]
	 });
	 var onSelectProduct = function(){
		 var warehouseId = $('#warehouse',editDialog).combobox('getValue'); 
		if(warehouseId==''){
			 $.messager.alert('提示','请先选择要退货仓库',"warning");
			 return;
		}
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 var searchBtnSelect = function(){
		var warehouseId = $('#warehouse',editDialog).combobox('getValue'); 
		var productCode = $('#productCodeSelectDialog',selectDialog).val();
		var productName = $('#productNameSelectDialog',selectDialog).val();
		var options = $(productList).datagrid('options');
		var url = "store/selectRejectStore.do";
		
		var content = {'warehouse.warehouseId':warehouseId,'product.productCode':productCode,'product.productName':productName,page:options.pageNumber,rows:options.pageSize};
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
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			 $(rejectDetail).datagrid('appendRow',{
				 rejectDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:row.qty,
				 price:0,
				 amount:0,
				 note1:'',
				 note2:'',
				 note3:''
			});
		}
		 $(rejectDetail).datagrid('endEdit', lastIndex);
		 $(rejectDetail).datagrid('unselectAll');
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
		 var row = $(rejectDetail).datagrid('getSelected');
		 var rowIndex = $(rejectDetail).datagrid('getRowIndex',row);
		 $(rejectDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(rejectDetail).datagrid('getRows');
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			totalAmount += parseFloat(row.amount);
		}
		$('#amount',editForm).val(totalAmount);
		$('#amount',editForm).change();
	 }
	 //应付金额发生改变
	 $('#amount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 $('#payAmount',editForm).numberbox('setValue',newValue);
		 }
	});
	//应付金额发生改变
	 $('#payAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 if(newValue==''){
				 $('#payAmount',editForm).numberbox('setValue',0.00);
			 }
		 }
	});
	 $(this).mask('hide');
  }
})(jQuery);   