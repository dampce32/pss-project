// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.operatorInit = function() {
	  var $this = $(this);
	  var id =   $(this).attr('id');
	  
	  var selectRow = null;
	  var selectIndex = null;
	
	  var pageNumber = 1;
	  var pageSize = 20;
	  var isUpdate = 0 ;
	  var isEdit = false;
	  var isUpdateInit = false;
	  var importDialog = $('#importDialog',$this);
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	//加载列表
	$('#operatorList').datagrid({
			url:"xq/queryXQ.do",
			fit:true,
			singleSelect:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:false,
			pagination:false,
			remoteSort:false,
			toolbar:[	
						{id:'add',text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},
						{id:'update',text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},
						{id:'delete',text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}},
						{id:'reload',text:'刷新',iconCls:'icon-reload',handler:function(){onReload()}}
					],
			columns:[[
				{field:'XQBH',hidden:true},
				{field:'userName',title:'用户名称',width:100,align:"center"},
				{field:'userCode',title:'用户登录名',width:100,align:"center"},
				{field:'password',title:'用户密码',width:150,align:"center"}
			]],
			onClickRow:function(rowIndex, rowData){
			},
			onDblClickRow:function(rowIndex,rowData){
			}
		});
  	}; 
	  //编辑框
	  $(editDialog).dialog({  
	    title: '编辑用户信息',  
	    width:800,
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
			iconCls:'icon-exit',
			handler:function(){
				$(editDialog).dialog('close');
			}
		}]
	  });
	  
	//添加信息
	var onAdd = function(){
		isUpdate = 0;
		$(editForm).form('clear');
		$(editDialog).dialog('open');
	}
	//提交前验证
	var onSubmit = function(form){
		if($(form).form('validate')){
			return true;
		}else{
		 	$.messager.alert('提示：','请填写完整信息','error');
		 	return false;
		}
	}
	//保存
	var onSave = function(){
		var url = null;
		if(isUpdate==0){
			url = 'system/addUser.do'
		}else{
			url = 'system/updateUser.do'
		}
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				onSubmit(this);
			},
			success: function(data){
				var data = eval('(' + data + ')');  // change the JSON string to javascript object  
				var fn = function(){
					//新增
					if(isUpdate==0){
					}else{
					}
				}
				$.messager.alert('提示','保存成功','info',fn);
			}
		});
	}
		
})(jQuery);   