// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.productTypeInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var isAdd = false;
	  
	  var selectRow = null;
	  var selectIndex = null;
	  var pageNumber = 1;
	  var pageSize = 20;
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  var productTypeTree =  $('#productTypeTree',$this);
	  var productTypeList =  $('#productTypeList',$this);
	  
	  //商品类别树  
	  $(productTypeTree).tree({
			url: 'dict/selectRootProductType.do',
			onBeforeExpand:function(node,param){
				$(productTypeTree).tree('options').url = 'dict/selectTreeNodeProductType.do?productTypeId='+node.id;  
	        },
			onClick:function(node){ 
				$(productTypeTree).tree('expand',node.target);
				var productTypeName = $('#productTypeNameSearch').val();
				var content = {productTypeId:node.id,productTypeName:productTypeName,page:pageNumber,rows:pageSize};
				$(productTypeList).datagrid({
					queryParams:content
				});
			}
			
	  });
	  //列表
	  $(productTypeList).datagrid({
			fit:true,
			url:'dict/getTreeNodeChildrenProductType.do',
			idField:'productTypeId',
			columns:[[{field:'ck',checkbox:true},
			    {field:'productTypeCode',title:'商品类别编号',width:120,sortable:true},
				{field:'productTypeName',title:'商品类别名称',width:300,sortable:true}
			]],
			rownumbers:true,
			pagination:true,
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
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑系统商品类别信息',  
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
		isAdd = true;
		$(editForm).form('clear');
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var productTypeCode = $.trim($('#productTypeCode',editForm).val());
		if(productTypeCode==''){
			$.messager.alert('提示','请填写商品类别编号','warning');
			return false;
		}
		var productTypeName = $.trim($('#productTypeName',editForm).val());
		if(productTypeName==''){
			$.messager.alert('提示','请填写商品类别名称','warning');
			return false;
		}
		var selectedNote = $(productTypeTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(productTypeTree).tree('getRoot');
			if(root==null){
				$('#parentID',editForm).val(null);
			}else{
				$('#parentID',editForm).val(root.id);
			}
		}else{
			$('#parentID',editForm).val(selectedNote.id);
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = null;
		if(isAdd){
			url = 'dict/addProductType.do'
		}else{
			url = 'dict/updateProductType.do'
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
							var node = $(productTypeTree).tree('getSelected');
							if(node==null){
								node = $(productTypeTree).tree('getRoot');
							}
							var productTypeName = $('#productTypeName',editForm).val();
							$(productTypeTree).tree('append',{
								parent: (node?node.target:null),
								data:[{
									id:result.data.productTypeId,
									text:productTypeName
								}]
							});
							$(productTypeList).datagrid('reload');
						}else{
							var row = $(editForm).serializeObject();
							$(productTypeList).datagrid('updateRow',{index:selectIndex,row:row});
							
							var productTypeId=$("#productTypeId",editForm).val();
							var productTypeName = $('#productTypeName',editForm).val();
							var updateNote=$(productTypeTree).tree('find',productTypeId);
							updateNote.text=productTypeName;
							$(productTypeTree).tree('update', updateNote);
						}
					}
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			}
		 });
	}
	//修改
	var onUpdate = function(){
		isAdd = false;
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var rows = $(productTypeList).datagrid('getSelections');
		if(rows.length==0){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要删除的纪录","warming");
				 return;	
			}
			var idArray = new Array();
			
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].productTypeId);
			}
			var ids = idArray.join(LYS.Join);
			var url = "dict/mulDeleteProductType.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var rows = $(productTypeList).datagrid('getSelections');
						for(var i=0;i<rows.length>0;i++){
							$(productTypeTree).tree('remove',$(productTypeTree).tree('find',rows[i].productTypeId).target);
						}
						$(productTypeList).datagrid('reload');
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//查询
	$('#search',$this).click(function(){
		search();
	})
	//分页操作
	var search = function(){
		var queryContent = $('.queryContent',$this);
		var productTypeCodeSearch = $('#productTypeCodeSearch',queryContent).val();
		var productTypeNameSearch = $('#productTypeNameSearch',queryContent).val();
		var productTypeId = '';
		var selectedNote = $(productTypeTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(productTypeTree).tree('getRoot');
			if(root!=null){
				productTypeId = root.id;
			}
		}else{
			productTypeId = selectedNote.id;
		}
		var content = {productTypeId:productTypeId,productTypeName:productTypeNameSearch,productTypeCode:productTypeCodeSearch,page:pageNumber,rows:pageSize};
		
		$(productTypeList).datagrid({
			queryParams:content
		});
		selectRow = null;
	}
  }
})(jQuery);   