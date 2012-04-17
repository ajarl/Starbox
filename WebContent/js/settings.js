$(document).ready(function() {
	
	$('button.save-setting').click(function() {
		var data = $(this).prev('input').serialize();

		$.post("/starbox/settings", data, function(){
			$(this).prev('input');
			alert("success");
	    })
	    .error(function() { 
	    	alert("error"); 
	    })
	    .complete(function() { 
	    	alert("complete"); 
	    });
	});
});
