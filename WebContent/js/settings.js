$(document).ready(function() {
	$('#update-index').click(function() {
		var button = $(this);
		$.post('/starbox/settings', 'updateindex=true', function() {
			$(button).text('Updating...');
		});
	});
	$('#shut-down').click(function() {
		var button = $(this);
		$.post('/starbox/settings', 'shutdown=true', function() {
			$(button).removeClass('button-blue').addClass('button-red').text('Server stopped');
		});
	});
	$('.save-setting').click(function() {
		var button = $(this);
		var data = $(this).parent().prev('td').children('input').serialize();
		$(this).next('span').css('color', '#666').text('Saving');

		$.post('/starbox/settings', data, function(){
			$(button).next('span').css('color', 'green').text('Saved');
	    })
	    .error(function() { 
	    	$(button).next('span').css('color', 'red').text('Error');
	    });
	});
});
