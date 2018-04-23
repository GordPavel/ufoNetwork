<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title>Страница группы</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <script src="<c:url value="/resources/js/jquery.sse.min.js"/>"></script>
    <%--@elvariable id="group" type="com.netcracker.DAO.GroupEntity"--%>
    <script>
        function generateMessage(id, text, date, writerId, writerName) {
            return "<div id=\"message-" + id + "\">\n" +
                "                    <b>" + writerName + " " + date + "</b>:\n" +
                ((parseInt(writerId) === ${cookie.userID.value} || ${group.owner.id} === ${cookie.userID.value}) ?
                    "                        <button class=\"btn btn-primary deleteMessage\" onclick=\"deleteMessage(" + id + ")\" value=\"Удалить\"></button>" : "") +

                "                    <br>" + text + "\n" +
                "                </div>";
        }

        function generateUsersHref(id, name, race, age) {
            return "<div id=\"member-" + id + "\"><a href=\"${pageContext.request.contextPath}/persons/" + id + "\">" + name + " (" + race + "," + age + ")</a><br/></div>";
        }

        var errorHandler = function () {
            console.log('Ошибка соединения')
        };

        function sendMessage() {
            $.ajax({
                url: '${pageContext.request.contextPath}/groups/${group.id}/message',
                type: 'POST',
                // todo Исправить время
                timeout: 10 * 60 * 1000,
                data: $('#textarea2').val(),
                contentType: "text/html;charset=utf-8",
                success: function (response) {
                    if (response === "success") {
                        console.log('Сообщение отправлено');
                        $('#textarea2').val('');
                    }
                    else if (response === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else
                        console.log(response);
                },
                error: errorHandler
            });
        }

        function deleteMessage(messageId) {
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/${group.id}/message-".concat(messageId.toString()),
                type: 'DELETE',
                // todo Исправить время
                timeout: 10 * 60 * 1000,
                success: function (response) {
                    if (response === "success")
                        console.log("Сообщение удалено");
                    else if (response === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else
                        console.log(response);
                },
                error: errorHandler
            });
        }

        function joinGroup() {
            $.ajax({
                url: '${pageContext.request.contextPath}/groups/${group.id}/join',
                type: 'POST',
                timeout: 1000,
                success: function (response) {
                    if (response === "success") {
                        console.log("Вышел из группы");
                        $('#l3').html("<button onclick=\"leaveGroup()\">Покинуть группу</button><br/>");
                    }
                    else if (response === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else
                        console.log(response);
                },
                error: errorHandler
            });
        }

        function leaveGroup() {
            $.ajax({
                url: '${pageContext.request.contextPath}/groups/${group.id}/leave',
                type: 'POST',
                timeout: 1000,
                success: function (response) {
                    if (response === "success") {
                        console.log("Вступил в группу");
                        $('#l3').html("<button onclick=\"joinGroup()\">Вступить в группу</button><br/>");
                    }
                    else if (response === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else
                        console.log(response);
                },
                error: errorHandler
            });
        }

        $(document).ready(function () {
            $('#send').click(sendMessage);
            var sse = $.SSE('${pageContext.request.contextPath}/groups/${group.id}/messages/sse', {
                onOpen: function (e) {
                    console.log("Open");
                    console.log(e);
                },
                onEnd: function (e) {
                    console.log("End");
                    console.log(e);
                    alert('Вы оффлайн. Обновления могут не приходить. Лучше перезагрузить страницу');
                },
                onError: function (e) {
                    console.log("Could not connect");
                    alert('Вы оффлайн. Обновления могут не приходить. Лучше перезагрузить страницу');
                },
                events: {
                    post: function (data) {
                        data = JSON.parse(data.data);
                        $('#messages').append(generateMessage(data.id, data.text, data.date, data.writerId, data.writerName));
                    },
                    delete: function (data) {
                        data = JSON.parse(data.data);
                        $('#message-' + data.id).remove();
                    }
                }
            });
            sse.start();
        });
    </script>
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">
    <img src='<c:url value="/group-${group.id}/image"/>' width="32" height="32">
    <%--<img src='<c:url value="/user-${person.id}/image"/>' width="32" height="32">--%>
    <div id="l1">
        <table>
            <tr>
                <th align='center'>${group.name}
                    <c:if test="${group.owner.id.toString().equals(cookie['userID'].value)}">
                        <a href="<c:url value="/groups/${group.id}/settings"/>">Настройки</a>
                    </c:if>
                </th>
            </tr> <!--ряд с ячейками заголовков-->
            <tr>
                <td align='left'>Владелец: <a href="<c:url value="/persons/${group.owner.id}"/>">${group.owner.name}</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="l3">
        <c:choose>
            <c:when test="${isMember}">
                <button onclick="leaveGroup()">Покинуть группу</button>
                <br/>
            </c:when>
            <c:otherwise>
                <button onclick="joinGroup()">Вступить в группу</button>
                <br/>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="l2" style="overflow:auto; width: 30%; max-height: 20%;">
        <label>Список участников</label>
        <div class="list-group" id="members">
            <c:forEach items="${group.users}" var="user">
                <div id="member-${user.id}"><a
                        href="<c:url value="/persons/${user.id}"/>">${user.name}(${user.race.name},${user.age})</a><br/>
                </div>
            </c:forEach>
        </div>
    </div>
    <div id="l6" style="overflow-y:auto; width: 30%; max-height: 50%;">
        <div id="messages" border="1" width="99%">
            <c:forEach items="${group.messages}" var="message">
                <div id="message-${message.id}">
                    <b>${message.writer.name} ${message.dateOfSubmition.format(formatter)}</b>:
                    <c:if test="${message.writer.id.toString().equals(cookie[\"userID\"].value)||group.owner.id.toString().equals(cookie[\"userID\"].value)}">
                        <button class="btn btn-primary deleteMessage" onclick="deleteMessage(${message.id})"
                                value="Удалить"></button>
                    </c:if>
                    </br>${message.text}
                </div>
            </c:forEach>
        </div>
    </div>
    <textarea name="message" id="textarea2" style="resize: none;"></textarea>
    <input type="submit" class="btn" id="send" value="Отправить"/>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
