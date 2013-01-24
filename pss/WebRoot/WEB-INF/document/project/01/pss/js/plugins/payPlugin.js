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
	  
	  var changeSearch = false;
	  var delPayDetailIdArray = null;
	  
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
				{field:'supplierName',title:'供应商',width:90,align:"center"},
				{field:'discountAmount',title:'优惠金额',width:80,align:"center"},
				{field:'payAmount',title:'实付金额',width:80,align:"center"},
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
				{text:'反审',iconCls:'icon-warn',handler:function(){onMulUpdateStatus(0)}}
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
		$('#addCheck'+id).linkbutton('enable');
		$('#deleteCheck'+id).linkbutton('enable');
	}
	//审核通过按钮的状态
	var shBtnStatus = function(){
		$('#save'+id).linkbutton('disable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
		$('#addCheck'+id).linkbutton('disable');
		$('#deleteCheck'+id).linkbutton('disable');
	}
	//反审后的按钮状态
	var fsBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('enable');
		$('#sh'+id).linkbutton('enable');
		$('#fs'+id).linkbutton('disable');
		$('#addCheck'+id).linkbutton('enable');
		$('#deleteCheck'+id).linkbutton('enable');
	}
	//添加
	var onAdd = function(){
		delPayDetailIdArray = new Array();
		var rows  = $(payDetail).datagrid('getRows');
		if(rows.length!=0){
			$(payDetail).datagrid('loadData',LYS.ClearData);
		}
		$(viewList).datagrid('unselectAll');
		selectIndex==null
		selectRow==null
		$(editForm).form('clear');
		initChoose();
		addBtnStatus();
		$('#amount',editForm).numberbox('setValue',0);
		$('#discountAmount',editForm).numberbox('setValue',0);
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
		var payAmount = $('#payAmount',editForm).numberbox('getValue');
		if(payAmount<0){
			$.messager.alert('提示','请选择合适的单据，本次付款不能小于0','warning');
			return false;
		}
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
			$.messager.alert('提示','请添加欠款单','warning');
			return false;
		}
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(row.payAmount==0){
				var msg = '第'+(i+1)+'行本次实付的金额为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
			if(Math.abs(row.needPayAmount)<Math.abs(row.discountAmount+row.payAmount)){
				var msg = '第'+(i+1)+'行';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
			
		}
		var payDetailIdArray = new Array();
		var sourceIdArray = new Array();
		var sourceCodeArray = new Array();
		var sourceDateArray = new Array();
		var payKindArray = new Array();
		var amountArray = new Array();
		var payedAmountArray = new Array();
		var discountAmountArray = new Array();
		var payAmountArray = new Array();
		
		for ( var i = 0; i < rows.length; i++) {
			payDetailIdArray.push(rows[i].payDetailId);
			sourceIdArray.push(rows[i].sourceId);
			sourceCodeArray.push(rows[i].sourceCode);
			sourceDateArray.push(rows[i].sourceDate);
			payKindArray.push(rows[i].payKind);
			amountArray.push(rows[i].amount);
			payedAmountArray.push(rows[i].payedAmount);
			discountAmountArray.push(rows[i].discountAmount);
			payAmountArray.push(rows[i].payAmount);
		}
		
		$('#payDetailIds',editForm).val(payDetailIdArray.join(LYS.join));
		$('#delPayDetailIds',editForm).val(delPayDetailIdArray.join(LYS.join));
		$('#sourceIds',editForm).val(sourceIdArray.join(LYS.join));
		$('#sourceCodes',editForm).val(sourceCodeArray.join(LYS.join));
		$('#sourceDates',editForm).val(sourceDateArray.join(LYS.join));
		$('#payKinds',editForm).val(payKindArray.join(LYS.join));
		$('#amounts',editForm).val(amountArray.join(LYS.join));
		$('#payedAmounts',editForm).val(payedAmountArray.join(LYS.join));
		$('#discountAmounts',editForm).val(discountAmountArray.join(LYS.join));
		$('#payAmounts',editForm).val(payAmountArray.join(LYS.join));
		
		alert(33);
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
		delPayDetailIdArray = new Array();
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
		delPayDetailIdArray = new Array();
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
				$('#payway',editDialog).combobox('setValue',payData.payway);
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
		if(payId==''){
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
		for ( var int = 0; int < rows.length; int++) {
			idArray.push(rows[int].payId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录"+msg+"后系统将进行财务计算!!",function(t){ 
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
	//-----付款明细----------
	var payDetail = $('#payDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var sourceList = $('#sourceList');
	var lastIndex=null;
	
	var oldPayDetailIdArray = new Array();
	$(payDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'sourceCode',title:'单据号',width:150,align:"center"},
			{field:'payKind',title:'单据类型',width:90,align:"center"},
		    {field:'sourceDate',title:'单据日期',width:90,align:"center"},
		    {field:'amount',title:'应付金额',width:90,align:"center"},
		    {field:'payedAmount',title:'已付金额',width:90,align:"center"},
		    {field:'needPayAmount',title:'还需付金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}},
		    {field:'discountAmount',title:'优惠金额',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'payAmount',title:'本次实付',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addCheck'+id,text:'添加',iconCls:'icon-add',handler:function(){onSelectCheck()}},'-',
			{id:'deleteCheck'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteCheck()}}
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
	    	//优惠只能是采购入库单，其他付款方式只能要来抵账
	    	var row = $(payDetail).datagrid('getSelected');
	    	var needPayAmount = needPayAmountEditor.target.val();
	    	var discountAmount = discountAmountEditor.target.val();
	    	$(payAmountEditor.target).numberbox('setValue',needPayAmount-discountAmount);
	    	//修改优惠金额
	    	var totalAmount = 0 ;
			var rows =  $(payDetail).datagrid('getRows');
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
	    title: '选择欠款单',  
	    width:1000,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
	});
	$(sourceList).datagrid({
		  fit:true,
		  cache: false, 
		  columns:[[
			    {field:'ck',checkbox:true},
			    {field:'sourceCode',title:'单据号',width:150,align:"center"},
			    {field:'payKind',title:'单据类型',width:90,align:"center"},
			    {field:'sourceDate',title:'单据日期',width:90,align:"center"},
			    {field:'amount',title:'应付金额',width:90,align:"center"},
			    {field:'payedAmount',title:'已付金额',width:90,align:"center"},
			    {field:'needPayAmount',title:'还需付金额',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKCheck()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectCheck();
				}}
		  ]
	 });
	 var onSelectCheck = function(){
		//供应商
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		if(supplierId==''){
			$.messager.alert('提示','请选择供应商','warning');
			return;
		}
		searchBtnSelect();
	 }
	 //查询
	 var searchBtnSelect = function(){
		var rows = $(payDetail).datagrid('getRows');
		var idArray = new Array();
		for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			if(''!=row.sourceId){
				idArray.push(row.sourceId);
			}
		}
		var supplierId = $('#supplier',editForm).combogrid('getValue');
		
		var url = "finance/queryNeedCheckPay.do";
		var content = {ids:idArray.join(LYS.join),supplierId:supplierId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(selectDialog).dialog('open');
			var data = result.data;
			$(sourceList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	 //选择入库单
	 var onSelectOKCheck = function(){
		
		 var rows = $(sourceList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择入库单',"warning");
			 return;
		 }
		 for ( var int = 0; int < rows.length; int++) {
			var row = rows[int];
			 $(payDetail).datagrid('appendRow',{
				 payDetailId:'',
				 sourceId:row.sourceId,
				 sourceCode:row.sourceCode,
				 payKind:row.payKind,
				 sourceDate:row.sourceDate,
				 amount:row.amount,
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
		onExitSelectCheck();
	 }
	 //退出选择入库单界面
	 var onExitSelectCheck = function(){
		 $(selectDialog).dialog('close');
		 $(sourceList).datagrid('loadData',LYS.ClearData);
	 }
	 //删除入库单
	 var onDeleteCheck = function(){
		 var row = $(payDetail).datagrid('getSelected');
		 if(row.payDetailId!=''){
			 delPayDetailIdArray.push(row.payDetailId);
		 }
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
			 var amount = $('#amount',editForm).numberbox('getValue');
			 $('#payAmount',editForm).numberbox('setValue', parseFloat(amount)-newValue);
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
		$('#amount',editForm).numberbox('setValue',totalAmount);
		$('#discountAmount',editForm).numberbox('setValue',totalDiscountAmount);
		$('#payAmount',editForm).numberbox('setValue',totalPayAmount);
	} 
  }
})(jQuery);   