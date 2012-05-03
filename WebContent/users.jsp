<html>
<head>
<title>StarBox</title>
<link rel="stylesheet" href="/starbox/css/main.css" />
<link rel="stylesheet" href="/starbox/css/users.css" />
<%@ page import="java.util.*"%>
<%@ page import="se.starbox.models.User"%>
</head>
<body>
	<jsp:include page="_header.jsp" />
	<div class="main">
		<div class="layout">
			<div class="users-container clearfix">
				<div class="left">	
					<h2>Controllers</h2>	
					<form name="add-user-form" class="add-user-form">
						<input type="textfield" name="name" id="name" placeholder="Name"/> 
		          		<input type="textfield" name="ip" id="ip" placeholder="IP"/>  
		          		<input type="textfield" name="email" placeholder="Email-adress"/> 
		          		<input type="textfield" name="group" placeholder="Group"/>  		
		          	</form>		
					<div class="add-users">
						<a class="button button-blue button-add-user">Add user</a>
						<a class="cancel-add-user">Cancel</a>
						<span class="status-add-user" style="display:none;"></span>
					</div>
				</div>
				
				<div class="right">
					<%
					List<User> pending = (List<User>) request.getAttribute("USERS_PENDING");
		          	List<User> accepted = (List<User>) request.getAttribute("USERS_ACCEPTED");
		          	List<User> sent = (List<User>) request.getAttribute("USERS_SENT");
					%>
					<h2>Users</h2>
					<div class="accepted-users">
						<h3>Current Users(<% out.println(accepted.size()); %>)</h3>
						<table>
							<% for(User u : accepted) { %>
							<tr>
								<td><% out.println(u.getName()); %></td>
							</tr>
							<% } %>
						</table>
					</div>
					
					<div class="pending-users">
						<h3>Pending Users(<% out.println(pending.size()); %>)</h3>
						<table>
							<% for(User u : pending) { %>
							<tr>
								<td><% out.println(u.getName()); %></td>
							</tr>
							<% } %>
						</table>
					</div>
					
					<div class="requested-users">
						<h3>Requested Users (<% out.println(sent.size()); %>)</h3>
						<table>
							<% for(User u: sent) { %>
							<tr>
								<td><% out.println(u.getName()); %></td>
								<td><% out.println(u.getIp()); %></td>
								<td><% out.println(u.getEmail()); %></td>
								<td><% out.println(u.getGroup()); %></td>
							</tr>
							<% } %>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/starbox/js/jquery-1.7.1.min.js"></script>
	<script src="/starbox/js/users.js"></script>
	<script src="/starbox/js/spinner.js"></script>
</body>
</html>