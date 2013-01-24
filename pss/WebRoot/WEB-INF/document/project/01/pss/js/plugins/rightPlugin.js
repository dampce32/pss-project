// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.rightInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  
	  var selectRow = null;
	  var selectIndex = null;
	  var pageNumber = 1;
	  var pageSize = 20;
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  var rightTree =  $('#rightTree',$this);
	  var rightList =  $('#rightList',$this);
	  
	  //权限树  
	  $(rightTree).tree({
			url: 'system/selectRootRight.do',
			onBeforeExpand:function(node,param){
				$(rightTree).tree('options').url = 'system/selectTreeNodeRight.do?rightId='+node.id;  
	        },
			onClick:function(node){ 
				$(rightTree).tree('expand',node.target);
				var rightName = $('#rightNameSearch').val();
				var content = {rightId:node.id,rightName:rightName,page:pageNumber,rows:pageSize};
				$(rightList).datagrid({
					queryParams:content
				});
			}
	  });
	  //列表
	  $(rightList).datagrid({
			fit:true,
			url:'system/getTreeNodeChildrenRight.do',
			idField:'rightId',
			columns:[[{field:'ck',checkbox:true},
              {field:'rightName',title:'权限名称',width:120,sortable:true},
				{field:'rightUrl',title:'权限Url',width:300,sortable:true}
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
	    title: '编辑系统权限信息',  
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
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var rightName = $('#rightName',editForm).val();
		if(rightName==''){
			$.messager.alert('提示','请填写权限名称','warning');
			return false;
		}
		var selectedNote = $(rightTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(rightTree).tree('getRoot');
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
		 $(editForm).form('submit',{
			url: 'system/saveRight.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var rightId = $('#rightId',editForm).val();
						//新增
						if(rightId==''){
							var node = $(rightTree).tree('getSelected');
							if(node==null){
								node = $(rightTree).tree('getRoot');
							}
							var rightName = $('#rightName',editForm).val();
							$(rightTree).tree('append',{
								parent: (node?node.target:null),
								data:[{
									id:result.data.rightId,
									text:rightName
								}]
							});
							search();
						}else{
							var row = $(editForm).serializeObject();
							$(rightList).datagrid('updateRow',{index:selectIndex,row:row});
							
							var rightId=$("#rightId",editForm).val();
							var rightName = $('#rightName',editForm).val();
							var updateNote=$(rightTree).tree('find',rightId);
							updateNote.text=rightName;
							$(rightTree).tree('update', updateNote);
						}
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
				$(editDialog).dialog('close');
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
	//删除
	var onDelete = function(){
		var rows = $(rightList).datagrid('getSelections');
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
				idArray.push(rows[i].rightId);
			}
			var ids = idArray.join(LYS.join);
			var url = "system/mulDeleteRight.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var rows = $(rightList).datagrid('getSelections');
						for(var i=0;i<rows.length>0;i++){
							$(rightTree).tree('remove',$(rightTree).tree('find',rows[i].rightId).target);
						}
						search();
						$(rightList).datagrid('unselectAll');
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
		var rightNameSearch = $('#rightNameSearch',queryContent).val();
		var rightId = '';
		var selectedNote = $(rightTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(rightTree).tree('getRoot');
			if(root!=null){
				rightId = root.id;
			}
		}else{
			rightId = selectedNote.id;
		}
		var content = {rightId:rightId,rightName:rightNameSearch,page:pageNumber,rows:pageSize};
		
		$(rightList).datagrid({
			queryParams:content
		});
		selectRow = null;
	}
  }
})(jQuery);   