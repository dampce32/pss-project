//两个字符串型(yyyy-MM-dd)日期的差值
function DateDiff(d1,d2){ 
    var day = 24 * 60 * 60 *1000; 
	try{     
	        var dateArr = d1.split("-"); 
	   var checkDate = new Date(); 
	        checkDate.setFullYear(dateArr[0], dateArr[1]-1, dateArr[2]); 
	   var checkTime = checkDate.getTime(); 
	   
	   var dateArr2 = d2.split("-"); 
	   var checkDate2 = new Date(); 
	        checkDate2.setFullYear(dateArr2[0], dateArr2[1]-1, dateArr2[2]); 
	   var checkTime2 = checkDate2.getTime(); 
	     
	   var cha = (checkTime - checkTime2)/day;   
	        return cha; 
	}catch(e){ 
	   return false; 
	} 
}

//日期加上天数后的新日期. 
function addDays(date,days){ 
	var nd = new Date(date); 
	   nd = nd.valueOf(); 
	   nd = nd + days * 24 * 60 * 60 * 1000; 
	   nd = new Date(nd); 
	   //alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" + nd.getDate() + "日"); 
	var y = nd.getFullYear(); 
	var m = nd.getMonth()+1; 
	var d = nd.getDate(); 
	if(m <= 9) m = "0"+m; 
	if(d <= 9) d = "0"+d; 
	var cdate = y+"-"+m+"-"+d; 
	return cdate; 
}