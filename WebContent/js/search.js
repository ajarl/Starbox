$(document).ready(function() {

  $('#search-query').keyup(function() {
    $.get('/starbox/search/', { query: $(this).val() }, 
      function(data) {
    
    });
  });
});
