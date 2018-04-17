<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title> Профиль пользователя</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
    <%--@elvariable id="person" type="com.netcracker.DAO.PersonEntity"--%>
</head>

<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">
    <div id="img3">
        <img src='<c:url value="/user-${person.id}/image"/>' style="width:100px;height:100px;">
    </div>
    <div id="table1">
        <label id="t1">Список групп</label>
        <div class="list-group">
            <c:forEach items="${person.groups}" var="group">
                <a href="<c:url value="/groups/${group.id}"/>" class="list-group-item">${group.name}</a>
            </c:forEach>
        </div>
    </div>
    <div id="name7">
        <p>Имя: ${person.name}</p>
    </div>
    <div id="table2">
        <table>
            <tr>
                <th align='center'>Личные данные</th>
            </tr> <!--ряд с ячейками заголовков-->
            <tr>
                <td align='left'>Возраст: ${person.age}</td>
            </tr>
            <tr>
                <td align='left'>Пол:${person.sex}</td>
            </tr>
            <tr>
                <%--TODO: название рассы = ссылка на поиск по этой рассе--%>
                <td align='left'>Раса:${person.race.name}</td>
            </tr>          <!--ряд с ячейками тела таблицы-->
        </table>
    </div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>