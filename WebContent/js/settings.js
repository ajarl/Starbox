$(document).ready(function() {
	
	$('button.save-setting').click(function() {
		var input = $(this).prev('input');

		$.get($(input).data('href'), {
			value : $(input).val()
		}, function(data) {

		});
	});
});
