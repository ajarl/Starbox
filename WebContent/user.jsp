<html>
  <head>
    <title>StarBox</title>
	<link rel="stylesheet" href="/starbox/css/main.css" />
	<link rel="stylesheet" href="/starbox/css/search.css" />
  </head>
  <body>
  	<jsp:include page="_header.jsp" />
    <div class="main">
      <div class="layout">
        <div class="user-container clearfix">
        <a href="/starbox/users/">List all users</a>
          <div class="user-info">
            <h1>A user: <% out.println(request.getAttribute("userEmail")); %></h1>
            
          </div>
          
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>
