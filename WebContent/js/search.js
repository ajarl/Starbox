$(document).ready(function() {
	
	$('#search-query').focus();
	
	$('#search-query').keyup(function() {
		$.get('/starbox/search/', {
			query : $(this).val()
		}, function(data) {

		});
	});

	$('.search-filters h2').click(function() {
		$(this).next().toggle();
		$(this).toggleClass('open');

		if ($(this).hasClass('open')) {
			$(this).find('span').html('[');
		} else {
			$(this).find('span').html(']');
		}

		return false;
	});

	$('.search-filters li').click(function() {
		$(this).toggleClass('selected');

		return false;
	});
});
