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
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">

    <div id="img1"><img src='/resources/images/u157.png'></div>
    <div id="img2"><img src='/resources/images/chat-104.png' width="32" height="32"></div>
    <div id="l1">
        <table>
            <tr>
                <th align='center'>${group.name}</th>
            </tr> <!--ряд с ячейками заголовков-->
            <tr>
                <td align='left'>Владелец: <a href="<c:url value="/persons/${group.owner.id}"/>">${group.owner.name}</a></td>
            </tr>
        </table>
    </div>
    <div id="l3">
        <label>Список участников</label>
    </div>
    <div id="l2" style="overflow:auto; width: 30%; max-height: 20%;">

        <div class="list-group">
            <c:forEach items="${group.users}" var="user">
                <a href="<c:url value="/persons/${user.id}"/>" class="list-group-item">${user.name}</a>
            </c:forEach>
        </div>
    </div>
    <div id="l6" style="overflow-y:auto; width: 30%; max-height: 50%;">
        <table border="1" width="99%">
            <c:forEach items="${group.messages}" var="message">
                <tr>
                    <th><b>${message.writer.name}</b>:<br/>${message.text}</th>
                </tr>
            </c:forEach>
        </table>

    </div>
    <textarea id="textarea2"></textarea>
    <button class="btn" id="send"> Отправить </button>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
