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
          	<h2></h2>
          	<a href=http://localhost:8080/starbox/users/?action=gotoadd>Add user</a><br>
          	<% List<User> pending = (List<User>) request.getAttribute("USERS_PENDING");
          	List<User> accepted = (List<User>) request.getAttribute("USERS_ACCEPTED");
          	List<User> sent = (List<User>) request.getAttribute("USERS_SENT");
          	String b = "<br>";
          	out.println("Pending:"+b);
          	out.println("------------------"+b);
          	for(User u: pending){
          		out.println("Name: "+u.getName()+b);
          		out.println("IP: "+u.getIp()+b);
          		out.println("Email: "+u.getEmail()+b);
          		out.println("Group: "+u.getGroup()+b);
          		out.println("------------------"+b);
          	}
          	out.println("Accepted:"+b);
          	out.println("------------------"+b);
          	for(User u: accepted){
          		out.println("Name: "+u.getName()+b);
          		out.println("IP: "+u.getIp()+b);
          		out.println("Email: "+u.getEmail()+b);
          		out.println("Group: "+u.getGroup()+b);
          		out.println("------------------"+b);
          	}
          	out.println("Sent:"+b);
          	out.println("------------------"+b);
          	for(User u: sent){
          		out.println("Name: "+u.getName()+b);
          		out.println("IP: "+u.getIp()+b);
          		out.println("Email: "+u.getEmail()+b);
          		out.println("Group: "+u.getGroup()+b);
          		out.println("------------------"+b);
          	}
          	
          	%>
          	
          	
          </div>
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>