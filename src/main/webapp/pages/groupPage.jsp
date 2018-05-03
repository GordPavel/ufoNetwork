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
    <%--@elvariable id="group" type="com.netcracker.DAO.GroupEntity"--%>
    <script>
        function generateMessage(id, text, date, writerId, writerName, writerDeleted) {

                return "<div id=\"message-" + id + "\">\n" +
                    "                    <b> " + generateMessageSign(writerId, writerName, writerDeleted) + date + "</b>:\n" +
                    ((parseInt(writerId) === ${cookie.userID.value} || ${group.owner.id} === ${cookie.userID.value}) ?
                        "                        <button class=\"btn btn-primary deleteMessage\" onclick=\"deleteMessage(" + id + ")\" value=\"Удалить\"></button>" : "") +

                    "                    <br>" + text + "\n" +
                    "                </div>";
        }

        function generateMessageSign(writerId, writerName, writerDeleted) {
            if (writerDeleted==="true"){
                return "DELETED ";
            }
            return "<a href=\"${pageContext.request.contextPath}/persons/"  + writerId + "\">"
                + writerName + "</a> ";
        }

        function ajaxAllMessagesOfGroup() {
            var errorHandler = function () {
                alert('Сообщения не может быть получены')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/${group.id}/messages",
                type: 'GET',
                timeout: 400,
                success: function (response) {
                    if (response.error === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else {
                        $('#messages').html(response.map(function (message) {
                            return generateMessage(message.id, message.text, message.date, message.writerId, message.writerName, message.writerDeleted);
                        }).join('\n'));
                    }
                },
                error: errorHandler
            });
        }

        function sendMessage() {
            var errorHandler = function () {
                alert('Сообщение не может быть отправлено')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/${group.id}/message",
                type: 'POST',
                timeout: 5 * 1000,
                contentType: "application/json",
                data: JSON.stringify({messageText: $('#textarea2').val()}),
                success: function (response) {
                    if (response === "fail")
                        errorHandler();
                    else {
                        ajaxAllMessagesOfGroup();
                        $('#textarea2').val("");
                    }
                },
                error: errorHandler
            });
        }

        function deleteMessage(messageId) {
            var errorHandler = function () {
                alert('Сообщение не может быть удалено')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/${group.id}/message-".concat(messageId.toString()),
                type: 'DELETE',
                timeout: 1000 * 5,
                success: function (response) {
                    if (response === "fail")
                        errorHandler();
                    else if (response === "home")
                        window.location.href = "${pageContext.request.contextPath}";
                    else
                        ajaxAllMessagesOfGroup();
                },
                error: errorHandler
            });
        }

        function generateUsersHref(id, name, race, age) {
            return "<a id=\"member-" + id + "\" href=\"${pageContext.request.contextPath}/persons/"
                + id + "\">" + name + " (" + race + "," + age + ")</a><br/>";
        }

        function joinGroup() {
            function errorHandler() {
                alert("Нельзя вступить в группу");
            }

            $.post("${pageContext.request.contextPath}/groups/${group.id}/join").success(function (response) {
                if (response === "fail")
                    errorHandler();
                else if (response === "home")
                    window.location.href = "${pageContext.request.contextPath}";
                else {
                    $('#members').append(generateUsersHref(response.id, response.name, response.race, response.age));
                    $('#l3').html("<button onclick=\"leaveGroup()\">Покинуть группу</button><br/>");
                }
            }).error(errorHandler);
        }

        function leaveGroup() {
            function errorHandler() {
                alert("Нельзя покинуть группу");
            }

            $.post("${pageContext.request.contextPath}/groups/${group.id}/leave").success(function (response) {
                if (response === "fail")
                    errorHandler();
                else if (response === "home")
                    window.location.href = "${pageContext.request.contextPath}";
                else {
                    $('#member-'.concat(response)).remove();
                    $('#l3').html("<button onclick=\"joinGroup()\">Вступить в группу</button><br/>");
                }
            }).error(errorHandler);
        }

        $(document).ready(function () {
            $('#send').click(sendMessage);
            setInterval(ajaxAllMessagesOfGroup, 500);
        });
    </script>
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">
    <img id=img1 src='<c:url value="/group-${group.id}/image"/>' >
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
                <c:if test="${!user.deleted}">
                    <a id="member-${user.id}"
                       href="<c:url value="/persons/${user.id}"/>">${user.name}(${user.race.name},${user.age})</a><br/>
                </c:if>
            </c:forEach>
        </div>
    </div>
    <div id="l6" style="overflow-y:auto; width: 30%; max-height: 50%;">
        <div id="messages" border="1" width="99%">
            <c:forEach items="${group.messages}" var="message">
                <div id="message-${message.id}">
                    <c:choose>
                        <c:when test="${!message.writer.deleted}">
                            <b><a href="<c:url value="/persons/${message.writer.id}"/>">${message.writer.name}</a> ${message.dateOfSubmition.format(formatter)}</b>:
                        </c:when>
                        <c:otherwise>
                            <b>DELETED ${message.dateOfSubmition.format(formatter)}</b>:
                        </c:otherwise>
                    </c:choose>
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
    <c:choose>
        <c:when test="${isMember}">
            <input type="submit" class="btn" id="send" value="Отправить"/>
        </c:when>
        <c:otherwise>
            <input type="submit" class="btn" id="send" disabled="true" value="Вступите в группу"/>
        </c:otherwise>
    </c:choose>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
