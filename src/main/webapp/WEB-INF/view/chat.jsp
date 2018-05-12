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
<%@ page import="java.util.List" %>
<%@ page import="codeu.model.data.Conversation" %>
<%@ page import="codeu.model.data.Message" %>
<%@ page import="codeu.model.store.basic.UserStore" %>
<%@ page import="com.vdurmont.emoji.EmojiParser" %>
<%@ page import = "org.commonmark.node.*" %>
<%@ page import = "org.commonmark.parser.Parser" %>
<%@ page import = "org.commonmark.renderer.html.HtmlRenderer"%>
<%@ page import = "org.apache.commons.validator.routines.UrlValidator"%>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
Conversation conversation = (Conversation) request.getAttribute("conversation");
List<Message> messages = (List<Message>) request.getAttribute("messages");
String[] schemes = {"http","https"};
UrlValidator urlValidator = new UrlValidator(schemes);
BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
String chatUploadUrl = "/uploadchat/" + conversation.getTitle();
%>

<!DOCTYPE html>
<html>
<head>
  <title><%= conversation.getTitle() %></title>
  <link rel="stylesheet" href="/css/main.css" type="text/css">
  <script type="text/javascript" src="https://code.jquery.com/jquery-1.11.2.min.js"></script>
  <link rel="stylesheet" type="text/css" href="/css/jquery.emojipicker.css">
  <script type="text/javascript" src="/js/jquery.emojipicker.js"></script>
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

  <!-- Emoji Data -->
  <link rel="stylesheet" type="text/css" href="/css/jquery.emojipicker.a.css">
  <script type="text/javascript" src="/js/jquery.emojis.js"></script>

  <script type="text/javascript">
  	$(document).ready(function(e) {
		$('#input-default').emojiPicker();
	});
  </script>

  <style>
    #chat {
      background-color: white;
      height: 500px;
      overflow-y: scroll
    }
  </style>

  <script>
    // scroll the chat div to the bottom
    function scrollChat() {
      var chatDiv = document.getElementById('chat');
      chatDiv.scrollTop = chatDiv.scrollHeight;
    };
  </script>
</head>
<body onload="scrollChat()">

  <nav>
    <a id="navTitle" href="/">
    <img src = "https://drive.google.com/uc?id=1dG9V-sBNMS9hEivT4L-sn1M0m7RIn0Gr" width="160" height="80" /></a>
    <a href="/conversations">Conversations</a>
      <% if (request.getSession().getAttribute("user") != null) { %>
    <a href="/profile/<%=request.getSession().getAttribute("user")%>">Hello <%= request.getSession().getAttribute("user") %>!</a>
    <% } else { %>
      <a href="/login">Login</a>
      <a href = "/register">Register</a>
    <% } %>
    <a href="/about.jsp">About</a>
  </nav>

  <div id="container">

    <h1><%= conversation.getTitle() %>
      <a href="" style="float: right">&#8635;</a></h1>

    <hr/>

    <div id="chat">
      <ul>
    <%
      for (Message message : messages) {
        String author = UserStore.getInstance()
          .getUser(message.getAuthorId()).getName();
        String str = message.getContent();
        String messageId = message.getId().toString();
        //This renders the text using the library added.
        //The result (renderedMessage) is a string that 
        //replaced the markdown syntax to the styles.
        Parser parser = Parser.builder().build();
        Node document = parser.parse(str);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String renderedMessage = renderer.render(document);
        
        String result = EmojiParser.parseToUnicode(renderedMessage);	
		String resultDecimal = EmojiParser.parseToHtmlDecimal(result);

        //uses original str to validate url because the other strings contain extra stuff
		if (urlValidator.isValid(str) || str.contains("http://localhost:8080/_ah/img/") ) {
		   %>
              <li>
                <a href="/profile/<%=author%>"><strong><%= author %>:</strong></a>
                <br/>
                <span id="<%= messageId %>" >
                    <img src="<%=str%>" onerror="document.getElementById('<%= messageId %>').innerHTML = '<%=str%>'" />
                </span>
              </li>
           <%
	    } else {
          %>
              <li><a href="/profile/<%=author%>"><strong><%= author %>:</strong></a> <%= resultDecimal %> </li>
          <%
	    }
      }
    %>
      </ul>
    </div>

    <hr/>

    <% if (request.getSession().getAttribute("user") != null) { %>
    <form action="/chat/<%= conversation.getTitle() %>" method="POST">
    
        <!-- <input type="text" name="message"> -->
        <input type = "text" id="input-default" class="emojiable-option" name="message">

        <br/>
        <button type="submit"class="w3-btn w3-green">Send</button>
    </form>

    <form action="<%= blobstoreService.createUploadUrl(chatUploadUrl) %>" method="POST" enctype="multipart/form-data">
      <input type="file" name="myFileFromChat" accept="image/*">
      <input type="submit" value="Submit">
    </form>
    
    <span id = "key">
      <p>
      Markdown Key
      <br>
      **bold text** or __bold text__  &#8680; <b>bold text</b>
      <br>
      *italic text* or _italic text_  &#8680; <i>italic text</i>
      <br>
      <i>For more information please visit: </i> <a href = "http://commonmark.org/help/">Commonmark Help Page</a>
      </p>
    </span>
    
    <% } else { %>
      <p><a href="/login">Login</a> to send a message.</p>
    <% } %>

    <hr/>

  </div>

    <script type="text/javascript">
        var encodedMsg = $('<div />').text(message).html();
        $('#container').html(encodedMsg);
    </script>

</body>
</html>
