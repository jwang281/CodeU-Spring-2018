<%--
  Copyright 2017 Google Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<!DOCTYPE html>
<html>
<head>
  <title>Team int ellects; CodeU Chat App</title>
  <link rel="stylesheet" href="/css/main.css">
</head>
<body>

  <nav>
    <a id="navTitle" href="/">
    <img src = "https://drive.google.com/uc?id=1dG9V-sBNMS9hEivT4L-sn1M0m7RIn0Gr" width="160" height="80" /></a>
    <a href="/conversations"> Conversations</a>
    <% if(request.getSession().getAttribute("user") != null){ %>
      <a>Hello <%= request.getSession().getAttribute("user") %>!</a>
      <a href="/profile">Profile</a>
    <% } else{ %>
      <a href="/login">Login</a>
      <a href = "/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
    <a href="/testdata">Load Test Data</a>
  </nav>

  <div id="container"><center>
    <div
      style="width:75%; margin-left:auto; margin-right:auto; margin-top: 50px;">
    </div>

      <h1><img src = "homepage.gif"/>  </h1>
      <a href="/login">
      <img src = "https://drive.google.com/uc?id=1_bC0JRRBB5Gm2P8MxaFtd5IFeLIKjPkb" /></a>
      <a href="/conversations">
      <img src = "https://drive.google.com/uc?id=1L2fNzsLRiZFappZflKpEsbdHObLwEAb8 "/></a>
      <a href="/about.jsp">
      <img src = "https://drive.google.com/uc?id=1h10kd1nXgN4e3URjaazEqS5cwCru0id5" /></a>
      <a href="/testdata">
      <img src = "https://drive.google.com/uc?id=1n8h9a3p_enwmREP7LBKI8ZOtgl-4i1Cr" /></a>
      </center>
 </div>


</body>
</html>
