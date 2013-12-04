<%@ page import="ru.sgu.itcourses.fileserver.utils.Message" %>
<%@ page import="java.util.List" %>
<% for (Message message: (List<Message>)request.getAttribute("messages")) { %>
<span style="color: dodgerblue"><%= message.username %></span>: <%= message.text%>
<br>
<% } %>
