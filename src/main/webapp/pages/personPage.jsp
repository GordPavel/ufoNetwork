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
    <div onclick="show('none')" id="wrap" ></div>
    <c:url value="/groups/create" var="creationUrl"  />
    <form:form method="post" action="${creationUrl}" modelAttribute="groupCreateForm"
               enctype="multipart/form-data" id="groupCreateForm">
        <div id="window">
            <!-- Крестик-->
            <span class="close" onclick="show('none')">X</span>

            <label>Название</label>
            <spring:bind path="name">
            <div class="group-create-validation" id="nameGroupCreateValidation">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input class="form-control" path="name" placeholder="Имя"/><span></span>
                    <div class="error-message"><form:errors path="name" cssClass="error"/></div>
                </div>
            </div>
            </spring:bind>

            <label>Фотография</label>
            <spring:bind path="image">
            <div class="group-create-validation" id="imageGroupCreateValidation">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input type="file" class="form-control" path="image"/><span></span>
                    <div class="error-message"><form:errors path="image" cssClass="error"/></div>
                </div>
            </div>
            </spring:bind>

            <button class="btn btn-primary" onclick="show('none')" align="left"  form=""> Отмена</button>
            <button type="submit" class="btn btn-primary" id="right"> Создать</button>
        </div>
    </form:form>
    <div id="img3">
        <img src='<c:url value="/user-${person.id}/image"/>' style="width:100px;height:100px;">
    </div>
    <div id="table1" border="1" width="99%">
        <label id="t1">Список групп</label>
        <div class="list-group" style="overflow-y:auto; width: 180px; max-height: 200px;" >
            <div border="1" width="99%">
            <c:forEach items="${person.groups}" var="group">
                <a href="<c:url value="/groups/${group.id}"/>" class="list-group-item">${group.name}</a>
            </c:forEach>
        </div>
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
                        <a href="/groups/create"> <button class="btn"  id="createGroup">Создать группу</button></a>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>

<%--<script type="text/javascript">--%>
    <%--function collectFormData(fields) {--%>
        <%--var data = {};--%>
        <%--for (var i = 0; i < fields.length; i++) {--%>
            <%--var $item = $(fields[i]);--%>
            <%--data[$item.attr('name')] = $item.val();--%>
        <%--}--%>
        <%--return data;--%>
    <%--}--%>

    <%--$(document).ready(function() {--%>
        <%--var $formGroup = $('#groupCreateForm');--%>
        <%--$formGroup.bind('submit', function(e) {--%>
            <%--// Ajax validation--%>
            <%--var $inputs = $formGroup.find('input');--%>
            <%--var data = collectFormData($inputs);--%>

            <%--$.post('${groupCreateFormJsonUrl}', data, function(response) {--%>
                <%--$formGroup.find('.group-create-validation').removeClass('error');--%>
                <%--$formGroup.find('.error-message').empty();--%>

                <%--if (response.status == 'FAIL') {--%>
                    <%--for (var i = 0; i < response.errorMessageList.length; i++) {--%>
                        <%--var item = response.errorMessageList[i];--%>
                        <%--var $controlGroup = $('#' + item.fieldName + 'GroupCreateValidation');--%>
                        <%--$controlGroup.addClass('error');--%>
                        <%--$controlGroup.find('.error-message').html(item.message);--%>
                    <%--}--%>
                <%--} else {--%>
                    <%--$formGroup.unbind('submit');--%>
                    <%--$formGroup.submit();--%>
                <%--}--%>
            <%--}, 'json');--%>

            <%--e.preventDefault();--%>
            <%--return false;--%>
        <%--});--%>
        <%--function beforeSubmit(arr, $formGroup, options){--%>
            <%--var fileDataIndex = -1;--%>

            <%--$.each(arr, function(index, value) {--%>
                <%--if (value.name == "image"){--%>
                    <%--if (value.value.length == 0){--%>
                        <%--fileDataIndex = index;--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>

            <%--if (fileDataIndex != -1){--%>
                <%--arr.remove(fileDataIndex);--%>
            <%--}--%>
        <%--}--%>
    <%--});--%>



<%--</script>--%>