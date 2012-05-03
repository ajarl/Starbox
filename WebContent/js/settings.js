$(document).ready(function() {
	
	$('.save-setting').click(function() {
		var button = $(this);
		var data = $(this).parent().prev('td').children('input').serialize();
		$(this).next('span').css('color', '#666').text('Saving');

		$.post("/starbox/settings", data, function(){
			$(button).next('span').css('color', 'green').text('Saved');
	    })
	    .error(function() { 
	    	$(button).next('span').css('color', 'red').text('Error');
	    });
	});
});
