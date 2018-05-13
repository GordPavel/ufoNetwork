<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title> Страничка входа</title>
    <script type="text/javascript">
        function show(state) {
            document.getElementById('window').style.display = state;
            document.getElementById('wrap').style.display = state;
        }
    </script>
</head>
<body>

<%@include file="/resources/templates/header.jsp" %>

<div class="content">

    <c:url value="/registration" var="registrationUrl"/>
    <form:form method="post" action="${registrationUrl}" modelAttribute="registrationForm"
               enctype="multipart/form-data">

        <div id="loginRegLb">
        <label>Логин</label>
        </div>
        <spring:bind path="login">
            <div id="loginRegIn">
                <form:input class="form-control" path="login" placeholder="Логин"/><span></span>
                <form:errors path="login" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="nameRegLb">
        <label>Имя</label>
        </div>
        <spring:bind path="name">
            <div id="nameRegIn">
                <form:input class="form-control" path="name" placeholder="Имя"/><span></span>
                <form:errors path="name" cssClass="error"/>
            </div>
        </spring:bind>
        <div id="raceRegLb">
        <label>Раса</label>
        </div>
        <spring:bind path="race">
            <div id="racesReg">
            <form:input path="race" list="races"/>
            <datalist id="races">
                <c:forEach items="${races}" var="race">
                    <option value="${race}"></option>
                </c:forEach>
            </datalist>
            <form:errors path="race" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="ageRegLb">
        <label>Возраст</label>
        </div>
        <spring:bind path="age">
            <div id="ageRegIn">
                <form:input class="form-control" path="age" placeholder="Возраст"/><span></span>
                <form:errors path="age" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="sexRegLb">
        <label>Пол</label>
        </div>
        <spring:bind path="sex">
            <div id="sexRegIn" >
                <form:input class="form-control" path="sex" placeholder="Пол"/><span></span>
                <form:errors path="sex" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="passRegLb">
        <label>Пароль</label>
        </div>
        <spring:bind path="pass">
            <div id="passRegIn">
                <form:input class="form-control" type="password" path="pass" placeholder="Пароль"/><span></span>
                <form:errors path="pass" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="passAcRegLb">
        <label>Подтверджение пароля</label>
        </div>
        <spring:bind path="passAccept">
            <div id="passAcRegIn">
                <form:input class="form-control" type="password" path="passAccept"
                            placeholder="Подтверждение"/><span></span>
                <form:errors path="passAccept" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="photoReg">
            <label>Фотография</label>
        </div>
        <spring:bind path="image">
            <div  id="photoRegIn">
                <form:input type="file" class="form-control" path="image"/><span></span>
                <form:errors path="image" cssClass="error"/>
            </div>
        </spring:bind>

        <button class="btn btn-primary"  align="left" id="cancelReg"> Отмена</button>
        <button type="submit" class="btn btn-primary" id="reg"> Зарегистрироваться</button>
    </div>
    </form:form>

    <%@include file="/resources/templates/footer.jsp" %>
</body>

</html>
