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
pageContext.setAttribute("resources", resources);
%>

<%@ include file="../includes/header.jsp" %>

<p>
<a class="btn btn-primary" href="add.jsp">Add new resource</a>
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
<c:forEach var="r" items="#{resources}">
<tr>
	<td>${r.contentType}</td>
	<td>${r.fileName}</td>
	<td>${r.size}</td>
	<td>${r.title}</td>
	<td><img src="<%= UrlFactory.getInstance().createResourceUrl((Resource)pageContext.findAttribute("r")) %>" style="width: 100px; border: 1px solid gray; padding: 3px;" /></td>
	<td>
		<a class="btn btn-danger" href="/DeleteResource?id=${r.key.id}">Delete</a>
	</td>
</tr>
</c:forEach>
</tbody>
</table>

<%@ include file="../includes/footer.jsp" %>
