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
    <label>Search criteria</label>
    </div>
    <div id="tableUS1">
        <table>
            <tr>
                <td align='left'>Name: ${not empty name ? name:"<i>Not specified</i>"}</td>
            </tr>
            <tr>
                <td align='left'>Race: ${not empty race ? race:"<i>Not specified</i>"}</td>
            </tr>
            <tr>
                <td align='left'>Age: ${not empty ageFrom ? "from ":"от <i>Not specified</i>"} ${ageFrom}
                                                      ${not empty ageTo ? " to":" до <i>Not specified</i>"} ${ageTo}
            </tr>
            <tr>
                <td align='left'>Sex: ${not empty sex ? sex:"<i>Not specified</i>"}</td>
            </tr>
        </table>
    <div id="tableUS2" >
        <div id="listUS">
        <label>Users</label>
        </div>
        <div class="list-group" style="overflow-y:auto; width: 300px; max-height: 500px;" >
            <div border="1" width="99%">
            <c:forEach items="${persons}" var="person">
                <a href="<c:url value="/persons/${person.id}"/>" class="list-group-item">${person.name}</a>
            </c:forEach>
            </div>
        </div>
    </div>

    <a href="javascript:history.back()" title="Back" id="back">Back</a>
</div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
