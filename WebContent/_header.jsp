<%@page import="se.starbox.models.SettingsModel"%>
<div class="menu-container">
  <div class="layout clearfix">
  	<div class="logo clearfix" onclick="location.href='/starbox/search/'">
  		<img src="/starbox/css/images/starbox_logo.png"></img>
  		<span>Starbox</span>
  	</div>
    <ul class="menu clearfix">
      <li class="ip"><%= SettingsModel.getIP() %></li>
      <li class="icon"><a href="/starbox/search/" class="search"></a></li>
      <li class="icon"><a href="/starbox/users/" class="user"></a></li>
      <li class="icon"><a href="/starbox/settings/" class="settings"></a></li>
      <li><a href="/starbox/help/" class="help">?</a></li>
    </ul>
  </div>
</div>