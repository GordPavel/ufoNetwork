<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<script type="text/javascript">
    function show(state) {
        document.getElementById('window').style.display = state;
        document.getElementById('wrap').style.display = state;
    }
</script>
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
    <div onclick="show('none')" id="wrap" ></div>
    <c:url value="/groups/create" var="creationUrl"  />
    <form:form method="post" action="${creationUrl}" modelAttribute="groupCreateForm"
               enctype="multipart/form-data">
        <div id="window">
            <!-- Крестик-->
            <span class="close" onclick="show('none')">X</span>

            <label>Название</label>
            <spring:bind path="name">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="name" placeholder="Имя"/><span></span>
                    <form:errors path="name" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Фотография</label>
            <spring:bind path="image">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="file" class="form-control" path="image"/><span></span>
                    <form:errors path="image" cssClass="error"/>
                </div>
            </spring:bind>

            <button class="btn btn-primary" onclick="show('none')" align="left"  form=""> Отмена</button>
            <button type="submit" class="btn btn-primary" id="right"> Создать</button>
        </div>
    </form:form>
    <div id="img3">
        <img src='<c:url value="/user-${person.id}/image"/>' style="width:100px;height:100px;">
    </div>

    <div id="table1" >
        <label id="t1">Список групп</label>
        <div class="list-group" >
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
                <td align='left'>Раса:${person.race.name}</td>
            </tr>          <!--ряд с ячейками тела таблицы-->
            <tr>
                <td align="left">
                    <c:if test="${person.id.toString().equals(cookie['userID'].value)}">
                        <button class="btn" onclick="show('block')" align="center"> Создать группу</button>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>