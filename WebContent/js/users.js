$(document).ready(function() {
	var cancel_add = $('.cancel-add-user');
	var button_add = $('.button-add-user');
	var add_user_form = $('.add-user-form');
	var status_add_user = $('.status-add-user');
	
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
