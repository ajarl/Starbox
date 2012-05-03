function applyFilters(data) {
	//alert($(data).attr('data-type') + ' ' + $(data).attr('data-value'));
	
	$('.search-results tr[data-' + $(data).attr('data-type') + '=' + $(data).attr('data-value') + ']').toggle();
}

function renderResults(data) {
	var tbody = $('.search-results tbody');
	var table = $('.search-results table');
	
	$(tbody).empty();
	
	$(data).each(function(index, value) {
		$(tbody).append(
				'<tr data-user="' + 'Otto' + '" data-format="' + value.filetype + '">' +
				'<td>' + value.name + '</td>' +
				'<td>' + value.filesize + '</td>' +
				'<td>' + value.timestamp + '</td>' +
				'<td>Username</td>' +
				'<td><a href="' + value.url + '" class="button-green button-tiny">DL</a></td>' +
				'</tr>');	
	});
	
	$(table).trigger('update');
	$('.match-highlight').show().fadeOut(500);
}

$(document).ready(function() {
	$('#search-query').focus();
	
	$('.search-results table').tablesorter();
	
	$('#search-query').keyup(function() {
		$.get('/starbox/search/', {
			query : $(this).val()
		}, function(data) {
			if(data.length > 0) {
				renderResults(data);
			}
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
		
		applyFilters($(this));
		return false;
	});
	
	
});
