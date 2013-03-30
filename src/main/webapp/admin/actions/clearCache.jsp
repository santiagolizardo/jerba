<%@include file="../includes/header.jsp"%>
<%@page import="com.santiagolizardo.jerba.utilities.CacheSingleton"%>
<% CacheSingleton.getInstance().getCache().clear(); %>

<h2>Done!</h2>

<%@include file="../includes/footer.jsp"%>