// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.invoiceTypeInit = function() {
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
		  singleSelect:true,
		  fit:true,
		  columns:[[
				{field:'invoiceTypeName',title:'发票类型名称',width:200,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},
				{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}}
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
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		var invoiceTypeName = $('#invoiceTypeNameSearch',queryContent).val();
		
		var url = "dict/queryInvoiceType.do";
		var content = {invoiceTypeName:invoiceTypeName,page:pageNumber,rows:pageSize};
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
		var url = "dict/getTotalCountInvoiceType.do";
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
	    title: '编辑发票类型信息',  
	    width:350,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onSave();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(editDialog).dialog('close');
			}
		}]
	});    
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var invoiceTypeName = $.trim($('#invoiceTypeName',editForm).val());
		if(invoiceTypeName==''){
			$.messager.alert('提示','请填写发票类型名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveInvoiceType.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var invoiceTypeId = $.trim($('#invoiceTypeId',editForm).val());
						if(invoiceTypeId==''){//新增
							search(true);
						}else{
							var row = $(editForm).serializeObject();
							$(viewList).datagrid('updateRow',{index:selectIndex,row:row});	
						}
						$(editDialog).dialog('close');
						$(editForm).form('clear');
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
		$(editForm).form('clear');
		$(editDialog).dialog('open');
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		if(selectRow==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'dict/deleteInvoiceType.do';
				var content ={invoiceTypeId:selectRow.invoiceTypeId};
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
  }
})(jQuery);   