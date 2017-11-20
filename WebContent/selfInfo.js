/**在前台检查用户输入的信息的函数*/
//validate
function validate() {
	// 获得昵称
	var username = document.getElementById("username");
	// 获得昵称后的span元素
	var warn0 = document.getElementById("warn0");
	// 在前台验证
	if (username.value === "") {
		warn0.innerHTML = "不得为空！";
		return false;
	} else {
		warn0.innerHTML = "√";
	}
	
	// 获得爱好
	var hobby = document.getElementById("hobby");
	// 获得账号后的span元素用以标记用户输入是否符合要求，放提示信息
	var warn1 = document.getElementById("warn1");
	// 在前台验证
	if (hobby.value === "") {
		warn1.innerHTML = "不得为空！";
		return false;
	} else {
		warn1.innerHTML = "√";
	}
	
	// 获得喜欢的颜色
	var color = document.getElementById("color");
	// 获得账号后的span元素用以标记用户输入是否符合要求，放提示信息
	var warn2 = document.getElementById("warn2");
	// 在前台验证
	if (color.value === "") {
		warn2.innerHTML = "不得为空！";
		return false;
	} else {
		warn2.innerHTML = "√";
	}

	
        return true;

}

function validatePass(){
    // 获得密码
	var newPassword = document.getElementById("newPassword");
	// 获得密码后的span元素
	var warn3 = document.getElementById("warn3");
	// 密码只能是英文、数字或符号
	var passwordPattern1 = /^\w+$/;
	var passwordPattern2 = /\D/;// match only one can return true
	// 在前台验证
	if (newPassword.value === "") {
		warn3.innerHTML = "密码不得为空！";
		return false;
	} else if (!passwordPattern1.test(newPassword.value)) {
		warn3.innerHTML = "密码只能包含英文字母、数字和下划线！";
		return false;
	} else if (!passwordPattern2.test(newPassword.value)) {// show that there are
														// only numbers//must
														// include letter
		warn3.innerHTML = "密码不得为纯数字！";
		return false;
	} else if (newPassword.value.length <= 6) {
		warn3.innerHTML = "密码长度必须超过6个字符！";
		return false;
	}
	else {
		warn3.innerHTML = "√";
                return true;
	}
}

// 在表单提交之前进行的验证
function validateForm() {
	if (validatePass()) {
		//inner function
		function serialize(form) {// serialize an element: name=value
			var parts = [], field = null, i, len;// parts is an array, field is an object, synchronously declare these 4 variable
			for (i = 0, len = form.elements.length; i < len; i++) {
				field = form.elements[i];
				if (field.name.length) {// if the element of the form has "name"attribute
					parts.push(encodeURIComponent(field.name) + "="
							+ encodeURIComponent(field.value));
				}
			}
			return parts.join("&");// return a String instead of as Array
		}
		// 验证账户唯一
		var xhr = new XMLHttpRequest();
		xhr.open("post", "SelfInfo", false);
        xhr.setRequestHeader("Execute","true");//to prevent insert twice
        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");//default
		var form = document.getElementById("change_pass");
		xhr.send(serialize(form));
		//wait for servlet
		var status = xhr.getResponseHeader("PasswordRepeat");
		var status2 = xhr.getResponseHeader("Success");
		if (!(status === null)) {//the situation happen
			alert("新密码不得与旧密码相同！");
			return false;
		}
		return true;
	} 
	else {
		alert("有空格没有填写!");
		return false;
	}

}
