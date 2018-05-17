<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title> Entrance</title>
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
    <c:url value="/registration" var="registrationUrl"/>
    <form:form method="post" action="${registrationUrl}" modelAttribute="registrationForm"
               enctype="multipart/form-data">
        <div id="window">
            <!-- Крестик-->
            <span class="close" onclick="show('none')">X</span>

            <label>Login</label>
            <spring:bind path="login">
                <div>
                    <form:input class="form-control" path="login" placeholder="Login"/><span></span>
                    <form:errors path="login" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Name</label>
            <spring:bind path="name">
                <div>
                    <form:input class="form-control" path="name" placeholder="Name"/><span></span>
                    <form:errors path="name" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Race</label>
            <spring:bind path="race">
                <form:input path="race" list="races"/>
                <datalist id="races">
                    <c:forEach items="${races}" var="race">
                        <option value="${race}"></option>
                    </c:forEach>
                </datalist>
                <form:errors path="race" cssClass="error"/>
            </spring:bind>

            <label>Age</label>
            <spring:bind path="age">
                <div>
                    <form:input class="form-control" path="age" placeholder="Age"/><span></span>
                    <form:errors path="age" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Sex</label>
            <spring:bind path="sex">
                <div>
                    <form:input class="form-control" path="sex" placeholder="Sex"/><span></span>
                    <form:errors path="sex" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Password</label>
            <spring:bind path="pass">
                <div>
                    <form:input class="form-control" type="password" path="pass" placeholder="Password"/><span></span>
                    <form:errors path="pass" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Confirm</label>
            <spring:bind path="passAccept">
                <div>
                    <form:input class="form-control" type="password" path="passAccept"
                                placeholder="Confirm"/><span></span>
                    <form:errors path="passAccept" cssClass="error"/>
                </div>
            </spring:bind>

            <label>Picture</label>
            <spring:bind path="image">
                <div>
                    <form:input type="file" class="form-control" path="image"/><span></span>
                    <form:errors path="image" cssClass="error"/>
                </div>
            </spring:bind>

            <button class="btn btn-primary" onclick="show('none')" align="left" form=""> Cancel</button>
            <button type="submit" class="btn btn-primary" id="right"> Registration</button>
        </div>
    </form:form>
    <div>
    <form:form method="post" modelAttribute="loginForm">
        <spring:bind path="login">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label" id="loginLb">Login</label>
                <div class="col-sm-10"  id="loginIn">
                    <form:input path="login" class="form-control"
                                id="name" placeholder="Login"/>
                    <form:errors path="login" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <spring:bind path="pass">
            <div class="form-group ${status.error ? 'has-error' : ''}"  align="centre"  >
                <label class="col-sm-2 control-label" id="passLb">Password</label>
                <div class="col-sm-10" id="passIn">
                    <form:password path="pass" class="form-control"
                                   id="name" placeholder="Password"/>
                    <form:errors path="pass" class="control-label"/>
                </div>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="enter"  > Login</button>
    </form:form>
</div>
    <a href="registration"> <button class="btn" id="register">Registration</button></a>
</div>

<%@include file="/resources/templates/footer.jsp" %>

</body>
</html>