// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.receiveOtherInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var queryContent = $('#queryContent',$this);
	  var searchBtn = $('#searchBtn',$this);
	  var mulSearchBtn = $('#mulSearchBtn',$this);
	 
	  var editDialog = $('#editDialog',$this);
	  var editForm = $('#editForm',editDialog);
	  
	  var viewList =  $('#viewList',$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var pager = $('#pager',$this);
	  var pageNumber = 1;
	  var pageSize = 10;
	  
	  var changeSearch = false;
	  var statusList = [{"value":"-1","text":"所有","selected":true},{"value":"1","text":"已审"},{"value":"0","text":"未审"}]; 
	  $('#statusSearch',queryContent).combobox({
		editable:false,
		valueField:'value',
		textField:'text',
		width:50,
		data:statusList
	  })
	  //列表
	  $(viewList).datagrid({
		  fit:true,
		  nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
		  columns:[[
		        {field:'ck',title:'选择',checkbox:true},
				{field:'receiveCode',title:'入库单号',width:200,align:"center"},
				{field:'receiveDate',title:'入库日期',width:80,align:"center"},
				{field:'deliverCode',title:'原始单号',width:120,align:"center"},
				{field:'warehouseName',title:'存入仓库',width:90,align:"center"},
				{field:'employeeName',title:'经手人',width:90,align:"center"},
				{field:'note',title:'备注',width:90,align:"center"},
				{field:'status',title:'状态',width:80,align:"center",
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
				{text:'打印',iconCls:'icon-print',handler:function(){onPrint()}}
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
	//高级查询
	$(mulSearchBtn).click(function(){
		if(changeSearch){
			$('#mulSearchPanel',$this).panel('close');
			$('#'+id).layout('panel','north').panel('resize',{
				height: 32
			});
			$('#'+id).layout('resize');
			changeSearch = false;
		}else{
			$('#mulSearchPanel',$this).panel('open');
			$('#'+id).layout('panel','north').panel('resize',{
				height: 140
			});
			$('#'+id).layout('resize');
			changeSearch = true;
		}
	})
	
	$("#mulSearch",$this).click(function(){
		var receiveCode = $('#receiveCodeMulSearch',$this).val();
		
		var url = "inWarehouse/queryReceive.do";
		var content = {receiveCode:receiveCode,kind:'other',page:pageNumber,rows:pageSize};
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
	})
	
	$("#mulSearchClear").click(function(){
		$("#mulSearchForm").form('clear');
		return false;
	});
	//查询
	$(searchBtn).click(function(){
		search(true);
	})
	//分页操作
	var search = function(flag){
		var receiveCode = $('#receiveCodeSearch',queryContent).val();
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var beginDate = $('#beginDateSearch_'+id).val();
		var endDate = $('#endDateSearch_'+id,queryContent).val();
		var url = "inWarehouse/queryReceive.do";
		var content = {receiveCode:receiveCode,kind:'other',status:status,beginDate:beginDate,endDate:endDate,page:pageNumber,rows:pageSize};
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
		var url = "inWarehouse/getTotalCountReceive.do";
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
	    title: '其他入库单',  
	    width:width,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	lastIndex = null;
	    	search(true);
	    	$(receiveDetail).datagrid({url:LYS.ClearUrl});
	    	$(editForm).form('clear');
	    },
	    toolbar:[	
		 			{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
		 			{id:'print'+id,text:'打印',iconCls:'icon-print',handler:function(){onPrintIn()}},'-',
		 			{id:'add'+id,text:'新增',iconCls:'icon-add',handler:function(){onAdd();}},'-',
		 			{id:'delete'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDelete();}},'-',
		 			{id:'sh'+id,text:'审核',iconCls:'icon-info',handler:function(){onUpdateStatus(1);}},'-',
		 			{id:'fs'+id,text:'反审',iconCls:'icon-warn',handler:function(){onUpdateStatus(0);}},'-',
		 			{id:'pre'+id,text:'上一笔',iconCls:'icon-left',handler:function(){onOpenIndex(-1);}},'-',
		 			{id:'next'+id,text:'下一笔',iconCls:'icon-right',handler:function(){onOpenIndex(1);}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(editDialog).dialog('close');
		 				}
		 			}
		 	  ]
	}); 
	//新增时，按钮的状态
	var addBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#print'+id).linkbutton('disable');
		$('#add'+id).linkbutton('disable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('disable');
		$('#isPay'+id).linkbutton('disable');
		$('#pre'+id).linkbutton('disable');
		$('#next'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('enable');
		$('#addBuyProduct'+id).linkbutton('enable');
		$('#deleteProduct'+id).linkbutton('enable');
	}
	//审核通过按钮的状态
	var shBtnStatus = function(){
		$('#save'+id).linkbutton('disable');
		$('#print'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('disable');
		$('#sh'+id).linkbutton('disable');
		$('#fs'+id).linkbutton('enable');
		$('#addProduct'+id).linkbutton('disable');
		$('#addBuyProduct'+id).linkbutton('disable');
		$('#deleteProduct'+id).linkbutton('disable');
	}
	//反审后的按钮状态
	var fsBtnStatus = function(){
		$('#save'+id).linkbutton('enable');
		$('#print'+id).linkbutton('enable');
		$('#add'+id).linkbutton('enable');
		$('#delete'+id).linkbutton('enable');
		$('#sh'+id).linkbutton('enable');
		$('#fs'+id).linkbutton('disable');
		$('#addProduct'+id).linkbutton('enable');
		$('#addBuyProduct'+id).linkbutton('enable');
		$('#deleteProduct'+id).linkbutton('enable');
	}
	//添加
	var onAdd = function(){
		var rows  = $(receiveDetail).datagrid('getRows');
		if(rows.length!=0){
			$(receiveDetail).datagrid({url:LYS.ClearUrl});
		}
		$(viewList).datagrid('unselectAll');
		selectIndex=null;
		selectIndex=null;
    	$(editForm).form('clear');
		initChoose();
		addBtnStatus();
		$(editDialog).dialog('open');
	}
	
	var initChoose = function(){
		//仓库
		$('#warehouse',editDialog).combobox({
			valueField:'warehouseId',
			textField:'warehouseName',
			width:150,
			data:PSS.getWarehouseList()
		})
		//经办人
		$('#employee',editDialog).combobox({
			valueField:'employeeId',
			textField:'employeeName',
			width:150,
			data:PSS.getEmployeeList()
		})
	}
	//保存前的赋值操作
	var setValue = function(){
		//存放仓库
		var warehouseId = $('#warehouse',editForm).combobox('getValue');
		if(warehouseId==''){
			$.messager.alert('提示','请选择存放仓库','warning');
			return false;
		}
		$('#warehouseId',editForm).val(warehouseId);
		//收货日期
		var receiveDate = $('#receiveDate',editForm).val();
		if(receiveDate==''){
			$.messager.alert('提示','请选择入库日期','warning');
			return false;
		}
		//经办人
		var employeeId = $('#employee',editForm).combobox('getValue');
		if(employeeId==''){
			$.messager.alert('提示','请选择经办人','warning');
			return false;
		}
		$('#employeeId',editForm).val(employeeId);
		//验证添加的商品行
		$(receiveDetail).datagrid('endEdit', lastIndex);
		 $(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		var rows = $(receiveDetail).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请选择添加商品','warning');
			return false;
		}
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			if(row.qty==0){
				var msg = '第'+(i+1)+'行商品的数量为0,请输入';
				$.messager.alert('提示',msg,'warning');
				return false;
			}
		}
		var receiveDetailIdArray = new Array();
		var buyDetailIdArray = new Array();
		var productIdArray = new Array();
		var colorIdArray = new Array();
		var qtyArray = new Array();
		var priceArray = new Array();
		var note1Array = new Array();
		var note2Array = new Array();
		var note3Array = new Array();
		for ( var i = 0; i < rows.length; i++) {
			buyDetailIdArray.push(rows[i].buyDetailId);
			receiveDetailIdArray.push(rows[i].receiveDetailId);
			productIdArray.push(rows[i].productId);
			colorIdArray.push(rows[i].colorId);
			qtyArray.push(rows[i].qty);
			priceArray.push(rows[i].price);
			note1Array.push(rows[i].note1);
			note2Array.push(rows[i].note2);
			note3Array.push(rows[i].note3);
		}
		
		delReceiveDetailIdArray = new Array();
		//统计原记录中被删除的记录
		for ( var i = 0; i < oldReceiveDetailIdArray.length; i++) {
			var haveDel = true;
			for(var j=0;j<rows.length;j++){
				if(oldReceiveDetailIdArray[i]==rows[j].receiveDetailId){
					haveDel = false;
					break;
				}
			}
			if(haveDel){
				delReceiveDetailIdArray.push(oldReceiveDetailIdArray[i]);
			}
		}
		$('#receiveDetailIds',editForm).val(receiveDetailIdArray.join(LYS.join));
		$('#delReceiveDetailIds',editForm).val(delReceiveDetailIdArray.join(LYS.join));
		$('#buyDetailIds',editForm).val(buyDetailIdArray.join(LYS.join));
		$('#productIds',editForm).val(productIdArray.join(LYS.join));
		$('#colorIds',editForm).val(colorIdArray.join(LYS.join));
		$('#qtys',editForm).val(qtyArray.join(LYS.join));
		$('#prices',editForm).val(priceArray.join(LYS.join));
		$('#note1s',editForm).val(note1Array.join(LYS.join));
		$('#note2s',editForm).val(note2Array.join(LYS.join));
		$('#note3s',editForm).val(note3Array.join(LYS.join));
		$('#kind',editForm).val('other');
		$(editDialog).mask({maskMsg:'正在保存'});
		return true;
	}
	//保存
	var onSave = function(){
		var url = 'inWarehouse/saveReceive.do'
		$(editForm).form('submit',{
			url: url,
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				$(editDialog).mask('hide');
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var data = result.data;
						//赋值receiveId，并加载receiveDetail
						onOpen(data.receiveId);
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
		onOpen(selectRow.receiveId);
		$(editDialog).dialog('open');
	 }
	//删除
	var onDelete = function(){
		var receiveId = $('#receiveId',editDialog).val();
		$.messager.confirm("提示","确定删除记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/deleteReceive.do';
				var content ={receiveId:receiveId};
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
	//批量删除
	var onMulDelete = function(){
		var rows =  $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("提示","请选择要删除的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].receiveId);
		}
		$.messager.confirm("提示","确定要删除选中的记录?",function(t){ 
			if(t){
				var url = 'inWarehouse/mulDelReceive.do';
				var content ={ids:idArray.join(LYS.join)};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							selectRow = null;
							selectIndex = null;
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
		var receiveId = $('#receiveId',editDialog).val();
		var msg = '';
		if(status==1){
			msg ='审核';
		}else{
			msg = '反审';
		}
		if(receiveId==''){
			$.messager.alert("提示","请选择需要"+msg+"数据行","warning");
			return;
		}
		
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行库存计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/updateStatusReceive.do';
				var content ={receiveId:receiveId,status:status,kind:'other'};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							if(status==1){
								shBtnStatus();
								onOpen(receiveId);
							}else{
								fsBtnStatus();
								$('#status',editDialog).val('未审核'); 
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
	//修改审核状态
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
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].receiveId);
		}
		$.messager.confirm("提示","确定要"+msg+"选中的记录?"+msg+"后系统将进行库存计算!!",function(t){ 
			if(t){
				var url = 'inWarehouse/mulUpdateStatusReceive.do';
				var content ={ids:idArray.join(LYS.join),status:status,kind:'other'};
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
	//打开
	var onOpen = function(receiveId){
		var url = 'inWarehouse/initReceive.do';
		var content ={receiveId:receiveId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var preDisable = false;
				var nextDisable = false;
				if(selectIndex==0||selectIndex==null){
					preDisable = true;
				}
				var rows = $(viewList).datagrid('getRows');
				if(selectIndex==rows.length-1||selectIndex==null){
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
				var receiveData = eval("("+data.receiveData+")");
				$('#receiveId',editDialog).val(receiveData.receiveId);
				$('#receiveCode',editDialog).val(receiveData.receiveCode);
				$('#receiveDate',editDialog).val(receiveData.receiveDate);
				$('#deliverCode',editDialog).val(receiveData.deliverCode);
				if(receiveData.status==1){
					$('#shzt',editDialog).val('已审核');
					shBtnStatus();
				}else{
					$('#shzt',editDialog).val('未审核');
					fsBtnStatus();
				}
				$('#warehouse',editDialog).combobox('setValue',receiveData.warehouseId);
				
				$('#employee',editDialog).combobox('setValue',receiveData.employeeId);
				$('#note',editDialog).val(receiveData.note);
				
				var detailData = eval("("+data.detailData+")");
				$(receiveDetail).datagrid('loadData',detailData);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//上下一笔
	var onOpenIndex = function(index){
		var rows = $(viewList).datagrid('getRows');
		selectIndex = selectIndex + index;
		selectRow = rows[selectIndex];
		onOpen(selectRow.receiveId);
		//界面选中
		$(viewList).datagrid('unselectAll');
		$(viewList).datagrid('selectRow',selectIndex);
	}
	//打印 
	var onPrint = function(){
		if(selectRow ==null){
			$.messager.alert('提示','请选中要打印的记录行','warning');
    		return;
		}
		print(selectRow.receiveId);
	}
	//打印
	var print = function(receiveId){
		window.open("printReport.jsp?report=receiveOther&data=ReportServlet?receiveId="+receiveId);
	}
	//内层打印
	var onPrintIn = function(){
		var receiveId = $('#receiveId',editDialog).val();
		print(receiveId);
	}
	//-----收货明细----------
	var receiveDetail = $('#receiveDetail',editDialog);
	var selectDialog = $('#selectDialog',$this);
	var productList = $('#productList');
	var lastIndex=null;
	var colors=PSS.getColorList();
	function colorFormatter(value){
		for(var i=0; i<colors.length; i++){
			if (colors[i].colorId == value) return colors[i].colorName;
		}
		return value;
	}
	var oldReceiveDetailIdArray = new Array();
	$(receiveDetail).datagrid({
	  singleSelect:true,	
	  fit:true,
	  columns:[[
		    {field:'productCode',title:'商品编号',width:90,align:"center"},
			{field:'productName',title:'商品名称',width:200,align:"center"},
		    {field:'unitName',title:'单位',width:90,align:"center"},
		    {field:'sizeName',title:'规格',width:90,align:"center"},
		    {field:'colorId',title:'颜色',width:90,align:"center",editor:{type:'combobox',options:{valueField:'colorId',
				textField:'colorName',
				data:colors}},formatter:colorFormatter},
		    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:2}}},
		    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{precision:5}}},
		    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}},
		    {field:'note1',title:'备注1',width:120,align:"center",editor:{type:'text'}},
		    {field:'note2',title:'备注2',width:120,align:"center",editor:{type:'text'}},
		    {field:'note3',title:'备注3',width:120,align:"center",editor:{type:'text'}}
	  ]],
	  rownumbers:true,
	  pagination:false,
	  toolbar:[	
			{text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProduct()}},'-',
			{text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProduct()}}
	  ],
	  onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
	  },
	  onClickRow:function(rowIndex){
		if (lastIndex != rowIndex){
			$(receiveDetail).datagrid('endEdit', lastIndex);
			$(receiveDetail).datagrid('beginEdit', rowIndex);
			setEditing(rowIndex);
		}
		lastIndex = rowIndex;
	  },onLoadSuccess:function(data){
			var rows = data.rows;
			oldReceiveDetailIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				oldReceiveDetailIdArray.push(rows[i].receiveDetailId);
			}
		}
	 });
	function setEditing(rowIndex){  
	    var editors = $(receiveDetail).datagrid('getEditors', rowIndex);  
	    var qtyEditor = editors[1];  
	    var priceEditor = editors[2];  
	    var amountEditor = editors[3];  
	    qtyEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    priceEditor.target.bind('change', function(){  
	        calculate(rowIndex);  
	    });  
	    function calculate(rowIndex){  
	    	if(qtyEditor.target.val()==''){
	    		$(qtyEditor.target).numberbox('setValue',0.00);
	    	}
	    	if(priceEditor.target.val()==''){
	    		$(priceEditor.target).numberbox('setValue',0.00);
	    	}
	        var cost = qtyEditor.target.val() * priceEditor.target.val();  
	        $(amountEditor.target).numberbox('setValue',cost);
	    }  
	}  
	//编辑框
	$(selectDialog).dialog({  
	    title: '选择商品',  
	    width:800,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
	});
	$(productList).datagrid({
		  fit:true,
		  cache: false, 
		  columns:[[
			    {field:'ck',checkbox:true},
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:120,align:"center"},
			    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'colorName',title:'颜色',width:90,align:"center"},
			    {field:'note',title:'备注',width:90,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  toolbar:[	
		        {text:'新增',iconCls:'icon-add',handler:function(){onNewProduct()}},'-',
				{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProduct()}},'-',
				{text:'退出',iconCls:'icon-cancel',handler:function(){
					onExitSelectProduct();
				}}
		  ]
	 });
	 var onSelectProduct = function(){
		 $(selectDialog).dialog('open');
	 }
	 //查询
	 $('#searchBtnSelectDialog',selectDialog).click(function(){
		 searchBtnSelect();
	 })
	 var searchBtnSelect = function(){
		var productCode = $('#productCodeSelectDialog',selectDialog).val();
		var productName = $('#productNameSelectDialog',selectDialog).val();
		
		var options = $(productList).datagrid('options');
		var url = "dict/selectProduct.do";
		var content = {productCode:productCode,productName:productName,page:options.pageNumber,rows:options.pageSize};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			var data = result.data;
			$(productList).datagrid('loadData',eval("("+data.datagridData+")"));
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	 }
	//分页条
	$(productList).datagrid('getPager').pagination({   
	    onSelectPage: function(page, rows){
	    	searchBtnSelect();
	    }
	});
	 //选择商品
	 var onSelectOKProduct = function(){
		 var rows = $(productList).datagrid('getSelections');
		 if(rows.length==0){
			 $.messager.alert('提示','请选择商品',"warning");
			 return;
		 }
		 for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			 $(receiveDetail).datagrid('appendRow',{
				 receiveDetailId:'',
				 productId:row.productId,
				 productCode:row.productCode,
				 productName:row.productName,
				 unitName:row.unitName,
				 sizeName:row.sizeName,
				 colorId:row.colorId,
				 qty:0,
				 price:row.buyingPrice,
				 amount:0,
				 note1:'',
				 note2:'',
				 note3:''
			});
		}
		 $(receiveDetail).datagrid('endEdit', lastIndex);
		 $(receiveDetail).datagrid('unselectAll');
		lastIndex = null;
		onExitSelectProduct();
	 }
	 //退出选择商品界面
	 var onExitSelectProduct = function(){
		 $(selectDialog).dialog('close');
		 $(productList).datagrid({url:LYS.ClearUrl});
	 }
	 //删除商品
	 var onDeleteProduct = function(){
		 var row = $(receiveDetail).datagrid('getSelected');
		 var rowIndex = $(receiveDetail).datagrid('getRowIndex',row);
		 $(receiveDetail).datagrid('deleteRow',rowIndex);
		 lastIndex = null;
	 }
	//----新增商品s--------
		var newProductDialog = $('#newProductDialog',$this);
		var newProductForm = $('#newProductForm',newProductDialog);
		//编辑框
		$(newProductDialog).dialog({  
		    title: '新增商品信息',  
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    toolbar:[{id:'save'+id,text:'保存',iconCls:'icon-save',handler:function(){onSaveNewProduct();}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(newProductDialog).dialog('close');
		 				}
		 			}]
		});
		//------默认商品组装--------
		var defaultPackagingList = $('#defaultPackagingList',newProductDialog);
		var lastIndexNewProduct=null;
		$(defaultPackagingList).datagrid({
		  title:'默认商品组装',
		  singleSelect:true,	
		  fit:true,
		  columns:[[
			    {field:'productCode',title:'商品编号',width:90,align:"center"},
				{field:'productName',title:'商品名称',width:200,align:"center"},
			    {field:'unitName',title:'单位',width:90,align:"center"},
			    {field:'sizeName',title:'规格',width:90,align:"center"},
			    {field:'qty',title:'数量',width:90,align:"center",editor:{type:'numberbox',options:{precision:0}}},
			    {field:'price',title:'单价',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:5}}},
			    {field:'amount',title:'金额',width:90,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:2}}}
		  ]],
		  rownumbers:true,
		  pagination:false,
		  toolbar:[	
				{id:'addProduct'+id,text:'添加商品',iconCls:'icon-add',handler:function(){onSelectProductNewProduct()}},'-',
				{id:'deleteProduct'+id,text:'删除商品',iconCls:'icon-remove',handler:function(){onDeleteProductNewProduct()}}
		  ],
		  onBeforeLoad:function(){
				$(this).datagrid('rejectChanges');
		  },
		  onClickRow:function(rowIndex){
			if (lastIndexNewProduct != rowIndex){
				$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
				$(defaultPackagingList).datagrid('beginEdit', rowIndex);
				setEditingNewProduct(rowIndex);
			}
			lastIndexNewProduct = rowIndex;
		  }
		 });
		function setEditingNewProduct(rowIndex){  
		    var editors = $(defaultPackagingList).datagrid('getEditors', rowIndex);  
		    var qtyEditor = editors[0];  
		    var priceEditor = editors[1];  
		    var amountEditor = editors[2];  
		    qtyEditor.target.bind('change', function(){  
		        calculate(rowIndex);  
		    });  
		    function calculate(rowIndex){  
		    	if(qtyEditor.target.val()==''){
		    		$(qtyEditor.target).numberbox('setValue',0.00);
		    	}
		        var cost = qtyEditor.target.val() * priceEditor.target.val();  
		        $(amountEditor.target).numberbox('setValue',cost);
		    }  
		}  
		//初始化Combobox
		var initComboboxNewProduct = function(){
			//规格
		   $('#size',newProductDialog).combobox({
				valueField:'sizeId',
				textField:'sizeName',
				width:250,
				data:PSS.getSizeList()
		  })
		  //颜色
		   $('#color',newProductDialog).combobox({
				valueField:'colorId',
				textField:'colorName',
				width:250,
				data:PSS.getColorList()
		  })
		  //单位
		   $('#unit',newProductDialog).combobox({
				valueField:'unitId',
				textField:'unitName',
				width:250,
				data:PSS.getUnitList()
		  })
		}
		//初始化选择
		var initChooseNewProduct = function(){
			initComboboxNewProduct();
			  //商品类型
			  $('#productType',newProductDialog).combogrid({
				    panelWidth:480, 
					mode: 'remote',  
					url: 'dict/queryCombogridProductType.do',
					idField: 'productTypeId',  
					textField: 'productTypeName',  
					pagination:true,
					columns: [[  
					    {field:'productTypeName',title:'商品类型',width:150,sortable:true},  
					    {field:'productTypeCode',title:'商品类型编号',width:150,sortable:true}
					]]
				});
		}
		//新增商品
		var onNewProduct = function(){
			$(newProductDialog).form('clear');
			$('#uploadImgForm',newProductDialog).form('clear');
			initChooseNewProduct();
			$('#wholesalePrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#vipPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#memberPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#salePrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#buyingPrice',newProductDialog).numberbox('setValue',0.0);
		   	$('#status',newProductDialog).combobox('setValue',1);
			$(newProductDialog).dialog('open');
		}
		//编号双击 后台编号
		$('#productCode',newProductDialog).dblclick(function(){
			var obj = $(this);
			var productCode = $(obj).val();
			var url = 'dict/newCodeProduct.do';
			var content ={productCode:productCode};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					var data = result.data;
					$('#productCode',newProductDialog).val(data.productCode);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			})
			
		})
		//保存前的赋值操作
		var setValueNewProduct = function(){
			var productName = $.trim($('#productName',newProductForm).val());
			if(productName==''){
				$.messager.alert('提示','请填写商品名称','warning');
				return false;
			}
			//商品类别
			var productTypeId = $('#productType',newProductForm).combogrid('getValue');
			if(productTypeId==''){
				$.messager.alert('提示','请选择商品类别','warning');
				return false;
			}
			var productTypeName = $('#productType',newProductForm).combogrid('getText');
			$('#productTypeId',newProductForm).val(productTypeId);
			//商品单位
			var unitId = $('#unit',newProductForm).combobox('getValue');
			$('#unitId',newProductForm).val(unitId);
			//商品颜色
			var colorId = $('#color',newProductForm).combobox('getValue');
			$('#colorId',newProductForm).val(colorId);
			//商品尺码
			var sizeId = $('#size',newProductForm).combobox('getValue');
			$('#sizeId',newProductForm).val(sizeId);
			$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
			$(defaultPackagingList).datagrid('unselectAll');
			lastIndexNewProduct = null;
			var rows =  $(defaultPackagingList).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if(row.qty==0){
					var msg = '第'+(i+1)+'行商品的数量为0,请输入';
					$.messager.alert('提示',msg,'warning');
					return false;
				}
			}
			var defaultPackagingIdArray = new Array();
			var productIdArray = new Array();
			var qtyArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				defaultPackagingIdArray.push(rows[i].defaultPackagingId);
				productIdArray.push(rows[i].productId);
				qtyArray.push(rows[i].qty);
			}
			$('#defaultPackagingIds',newProductForm).val(defaultPackagingIdArray.join(LYS.join));
			$('#deleleIds',newProductForm).val('');
			$('#productIds',newProductForm).val(productIdArray.join(LYS.join));
			$('#qtys',newProductForm).val(qtyArray.join(LYS.join));
			return true;
		}
		//保存
		var onSaveNewProduct = function(){
			$(newProductForm).form('submit',{
				url: 'dict/saveProduct.do',
				onSubmit: function(){
					return setValueNewProduct();
				},
				success: function(data){
					var result = eval('('+data+')');
					if(result.isSuccess){
						var fn = function(){
							var data = result.data;
							if(data.isUnitChange){
								PSS.UnitList = null;
							}
							if(data.isSizeChange){
								PSS.SizeList = null;
							}
							if(data.isColorChange){
								PSS.ColorList = null;
							}
							$('#productId',newProductForm).val(data.productId);
						}
						$.messager.alert('提示','保存成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				}
			 });
		}
		//----选择商品---------
		var selectProductDialog = $('#selectProductDialog',$this);
		var productListSelectProductDialog = $('#productListSelectProductDialog',selectProductDialog);
		$(selectProductDialog).dialog({  
		    title: '选择商品',  
		    width:1000,
		    height:height-30,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    onClose:function(){
		    	var rows = $(productListSelectProductDialog).datagrid('getRows');
		    	if(rows.length!=0){
		    		$(productListSelectProductDialog).datagrid({url:LYS.ClearUrl});
		    	}
		    }
		});
		$(productListSelectProductDialog).datagrid({
			  fit:true,
			  cache: false, 
			  columns:[[
				    {field:'ck',checkbox:true},
				    {field:'productCode',title:'商品编号',width:90,align:"center"},
					{field:'productName',title:'商品名称',width:120,align:"center"},
				    {field:'productTypeName',title:'商品类型',width:120,align:"center"},
				    {field:'unitName',title:'单位',width:90,align:"center"},
				    {field:'sizeName',title:'规格',width:90,align:"center"},
				    {field:'colorName',title:'颜色',width:90,align:"center"},
				    {field:'buyingPrice',title:'进价',width:90,align:"center"},
				    {field:'note',title:'备注',width:90,align:"center"}
			  ]],
			  rownumbers:true,
			  pagination:true,
			  toolbar:[	
					{text:'选择',iconCls:'icon-ok',handler:function(){onSelectOKProductNewProduct()}},
					{text:'退出',iconCls:'icon-cancel',handler:function(){
						 $(selectProductDialog).dialog('close');
					}}
			  ]
		 });
		var onSelectProductNewProduct = function(){
			 $(selectProductDialog).form('clear');
			 $(selectProductDialog).dialog('open');
		 }
		//查询
		 $('#searchBtnSelectProductDialog',selectProductDialog).click(function(){
			 searchBtnProductSelect();
		 })
		 var searchBtnProductSelect = function(){
			var productCode = $('#productCodeSelectProductDialog',selectProductDialog).val();
			var productName = $('#productNameProductSelectDialog',selectProductDialog).val();
			var idArray = new Array();
			var rows = $(defaultPackagingList).datagrid('getRows');
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				idArray.push(row.productId);
			}
			var productId = $.trim($('#productId',newProductForm).val());
			if(productId!=''){
				idArray.push(productId);
			}
			var url = "dict/selectDefaultPackingProduct.do";
			var content = {productCode:productCode,productName:productName,ids:idArray.join(LYS.join)};
			var result = syncCallService(url,content);
			if(result.isSuccess){
				var data = result.data;
				$(productListSelectProductDialog).datagrid('loadData',eval("("+data.datagridData+")"));
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		 }
		//选择商品
		 var onSelectOKProductNewProduct = function(){
			 var rows = $(productListSelectProductDialog).datagrid('getSelections');
			 if(rows.length==0){
				 $.messager.alert('提示','请选择商品',"warning");
				 return;
			 }
			 for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				 $(defaultPackagingList).datagrid('appendRow',{
					 defaultPackagingId:'',
					 productId:row.productId,
					 productCode:row.productCode,
					 productName:row.productName,
					 unitName:row.unitName,
					 sizeName:row.sizeName,
					 qty:1,
					 price:row.buyingPrice,
					 amount:row.buyingPrice
				});
			}
			$(defaultPackagingList).datagrid('endEdit', lastIndexNewProduct);
			$(defaultPackagingList).datagrid('unselectAll');
			lastIndexNewProduct = null;
			$(selectProductDialog).dialog('close');
		 }
		//删除商品
		 var onDeleteProductNewProduct = function(){
			 var row = $(defaultPackagingList).datagrid('getSelected');
			 var rowIndex = $(defaultPackagingList).datagrid('getRowIndex',row);
			 $(defaultPackagingList).datagrid('deleteRow',rowIndex);
			 lastIndexNewProduct = null;
		 }
		//------商品照片--------
		 $('#uploadBtn',newProductDialog).click(function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 $.messager.alert('提示','先保存商品后上传照片','warning');
			 }else{
				 if(setValueUploadImg()){
					 $('#uploadImgForm',newProductDialog).ajaxSubmit({
							url:'dict/uploadImgProduct.do',
							type:'post',
							uploadFiles:true,
							dataType:'json',
							success:function(result){
								if(result.isSuccess){
									var fn = function(){
										showImg();
									}
									$.messager.alert('提示','上传成功','info',fn);
								}else{
									$.messager.alert('提示',result.message,'error');
								}
							}
					 });
				 }
			 }
		 })
		 //上传图片前检查
		 var setValueUploadImg = function(){
			 var file = $('#file',newProductDialog).val();
			 if(file==''){
				 $.messager.alert('提示','请选择上传文件','warning');
				 return false;
			 }
			 if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file)){
				 $.messager.alert('提示','图片类型必须是.gif,jpeg,jpg,png中的一种','warning');
				 return false;
			 }
			 var productId = $('#productId',newProductDialog).val();
			 $('#productIdUploadImgForm',newProductDialog).val(productId);
			 var pos = file.lastIndexOf("\\");
			 $('#fileName',newProductDialog).val(file.substring(pos+1));
			 return true;
		 }
		 //显示图片
		 var showImg = function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 return;
			 }
			 $('#imgPic').attr('src','dict/showImgProduct.do?productId='+productId);
		 }
		 $('#removeBtn',newProductDialog).click(function(){
			 var productId = $('#productId',newProductDialog).val();
			 if(productId==''){
				 $.messager.alert('提示','先保存商品后上传照片','warning');
			 }else{
				var url = 'dict/deleteImgProduct.do';
				var content ={productId:productId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							$('#imgPic',newProductDialog).attr('src','#');
							$('#uploadImgForm',newProductDialog).form('clear');
						}
						$.messager.alert('提示','移除成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			 }
		 });
		//----添加商品e--------
	 $(this).mask('hide');
  }
})(jQuery);   