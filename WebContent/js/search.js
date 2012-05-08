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
	$('#search-help').qtip({
			content: 'Here are some tips & trix that you can use to filter you search!<br/><ul>	<li>Use filetype:avi,exe to filter out specific filetypes.</li></ul>',
			position: {
				corner: {
					tooltip: 'bottomRight',
					target: 'topLeft'
				}
			},
            style: {
				width: 700,
				'font-size': 17,
				'font-family': 'arial',
                border: {
                   width: 2,
                   radius: 5 
                },
                padding: 5, 
                textAlign: 'left',
                tip: true, // Give it a speech bubble tip with automatic corner detection
                name: 'cream' // Style it according to the preset 'cream' style
	         }
	});
	
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
