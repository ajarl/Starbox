<%@page import="java.net.InetAddress"%>
<div class="menu-container">
  <div class="layout clearfix">
    <ul class="menu clearfix">
      <li class="ip"><%= InetAddress.getLocalHost().getHostAddress() %></li>
      <li class="icon"><a href="/starbox/search/" class="search"></a></li>
      <li class="icon"><a href="/starbox/users/" class="user"></a></li>
      <li class="icon"><a href="/starbox/settings/" class="settings"></a></li>
    </ul>
  </div>
</div>