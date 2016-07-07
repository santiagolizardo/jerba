<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.santiagolizardo.jerba.managers.ConfigManager"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<script src="/components/jquery/dist/jquery.min.js"></script>
	<script src="/components/datatables/media/js/dataTables.bootstrap.min.js"></script>
	<script src="/components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="/components/ckeditor/ckeditor.js"></script>
	<link href="/components/datatables/media/css/dataTables.bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="/components/bootstrap/dist/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
</head>
<body>

<div class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
        <div class="navbar-header">
		    <a class="navbar-brand" href="#">Jerba CMS</a>
        </div>
		<a class="btn btn-default navbar-btn" href="/admin">Control panel</a>
		<a class="btn btn-default navbar-btn" target="jcms-web" href="<%= ConfigManager.getInstance().getValue(ConfigManager.WEBSITE_URL) %>">View Web</a>
	</div>	
</div>

<div class="container-fluid">

