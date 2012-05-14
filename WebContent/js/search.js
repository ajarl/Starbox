function applyFilters(data) {
	var formats = $(data).attr('data-value');
	
	$.each(formats.split(','), function(i, val) {
		$('.search-results tr[data-' + $(data).attr('data-type') + '=' + val + ']').toggle();
	});	
}

function hideResults(data) {
	var formats = $(data).attr('data-value');
	
	$.each(formats.split(','), function(i, val) {
		$('.search-results tr[data-' + $(data).attr('data-type') + '=' + val + ']').hide();
	});	
}

function renderResults(data) {
	var tbody = $('.search-results tbody');
	var table = $('.search-results table');
	
	$(tbody).empty();
	
	$(data).each(function(index, value) {
		var qtip_data = "";
		var qtip_class = "";
		if(value.name.length > 35) {
			qtip_data = value.name;
			qtip_class = 'qtip';
			value.name = value.name.substring(0, 25) + '...' + value.name.substring(value.name.length - 7);
		}
		$(tbody).append(
				'<tr class="' + qtip_class + '" data-qtip="' + qtip_data + '" data-user="' + value.username + '" data-format="' + value.filetype + '">' +
				'<td>' + value.name + '</td>' +
				'<td>' + value.filesize + '</td>' +
				'<td>' + value.timestamp + '</td>' +
				'<td>' + value.username + '</td>' +
				'<td><a href="' + value.url + '" class="button-green button-tiny">DL</a></td>' +
				'</tr>');	
	});
	
	$('.search-filters li').each(function() {
		if(!$(this).hasClass('selected')) {
			hideResults($(this));
		}
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
		if($(this).val().length > 0) {
			$.get('/starbox/search/', {
				query : $(this).val()
			}, function(data) {
				if(data.length > 0) {
					$('.search-results table').show();
					$('.search-results span').hide();
					renderResults(data);
				}
				else {
					$('.search-results table').hide();
					$('.search-results span').show().text('Nothing found:(');
				}
			});
		}
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
	
	$('.qtip').live('mouseover', function() {
		   $(this).qtip({
		      overwrite: false,
		      content: $(this).attr('data-qtip'),
		      show: {
		         ready: true
		      },
		      position: {
					corner: {
						tooltip: 'bottomMiddle',
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
		});
});
