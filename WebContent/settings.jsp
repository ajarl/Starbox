<html>
<head>
<title>StarBox</title>
<link rel="stylesheet" href="/starbox/css/main.css" />
<link rel="stylesheet" href="/starbox/css/settings.css" />
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
								<td>
									<button class="control-button" id="update-index"
									data-href="/settings/updateIndex">Update index</button>
								</td>
							</tr>
							<tr>
								<td>
									<button class="control-button" id="shut-down"
									data-href="/settings/shutdown" >Shut down</button>
								</td>
							</tr>
						</table>
					</div>
					<div class="right">
						<table>
							<tr>
								<td><label for="path">Path</label></td>
								<td><input type="text" name="path" id="path"
									data-href="/settings/updatePath"
									value="<%= request.getAttribute("starboxFolder") %>" />
									<button class="save-setting">Save</button></td>
							</tr>
							<tr>
								<td><label for="interval">Interval</label></td>
								<td><input type="text" name="interval" id="interval"
									data-href="/settings/updateInterval"
									value="<%= request.getAttribute("indexUpdateInterval") %>" />
									<button class="save-setting">Save</button></td>
							</tr>
							<tr>
								<td><label for="displayname">Displayname</label></td>
								<td><input type="text" name="displayname" id="displayname"
									data-href="/settings/updateName"
									value="<%= request.getAttribute("displayName") %>" />
									<button class="save-setting">Save</button></td>
							</tr>
							<tr>
								<td><label for="email">Email</label></td>
								<td><input type="text" name="email" id="email"
									data-href="/settings/updateEmail"
									value="<%= request.getAttribute("email") %>" />
									<button class="save-setting">Save</button></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/starbox/js/jquery-1.7.1.min.js"></script>
	<script src="/starbox/js/settings.js"></script>
</body>
</html>
