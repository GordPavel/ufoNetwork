<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title>Настройки</title>
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">
    <label>Критерии поиска</label>
    <div id="tableUS1">
        <table>
            <tr>
                <td align='left'>Имя: ${}</td>
            </tr>
            <tr>
                <td align='left'>Раса:${}</td>
            </tr>
            <tr>
                <td align='left'>Возрастной диапазон:${}</td>
            </tr>
            <tr>
                <td align='left'>Пол:${}</td>
            </tr>
        </table>
    <div id="tableUS2" >
        <label id="tg">Список пользователей</label>
        <div class="list-group" >
            <c:forEach items="${persons}" var="person">
                <a href="<c:url value="/search/${person.id}"/>" class="list-group-item">${person.name}</a>
            </c:forEach>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/" title="Back">Вернуться</a>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
