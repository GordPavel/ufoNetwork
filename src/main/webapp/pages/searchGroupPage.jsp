<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title>Результаты поиска</title>
</head>
<body>
<%@include file="/resources/templates/header.jsp" %>

<div class="content">
    <div id="cryteriaGR">
    <label>Критерии поиска</label>
    </div>
    <div id="tableGR1">
        <table>
            <tr>
                <td align='left'>Создатель: ${ownerName}</td>
            </tr>
            <tr>
                <td align='left'>Название:${name}</td>
            </tr>
        </table>
    <div id="tableGr2" >
        <div id="listGR">
        <label>Список групп</label>
        </div>
        <div class="list-group" >
            <c:forEach items="${groups}" var="group">
                <a href="<c:url value="/groups/${group.id}"/>" class="list-group-item">${group.name}</a>
            </c:forEach>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/" title="Back" id="back">Вернуться</a>
</div>
</div>

<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>