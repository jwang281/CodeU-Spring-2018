<!DOCTYPE html>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
   BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<html>
<head>

 <title>Profile</title>
 <link rel="stylesheet" href="/css/main.css">
 <style>
  label {
    display: inline-block;
    width: 100px;
  }
  </style>
</head>

<body>

  <nav>
    <a id="navTitle" href="/">CodeU Chat App</a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } %>
    <a href="/profile.jsp">Profile</a>
  </nav>

  <div id="container">
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
      <h1>Profile</h1>

      <% if(request.getSession().getAttribute("user") != null){ %>
        <h2><%= request.getSession().getAttribute("user") %></a>
      <% } %>

        <p>Choose a profile picture</p>

        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
           <input type="file" name="myFile">
           <input type="submit" value="Submit">
        </form>

      <ul>
        <li>Bio: </li>
        <li>Status: </li>
      </ul>

    </div>
  </div>
</body>

</html>
