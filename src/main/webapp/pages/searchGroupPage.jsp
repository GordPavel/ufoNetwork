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
    <div id="tableGR1">
        <table>
            <tr>
                <td align='left'>Создатель: ${name}</td>
            </tr>
            <tr>
                <td align='left'>Название:${ownerName}</td>
            </tr>
        </table>
    <div id="tableGr2" >
        <label id="tg">Список групп</label>
        <div class="list-group" >
            <c:forEach items="${groups}" var="group">
                <a href="<c:url value="/search/${group.id}"/>" class="list-group-item">${group.name}</a>
            </c:forEach>
        </div>
    </div>

    <a href="${pageContext.request.contextPath}/" title="Back">Вернуться</a>

</div>


<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>