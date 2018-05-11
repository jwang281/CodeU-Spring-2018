<!DOCTYPE html>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="codeu.model.data.User" %>


<%
   BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String profilePicUrl = (String) request.getAttribute("profilePic");
   User currentUser = (User) request.getAttribute("currentUser");
   User displayUser = (User) request.getAttribute("displayUser");

<%
   BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String profilePicUrl = (String) request.getAttribute("profilePic");
   String bioUrl = (String) request.getAttribute("bio");

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
      <a href="/profile/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } %>
  </nav>

  <div id="container">
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
      <h1>Profile</h1>

      <% if( displayUser.getName() != null){ %>
        <h2><%=displayUser.getName()%></a>
      <% } %>
        <br/>

        <% if (profilePicUrl!=""){ %>
         <img src="<%=profilePicUrl%>" >
        <%}%>

        <% if ( currentUser.getName().equals(displayUser.getName()) ){ %>
            <p>Choose a profile picture</p>

        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
           <input type="file" name="myFile" accept="image/*">
           <input type="submit" value="Submit">
        </form>

        <%}%>

      <ul>
        <li>Bio: </li>
        <li>Status: </li>
      </ul>

    <p>Bio: </p>
    <p><font size="4">${bio}</font></p>
       <% if(request.getSession().getAttribute("user") != null){ %>
               <h2><%= request.getSession().getAttribute("user") %></a>
       <% } %>
       <% if (bioUrl != ""){ %>
         <form action="/profile" method="post">
                <input type="text" id="input-bio" class="emojiable-option" name="bio" >
                <button type="submit">Save Bio</button>
          </form>
          <% } else { %>
            <p><a href="/login">Login</a> to update profile.</p>
          <% } %>



    </div>
  </div>
</body>

</html>
