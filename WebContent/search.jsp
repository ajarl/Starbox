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
        <div class="search-container">
          <div class="search-inputs">
            <input type="text" name="query" id="search-query"/>
          </div>
          <div class="search-results">
            <table>
              <thead>
                <tr>
                  <th>Name</th>
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
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>
