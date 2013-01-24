// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.sizeInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var pageNumber = 1;
	  var pageSize = 20;
	  var isAdd = false;
	  var isEdit = false;
	  var viewList = $('#viewList',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',$this);
	  
	//加载列表
	  $(viewList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fit:true,
		toolbar:[	
					{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
					{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},
					{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}}
				],
		columns:[[
			{field:'dataDictionaryName',title:'商品规格',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onUpdate();
		},
		onLoadSuccess:function(){
			selectRow = null;
	 		selectIndex = null;
			pageNumber = 1;
		}
	  });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑商品规格',  
	    width:400,
	    height:200,
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
		isAdd = true;
		$('#dataDictionaryKind',editForm).val('size');
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var dataDictionaryName = $.trim($('#dataDictionaryName',editForm).val());
		if(''==dataDictionaryName){
			$.messager.alert('提示','请填写商品规格','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = null;
		if(isAdd){
			url = 'dict/addDataDict.do'
		}else{
			url = 'dict/updateDataDict.do'
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
						//新增
						if(isAdd){
							search(true);
						}else{
							var row = $(editForm).serializeObject();
							$(viewList).datagrid('updateRow',{index:selectIndex,row:row});
						}
						$(editDialog).dialog('close');
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,"error");
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
		isAdd = false;
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$.messager.confirm('确认', '确定删除该记录吗?', function(r){
			if(r){
				var url = "dict/deleteDataDict.do";
				var content = {dataDictionaryId:selectRow.dataDictionaryId};
				$.post(url,content,
					function(result){
						if(result.isSuccess){
							$(viewList).datagrid('deleteRow',selectIndex);
						}else{
							$.messager.alert('提示',result.message,"error");
						}
				},'json');
			}
		})
	}
	//查询
	var search = function(flag){
		var queryContent = $('.queryContent',$this);
		var dataDictionaryName = $('#dataDictionaryNameSearch',queryContent).val();
		var content = {dataDictionaryName:dataDictionaryName,dataDictionaryKind:'size'};
		//取得列表信息
		var url = 'dict/queryDataDict.do';
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var  data = result.data;
			var datagridData = eval("("+data.datagridData+")");
			$(viewList).datagrid('loadData',datagridData);
		}else{
			$.messager.alert('提示',result.message,'error');
		}
	}
	//查询
	$('#search',$this).click(function(){
		search(true);
	})
  }
})(jQuery);   