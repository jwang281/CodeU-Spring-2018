<!DOCTYPE html>

<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="codeu.model.data.User" %>


<%
   BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   String profilePicUrl = (String) request.getAttribute("profilePic");
   User currentUser = (User) request.getAttribute("currentUser");
   User displayUser = (User) request.getAttribute("displayUser");
   String bioUrl = (String) request.getAttribute("bio");
   String statusUrl = (String) request.getAttribute("status");
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
   <a id="navTitle" href="/">
   <img src = "https://drive.google.com/uc?id=1dG9V-sBNMS9hEivT4L-sn1M0m7RIn0Gr" width="160" height="80" /></a>
    <a href="/conversations">Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a href="/profile/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } %>
  </nav>

  <div id="container">
    <div style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
      <h1><img src = "https://drive.google.com/uc?id=1HV6r8D7Xeoic1UCRkcXCoKsF1JkLsJV2" width="222" height="75"/></h1>

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

    <p>Bio: </p>
    <p><font size="4">${bio}</font></p>
    <% if ( currentUser.getName().equals(displayUser.getName()) ){ %>
         <form action="/profile/<%=request.getSession().getAttribute("user")%>" method="post">
              <input type="text" id="input-bio" class="emojiable-option" name="bio" >
              <button type="submit">Save Bio</button>
          </form>
    <% } %>

    <p>Status: </p>
    <p><font size="4">${status}</font></p>
    <% if ( currentUser.getName().equals(displayUser.getName()) ){ %>
         <form action="/profile/<%=request.getSession().getAttribute("user")%>" method="post">
              <input type="text" id="input-status" class="emojiable-option" name="status" >
              <button type="submit">Post Status</button>
          </form>
    <%}%>
    </div>
  </div>
</body>

</html>
