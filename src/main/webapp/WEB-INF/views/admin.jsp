<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script>

        //        {"users":[{"login":"...",
        //                    rel:"add/remove",
        //                    href:"..."
        //        }]}

        /*{auth: "yes",…}
         auth : "yes"
         users :
         [{login: "user2", links: [{rel: "remove", href: "http://127.0.0.1:8080/admin/delete/45"}]},…]
         0 : {login: "user2", links: [{rel: "remove", href: "http://127.0.0.1:8080/admin/delete/45"}]}
         1 : {login: "user1", links: [{rel: "remove", href: "http://127.0.0.1:8080/admin/delete/41"}]}
         2 : {login: "ivanLogin", links: [{rel: "add", href: "http://127.0.0.1:8080/admin/add"}]}*/

        function getUsers(){
            $.ajax({
                type:'GET',
                contentType: 'application/json',
                url:'${usersUrl}',
                dataType:'json',
                success:function (data,textStatus,jqXHR){
                    var userList = data.users;
                    var formTag = document.createElement("form");
                    var ulTag = document.createElement("ul");
                    for(var i=0;i<userList.length;i++){
                        var liTag = document.createElement("li");
                        liTag.innerText=userList[i].login;
                        var inputTag = document.createElement("input");
                        inputTag.type="button";
                        inputTag.value=userList[i].links[0].rel;
                        inputTag.name = userList[i].links[0].href;
                        inputTag.id = userList[i].login;
                        inputTag.addEventListener("click",userBan);
                        liTag.appendChild(inputTag);
                        ulTag.appendChild(liTag);
                    }
                    formTag.appendChild(ulTag);
                    document.getElementById("users").innerHTML="";
                    document.getElementById("users").appendChild(formTag);
                }
            });
        }

        function userBan (event) {
            var rel = event.currentTarget.value;
            var href = event.currentTarget.name;
            var login = event.currentTarget.id;
            if(rel=="add"){
                $.ajax({
                    type:'POST',
                    contentType: 'application/json',
                    url:href,
                    data:JSON.stringify({"login":login,}),
                    success: function(data,textStatus,jqXHR){
                        getUsers();
                    }
                });
            }else {
                $.ajax({
                    type:'DELETE',
                    contentType: 'application/json',
                    url:href,
                    success: function(data,textStatus,jqXHR){
                        getUsers();
                    }
                });
            }
        }
        window.onload=function () {
            getUsers();
        }
    </script>
</head>
<body>
    <h1>Welcome to admin page!</h1>
    <br><a href="/">Logout -></a><br>
    <div id="users">
    </div>
</body>
</html>

