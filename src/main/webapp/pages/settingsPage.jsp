<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<spring:url value="/pass.json" var="passFormJsonUrl" />
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
           enctype="multipart/form-data" id="passForm">
    <div id="window" height="500px">
        <!-- Крестик-->
        <span class="close" onclick="show('none')">X</span>

        <label>Старый пароль</label>
        <spring:bind path="oldPasswd">
            <div>
                <div class="pass-validation" id="oldPasswdValidation">
                    <form:input class="form-control" type="password" path="oldPasswd"/><span></span>
                    <div class="error-message"><form:errors path="oldPasswd"/></div>
                </div>
            </div>
        </spring:bind>

        <label>Новый пароль</label>
        <spring:bind path="newPasswd">
            <div>
                <div class="pass-validation" id="newPasswdValidation">
                    <form:input class="form-control" type="password" path="newPasswd"/><span></span>
                    <div class="error-message"><form:errors path="newPasswd"/></div>
                </div>
            </div>
        </spring:bind>

        <label>Подтверждение пароля</label>
        <spring:bind path="acceptPasswd">
            <div>
                <div class="pass-validation" id="acceptPasswdValidation">
                    <form:input class="form-control" type="password" path="acceptPasswd"/><span></span>
                    <div class="error-message"><form:errors path="acceptPasswd"/></div>
                </div>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="savePasswd" align="right" > Сохранить </button>
        <button class="btn btn-primary" id="cancelPass" onclick="show('none')" align="left"  > Отмена</button>
    </div>
</form:form>

<div id="language">
<label>Здесь может быть изменение языка</label>
</div>
<div id="passButton">
<button type="submit" class="btn" id="changePass" onclick="show('block')" align="center"> Изменить пароль </button>
</div>
<form:form method="post" modelAttribute="changeForm">
    <div id="chLoginLb">
    <label>Логин</label>
    </div>
    <spring:bind path="login">
        <div id="chLoginIn">
            <form:input class="form-control" path="login" value="${person.login}"/><span></span>
            <form:errors path="login"/>
        </div>
    </spring:bind>

    <div id="chNameLb">
    <label>Имя</label>
    </div>
    <spring:bind path="name">
        <div id="chNameIn">
            <form:input class="form-control" path="name" value="${person.name}"/><span></span>
            <form:errors path="name"/>
        </div>
    </spring:bind>

    <div id="chRaceLb">
    <label>Раса</label>
    </div>
    <spring:bind path="race">
        <div id="racesIn">
        <form:input path="race" list="races" value="${person.race.name}"/>
            <datalist id="racesIn" >
                <c:forEach items="${races}" var="race">
                    <option value="${race}"></option>
                </c:forEach>
            </datalist>
        <form:errors path="race" cssClass="error"/>
        </div>
    </spring:bind>

    <div id="chAgeLb">
    <label>Возраст</label>
    </div>
    <spring:bind path="age">
        <div id="chAgeIn">
            <form:input class="form-control" path="age" value="${person.age}"/><span></span>
            <form:errors path="age"/>
        </div>
    </spring:bind>
    <div id="chSexLb">
    <label>Пол</label>
    </div>
    <spring:bind path="sex">
        <div id="chSexIn">
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

<script type="text/javascript">
    function collectFormData(fields) {
        var data = {};
        for (var i = 0; i < fields.length; i++) {
            var $item = $(fields[i]);
            data[$item.attr('name')] = $item.val();
        }
        return data;
    }

    $(document).ready(function() {
        var $formPass = $('#passForm');
        $formPass.bind('submit', function(e) {
            // Ajax validation
            var $inputs = $formPass.find('input');
            var data = collectFormData($inputs);

            $.post('${passFormJsonUrl}', data, function(response) {
                $formPass.find('.pass-validation').removeClass('error');
                $formPass.find('.error-message').empty();

                if (response.status == 'FAIL') {
                    for (var i = 0; i < response.errorMessageList.length; i++) {
                        var item = response.errorMessageList[i];
                        var $controlGroup = $('#' + item.fieldName + 'Validation');
                        $controlGroup.addClass('error');
                        $controlGroup.find('.error-message').html(item.message);
                    }
                } else {
                    $formPass.unbind('submit');
                    $formPass.submit();
                }
            }, 'json');

            e.preventDefault();
            return false;
        });
    });

</script>