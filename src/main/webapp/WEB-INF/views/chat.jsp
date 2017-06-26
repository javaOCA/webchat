<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chat page</title>
    <link rel="stylesheet" media="screen" href="./static/css/chat.css"/>
    <script src="http://cdn.jsdelivr.net/sockjs/0.3.4/sockjs.min.js"></script>
    <script>
        function ready() {
            document.getElementById("sendButton").addEventListener("click", send);
            document.getElementById("broadcastButton").addEventListener("click", broadcast);
            document.getElementById("resetButton").addEventListener("click", reset);
            document.getElementById("logoutButton").addEventListener("click", logout);
        }
        document.addEventListener("DOMContentLoaded", ready);
        var socket = new SockJS("${sockUrl}");
        socket.onopen = function () {
            console.log("Connection successful!");
            registration();
        };
        socket.onclose = function (event) {
            if (event.wasClean) {
                console.log("Connection close!");
            } else {
                console.log("Connection closed because of error!");
            }
//            window.location.href = "/";
        };
        socket.onerror = function (error) {
            console.log("error");
//            window.location.href = "/";
        };
        socket.onmessage = function (event) {
            var json_message = JSON.parse(event.data);
            if (typeof json_message.auth === "undefined") {
                console.log("Bad JSON");
//                window.location.href = "/";
            }
            if (json_message.auth == "yes" && typeof json_message.list !== "undefined") {
                var array_active_users = json_message.list;
                var ul_element = document.createElement("ul");
                for (var i = 0; i < array_active_users.length; i++) {
                    var li_element = document.createElement("li");
                    li_element.className = "active-users";
                    li_element.id = array_active_users[i];
                    li_element.innerText = array_active_users[i];
                    li_element.addEventListener("click", addUser);
                    ul_element.appendChild(li_element);
                }
                var div_element = document.getElementById("activeUsers");
                div_element.innerHTML="";
                div_element.appendChild(ul_element);
            }
            if (json_message.auth == "yes" && typeof json_message.login !== "undefined") {
                var output = json_message.login + ": " + json_message.message;
                document.getElementById("inputMessage").value += output +"\n";
            }
            //TODO "name":"..."; "message":"..."

            if(json_message.auth == "yes" && typeof json_message.name !== "undefined"
                && typeof json_message.message!=="undefined"){
                var output = json_message.name+ ": " + json_message.message;
                document.getElementById("inputMessage").value += output+"\n";
            }

        };
        function registration() {
            var sessionid = getCookie("JSESSIONID");
            var answer = {};
            answer["sessionid"] = sessionid;
            socket.send(JSON.stringify(answer));
        }
        function getCookie(name) {
            var matches = document.cookie.match(new RegExp(
                "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
            ));
            return matches ? decodeURIComponent(matches[1]) : undefined;
        }
        function addUser(event) {
            var login = event.currentTarget.id;
            document.getElementById("outputMessage").value = login + ":";
        }
        function send() {
            var array_message = document.getElementById("outputMessage").value.split(":");
            reset();
            var answer = {};
            answer["login"] = array_message[0];
            answer["message"] = array_message[1];
            socket.send(JSON.stringify(answer));
        }
        function broadcast() {
            var message = document.getElementById("outputMessage").value;
            var answer = {};
            answer["broadcast"] = message;
            socket.send(JSON.stringify(answer));
        }
        function reset() {
            document.getElementById("outputMessage").value = "";
        }
        function logout() {
            var answer = {};
            answer["logout"] = "";
            socket.send(JSON.stringify(answer));
            window.location.href = "/";
        }
    </script>
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h2>Welcome&nbsp;${user.name}&nbsp;in our chat!</h2>
        </div>
    </div>
    <div id="main">
        <form>
            <textarea id="inputMessage" readonly></textarea>
        </form>
    </div>
    <div id="activeUsers">

    </div>
    <div id="footer">
        <form>
            <input type="text" id="outputMessage"/>
        </form>
        <form>
            <input type="button" id="sendButton" value="Send"/>
            <input type="button" id="broadcastButton" value="Broadcast"/>
            <input type="button" id="resetButton" value="Clear"/>
            <input type="button" id="logoutButton" value="Logout"/>
        </form>
    </div>
</body>
</html>
