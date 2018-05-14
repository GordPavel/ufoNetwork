<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<spring:url value="/groups/create.json" var="groupCreateFormJsonUrl" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

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
    <c:url value="/groups/create" var="creationUrl"  />
    <form:form method="post" action="${creationUrl}" modelAttribute="groupCreateForm"
               enctype="multipart/form-data" id="groupCreateForm">

        <div id="nameGroup">
        <label>Название</label>
        </div>
        <spring:bind path="name">
            <div class="group-create-validation" id="nameGroupCreateValidation">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="name" placeholder="Имя"/><span></span>
                    <div class="error-message"><form:errors path="name" cssClass="error"/></div>
                </div>
            </div>
        </spring:bind>
        <div id="photoGroup">
        <label>Фотография</label>
        </div>
        <spring:bind path="image">
            <div class="group-create-validation" id="imageGroupCreateValidation">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="file" class="form-control" path="image"/><span></span>
                    <div class="error-message"><form:errors path="image" cssClass="error"/></div>
                </div>
            </div>
        </spring:bind>

        <button type="submit" class="btn btn-primary" id="right"> Создать</button>
    </div>
    </form:form>
<a href="javascript:history.back()" title="Отмена"><button class="btn btn-primary"  align="left"  id="cancelGroup">Отмена</button></a>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>
