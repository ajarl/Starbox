<html>
  <head>
    <title>StarBox</title>
	<link rel="stylesheet" href="/starbox/css/main.css" />
	<link rel="stylesheet" href="/starbox/css/search.css" />
	<%@ page import="java.util.*" %>
	<%@ page import="se.starbox.models.User" %>
  </head>
  <body>
  	<jsp:include page="_header.jsp" />
    <div class="main">
      <div class="layout">
        <div class="users-container clearfix">
        
          <div class="add-users">
          	<h2>Current Users:</h2>
          	<% 
          		ArrayList<User> users = (ArrayList<User>) request.getAttribute("userList");
          		
          		%>
          	
          </div>
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>