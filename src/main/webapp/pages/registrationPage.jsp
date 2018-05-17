<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title> Registration</title>
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
        <label>Login</label>
        </div>
        <spring:bind path="login">
            <div id="loginRegIn">
                <form:input class="form-control" path="login" placeholder="Login"/><span></span>
                <form:errors path="login" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="nameRegLb">
        <label>Name</label>
        </div>
        <spring:bind path="name">
            <div id="nameRegIn">
                <form:input class="form-control" path="name" placeholder="Name"/><span></span>
                <form:errors path="name" cssClass="error"/>
            </div>
        </spring:bind>
        <div id="raceRegLb">
        <label>Race</label>
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
        <label>Age</label>
        </div>
        <spring:bind path="age">
            <div id="ageRegIn">
                <form:input class="form-control" path="age" placeholder="Age"/><span></span>
                <form:errors path="age" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="sexRegLb">
        <label>Sex</label>
        </div>
        <spring:bind path="sex">
            <div id="sexRegIn" >
                <form:input class="form-control" path="sex" placeholder="Sex"/><span></span>
                <form:errors path="sex" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="passRegLb">
        <label>Password</label>
        </div>
        <spring:bind path="pass">
            <div id="passRegIn">
                <form:input class="form-control" type="password" path="pass" placeholder="Password"/><span></span>
                <form:errors path="pass" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="passAcRegLb">
        <label>Confirm</label>
        </div>
        <spring:bind path="passAccept">
            <div id="passAcRegIn">
                <form:input class="form-control" type="password" path="passAccept"
                            placeholder="Confirm"/><span></span>
                <form:errors path="passAccept" cssClass="error"/>
            </div>
        </spring:bind>

        <div id="photoReg">
            <label>Picture</label>
        </div>
        <spring:bind path="image">
            <div  id="photoRegIn">
                <form:input type="file" class="form-control" path="image"/><span></span>
                <form:errors path="image" cssClass="error"/>
            </div>
        </spring:bind>

        <button type="submit" class="btn btn-primary" id="reg"> Registrate</button>
    </form:form>
</div>
<a href="javascript:history.back()" title="Отмена"><button class="btn btn-primary"  align="left" id="cancelReg">Cancel</button></a>

<%@include file="/resources/templates/footer.jsp" %>
</body>

</html>
