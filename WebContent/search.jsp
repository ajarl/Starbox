<html>
<head>
<title>StarBox</title>
<link rel="stylesheet" href="/starbox/css/main.css" />
<link rel="stylesheet" href="/starbox/css/search.css" />
<%@ page import="java.util.*"%>
<%@ page import="se.starbox.models.User"%>
</head>
<body>
	<jsp:include page="_header.jsp" />
	<div class="main">
		<div class="layout">
			<div class="search-container clearfix">
				<div class="left">
					<div class="search-filters">
						<h1>Filters</h1>
						<h2>Users<span class="websymbol">]</span></h2>
						<% List<User> accepted = (List<User>) request.getAttribute("USERS_ACCEPTED"); %>
						<ul>
							<% for (User u : accepted) { %>
							<li class="selected" data-type="ip" data-value="<% out.print(u.getIp().replaceAll("\\.", "")); %>"><% out.print(u.getName()); %><span></span></li>
							<% } %>
							<li class="selected" data-type="ip" data-value="localhost"><% out.println(request.getAttribute("me")); %><span></span></li>
						</ul>
						<h2>Formats<span class="websymbol">]</span></h2>
						<ul>
							<li class="selected" data-type="format" data-value="exe,dmg,out">Executables<span></span></li>
							<li class="selected" data-type="format" data-value="doc,pdf,docx,odf,txt,js,py,c,php,html,htm,rb">Documents<span></span></li>
							<li class="selected" data-type="format" data-value="rar,zip,gzip,tar,bz2,gz,bz,7zip">Archives<span></span></li>
							<li class="selected" data-type="format" data-value="torrent">Torrents<span></span></li>
							<li class="selected" data-type="format" data-value="avi,mkv,mov,wmv">Movies<span></span></li>
							<li class="selected" data-type="format" data-value="mp3,ogg,flac">Music<span></span></li>
							<li class="selected" data-type="format" data-value="jpg,jpeg,png,gif,psd">Images<span></span></li>
						</ul>
					</div>
				</div>
				<div class="right">
					<div class="search-inputs">
						<input type="text" name="query" id="search-query">
						<div id="search-help" class="websymbol">
							L
						</div>
						<div class="match-highlight">
							Match
						</div>
					</div>
					<div class="search-results">
						<table style="display:none;">
							<thead>
								<tr>
									<th width="330px">Name</th>
									<th width="70px">Size</th>
									<th width="140px">Changed</th>
									<th width="135px">User</th>
									<td width="25px"></td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						<span>Type something to search!</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/starbox/js/jquery-1.7.1.min.js"></script>
	<script src="/starbox/js/search.js"></script>
	<script src="/starbox/js/jquery.tablesorter.js"></script>
	<script src="/starbox/js/jquery.qtip-1.0.0-rc3.min.js"></script>
</body>
</html>
