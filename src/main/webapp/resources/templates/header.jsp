<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>

<spring:url value="/groups/search.json" var="groupFormJsonUrl" />
<spring:url value="/persons/search.json" var="personFormJsonUrl" />


<div class="header">
    <div id="mainmenu">
        <ul class="nav navbar-nav">
            <img id="icon1" src='<c:url value="/resources/images/favicon.ico"/>' width='30'
                 height='30'
                 align='left'>
            <li class="dropdown"><a href="<c:url value="/persons/${cookie['userID'].value}"/>" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                My profile <span class="caret"></span></a>
                <ul class="dropdown-menu">
                     <li><a href= "<c:url value="/persons/${cookie['userID'].value}"/>">Page</a></li>
                     <li><a href= "<c:url value="/persons/${cookie['userID'].value}/settings"/>">Settings</a></li>
                </ul>
            </li>
            <li class="dropdown"><a href="#" data-toggle="dropdown"><span class="glyphicon glyphicon-search"></span>
                Search <span class="caret"></span></a>
                <ul class="dropdown-menu">
                   <li><a href="#win1">Groups</a></li>
                    <li><a href="#win2">Users</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<c:url value="/groups/search" var="srcgroupsUrl"/>
<form:form method="post" action="${srcgroupsUrl}" modelAttribute="searchGroupsForm"
           enctype="multipart/form-data" id="groupSearchForm">
    <a href="#x" class="overlay" id="win1"></a>
    <div class="popup"  id="searchGroups">
        <div id="name">
        <label>Name</label>
        </div>
        <spring:bind path="name">
            <div class="group-validation" id="nameGroupValidation">
                <form:input class="form-control" path="name" placeholder="Inser name"/><span></span>
                <div class="error-message"><form:errors path="name"/></div>
            </div>
        </spring:bind>
        <div id="owner">
        <label>Owner</label>
        </div>
        <spring:bind path="ownerName">
            <div class="group-validation" id="ownerNameGroupValidation">
                <form:input class="form-control" path="ownerName" placeholder="Inser owner name"/><span></span>
                <div class="error-message"><form:errors path="ownerName"/></div>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="searchGroupsBt"> Search </button>
        <a class="close" title="Close" href="#close"></a>
    </div>
</form:form>


<c:url value="/persons/search" var="srcpersonsUrl"/>
<form:form method="post" action="${srcpersonsUrl}" modelAttribute="searchPersonsForm"
           enctype="multipart/form-data" id="personSearchForm">
    <a href="#x" class="overlay" id="win2"></a>
    <div class="popup" id="searchUsers" >
        <div id="shLogin">
         <label>Name</label>
        </div>
      <spring:bind path="name">
          <div class="person-validation" id="namePersonValidation">
              <form:input class="form-control" path="name" placeholder="Insert name"/><span></span>
              <div class="error-message"><form:errors path="name"/></div>
          </div>
      </spring:bind>

    <div id="shRace">
      <label>Race</label>
    </div>
      <spring:bind path="race">
          <div class="person-validation" id="racePersonValidation">
          <form:input path="race" list="races" placeholder="Select race"/>
          <datalist id="races" >
              <c:forEach items="${races}" var="race">
                  <option value="${race}"></option>
              </c:forEach>
          </datalist>
          <div class="error-message"><form:errors path="race" cssClass="error"/></div>
          </div>
      </spring:bind>
    <div id="shAge">
    <label>Age</label>
    </div>
      <spring:bind path="ageFrom">
          <div class="person-validation" id="ageFromPersonValidation">
              <form:input class="form-control" path="ageFrom" placeholder="From"/><span></span>
              <div class="error-message"><form:errors path="ageFrom"/></div>
          </div>
      </spring:bind>

      <spring:bind path="ageTo">
          <div class="person-validation" id="ageToPersonValidation">
              <form:input class="form-control" path="ageTo" placeholder="To"/><span></span>
              <div class="error-message"><form:errors path="ageTo"/></div>
          </div>
      </spring:bind>
    <div id="shSex">
    <label>Sex</label>
    </div>
      <spring:bind path="sex">
          <div class="person-validation" id="sexPersonValidation">
              <form:input class="form-control" path="sex" placeholder="Inser sex"/><span></span>
              <div class="error-message"><form:errors path="sex"/></div>
          </div>
      </spring:bind>
      <button type="submit" class="btn" id="searchUsersBt"> Search </button>
      <a class="close" title="Close" href="#close"></a>
    </div>
</form:form>

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
        var $formGroup = $('#groupSearchForm');
        var $formPerson = $('#personSearchForm');
        $formGroup.bind('submit', function(e) {
            // Ajax validation
            var $inputs = $formGroup.find('input');
            var data = collectFormData($inputs);

            $.post('${groupFormJsonUrl}', data, function(response) {
                $formGroup.find('.group-validation').removeClass('error');
                $formGroup.find('.error-message').empty();

                if (response.status == 'FAIL') {
                    for (var i = 0; i < response.errorMessageList.length; i++) {
                        var item = response.errorMessageList[i];
                        var $controlGroup = $('#' + item.fieldName + 'GroupValidation');
                        $controlGroup.addClass('error');
                        $controlGroup.find('.error-message').html(item.message);
                    }
                } else {
                    $formGroup.unbind('submit');
                    $formGroup.submit();
                }
            }, 'json');

            e.preventDefault();
            return false;
        });
        $formPerson.bind('submit', function(e) {
            // Ajax validation
            var $inputs = $formPerson.find('input');
            var data = collectFormData($inputs);

            $.post('${personFormJsonUrl}', data, function(response) {
                $formPerson.find('.person-validation').removeClass('error');
                $formPerson.find('.error-message').empty();

                if (response.status == 'FAIL') {
                    for (var i = 0; i < response.errorMessageList.length; i++) {
                        var item = response.errorMessageList[i];
                        var $controlPerson = $('#' + item.fieldName + 'PersonValidation');
                        $controlPerson.addClass('error');
                        $controlPerson.find('.error-message').html(item.message);
                    }
                } else {
                    $formPerson.unbind('submit');
                    $formPerson.submit();
                }
            }, 'json');

            e.preventDefault();
            return false;
        });
    });

</script>