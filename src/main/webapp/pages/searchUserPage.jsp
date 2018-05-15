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
    <div id="cryteriaUS">
    <label>Критерии поиска</label>
    </div>
    <div id="tableUS1">
        <table>
            <tr>
                <td align='left'>Имя: ${not empty name ? name:"<i>Не задано</i>"}</td>
            </tr>
            <tr>
                <td align='left'>Раса: ${not empty race ? race:"<i>Не задано</i>"}</td>
            </tr>
            <tr>
                <td align='left'>Возрастной диапазон: ${not empty ageFrom ? "от ":"от <i>Не задано</i>"} ${ageFrom}
                                                      ${not empty ageTo ? " до":" до <i>Не задано</i>"} ${ageTo}
            </tr>
            <tr>
                <td align='left'>Пол: ${not empty sex ? sex:"<i>Не задано</i>"}</td>
            </tr>
        </table>
    <div id="tableUS2" >
        <div id="listUS">
        <label>Список пользователей</label>
        </div>
        <div class="list-group" style="overflow-y:auto; width: 300px; max-height: 500px;" >
            <div border="1" width="99%">
            <c:forEach items="${persons}" var="person">
                <a href="<c:url value="/persons/${person.id}"/>" class="list-group-item">${person.name}</a>
            </c:forEach>
            </div>
        </div>
    </div>

    <a href="javascript:history.back()" title="Back" id="back">Вернуться</a>
</div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
