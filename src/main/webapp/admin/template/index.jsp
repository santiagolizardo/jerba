<%@page import="com.santiagolizardo.jerba.managers.TemplateManager"%>
<%@page import="com.santiagolizardo.jerba.model.Template"%>
<%@page import="java.util.List"%>
<%@page import="com.santiagolizardo.jerba.model.PMF"%>
<%@page import="javax.jdo.PersistenceManager"%>
<%
PersistenceManager pm = PMF.get().getPersistenceManager();
List<Template> templates = new TemplateManager(pm).findAll();
pm.close();
%>

<%@ include file="../includes/header.jsp" %>

<p>
<a href="add.jsp">Add</a>
</p>

<table id="table" class="table table-striped">
<thead>
<tr>
	<th>Identifier</th>
	<th>Options</th>
</tr>
</thead>
<tbody>
<% for(Template t : templates) { %>
<tr>
	<td><%= t.getIdentifier() %></td>
	<td>
		<div class="btn-group">
		<a class="btn" href="edit.jsp?identifier=<%= t.getIdentifier() %>">Edit</a>
		<a class="btn btn-danger" href="/DeleteTemplate?id=<%= t.getIdentifier() %>">Delete</a>
		</div>
	</td>
</tr>
<% } %>
</tbody>
</table>

<%@ include file="../includes/footer.jsp" %>
