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
    <div onclick="show('none')" id="wrap"></div>

    <!-- Всплывающее окно-->
    <%--todo Окно регистрации--%>
    <c:url value="/registration" var="registrationUrl"/>
    <form:form method="post" action="${registrationUrl}" modelAttribute="registrationForm"
               enctype="multipart/form-data">
        <div id="window">
            <!-- Крестик-->
            <span class="close" onclick="show('none')">X</span>

            <label>Логин</label>
            <spring:bind path="login">
                <div>
                    <form:input class="form-control" path="login"/><span></span>
                    <form:errors path="login"/>
                </div>
            </spring:bind>

            <label>Имя</label>
            <spring:bind path="name">
                <div>
                    <form:input class="form-control" path="name"/><span></span>
                    <form:errors path="name"/>
                </div>
            </spring:bind>

            <label>Раса</label>
            <spring:bind path="race">
                <div>
                    <form:input class="form-control" path="race"/><span></span>
                    <form:errors path="race"/>
                </div>
            </spring:bind>

            <label>Возраст</label>
            <spring:bind path="age">
                <div>
                    <form:input class="form-control" path="age"/><span></span>
                    <form:errors path="age"/>
                </div>
            </spring:bind>

            <label>Пол</label>
            <spring:bind path="sex">
                <div>
                    <form:input class="form-control" path="sex"/><span></span>
                    <form:errors path="sex"/>
                </div>
            </spring:bind>

            <label>Пароль</label>
            <spring:bind path="pass">
                <div>
                    <form:input class="form-control" type="password" path="pass"/><span></span>
                    <form:errors path="pass"/>
                </div>
            </spring:bind>

            <label>Подтверджение пароля</label>
            <spring:bind path="passAccept">
                <div>
                    <form:input class="form-control" type="password" path="passAccept"/><span></span>
                    <form:errors path="passAccept"/>
                </div>
            </spring:bind>

            <label>Фотография</label>
            <spring:bind path="image">
                <div>
                    <form:input type="file" class="form-control" path="image"/><span></span>
                    <form:errors path="image"/>
                </div>
            </spring:bind>

            <button class="btn" onclick="show('none')" align="left"> Отмена</button>
            <button class="btn" id="right"> Зарегистрироваться</button>
        </div>
    </form:form>
    <form:form method="post" modelAttribute="loginForm">
        <spring:bind path="login">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Логин</label>
                <div class="col-sm-10">
                    <form:input path="login" class="form-control"
                                id="name" placeholder="Логин"/>
                    <form:errors path="login" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="pass">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label">Пароль</label>
                <div class="col-sm-10">
                    <form:password path="pass" class="form-control"
                                id="name" placeholder="Логин"/>
                    <form:errors path="pass" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="enter"> Войти </button>
    </form:form>
    <!--Кнопка-->
    <button class="btn" id="register" onclick="show('block')" align="center"> Зарегистрироваться</button>

    <!--<p><input type="submit" value="Войти"></p>-->
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>