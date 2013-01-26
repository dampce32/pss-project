// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.bankStatementsInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	  var searchBtn = $('#searchBtn',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var pager = $('#pager',$this);
	  var pageNumber = 1;
	  var pageSize = 10;
	  
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  columns:[[
		        {field:'ck',title:'选择',checkbox:true},
				{field:'bankStatementsKind',title:'类型',width:100,align:"center"},
				{field:'bankName',title:'银行账户',width:100,align:"center"},
				{field:'amount',title:'存取金额',width:100,align:"center"},
				{field:'employeeName',title:'经办人',width:100,align:"center"},
			    {field:'bankStatementsDate',title:'日期',width:100,align:"center"},
			    {field:'status',title:'状态',width:60,align:"center",
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
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		
		var url = "finance/queryBankStatements.do";
		var content = {page:pageNumber,rows:pageSize};
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
		var url = "finance/getTotalCountBankStatements.do";
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
	    title: '编辑银行存取款信息',  
	    width:600,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	$(editForm).form('clear');
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
				}}
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
		selectIndex = null;
		selectRow = null;
		$(editForm).form('clear');
		initChoose();
		$('#amount',editForm).numberbox('setValue', 0.0);
		addBtnStatus();
		$(editDialog).dialog('open');
	}
	var initChoose = function(){
		//银行
		$('#bank',editDialog).combobox({
			valueField:'bankId',
			textField:'bankShortName',
			width:250,
			data:PSS.getBankList()
		})		  
		//经办人
		$('#employee',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:250,
			data:PSS.getEmployeeList()
		})
	}
	//保存前的赋值操作
	var setValue = function(){
		//存取款类型
		var bankStatementsKind = $('#bankStatementsKind',editForm).combobox('getValue');
		if(bankStatementsKind==''){
			$.messager.alert('提示','请选择存取款类型','warning');
			return false;
		}
		//银行
		var bankId = $('#bank',editForm).combobox('getValue');
		if(bankId==''){
			$.messager.alert('提示','请选择银行','warning');
			return false;
		}
		$('#bankId',editForm).val(bankId);
		var amount = $('#amount',editForm).numberbox('getValue');
		if(amount==''){
			$.messager.alert('提示','请输入金额','warning');
			return false;
		}
		if(amount==0){
			$.messager.alert('提示','金额不能为0，请重新输入','warning');
			return false;
		}
		//银行存取款日期
		var bankStatementsDate = $('#bankStatementsDate',editForm).val();
		if(bankStatementsDate==''){
			$.messager.alert('提示','请选择日期','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employee',editForm).combobox('getValue');
		if(employeeId!=''){
			$('#employeeId',editForm).val(employeeId);
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'finance/saveBankStatements.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var data = result.data;
					var fn = function(){
						var bankStatementsId = $.trim($('#bankStatementsId',editForm).val());
						if(bankStatementsId==''){//新增
							$('#bankStatementsId',editForm).val(data.bankStatementsId);
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
		onOpen(selectRow.bankStatementsId);
		$(editDialog).dialog('open');
	 }
	//打开
	var onOpen = function(bankStatementsId){
		var url = 'finance/initBankStatements.do';
		var content ={bankStatementsId:bankStatementsId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var preDisable = false;
				var nextDisable = false;
				if(selectIndex==null||selectIndex==0){
					preDisable = true;
				}
				var rows = $(viewList).datagrid('getRows');
				if(selectIndex==null||selectIndex==rows.length-1){
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
				var bankStatementsData = eval("("+data.bankStatementsData+")");
				$('#bankStatementsId',editDialog).val(bankStatementsData.bankStatementsId);
				$('#bankStatementsKind',editDialog).combobox('setValue',bankStatementsData.bankStatementsKind);
				$('#bankStatementsDate',editDialog).val(bankStatementsData.bankStatementsDate);
				if(bankStatementsData.status==1){
					shBtnStatus();
				}else{
					fsBtnStatus();
				}
				
				$('#amount',editDialog).numberbox('setValue',bankStatementsData.amount);
				
				$('#bank',editDialog).combobox('setValue',bankStatementsData.bankId);
				$('#employee',editDialog).combobox('setValue',bankStatementsData.employeeId);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//删除
	var onDelete = function(){
		if(selectRow==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/deleteBankStatements.do';
				var content ={bankStatementsId:selectRow.bankStatementsId};
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
			idArray.push(rows[int].bankStatementsId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'finance/mulDeleteBankStatements.do';
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
		var bankStatementsId = $('#bankStatementsId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(bankStatementsId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"该记录?"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/updateStatusBankStatements.do';
				var content ={bankStatementsId:bankStatementsId,status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							if(status==1){
								shBtnStatus();
							}else{
								fsBtnStatus();
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
			idArray.push(rows[int].bankStatementsId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录"+msg+"后系统将进行财务计算!!",function(t){ 
			if(t){
				var url = 'finance/mulUpdateStatusBankStatements.do';
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
		onOpen(selectRow.bankStatementsId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
  }
})(jQuery);   