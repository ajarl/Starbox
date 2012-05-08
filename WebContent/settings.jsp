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
								<h2>Controllers</h2>
							<tr>
								<td>
									<button class="button-small button-blue" id="update-index"
									data-param="updateindex">Update index</button>
								</td>
							</tr>
							<tr>
								<td>
									<button class="button-small button-blue" id="shut-down"
									data-param="shutdown" >Shut down</button>
								</td>
							</tr>
						</table>
					</div>
					<div class="right">
						<table>
							<tr>
								<h2>Settings</h2>
							<tr>
							<tr>
								<td><label for="path">Path</label></td>
								<td>
									<input type="text" name="path" id="path"
									value="<%= request.getAttribute("starboxFolder") %>" />
								<td>
									<button class="save-setting button-small button-green">Save</button>
									<span class="status"></span>
								</td>
							</tr>
							<tr>
								<td><label for="interval">Interval (minutes)</label></td>
								<td>
									<input type="number" name="interval" id="interval"
									value="<%= Integer.parseInt(request.getAttribute("indexUpdateInterval").toString()) / 60 %>" />
								<td>
									<button class="save-setting button-small button-green">Save</button>
									<span class="status"></span>
								</td>
							</tr>
							<tr>
								<td><label for="displayname">Displayname</label></td>
								<td>
									<input type="text" name="displayname" id="displayname"
									value="<%= request.getAttribute("displayName") %>" />
								</td>
								<td>
									<button class="save-setting button-small button-green">Save</button>
									<span class="status"></span>
								</td>
							</tr>
							<tr>
								<td><label for="email">Email</label></td>
								<td>
									<input type="email" name="email" id="email"
									value="<%= request.getAttribute("email") %>" />
								</td>
								<td>
									<button class="save-setting button-small button-green">Save</button>
									<span class="status"></span>
								</td>
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
