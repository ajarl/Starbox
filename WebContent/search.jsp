<html>
<head>
<title>StarBox</title>
<link rel="stylesheet" href="/starbox/css/main.css" />
<link rel="stylesheet" href="/starbox/css/search.css" />
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
						<ul>
							<li class="selected" data-type="user" data-value="Otto">Otto<span></span></li>
							<li class="selected" data-type="user" data-value="Kim">Kim<span></span></li>
							<li class="selected" data-type="user" data-value="Lukas">Lukas<span></span></li>
						</ul>
						<h2>Formats<span class="websymbol">]</span></h2>
						<ul>
							<li class="selected" data-type="format" data-value="exe,dmg,out">Executeable<span></span></li>
							<li class="selected" data-type="format" data-value="doc,pdf,docx,odf,txt">Document<span></span></li>
							<li class="selected" data-type="format" data-value="rar,zip,gzip,tar,bz2,gz,bz,7zip">Archives<span></span></li>
							<li class="selected" data-type="format" data-value="avi,mkv,mov,wmv">Movie<span></span></li>
							<li class="selected" data-type="format" data-value="jpg,jpeg,png,gif,psd">Image<span></span></li>
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
						<table>
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
								<tr data-user="Otto" data-format="exe">
									<td>Testfil.exe</td>
									<td>15 Mb</td>
									<td>2012-02-31</td>
									<td>Otto</td>
									<td>DL</td>
								</tr>
								<tr data-user="Kim" data-format="jpg">
									<td>profil.jpeg</td>
									<td>1 Mb</td>
									<td>2012-01-25</td>
									<td>Kim</td>
									<td>DL</td>
								</tr>
								<tr data-user="Lukas" data-format="pdf">
									<td>kex.pdf</td>
									<td>356 Kb</td>
									<td>2012-03-10</td>
									<td>Lukas</td>
									<td>DL</td>
								</tr>
							</tbody>
						</table>
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
