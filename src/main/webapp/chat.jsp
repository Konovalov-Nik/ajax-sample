<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/js/jquery.js" type="text/javascript"></script>

    <title>Ajax Chat</title>
</head>
<body>
<script type="text/javascript">
    $(function() {
        $("#send_button").click(function() {
            var text = $("#user_input").val();
            $.post("/chat", {"text": text});
        });
    });

    function loadMessages() {
        $.get("/messages").success(function(data) {
            $("#chat_area").html(data);
        });
    }

    $(function() {
        setInterval(loadMessages, 1000);
    });
</script>
<h5>You have logged in as <%= request.getAttribute("username")%>. <a href="/logout">Logout</a></h5>
<span id="chat_area" style="width: 80%; height: 90%">

</span>
<br>
<input type="text" placeholder="Type here" id="user_input"/>
<button value="Send" id="send_button">Send</button>
</body>
</html>