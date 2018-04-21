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
    <%
        boolean member = false;
    %>
    <script>
        function generateMessage(id, text, date, writerId, writerName) {
            return "<div id=\"message-" + id + "\">\n" +
                "                    <b>" + writerName + " " + date + "</b>:\n" +
                (writerId === ${cookie.userID.value} || ${group.owner.id} === ${cookie.userID.value} ?
                    "                        <button class=\"btn btn-primary deleteMessage\" onclick=\"deleteMessage(" + id + ")\" value=\"Удалить\"></button>" : "") +

                "                    <br>" + text + "\n" +
                "                </div>";
        }

        function ajaxAllMessagesOfGroup() {
            var errorHandler = function () {
                alert('Сообщения не может быть получены')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/" + ${group.id} +"/messages",
                type: 'GET',
                timeout: 60 * 1000,
                success: function (response) {
                    var divs = response.map(function (message) {
                        return generateMessage(message.id, message.text, message.date, message.writerId, message.writerName);
                    }).join('\n');
                    $('#messages').html(divs);
                },
                error: errorHandler
            });
        }

        function schelduleAjax() {
            ajaxAllMessagesOfGroup();
            setInterval(schelduleAjax, 10 * 1000);
        }

        function sendMessage() {
            var data = JSON.stringify({
                messageText: $('#textarea2').val(),
            });
            var errorHandler = function () {
                alert('Сообщение не может быть отправлено')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/" + ${group.id} +"/message",
                type: 'POST',
                timeout: 5 * 1000,
                contentType: "application/json",
                data: data,
                success: function (response) {
                    if (response === 'fail') {
                        errorHandler();
                    } else {
                        $('#textarea2').val("");
                    }
                    ajaxAllMessagesOfGroup();
                },
                error: errorHandler
            });
        }

        function deleteMessage(messageId) {
            var errorHandler = function () {
                alert('Сообщение не может быть удалено')
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/groups/message-".concat(messageId.toString()),
                type: 'DELETE',
                timeout: 1000 * 5,
                success: function (response) {
                    if ("success" !== response) {
                        errorHandler();
                    }
                    ajaxAllMessagesOfGroup();
                },
                error: errorHandler
            });
        }

        $(document).ready(function () {
            $('#send').click(sendMessage);
            setInterval(schelduleAjax, 10 * 1000);
        });
    </script>
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">

    <div id="img1"><img src='<c:url value="/group-${group.id}/image"/>'></div>
    <div id="img2"><img src='/resources/images/chat-104.png' src='<c:url value="/user-${person.id}/image"/>' width="32"
                        height="32"></div>
    <div id="l1">
        <table>
            <tr>
                <th align='center'>${group.name}
                    <c:if test="${group.owner.id.toString().equals(cookie[\"userID\"].value)}">
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
        <c:forEach items="${group.users}" var="user">
            <c:if test="${user.id.toString().equals(cookie[\"userID\"].value)}">
                <%
                    member = true;
                %>
            </c:if>
        </c:forEach>
        <c:choose>
            <c:when test="<%=member%>">
                <a href="<c:url value="/groups/${group.id}/leave"/>">Покинуть группу</a><br/>
            </c:when>
            <c:otherwise>
                <a href="<c:url value="/groups/${group.id}/join"/>">Вступить в группу</a><br/>
            </c:otherwise>
        </c:choose>
        <label>Список участников</label>
    </div>
    <div id="l2" style="overflow:auto; width: 30%; max-height: 20%;">
        <div class="list-group">
            <c:forEach items="${group.users}" var="user">
                <a href="<c:url value="/persons/${user.id}"/>" style="">${user.name} (${user.race.name},${user.age})</a>
                <br/>
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
