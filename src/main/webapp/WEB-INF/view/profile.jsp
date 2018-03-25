<!DOCTYPE html>
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
      <a href="/profile">Profile</a>
    <% }
  </nav>

  <div id="container">
    <h1>Profile</h1>

  </div>
</body>
</html>
