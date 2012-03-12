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
						<h2>Users</h2>
						<ul>
							<li class="selected">Otto<span></span></li>
							<li>Kim<span></span></li>
						</ul>
						<h2>Formats</h2>
						<ul>
							<li>Executeable<span></span></li>
							<li>Document<span></span></li>
							<li class="selected">Movie<span></span></li>
							<li>Image<span></span></li>
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
									<th style="font-family: 'CreteRoundRegular';">Name</th>
									<th>Size</th>
									<th>Changed</th>
									<th>User</th>
									<th>DL</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Testfil.exe</td>
									<td>15 Mb</td>
									<td>2012-02-31</td>
									<td>Otto Nordgren</td>
									<td>Ladda hem</td>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="/starbox/js/jquery-1.7.1.min.js"></script>
	<script src="/starbox/js/search.js"></script>
</body>
</html>
