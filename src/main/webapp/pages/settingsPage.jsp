<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title>Настройки</title>
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
<div onclick="show('none')" id="wrap"></div>

<c:url value="/pass" var="passUrl"/>
<form:form method="post" action="${passUrl}" modelAttribute="changePasswdForm"
           enctype="multipart/form-data">
    <div id="window">
        <!-- Крестик-->
        <span class="close" onclick="show('none')">X</span>

        <label>Старый пароль</label>
        <spring:bind path="oldPasswd">
            <div>
                <form:input class="form-control" type="password" path="oldPasswd"/><span></span>
                <form:errors path="oldPasswd"/>
            </div>
        </spring:bind>

        <label>Новый пароль</label>
        <spring:bind path="newPasswd">
            <div>
                <form:input class="form-control" type="password" path="newPasswd"/><span></span>
                <form:errors path="newPasswd"/>
            </div>
        </spring:bind>

        <label>Подтверждение пароля</label>
        <spring:bind path="acceptPasswd">
            <div>
                <form:input class="form-control" type="password" path="acceptPasswd"/><span></span>
                <form:errors path="acceptPasswd"/>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="savePasswd" align="right" > Сохранить </button>
        <button class="btn btn-primary" onclick="show('none')" align="left" > Отмена</button>
    </div>
</form:form>

<label>Здесь может быть изменение языка</label>
<div></div>

<button type="submit" class="btn" id="changePass" onclick="show('block')" align="center"> Изменить пароль </button>

<form:form method="post" modelAttribute="changeForm">

    <label>Логин</label>
    <spring:bind path="login">
        <div>
            <form:input class="form-control" path="login" value="${person.login}"/><span></span>
            <form:errors path="login"/>
        </div>
    </spring:bind>

    <label>Имя</label>
    <spring:bind path="name">
        <div>
            <form:input class="form-control" path="name" value="${person.name}"/><span></span>
            <form:errors path="name"/>
        </div>
    </spring:bind>

    <label>Раса</label>
    <spring:bind path="race">
        <form:input path="race" list="races" value="${person.race.name}"/>
            <datalist id="races" >
                <c:forEach items="${races}" var="race">
                    <option value="${race}"></option>
                </c:forEach>
            </datalist>
        <form:errors path="race" cssClass="error"/>
    </spring:bind>

    <label>Возраст</label>
    <spring:bind path="age">
        <div>
            <form:input class="form-control" path="age" value="${person.age}"/><span></span>
            <form:errors path="age"/>
        </div>
    </spring:bind>

    <label>Пол</label>
    <spring:bind path="sex">
        <div>
            <form:input class="form-control" path="sex" value="${person.sex}"/><span></span>
            <form:errors path="sex"/>
        </div>
    </spring:bind>
    <button type="submit" class="btn" id="saveChanges"> Сохранить изменения </button>
</form:form>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>



</html>
