// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.customerTypeInit = function() {
	  var $this = $(this);
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  //列表
	  $(viewList).datagrid({
		  url:'dict/queryCustomerType.do',
		  singleSelect:true,
		  fit:true,
		  rownumbers:true,
		  pagination:false,
		  columns:[[
			    {field:'customerTypeCode',title:'客户类型编号',width:120,align:"center"},
				{field:'customerTypeName',title:'客户类型名称',width:200,align:"center"},
			    {field:'note',title:'备注',width:300,align:"center"}
		  ]],
		  toolbar:[	
				{text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
				{text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}}
		  ],
		  onDblClickRow:function(rowIndex, rowData){
				onUpdate();
		  },
		  onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
		  }
	 });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑客户类型',  
	    width:600,
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
		var customerTypeCode = $.trim($('#customerTypeCode',editForm).val());
		if(customerTypeCode==''){
			$.messager.alert('提示','请填写客户类型编号','warning');
			return false;
		}
		var customerTypeName = $.trim($('#customerTypeName',editForm).val());
		if(customerTypeName==''){
			$.messager.alert('提示','请填写客户类型名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveCustomerType.do'
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
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
  }
})(jQuery);   