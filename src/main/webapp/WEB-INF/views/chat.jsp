<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My team</title>
</head>
<body>
<h1 class="table_dark">CHat</h1>
<div id="chat">
    <div id="username">
       Your name: <c:out value="${user.getName()}"/>
        <div hidden id="jwt_id"><c:out value="${user.getId()}"/></div>
    </div>
    <form id="input_message" action="">
        <input id="input_text" type="text">
        <input type="submit">
    </form>
</div>
</body>
<script !src="">

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

    const socket = new WebSocket('ws://localhost:8080/chat');
    // Connection opened
    socket.addEventListener('open', function (event) {
    });

    // Listen for messages
    socket.addEventListener('message', function (event) {
        console.log('Message from server ', event.data);
    });
</script>
</html>
