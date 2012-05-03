$(document).ready(function() {
	var cancel_add = $('.cancel-add-user');
	var button_add = $('.button-add-user');
	var add_user_form = $('.add-user-form');
	var status_add_user = $('.status-add-user');
	var button_accept_request = $('.button-accept-request');
	var button_deny_request = $('.button-deny-request');
	var button_remove_user = $('.button-remove-user');
	
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
			function(){
				alert('sent');
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
				alert('denied');
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
				alert('removed');
			}
		)
		.error(function() { 
				alert('error');
		});
		
	});
	
	$(button_add).click(function(event) {
		$(add_user_form).show();
		$(this).text('Save user');
		$(this).removeClass('button-blue').addClass('button-green').delay(800).addClass('submit');
		$(cancel_add).show();
	});
	
	$(cancel_add).click(function() {
		$(add_user_form).hide();
		$(button_add).text('Add user');
		$(button_add).removeClass('button-green').addClass('button-blue').removeClass('submit');
		$(this).hide();
	});
	
	$(button_add).click(function() {
						
		if($('#ip').val().length > 0 && $(this).hasClass('submit')) {
			$(cancel_add).hide();
			$(status_add_user).text('Sending').show();
			
			var data = $(add_user_form).serialize();

			$.post("/starbox/users/?action=create", data, function(){
				$(status_add_user).css('color', 'green').text('Accepted');
		    })
		    .error(function() { 
		    	$(status_add_user).css('color', 'red').text('Error');
		    });
		}
		
	});
});
