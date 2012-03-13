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
							<li class="selected" data-type="format" data-value="executable">Executeable<span></span></li>
							<li class="selected" data-type="format" data-value="document">Document<span></span></li>
							<li class="selected" data-type="format" data-value="movie">Movie<span></span></li>
							<li class="selected" data-type="format" data-value="image">Image<span></span></li>
						</ul>
					</div>
				</div>
				<div class="right">
					<div class="search-inputs">
						<input type="text" name="query" id="search-query" />
					</div>
					<div class="search-results">
						<table>
							<thead>
								<tr>
									<th width="30%">Name</th>
									<th width="10%">Size</th>
									<th width="15%">Changed</th>
									<th width="15%">User</th>
									<td></td>
								</tr>
							</thead>
							<tbody>
								<tr data-user="Otto" data-format="executable">
									<td>Testfil.exe</td>
									<td>15 Mb</td>
									<td>2012-02-31</td>
									<td>Otto</td>
									<td>Ladda hem</td>
								</tr>
								<tr data-user="Kim" data-format="image">
									<td>profil.jpeg</td>
									<td>1 Mb</td>
									<td>2012-01-25</td>
									<td>Kim</td>
									<td>Ladda hem</td>
								</tr>
								<tr data-user="Lukas" data-format="document">
									<td>kex.pdf</td>
									<td>356 Kb</td>
									<td>2012-03-10</td>
									<td>Lukas</td>
									<td>Ladda hem</td>
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
</body>
</html>
