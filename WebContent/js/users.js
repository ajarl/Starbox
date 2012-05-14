$(document).ready(function() {
	var cancel_add = $('.cancel-add-user');
	var button_add = $('.button-add-user');
	var add_user_form = $('.add-user-form');
	var status_add_user = $('.status-add-user');
	var button_accept_request = $('.button-accept-request');
	var button_deny_request = $('.button-deny-request');
	var button_remove_user = $('.button-remove-user');
	var button_edit_user = $('.button-edit-user');
	
	$('.users-container h3').click(function(event) {
		$(this).next('table').toggle();
		$(this).toggleClass('open');

		if ($(this).hasClass('open')) {
			$(this).find('span').html('[');
		} else {
			$(this).find('span').html(']');
		}
	});
	
	$(button_accept_request).click(function(event) {
		$.post(
			"/starbox/users/?action=friendrequest", 
			{ 
				response: "accepted", 
				ip: $(this).attr('data-IP'),
				email: $(this).attr('data-email'),
				name: $(this).attr('data-name')
			}, 
			function(data){
				//$(button_accept_request).closest('tr').hide();
				//alert('sent');
				location.href = "/starbox/users/"; 
			}
		)
		.error(function() { 
				alert('error');
		});
		
	});
	
	$(button_deny_request).click(function(event) {
		
		$.post(
			"/starbox/users/?action=friendrequest", 
			{ 
				response: "denied", 
				ip: $(this).attr('data-IP')
			}, 
			function(){
				//$(button_deny_request).closest('tr').hide();
				//alert('denied');
				location.href = "/starbox/users/"; 
			}
		)
		.error(function() { 
				alert('error');
		});
		
	});
	
	$(button_remove_user).click(function(event) {
		
		$.post(
			"/starbox/users/?action=remove", 
			{ 
				ip: $(this).attr('data-IP')
			}, 
			function(){
				location.href = "/starbox/users/"; 
			}
		)
		.error(function() { 
				alert('error');
		});
		
	});
	
	$(button_add).click(function(event) {
		if(!$(this).hasClass('update')) {
			$(add_user_form).show();
			$(this).text('Save user');
			$(this).removeClass('button-blue').addClass('button-green').removeClass('update').delay(800).addClass('submit');
			$(cancel_add).show();
		}
	});
	
	$(cancel_add).click(function() {
		$(add_user_form).hide();
		$(button_add).text('Add user');
		$(button_add).removeClass('button-green').addClass('button-blue').removeClass('submit').removeClass('update');
		$(add_user_form)[0].reset();
		$(this).hide();
	});
	
	$(button_edit_user).click(function() {
		$(button_add).text('Save user');
		$(cancel_add).show();
		$(button_add).removeClass('button-blue').removeClass('submit').addClass('button-green').delay(800).addClass('update');
		$('#name').val($(this).attr('data-name'));
		$('#ip').val($(this).attr('data-ip')).attr('disabled', 'disabled');
		$('#group').val($(this).attr('data-group'));
		$('#email').val($(this).attr('data-email'));
		$(add_user_form).show();
	});	
	
	
	
	$(button_add).click(function() {
		
		if($('#ip').val().length > 0) {
			if($(button_add).hasClass('submit')) {
				$(cancel_add).hide();
				$(status_add_user).css('color', 'green').text('Sending').show();
				
				var data = $(add_user_form).serialize();
	
				$.post("/starbox/users/?action=create", data, function(data){
					//$(status_add_user).css('color', 'green').text('Accepted');
					location.href = "/starbox/users/";
			    })
			    .error(function() { 
			    	$(status_add_user).css('color', 'red').text('IP not found');
			    });
			}
			else if($(button_add).hasClass('update')) {
				$(cancel_add).hide();
				$(status_add_user).css('color', 'green').text('Updating').show();
				
				$('#ip').removeAttr('disabled');
				
				var data = $(add_user_form).serialize();
	
				$.post("/starbox/users/?action=update", data, function(data){
					location.href = "/starbox/users/";
			    });
			}
		}
		
	});
});
