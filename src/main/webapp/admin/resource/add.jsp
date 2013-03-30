<%
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>
<%@page import="com.santiagolizardo.jerba.model.Article"%>
<%@page import="java.util.List"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreService"%>
<%@page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory"%>

<%@include file="../includes/header.jsp" %>

<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
<input type="hidden" name="action" value="Add" />

<legend>Add resource</legend>

<label for="myFile">File</label>
<input type="file" name="myFile" id="myFile" />

<label for="title">Title</label>
<input type="text" name="title" id="title" />

<div class="form-actions">
	<input class="btn" type="button" value="Cancel" onclick="window.history.go(-1);" />
	<div class="pull-right"><input class="btn btn-primary" type="submit" value="Add" /></div>
</div>

</form>

<%@ include file="../includes/footer.jsp" %>
