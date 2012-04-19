$(document).ready(function() {
	
	$('.button-add-user').click(function() {
		$('.add-user-form').show();
		$(this).text('Save user');
		$(this).removeClass('button-blue').addClass('button-green');
	});
});
