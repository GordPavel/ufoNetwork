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
            <tr>
                <td>
                    <c:url value="/groups/joinfew" var="joinUrl"/>
                    <form:form method="post" action="${joinUrl}" id="joinFewForm">
                        <input type = "submit" value = "Вступить в выбранные" />
                    </form:form>
                </td>
            </tr>
        </table>
    <div id="tableGr2" >
        <div id="listGR">
        <label>Список групп</label>
        </div>
        <div class="list-group" style="overflow-y:auto; width: 300px; max-height: 500px;" >
            <div border="1" width="99%">
            <c:forEach items="${groups}" var="group">
                <c:set var="contains" value="false" />
                <c:forEach var="item" items="${group.users}">
                    <c:if test="${item.id eq cookie['userID'].value}">
                        <c:set var="contains" value="true" />
                    </c:if>
                </c:forEach>
                <a href="<c:url value="/groups/${group.id}"/>" class="list-group-item"><input type="checkbox" form="joinFewForm" name="checkboxs" value="${group.id}" ${contains == 'true' ? 'hidden="true" >  <font size="-5"><i>(Участник)</i></font>' : ' >'} ${group.name}</a>
            </c:forEach>
            </div>
        </div>
    </div>

    <a href="javascript:history.back()" title="Back" id="back">Вернуться</a>
</div>
</div>
<%--<script>--%>
    <%--var join=[];--%>
    <%--var leave=[];--%>
    <%--function change(id) {--%>
        <%--if (document.getElementById('group-'+id).checked==true) {--%>
            <%--join.push(id);--%>
        <%--} else {--%>
            <%--leave.push(id);--%>
        <%--}--%>
    <%--}--%>

    <%--function button() {--%>
        <%--$.post("${pageContext.request.contextPath}/groups/join-leave",{join:join, leave:leave}).success(function (response) {--%>
        <%--}).error(errorHandler);--%>
    <%--};--%>

<%--</script>--%>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>