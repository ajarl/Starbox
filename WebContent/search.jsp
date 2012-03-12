<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>search controller</title>
	<script language="javascript" type="text/javascript">
		function focusIt()
		{
			var mytext = document.getElementById("textbox");
			mytext.focus();
		}
		
		function add_min()
		{
			var mytext = document.getElementById("textbox");
			mytext.value = mytext.value + " minfilesize:";
			mytext.focus();
		}
		
		function add_max()
		{
			var mytext = document.getElementById("textbox");
			mytext.value = mytext.value + " maxfilesize:";
			mytext.focus();
		}
		
		function add_type()
		{
			var mytext = document.getElementById("textbox");
			mytext.value = mytext.value + " filetype:avi,exe";
			mytext.focus();
		}
	</script>
</head>
<body onload="focusIt()">
	Otto kommer att byta ut den här filen!
	<input id="btn_max" type="button" onclick="add_max()" value="Max filstorlek">
	<input id="btn_min" type="button" onclick="add_min()" value="Min filstorlek">
	<input id="btn_typn" type="button" onclick="add_type()" value="Filtyp">
	<form method="get">
		Sök: <input size="100" type="text" id="textbox" name="query"/>
	</form>
	<% if (request.getParameter("query")  != "") { %>
		<div style="background-color:lightgrey">
			request.getParameter("query") = <%= request.getParameter("query") %>	
		</div>
	<% }  %>
	<% if (request.getAttribute("query")  != "") { %>
		<div style="background-color:lightblue">
			request.getAttribute("query") = <%= request.getAttribute("query") %>	
		</div>
	<% }  %>
	<% if (request.getAttribute("params")  != "") { %>
		<div style="background-color:lightgreen">
			request.getAttribute("params") = <%= request.getAttribute("params") %>	
		</div>
	<% }  %>
</body>
</html>