<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>You set param1</title>
</head>
<body>
<form method="get" action="/starbox/">
	Sök: <input type="text" name="query"/>
</form>
query: <%= request.getAttribute("query") %>
params: <%= request.getAttribute("params") %>
</body>
</html>