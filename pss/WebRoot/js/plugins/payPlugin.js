// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.payInit = function() {
	  
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
				{field:'payCode',title:'付款单号',width:120,align:"center"},
				{field:'payDate',title:'付款日期',width:80,align:"center"},
				{field:'receiveDate',title:'收货日期',width:80,align:"center"},
				{field:'sourceCode',title:'原始单号',width:120,align:"center"},
				{field:'supplierName',title:'供应商',width:90,align:"center"},
				{field:'otherAmount',title:'运费',width:80,align:"center"},
				{field:'amount',title:'应付定金',width:80,align:"center"},
				{field:'payAmount',title:'实付定金',width:80,align:"center"},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'status',title:'状态',width:80,align:"center",
					formatter: function(value,row,index){
						if(row.status==0){
							return '未审';
						}else if(row.status==1){
							return '已审';
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
				{text:'已审',iconCls:'icon-edit',handler:function(){onMulUpdateStatus(1)}},'-',
				{text:'反审',iconCls:'icon-edit',handler:function(){onMulUpdateStatus(0)}}
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
				height: 110
			});
			$('#'+id).layout('resize');
			changeSearch = true;
		}
	})
	
	$("#mulSearch",$this).click(function(){
		var payCode = $('#payCodeMulSearch',$this).val();
		
		var url = "finance/queryPay.do";
		var content = {payCode:payCode,page:pageNumber,rows:pageSize};
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
		var payCode = $('#payCodeSearch',queryContent).val();
		
		var url = "finance/queryPay.do";
		var content = {payCode:payCode,page:pageNumber,rows:pageSize};
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
		var url = "finance/getTotalCountPay.do";
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
	    title: '编辑付款单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	$(editForm).form('clear');
			$(payDetail).datagrid({url:LYS.ClearUrl});
	    },
	    toolbar:[	
	 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
	 			{id:'sh'+id,text:'审核',iconCls:'icon-edit',handler:function(){onUpdateStatus(1);}},'-',
	 			{id:'fs'+id,text:'反审',iconCls:'icon-edit',handler:function(){onUpdateStatus(0);}},'-',
	 			{id:'pre'+id,text:'上一笔',iconCls:'icon-edit',handler:function(){onOpenIndex(-1);}},'-',
	 			{id:'next'+id,text:'下一笔',iconCls:'icon-edit',handler:function(){onOpenIndex(1);}},'-',
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
	var shBtn = function(){
		$('#save'+id).linkbutton('disable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
		$('#addReceive'+id).linkbutton('disable');
		$('#deleteReceive'+id).linkbutton('disable');
	}
	//反审后的按钮状态
	var fsBtn = function(){
		$('#save'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('enable');
		$('#sh'+id).linkbutton('enable');
		$('#fs'+id).linkbutton('disable');
		$('#addReceive'+id).linkbutton('enable');
		$('#deleteReceive'+id).linkbutton('enable');
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
	}
	//保存前的赋值操作
	var setValue = function(){
		//付款日期
		var payDate = $('#payDate',editForm).val();
		if(payDate==''){
			$.messager.alert('提示','请选择付款日期','warning');
			return false;
		}
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return false;
		}
		$('#supplierId',editForm).val(supplierId);
		//银行
		var bankId = $('#bank',editForm).combobox('getValue');
		if(bankId==''){
			$.messager.alert('提示','请选择付款银行','warning');
			return false;
		}
		$('#bankId',editForm).val(bankId);
		//支付方式
		var payway = $('#payway',editForm).combobox('getValue');
		if(payway==''){
			$.messager.alert('提示','请选择支付方式','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employee',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		$('#employeeId',editForm).val(employeeId);
		//验证添加的入库单行
		$(payDetail).datagrid('endEdit', lastIndex);
		 $(payDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(payDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请添加欠款入库单','warning');
			return false;
		}
		for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			if(row.payAmount==0){
				var msg = '第'+(int+1)+'行本次实付的金额为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		}
		var payDetailIdArray = new Array();
		var receiveIdArray = new Array();
		var payKindArray = new Array();
		var amountArray = new Array();
		var discountedAmountArray = new Array();
		var payedAmountArray = new Array();
		var discountAmountArray = new Array();
		var payAmountArray = new Array();
		
		for ( var i = 0; i < rows.length; i++) {
			payDetailIdArray.push(rows[i].payDetailId);
			receiveIdArray.push(rows[i].receiveId);
			payKindArray.push(rows[i].payKind);
			amountArray.push(rows[i].amount);
			discountedAmountArray.push(rows[i].discountedAmount);
			payedAmountArray.push(rows[i].payedAmount);
			discountAmountArray.push(rows[i].discountAmount);
			payAmountArray.push(rows[i].payAmount);
		}
		
		delPayDetailIdArray = new Array();
		//统计原记录中被删除的记录
		for ( var int = 0; int < oldPayDetailIdArray.length; int++) {
			var haveDel = true;
			for(var i=0;i<rows.length;i++){
				if(oldPayDetailIdArray[int]==rows[i].payDetailId){
					haveDel = false;
					break;
				}
			}
			if(haveDel){
				delPayDetailIdArray.push(oldPayDetailIdArray[int]);
			}
		}
		$('#payDetailIds',editForm).val(payDetailIdArray.join(LYS.join));
		$('#delPayDetailIds',editForm).val(delPayDetailIdArray.join(LYS.join));
		$('#receiveIds',editForm).val(receiveIdArray.join(LYS.join));
		$('#payKinds',editForm).val(payKindArray.join(LYS.join));
		$('#amounts',editForm).val(amountArray.join(LYS.join));
		$('#discountedAmounts',editForm).val(discountedAmountArray.join(LYS.join));
		$('#payedAmounts',editForm).val(payedAmountArray.join(LYS.join));
		$('#discountAmounts',editForm).val(discountAmountArray.join(LYS.join));
		$('#payAmounts',editForm).val(payAmountArray.join(LYS.join));
		
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'finance/savePay.do'
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
						//赋值payId，并加载payDetail
						onOpen(data.payId);
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
		onOpen(selectRow.payId);
		
		$(editDialog).dialog('open');
	 }
	//打开
	var onOpen = function(payId){
		var url = 'finance/initPay.do';
		var content ={payId:payId};
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
				var payData = eval("("+data.payData+")");
				$('#payId',editDialog).val(payData.payId);
				$('#payCode',editDialog).val(payData.payCode);
				$('#payDate',editDialog).val(payData.payDate);
				if(payData.status==1){
					$('#status',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				
				$('#supplier',editDialog).combogrid('setValue',payData.supplierId);
				
				$('#bank',editDialog).combobox('setValue',payData.bankId);
				$('#employee',editDialog).combobox('setValue',payData.employeeId);
				$('#note',editDialog).val(payData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(payDetail).datagrid('loadData',detailData);
				summary();
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//删除
	var onDelete = function(){
		var payId = $('#payId',editDialog).val();
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/deletePay.do';
				var content ={payId:payId};
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
		for ( var int = 0; int < rows.length; int++) {
			idArray.push(rows[int].payId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/mulDelPay.do';
				var content ={ids:idArray.join(LYS.join)};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
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
		var payId = $('#payId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(selectRow==null){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/updateStatusPay.do';
				var content ={payId:payId,status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							if(status==1){
								shBtn();
								$('#status',editDialog).val('已审核');
							}else{
								fsBtn();
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
		for ( var int = 0; int < rows.length; int++) {
			idArray.push(rows[int].payId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录"+msg+"后系统将进行库存和财务计算!!",function(t){ 
			if(t){
				var url = 'finance/mulUpdateStatusPay.do';
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
	//上下一笔
	var onOpenIndex = function(index){
		var rows = $(viewList).datagrid('getRows');
		selectIndex = selectIndex + index;
		selectRow = rows[selectIndex];
		onOpen(selectRow.payId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
	//-----收货明细----------
	var payDetail = $('#payDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var receiveList = $('#receiveList');
	var lastIndex=null;
	
	var oldPayDetailIdArray = new Array();
	$(payDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'receiveCode',title:'入库单号',width:90,align:"center"},
			{field:'payKind',title:'单据类型',width:90,align:"center"},
		    {field:'receiveDate',title:'单据日期',width:90,align:"center"},
		    {field:'amount',title:'应付金额',width:90,align:"center"},
		    {field:'discountedAmount',title:'已优惠金额',width:90,align:"center"},
		    {field:'payedAmount',title:'已付金额',width:90,align:"center"},
		    {field:'needPayAmount',title:'还需付金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}},
		    {field:'discountAmount',title:'优惠金额',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'payAmount',title:'本次实付',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addReceive'+id,text:'添加入库单',iconCls:'icon-add',handler:function(){onSelectReceive()}},'-',
			{id:'deleteReceive'+id,text:'删除入库单',iconCls:'icon-remove',handler:function(){onDeleteReceive()}}
	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(payDetail).datagrid('endEdit', lastIndex);
			$(payDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  },onLoadSuccess:function(data){
			var rows = data.rows;
			oldPayDetailIdArray = new Array();
			for ( var int = 0; int < rows.length; int++) {
				oldPayDetailIdArray.push(rows[int].payDetailId);
			}
		}
	 });
	function setEditing(rowIndex){  
	    var editors = $(payDetail).datagrid('getEditors', rowIndex);  
	    var needPayAmountEditor = editors[0];  
	    var discountAmountEditor = editors[1];  
	    var payAmountEditor = editors[2];  
	    discountAmountEditor.target.bind('change', function(){  
	    	var needPayAmount = needPayAmountEditor.target.val();
	    	var discountAmount = discountAmountEditor.target.val();
	    	$(payAmountEditor.target).numberbox('setValue',needPayAmount-discountAmount);
	    	//修改优惠金额
	    	var totalAmount = 0 ;
			var rows =  $(payDetail).datagrid('getRows');
			var row = $(payDetail).datagrid('getSelected');
			var rowIndex = $(payDetail).datagrid('getRowIndex',row); 
			for ( var int = 0; int < rows.length; int++) {
				 if(int!=rowIndex){
					row = rows[int];
					totalAmount += parseFloat(row.discountAmount);
				 }
			}
			totalAmount+=parseFloat(discountAmount); 
			$('#discountAmount',editDialog).numberbox('setValue',totalAmount);
	    });  
	    payAmountEditor.target.bind('change', function(){  
	    	var payAmount = payAmountEditor.target.val();
	    	//修改本次实付金额
	    	var totalAmount = 0 ;
			var rows =  $(payDetail).datagrid('getRows');
			var row = $(payDetail).datagrid('getSelected');
			var rowIndex = $(payDetail).datagrid('getRowIndex',row); 
			for ( var int = 0; int < rows.length; int++) {
				 if(int!=rowIndex){
					row = rows[int];
					totalAmount += parseFloat(row.payAmount);
				 }
			}
			totalAmount+=parseFloat(payAmount); 
			$('#payAmount',editDialog).numberbox('setValue',totalAmount);
	    });  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择欠款入库单',  
	    width:1000,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
	});
	$(receiveList).datagrid({
		  fit:true,
		  cache: false, 
		  columns:[[
			    {field:'ck',checkbox:true},
			    {field:'receiveCode',title:'入库单号',width:90,align:"center"},
			    {field:'receiveDate',title:'入库日期',width:120,align:"center"},
			    {field:'amount',title:'应付金额',width:90,align:"center"},
			    {field:'discountedAmount',title:'已优惠金额',width:90,align:"center"},
			    {field:'payedAmount',title:'已付金额',width:90,align:"center"},
			    {field:'needPayAmount',title:'还需付金额',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-save',handler:function(){onSelectOKReceive()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectReceive();
				}}
		  ]
	 });
	 var onSelectReceive = function(){
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return;
		}
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 var searchBtnSelect = function(){
		var beginDate = $('#beginDate',selectDialog).val();
		if(beginDate==''){
			$.messager.alert('提示','请选择入库单开始日期','warning');
			return;
		}
		var endDate = $('#endDate',selectDialog).val();
		if(endDate==''){
			$.messager.alert('提示','请选择入库单结束日期','warning');
			return;
		}
		var rows = $(payDetail).datagrid('getRows');
		var idArray = new Array();
		for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			if(''!=row.receiveId){
				idArray.push(row.receiveId);
			}
		}
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		var receiveCode = $('#receiveCodeSelectDialog',selectDialog).val();
		
		var url = "inWarehouse/queryNeedPayReceive.do";
		var content = {beginDate:beginDate,endDate:endDate,ids:idArray.join(LYS.join),supplierId:supplierId,receiveCode:receiveCode};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(receiveList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	 //选择入库单
	 var onSelectOKReceive = function(){
		 var rows = $(receiveList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择入库单',"warning");
			 return;
		 }
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			 $(payDetail).datagrid('appendRow',{
				 payDetailId:'',
				 receiveId:row.receiveId,
				 receiveCode:row.receiveCode,
				 payKind:'采购入库',
				 receiveDate:row.receiveDate,
				 amount:row.amount,
				 discountedAmount:row.discountedAmount,
				 payedAmount:row.payedAmount,
				 needPayAmount:row.needPayAmount,
				 discountAmount:0,
				 payAmount:row.needPayAmount
			});
		}
		 $(payDetail).datagrid('endEdit', lastIndex);
		 $(payDetail).datagrid('unselectAll');
		 summary();
		lastIndex = null;
		
		onExitSelectReceive();
	 }
	 //退出选择入库单界面
	 var onExitSelectReceive = function(){
		 $(selectDialog).dialog('close');
		 $(receiveList).datagrid('loadData',LYS.ClearData);
	 }
	 //删除入库单
	 var onDeleteReceive = function(){
		 var row = $(payDetail).datagrid('getSelected');
		 var rowIndex = $(payDetail).datagrid('getRowIndex',row);
		 $(payDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 //统计删除后的应付金额
		 var totalAmount = 0 ;
		 var rows =  $(payDetail).datagrid('getRows');
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			totalAmount += parseFloat(row.amount);
		}
		$('#amount',editForm).val(totalAmount);
		$('#amount',editForm).change();
	 }
	 //优惠金额发生改变
	 $('#discountAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 var needPayAmount = $('#needPayAmount',editForm).numberbox('getValue');
			 $('#payAmount',editForm).numberbox('setValue', parseFloat(needPayAmount)-newValue);
		 }
	});
	//统计付款单中的应付金额、优惠金额、本次支付金额
	var summary = function(){
		//统计删除后的应付金额
		 var totalAmount = 0 ;
		 var totalDiscountAmount = 0 ;
		 var totalPayAmount = 0 ;
		 var rows =  $(payDetail).datagrid('getRows');
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			totalAmount += parseFloat(row.needPayAmount);
			totalDiscountAmount += parseFloat(row.discountAmount);
			totalPayAmount += parseFloat(row.payAmount);
		}
		$('#needPayAmount',editForm).numberbox('setValue',totalAmount);
		$('#discountAmount',editForm).numberbox('setValue',totalDiscountAmount);
		$('#payAmount',editForm).numberbox('setValue',totalPayAmount);
	} 
  }
})(jQuery);   