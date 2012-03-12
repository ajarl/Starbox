<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="settings" method="post">
	Email: <input type="text" name="email" value="<%= request.getAttribute("email") %>" />
	<input type="submit" value="Ändra" />
</form>
<form action="settings" method="post"><input type="submit" name="shutdown" value="Shut down Tomcat" /></form>
<p>
Current info:<br />
<b>Email:</b> <%= request.getAttribute("email") %><br />
<b>Display name:</b> <%= request.getAttribute("displayName") %><br />
<b>Starbox folder:</b> <%= request.getAttribute("starboxFolder") %><br />
<b>Index update interval:</b> <%= request.getAttribute("indexUpdateInterval") %><br />
<br /><b>IP:</b> <%= request.getAttribute("ip") %>
</p>

</body>
</html>