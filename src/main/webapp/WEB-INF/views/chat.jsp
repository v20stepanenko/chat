<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My team</title>
</head>
<body>
<h1 class="table_dark">Chat</h1>
<div id="chat">
    <div id="username">
       Your name: <c:out value="${user.getName()}"/>
        <div hidden id="jwt_id"><c:out value="${user.getId()}"/></div>
    </div>
    <div class="main_block" id="chat_message" style="overflow:scroll; height:200px;">
        <c:forEach items="${messages}" var="message">
            <div>
                <span>${message.getUserName()}: </span>
                <span>${message.getText()}</span>
            </div>
        </c:forEach>
    </div>
    <form id="input_message" action="">
        <input id="input_text" type="text">
        <input type="submit">
    </form>
</div>
</body>
<script !src="">
    const urlSocket = "ws://localhost:8080/chat";
    const form = document.getElementById("input_message");
    form.addEventListener("submit", sendMessage);
    const idUser = document.getElementById("jwt_id").innerHTML;
    function sendMessage(event){
        event.preventDefault();
        var textElement = document.getElementById("input_text");
        var messageObj = {
            ownerId: idUser,
            text: textElement.value
        }
        const jsonMessage = JSON.stringify(messageObj);
        socket.send(jsonMessage);
        textElement.value = "";
    }



    const socket = new WebSocket(urlSocket);
    // Connection opened
    socket.addEventListener('open', function (event) {
    });

    // Listen for messages
    socket.addEventListener('message', function (event) {
        console.log('Message from server ', event.data);
        addMessage(event.data);
    });

    const rootDivChat = document.getElementById("chat_message");

    function addMessage(messageJSON){
        const message = JSON.parse(messageJSON); // message : {text, userName}
        const messageDivRoot = document.createElement("div");
        const messageSpanUserName = document.createElement("span");
        const messageSpanText = document.createElement("span");
        messageSpanUserName.innerHTML = message.userName + ": ";
        messageSpanText.innerHTML = message.text;
        messageDivRoot.appendChild(messageSpanUserName);
        messageDivRoot.appendChild(messageSpanText);
        rootDivChat.appendChild(messageDivRoot);
        console.log(message.text);
    }
</script>
</html>
