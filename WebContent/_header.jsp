<%@page import="java.net.InetAddress"%>
<div class="header">
  <div class="layout clearfix">
    <ul class="menu clearfix">
      <li class="ip"><%= InetAddress.getLocalHost().getHostAddress() %></li>
      <li class="search"><a href="/search.html">L</a></li>
      <li class="users"><a href="/users.html">U</a></li>
      <li class="settings"><a href="/settings.html">S</a></li>
    </ul>
  </div>
</div>