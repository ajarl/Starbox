<%@page import="java.net.InetAddress"%>
<div class="menu-container">
  <div class="layout clearfix">
    <ul class="menu clearfix">
      <li class="ip"><%= InetAddress.getLocalHost().getHostAddress() %></li>
      <li class="search"><a href="/starbox/search/">L</a></li>
      <li class="users"><a href="/starbox/users/">U</a></li>
      <li class="settings"><a href="/starbox/settings/">S</a></li>
    </ul>
  </div>
</div>