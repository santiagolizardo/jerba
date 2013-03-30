<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<script type="text/javascript" src="/scripts/jquery.min.js"></script>
	<script type="text/javascript" src="/scripts/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/scripts/ckeditor/ckeditor.js"></script>
	<link href="/styles/demo_table.css" type="text/css" rel="stylesheet" />
	<link href="/styles/demo_table_jui.css" type="text/css" rel="stylesheet" />
	<link href="/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body>

<div class="navbar navbar-static-top">
	<div class="navbar-inner">
		<a href="#" class="brand">Jerba</a>
		<ul class="nav">
			<li><a href="/admin">Control panel</a></li>
			<li><a target="jcms-web" href="<%= ConfigManager.getInstance().getValue(ConfigManager.WEBSITE_URL) %>">View Web</a></li>
		</ul>
	</div>	
</div>

<div class="container-fluid">

