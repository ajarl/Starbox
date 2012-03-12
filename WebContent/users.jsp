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
        <div class="users-container clearfix">
        
          <div class="users-valid">
            <h1>Valid users</h1>
            <table>
              <tr>
                <td>
                  Otto Nordgren
                </td>
                <td>
                  <a href="#remove" data-userid="1" data-controller="users" data-method="delete">Remove</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="users-requested">
            <h1>Requested users</h1>
            <table>
              <tr>
                <td>
                  Pelle Larsson
                </td>
                <td>
                  <a href="#remove" data-userid="2" data-controller="users" data-method="delete">Remove</a>
                </td>
              </tr>
            </table>
          </div>
          <div class="users-valid">
            <h1>Pending users</h1>
            <table>
              <tr>
                <td>
                  Anders Jonsson
                </td>
                <td>
                  <a href="#accept" data-userid="1" data-controller="users" data-method="accept">Accept</a>
                  <a href="#deny" data-userid="1" data-controller="users" data-method="deny">Deny</a>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
    <script src="js/jquery-1.7.1.min.js"></script>
    <script src="js/search.js"></script>
  </body>
</html>
