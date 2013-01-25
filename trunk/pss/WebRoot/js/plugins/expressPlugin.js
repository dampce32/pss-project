// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.expressInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var viewList = $('#viewList',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',$this);
	  //添加
	  var onAdd = function(){
		$(editDialog).dialog('open');
	  }
	  //修改
	  var onUpdate = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	  }
	  //加载列表
	  $(viewList).datagrid({
		url:'dict/queryExpress.do',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fit:true,
		toolbar:[	
					{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
					{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}}
				],
		columns:[[
			{field:'expressId',hidden:true},
			{field:'expressName',title:'快递名称',width:300,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onUpdate();
		}
	  });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑快递',  
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
				onExit();
			}
		}]
	});    
	
	//保存前的赋值操作
	var setValue = function(){
		var expressName = $.trim($('#expressName',editForm).val());
		if(''==expressName){
			$.messager.alert('提示','请填快递名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveExpress.do';
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
					    $(viewList).datagrid('reload');
						onExit();
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	//退出
	var onExit = function(){
		$(editForm).form('clear');
		$(editDialog).dialog('close');
	}
  }
})(jQuery);   