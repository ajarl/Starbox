<html>
<head>
<title>StarBox</title>
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/settings.css" />
</head>
<body>
	<jsp:include page="_header.jsp" />
	<div class="main">
		<div class="layout">
			<div class="settings-container">
				<div class="settings-inputs clearfix">
					<div class="left">
						<table>
							<tr>
								<td><input type="button" name="update-index"
									id="update-index" value="Update index"
									data-href="/settings/update_index" /></td>
							</tr>
							<tr>
								<td><input type="button" name="shutdown"
									value="Shut down Tomcat" /></td>
							</tr>
						</table>
					</div>
					<div class="right">
						<table>
							<tr>
								<td><label for="path">Path</label></td>
								<td><input type="text" name="path" id="path"
									data-href="/settings/update_path"
									value="<%= request.getAttribute("starboxFolder") %>" /></td>
								<td><a href="#save">Save</a></td>
							</tr>
							<tr>
								<td><label for="interval">Interval</label></td>
								<td><input type="text" name="interval" id="interval"
									data-href="/settings/update_interval"
									value="<%= request.getAttribute("indexUpdateInterval") %>" /></td>
								<td><a href="#save">Save</a></td>
							</tr>
							<tr>
								<td><label for="displayname">Displayname</label></td>
								<td><input type="text" name="displayname" id="displayname"
									data-href="/settings/update_name"
									value="<%= request.getAttribute("displayName") %>" /></td>
								<td><a href="#save">Save</a></td>
							</tr>
							<tr>
								<td><label for="email">Email</label></td>
								<td><input type="text" name="email" id="email"
									data-href="/settings/update_email"
									value="<%= request.getAttribute("email") %>" /></td>
								<td><a href="#save">Save</a></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="js/jquery-1.7.1.min.js"></script>
	<script src="js/settings.js"></script>
</body>
</html>
