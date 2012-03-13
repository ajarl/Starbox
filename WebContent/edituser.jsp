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
            <h1>Edit user: <% out.println(request.getAttribute("userEmail")); %></h1>
            
            <form method="POST" action="?action=update" name="create">
            	<p>
            		Email:
            		<input type="textfield" value="<% out.println(request.getAttribute("userEmail")); %>"/>
            	</p>
            	<p>
            		Name:
            		<input type="textfield" value="<% out.println(request.getAttribute("userName")); %>"/>
            	</p>
            	<p>
            		IP:
            		<input type="textfield" value="<% out.println(request.getAttribute("userIP")); %>"/>
            	</p>
            	<p>
            		ID (hidden?)
            		<input type="textfield" value="<% out.println(request.getAttribute("userID")); %>"/>
            	</p>
            	<input type="submit" value="Save"/>
            </form>
          </div>
          
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>
