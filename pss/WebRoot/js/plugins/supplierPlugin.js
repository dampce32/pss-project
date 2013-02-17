// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.supplierInit = function() {
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
		  method:"POST",
		  nowrap:true,
		  striped: true,
		  collapsible:true,
		  fit:true,
		  columns:[[
			    {field:'supplierCode',title:'供应商编号',width:120,align:"center"},
				{field:'supplierName',title:'供应商名称',width:200,align:"center"},
				{field:'contact',title:'联系人',width:90,align:"center"},
				{field:'addr',title:'地址',width:200,align:"center"},
				{field:'phone',title:'电话',width:90,align:"center"},
				{field:'fax',title:'传真',width:90,align:"center"},
				{field:'email',title:'E-mail',width:90,align:"center"},
				{field:'note1',title:'备注1',width:120,align:"center"},
				{field:'note2',title:'备注2',width:120,align:"center"},
				{field:'note3',title:'备注3',width:120,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
				{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}},'-',
				{text:'下载上传模板',iconCls:'icon-download',handler:function(){onDownload()}},'-',
				{text:'上传',iconCls:'icon-upload',handler:function(){onUpload()}},'-',
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
		var supplierCode = $('#supplierCodeSearch',queryContent).val();
		var supplierName = $('#supplierNameSearch',queryContent).val();
		
		var url = "dict/querySupplier.do";
		var content = {supplierCode:supplierCode,supplierName:supplierName,page:pageNumber,rows:pageSize};
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
		var url = "dict/getTotalCountSupplier.do";
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
	    title: '编辑供应商信息',  
	    width:800,
	    height:400,
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
		var supplierCode = $.trim($('#supplierCode',editForm).val());
		if(supplierCode==''){
			$.messager.alert('提示','请填写供应商编号','warning');
			return false;
		}
		var supplierName = $.trim($('#supplierName',editForm).val());
		if(supplierName==''){
			$.messager.alert('提示','请填写供应商名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveSupplier.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var supplierId = $.trim($('#supplierId',editForm).val());
						if(supplierId==''){//新增
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
				var url = 'dict/deleteSupplier.do';
				var content ={supplierId:selectRow.supplierId};
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
	//下载
	var onDownload = function(){
		var url = 'common/downloadTemplateCommon.do?fileName='+encodeURIComponent(encodeURIComponent('供应商'))+'.xls';
		window.open(url);
	}
	//-----上传文件--------
	var uploadDialog = $('#uploadDialog',$this);
	var uploadForm = $('#uploadForm',uploadDialog);
	//编辑框
	$(uploadDialog).dialog({  
	    title: '上传文件',  
	    width:400,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'上传',
			iconCls:'icon-save',
			handler:function(){
				onUploadSave();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(uploadDialog).dialog('close');
			}
		}]
	});
	//上传文件
	var onUpload = function(){
		$(uploadForm).form('clear');
		$(uploadDialog).dialog('open');
	}
	//上传保存前检查
	var setValueUpload = function(){
		var file = $('#file',uploadForm).val();
		if(file == ''){
			$.messager.alert('提示','文件不能为空!','warning');
			return;
		} 
		 var pos = file.lastIndexOf("\\");
		$('#fileName',uploadForm).val(file.substring(pos+1));
		$(uploadDialog).mask({maskMsg:'正在保存'});
		return true;
	}
	//上传保存
	var onUploadSave = function(){
		$(uploadForm).form('submit',{
			url: 'dict/uploadSupplier.do',
			onSubmit: function(){
				return setValueUpload();
			},
			success: function(data){
				$(uploadDialog).mask('hide');
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						$(uploadDialog).dialog('close');
					}
					$.messager.alert('提示','上传成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
  }
})(jQuery);   