<%@page import="javax.cache.CacheStatistics"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.cache.Cache"%>
<%@include file="../includes/header.jsp"%>
<%@page import="com.santiagolizardo.jerba.utilities.CacheSingleton"%>
<%
	Cache cache = CacheSingleton.getInstance().getCache();
	CacheStatistics stats = cache.getCacheStatistics();
%>

<h2>Cache stats</h2>

<dl>
	<dt>Cache hits</dt>
	<dd><%= stats.getCacheHits() %></dd>
	<dt>Cache misses</dt>
	<dd><%= stats.getCacheMisses() %></dd>
	<dt>Object count</dt>
	<dd><%= stats.getObjectCount() %></dd>
</dl>

<%@include file="../includes/footer.jsp"%>