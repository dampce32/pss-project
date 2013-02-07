// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.employeeInit = function() {
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
				{field:'status',title:'状态',width:60,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else {
							return '<img src="style/v1/icons/info.png"/>';
						}
					}
				},
				{field:'employeeName',title:'员工名称',width:200,align:"center"},
				{field:'sex',title:'性别',width:50,align:"center"},
				{field:'birthday',title:'出生日期',width:100,align:"center"},
				{field:'isMarry',title:'婚否',width:50,align:"center"},
				{field:'nation',title:'民族',width:50,align:"center"},
				{field:'nativePlace',title:'籍贯',width:100,align:"center"},
				{field:'residence',title:'户口所在地',width:200,align:"center"},
				{field:'education',title:'学历',width:50,align:"center"},
				{field:'major',title:'专业',width:100,align:"center"},
				{field:'startWorkDate',title:'入职日期',width:80,align:"center"},
				{field:'salary',title:'工资',width:80,align:"center"},
				{field:'bankNo',title:'银行账号',width:100,align:"center"},
				{field:'idNo',title:'身份证号',width:100,align:"center"},
				{field:'phone',title:'电话',width:100,align:"center"},
				{field:'telPhone',title:'手机',width:100,align:"center"},
				{field:'qq',title:'QQ',width:100,align:"center"},
				{field:'note',title:' 备注',width:200,align:"center"}
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
		var employeeName = $('#employeeNameSearch',queryContent).val();
		
		var url = "dict/queryEmployee.do";
		var content = {employeeName:employeeName,page:pageNumber,rows:pageSize};
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
		var url = "dict/getTotalCountEmployee.do";
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
	    title: '编辑员工信息',  
	    width:800,
	    height:600,
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
		$('#salary',editForm).numberbox('setValue',0);
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var employeeName = $.trim($('#employeeName',editForm).val());
		if(employeeName==''){
			$.messager.alert('提示','请填写员工名称','warning');
			return false;
		}
		var startWorkDate = $.trim($('#startWorkDate',editForm).val());
		if(startWorkDate==''){
			$.messager.alert('提示','请选择入职日期','warning');
			return false;
		}
		var status = $.trim($('#status',editForm).combobox('getValue'));
		if(status==''){
			$.messager.alert('提示','请选择状态','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'dict/saveEmployee.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var employeeId = $.trim($('#employeeId',editForm).val());
						if(employeeId==''){//新增
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
				var url = 'dict/deleteEmployee.do';
				var content ={employeeId:selectRow.employeeId};
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
	 //优惠金额发生改变
	 $('#salary',editForm).numberbox({
		 onChange:function(newValue,oldValue){
			 if(newValue==''){
				 $('#salary',editForm).numberbox('setValue',0.00);
			 }
		 }
	});
  }
})(jQuery);   