// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.receiptInit = function() {
	  
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
	  //点击查询按钮
	 $('#searchBtn',queryContent).click(function(){
		  var receiptCode = $('#receiptCode',queryContent).val();
		  var customerName = $('#customer',queryContent).val();
		  var beginDate = $('#beginDate',queryContent).val();
		  var endDate = $('#endDate',queryContent).val();
		  var status = $('#status',queryContent).combobox('getValue');
		  var url = 'finance/queryReceipt.do';
		  var queryParams ={receiptCode:receiptCode,'customer.customerName':customerName,beginDate:beginDate,endDate:endDate,status:status};
		  $(viewList).datagrid({
			url:url,
			queryParams:queryParams,
			pageNumber:1
		  })
	  })
	  //重置按钮
	  $('#resetBtn',queryContent).click(function(){
		  $('#receiptCode',queryContent).val('');
		  $('#customer',queryContent).val('');
		  $('#beginDate',queryContent).val('');
		  $('#endDate',queryContent).val('');
		  $('#status',queryContent).combobox('setValue',-1);
	  })
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
		    {field:'receiptId',checkbox:true},
			{field:'receiptCode',title:'收款单号',width:120,align:"center"},
			{field:'receiptDate',title:'收款日期',width:80,align:"center"},
			{field:'customerName',title:'客户',width:90,align:"center"},
			{field:'discountAmount',title:'优惠金额',width:80,align:"center"},
			{field:'receiptAmount',title:'实收金额',width:80,align:"center"},
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
				$(viewList).datagrid('unselectAll');
				$(viewList).datagrid('selectRow',selectIndex);
		  }
	 });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑收款单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[	
	 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
	 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
	 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
	 			{id:'pre'+id,text:'上一笔',iconCls:'icon-left',handler:function(){onOpenIndex(-1);}},'-',
	 			{id:'next'+id,text:'下一笔',iconCls:'icon-right',handler:function(){onOpenIndex(1);}},'-',
	 			{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}
	 			}
	 	  ]
	}); 
	var onExit = function(){
		$(editDialog).dialog('close');
	}
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
		delReciptDetailIdArray = new Array();
		var rows  = $(receiptDetail).datagrid('getRows');
		if(rows.length!=0){
			$(receiptDetail).datagrid('loadData',LYS.ClearData);
		}
		$(viewList).datagrid('unselectAll');
		selectIndex==null
		selectRow==null
		$(editForm).form('clear');
		initChoose();
		addBtnStatus();
		$('#amount',editForm).numberbox('setValue',0);
		$('#discountAmount',editForm).numberbox('setValue',0);
		$('#receiptAmount',editForm).numberbox('setValue',0);
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
	}
	//保存前的赋值操作
	var setValue = function(){
		var receiptAmount = $('#receiptAmount',editForm).numberbox('getValue');
		if(receiptAmount<=0 || receiptAmount==''){
			$.messager.alert('提示','请选择合适的单据，本次收款不能小于0','warning');
			return false;
		}
		//付款日期
		var receiptDate = $('#receiptDate',editForm).val();
		if(receiptDate==''){
			$.messager.alert('提示','请选择收款日期','warning');
			return false;
		}
		//供应商
		var customerId = $('#customerId',editForm).val();
		if(customerId==''){
			$.messager.alert('提示','请选择客户','warning');
			return false;
		}
		//支付方式
		var receiptway = $('#receiptway',editForm).combobox('getValue');
		if(receiptway==''){
			$.messager.alert('提示','请选择支付方式','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employeeId',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		//验证添加的入库单行
		$(receiptDetail).datagrid('endEdit', lastIndex);
		$(receiptDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(receiptDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请添加收款单','warning');
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
		var receiptDetailIdArray = new Array();
		var sourceIdArray = new Array();
		var sourceCodeArray = new Array();
		var sourceDateArray = new Array();
		var receiptKindArray = new Array();
		var amountArray = new Array();
		var receiptedAmountArray = new Array();
		var discountAmountArray = new Array();
		var receiptAmountArray = new Array();
		
		for ( var i = 0; i < rows.length; i++) {
			receiptDetailIdArray.push(rows[i].receiptDetailId);
			sourceIdArray.push(rows[i].sourceId);
			sourceCodeArray.push(rows[i].sourceCode);
			sourceDateArray.push(rows[i].sourceDate);
			receiptKindArray.push(rows[i].receiptKind);
			amountArray.push(rows[i].amount);
			receiptedAmountArray.push(rows[i].receiptedAmount);
			discountAmountArray.push(rows[i].discountAmount);
			receiptAmountArray.push(rows[i].receiptAmount);
		}
		
		$('#receiptDetailIds',editForm).val(receiptDetailIdArray.join(LYS.join));
		$('#delreceiptDetailIds',editForm).val(delReciptDetailIdArray.join(LYS.join));
		$('#sourceIds',editForm).val(sourceIdArray.join(LYS.join));
		$('#sourceCodes',editForm).val(sourceCodeArray.join(LYS.join));
		$('#sourceDates',editForm).val(sourceDateArray.join(LYS.join));
		$('#receiptKinds',editForm).val(receiptKindArray.join(LYS.join));
		$('#amounts',editForm).val(amountArray.join(LYS.join));
		$('#receiptedAmounts',editForm).val(receiptedAmountArray.join(LYS.join));
		$('#discountAmounts',editForm).val(discountAmountArray.join(LYS.join));
		$('#receiptAmounts',editForm).val(receiptAmountArray.join(LYS.join));
		
		return true;
	}
	//保存
	var onSave = function(){
		var url = null;
		if($('#receiptId',editForm).val()==''){
			url='finance/addReceipt.do';
		}else{
			url = 'finance/updateReceipt.do';
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
						//赋值receiptId，并加载receiptDetail
						onOpen(data.receiptId);
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
		delReciptDetailIdArray = new Array();
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		initChoose();
		onOpen(selectRow.receiptId);
		
		$(editDialog).dialog('open');
	 }
	//打开
	var onOpen = function(receiptId){
		delReciptDetailIdArray = new Array();
		var url = 'finance/initReceipt.do';
		var content ={receiptId:receiptId};
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
				var receiptData = eval("("+data.receiptData+")");
				$('#receiptId',editDialog).val(receiptData.receiptId);
				$('#receiptCode',editDialog).val(receiptData.receiptCode);
				$('#receiptDate',editDialog).val(receiptData.receiptDate);
				if(receiptData.status==1){
					$('#status',editDialog).val('已审核');
					shBtnStatus();
				}else{
					fsBtnStatus();
					$('#status',editDialog).val('未审核'); 
				}
				$('#customerId',editDialog).val(receiptData.customerId);
				$('#customer',editDialog).combogrid('setText',receiptData.customerName);
				$('#receiptway',editDialog).combobox('setValue',receiptData.receiptway);
				$('#bankId',editDialog).combobox('setValue',receiptData.bankId);
				$('#employeeId',editDialog).combobox('setValue',receiptData.employeeId);
				$('#note',editDialog).val(receiptData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(receiptDetail).datagrid('loadData',detailData);
				summary();
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//删除
	var onDelete = function(){
		var receiptId = $('#receiptId',editDialog).val();
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/deleteReceipt.do';
				var content ={receiptId:receiptId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							selectRow = null;
							selectIndex = null;
							$(editDialog).dialog('close');
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
	//删除多个
	var onMulDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		var idArray = new Array();
		$(rows).each(function(index,row){
			idArray.push(row.receiptId);
		})
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/mulDeleteReceipt.do';
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
		var receiptId = $('#receiptId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(receiptId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/updateStatusReceipt.do';
				var content ={receiptId:receiptId,status:status};
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
			idArray.push(row.receiptId);
		})
		$.messager.confirm("提示","确定要"+msg+"选中的记录"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/mulUpdateStatusReceipt.do';
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
		onOpen(selectRow.receiptId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
	//-----收款明细----------
	var receiptDetail = $('#receiptDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var sourceList = $('#sourceList');
	var lastIndex=null;
	
	$(receiptDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{id:'addCheck'+id,text:'添加',iconCls:'icon-add',handler:function(){onSelectCheck()}},'-',
			{id:'deleteCheck'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteCheck()}}
	  ],
	  columns:[[
		    {field:'receiptDetailId',hidden:true},
		    {field:'sourceId',hidden:true},
		    {field:'sourceCode',title:'单据号',width:150,align:"center"},
			{field:'receiptKind',title:'单据类型',width:90,align:"center"},
		    {field:'sourceDate',title:'单据日期',width:90,align:"center"},
		    {field:'amount',title:'应收金额',width:90,align:"center"},
		    {field:'receiptedAmount',title:'已收金额',width:90,align:"center"},
		    {field:'needReceiptAmount',title:'还需收金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}},
		    {field:'discountAmount',title:'优惠金额',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'receiptAmount',title:'本次实收',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}}
	  ]],
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(receiptDetail).datagrid('endEdit', lastIndex);
			$(receiptDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  }
	 });
	function setEditing(rowIndex){  
	    var editors = $(receiptDetail).datagrid('getEditors', rowIndex);  
	    var needReceiptAmountEditor = editors[0];  
	    var discountAmountEditor = editors[1];  
	    var receiptAmountEditor = editors[2];  
	    discountAmountEditor.target.bind('change', function(){  
	    	//优惠只能是销售出库单，其他收款方式只能要来抵账
	    	var row = $(receiptDetail).datagrid('getSelected');
	    	if(row.receiptKind!='销售出库'){
	    		$.messager.alert('提示','只有销售出库，才能输入优惠金额，而其他类型的单据，只能要来作为销售出库单的抵扣','warning');
	    		 $(discountAmountEditor.target).numberbox('setValue',0.00);
	    		return;
	    	}
	    	var needReceiptAmount = needReceiptAmountEditor.target.val();
	    	var discountAmount = discountAmountEditor.target.val();
	    	if(needReceiptAmount==''){
	    		needReceiptAmount =0;
	    	}
	    	if(discountAmount==''){
	    		discountAmount =0;
	    	}
	    	$(receiptAmountEditor.target).numberbox('setValue',needReceiptAmount-discountAmount);
	    	//修改优惠金额
	    	var totalAmount = 0 ;
			var rows =  $(receiptDetail).datagrid('getRows');
			var rowIndex = $(receiptDetail).datagrid('getRowIndex',row); 
			$(rows).each(function(index,row){
				 if(index!=rowIndex){
					totalAmount += parseFloat(row.discountAmount);
				 }
			})
			totalAmount+=parseFloat(discountAmount); 
			$('#discountAmount',editDialog).numberbox('setValue',totalAmount);
	    });  
	    receiptAmountEditor.target.bind('change', function(){  
	    	var receiptAmount = receiptAmountEditor.target.val();
	    	//修改本次实付金额
	    	var totalAmount = 0 ;
			var rows =  $(receiptDetail).datagrid('getRows');
			var row = $(receiptDetail).datagrid('getSelected');
			var rowIndex = $(receiptDetail).datagrid('getRowIndex',row); 
			$(rows).each(function(index,row){
				if(index!=rowIndex){
					totalAmount += parseFloat(row.receiptAmount);
				 }
			})
			totalAmount+=parseFloat(receiptAmount); 
			$('#receiptAmount',editDialog).numberbox('setValue',totalAmount);
	    });  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择收款单',  
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
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKCheck()}},
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectCheck();
				}}
		  ],
		  columns:[[
			    {field:'sourceId',checkbox:true},
			    {field:'sourceCode',title:'单据号',width:150,align:"center"},
			    {field:'receiptKind',title:'单据类型',width:90,align:"center"},
			    {field:'sourceDate',title:'单据日期',width:90,align:"center"},
			    {field:'amount',title:'应收金额',width:90,align:"center"},
			    {field:'receiptedAmount',title:'已收金额',width:90,align:"center"},
			    {field:'needReceiptAmount',title:'还需收金额',width:90,align:"center"}
		  ]]
	 });
	 var onSelectCheck = function(){
		//客户
		var customerId = $('#customerId',editForm).val();
		if(customerId==''){
			$.messager.alert('提示','请选择客户','warning');
			return;
		}
		var rows = $(receiptDetail).datagrid('getRows');
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(''!=row.sourceId){
				idArray.push(row.sourceId);
			}
		}
		var url = "finance/queryNeedCheckReceipt.do";
		var content = {ids:idArray.join(LYS.join),'customer.customerId':customerId};
		$(sourceList).datagrid({
			url:url,
			queryParams:content,
			pageNumber:1
		});
		 $(selectDialog).dialog('open');
	 }
	 //选择
	 var onSelectOKCheck = function(){
		
		 var rows = $(sourceList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择出库单',"warning");
			 return;
		 }
		 $(rows).each(function(index,row){
			 $(receiptDetail).datagrid('appendRow',{
				 receiptDetailId:'',
				 sourceId:row.sourceId,
				 sourceCode:row.sourceCode,
				 receiptKind:row.receiptKind,
				 sourceDate:row.sourceDate,
				 amount:row.amount,
				 receiptedAmount:row.receiptedAmount,
				 needReceiptAmount:row.needReceiptAmount,
				 discountAmount:0.00,
				 receiptAmount:row.needReceiptAmount
			});
		 })
		$(receiptDetail).datagrid('endEdit', lastIndex);
		$(receiptDetail).datagrid('unselectAll');
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
		 var row = $(receiptDetail).datagrid('getSelected');
		 if(row.receiptDetailId!=''){
			 delReciptDetailIdArray.push(row.receiptDetailId);
		 }
		 var rowIndex = $(receiptDetail).datagrid('getRowIndex',row);
		 $(receiptDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
		 summary();
	 }
	 //优惠金额发生改变
	 $('#discountAmount',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 var amount = $('#amount',editForm).numberbox('getValue');
			 $('#receiptAmount',editForm).numberbox('setValue', parseFloat(amount)-newValue);
		 }
	});
	//统计付款单中的应付金额、优惠金额、本次支付金额
	var summary = function(){
		//统计删除后的应付金额
		 var totalAmount = 0 ;
		 var totalDiscountAmount = 0 ;
		 var totalReciptAmount = 0 ;
		 var rows =  $(receiptDetail).datagrid('getRows');
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			totalAmount += parseFloat(row.needReceiptAmount);
			totalDiscountAmount += parseFloat(row.discountAmount);
			totalReciptAmount += parseFloat(row.receiptAmount);
		}
		$('#amount',editForm).numberbox('setValue',totalAmount);
		$('#discountAmount',editForm).numberbox('setValue',totalDiscountAmount);
		$('#receiptAmount',editForm).numberbox('setValue',totalReciptAmount);
	} 
  }
})(jQuery);   