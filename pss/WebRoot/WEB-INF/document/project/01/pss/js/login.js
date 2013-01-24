$(function() {
	var userinfo = $.cookie("loginInfoSSH");
	if(userinfo!=null&&""!=userinfo){
		var array = userinfo.split("§");
		$("#userCode").val(array[0]);
		$("#userPwd").val(array[1]);
		$("#mindpwd").attr('checked','true');
	}
	var loginCaptchaImageRefresh = function(){
		$("#loginCaptcha").val('');
		$("#loginCaptchaImage").attr("src", "captcha.jpg?timestamp" + (new Date()).valueOf());
	}
	// 点击刷新验证码图片
	$("#loginCaptchaImage").click( function() {
		loginCaptchaImageRefresh();
	});
	$(document).keydown(function(e){ 
		var curKey = e.which; 
		if(curKey == 13){ 
			$('#loginBtn').click();
		} 
	}); 
	$("#loginBtn").click(function(){
		$('#loginForm').form('submit', {
			url : 'system/loginUser.do',
			onSubmit : function() {
				//记住密码
				 if($("#mindpwd").attr("checked")){
					 var loginInfo = $("#userCode").val()+"§"+$("#userPwd").val();
					 $.cookie("loginInfoSSH",loginInfo,{expires: 30,path:'/'});
				 }else{
					 $.cookie("loginInfoSSH",null,{path:'/'});
				 }
				return $(this).form('validate');
			},
			success : function(data) {
				var result = eval('(' + data + ')');
				if(result.isSuccess){
					var location = "main.do";
					window.location=location;
				}else{
					$.messager.alert('提示：',result.message,'error');
					loginCaptchaImageRefresh();
				}
				
			}
		});
	})
	
})
