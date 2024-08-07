
$(document).ready(function() {
	$('#login').click(function() {
		let account = $('#account').val();
		let password = $('#password').val();
		if (account == '' || password == '') {
			alert('請輸入帳號或密碼。');
			return;
		}
		$.ajax({
			type: "POST",
			url: '/project/newlogin',
			data: JSON.stringify({
				account: account,
				password: password
			}),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			success: function(response) {
				if (response.result === 'success') {
					window.location.href = response.requestPath;// 登入後重新導向
				} else {
					alert(response.error);
				}
			},
			error: function() {
				alert('登入錯誤，請重新嘗試。');
			}
		});

	});
	
	
	$('#logout').click(function() {
			$.ajax({
				type: "POST",
				url: '/project/logout',
				success: function(response) {
					if (response.result === 'success') {
						window.location.href = response.requestPath;// 登入後重新導向
					} else {
						alert(response.error);
					}
				},
				error: function() {
					alert("error");
				}
			});

		});
});
	



