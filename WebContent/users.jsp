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
						<input type="text" name="name" id="name" placeholder="Name"/> 
		          		<input type="text" name="ip" id="ip" placeholder="IP (Required)" required="required"/>  
		          		<input type="email" name="email" id="email" placeholder="Email-adress"/> 
		          		<input type="text" name="group" id="group" placeholder="Group"/>  		
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
						<h3>Current Users(<% out.println(accepted.size()); %>)<span class="websymbol">]</span></h3>
						<table>
							<% for(User u : accepted) { %>
							<tr>
								<td class="name"><% out.println(u.getName()); %></td>
								<td class="IP"><% out.println(u.getIp()); %></td>
								<td class="email"><% out.println(u.getEmail()); %></td>
								<td class="group"><% out.println(u.getGroup()); %></td>
								<td class="buttons">
									<a class="button-tiny button-blue button-edit-user"
									data-name="<% out.println(u.getName()); %>"
									data-email="<% out.println(u.getEmail()); %>"
									data-group="<% out.println(u.getGroup()); %>"
									data-IP="<% out.println(u.getIp()); %>">Edit</a>
								</td>
								<td>
									<a class="button-tiny button-red button-remove-user"
									data-IP="<% out.println(u.getIp()); %>">Remove</a>
								</td>
							</tr>
							<% } %>
						</table>
					</div>
					
					<div class="pending-users">
						<h3>Pending requests(<% out.println(pending.size()); %>)<span class="websymbol">]</span></h3>
						<table>
							<% for(User u : pending) { %>
							<tr>
								<td class="name"><% out.println(u.getName()); %></td>
								<td class="IP"><% out.println(u.getIp()); %></td>
								<td class="email"><% out.println(u.getEmail()); %></td>
								<td class="group"><% out.println(u.getGroup()); %></td>
								<td class="buttons">
									<a class="button-tiny button-green button-accept-request" 
									data-IP="<% out.println(u.getIp()); %>"
									data-email="<% out.println(u.getEmail()); %>"
									data-name="<% out.println(u.getName()); %>">Accept</a>
								</td>
								<td>
									<a class="button-tiny button-red button-deny-request" data-IP="<% out.println(u.getIp()); %>">Deny</a>
								</td>
							</tr>
							<% } %>
						</table>
					</div>
					
					<div class="requested-users">
						<h3>Requested Users (<% out.println(sent.size()); %>)<span class="websymbol">]</span></h3>
						<table>
							<% for(User u: sent) { %>
							<tr>
								<td class="name"><% out.println(u.getName()); %></td>
								<td class="IP"><% out.println(u.getIp()); %></td>
								<td class="email"><% out.println(u.getEmail()); %></td>
								<td class="group"><% out.println(u.getGroup()); %></td>
								<td class="buttons">
									<a class="button-tiny button-red button-remove-user"
									data-IP="<% out.println(u.getIp()); %>">Remove</a>
								</td>
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