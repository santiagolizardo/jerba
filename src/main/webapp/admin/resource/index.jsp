<%@page import="com.santiagolizardo.jerba.utilities.UrlFactory"%>
<%@page import="com.santiagolizardo.jerba.managers.ResourceManager"%>
<%@page import="com.santiagolizardo.jerba.model.Resource"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%
PersistenceManager pm = PMF.get().getPersistenceManager();
List<Resource> resources = new ResourceManager(pm).findAll();
pm.close();
%>

<%@ include file="../includes/header.jsp" %>

<p>
<a href="add.jsp">Add</a>
</p>

<table id="table" class="table table-striped">
<thead>
<tr>
	<th>Content type</th>
	<th>Filename</th>
	<th>Size</th>
	<th>Title</th>
	<th>Preview</th>
	<th>Options</th>
</tr>
</thead>
<tbody>
<% for(Resource r : resources) { %>
<tr>
	<td><%= r.getContentType() %></td>
	<td><%= r.getFileName() %></td>
	<td><%= r.getSize() %></td>
	<td><%= r.getTitle() %></td>
	<td><img src="<%= UrlFactory.getInstance().createResourceUrl(r) %>" style="width: 100px; border: 1px solid gray; padding: 3px;" /></td>
	<td>
		<a class="btn btn-danger" href="/DeleteResource?id=<%= r.getKey().getId() %>">Delete</a>
	</td>
</tr>
<% } %>
</tbody>
</table>

<%@ include file="../includes/footer.jsp" %>
