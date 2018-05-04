<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div id="mainmenu">
        <ul class="nav navbar-nav">
            <img id="icon1" src='<c:url value="/resources/images/favicon.ico"/>' width='30'
                 height='30'
                 align='left'>
            <li class="dropdown"><a href="<c:url value="/persons/${person.id}"/>" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>
                Мой профиль <span class="caret"></span></a>
                <ul class="dropdown-menu">
                      <li><a href= "<c:url value="/persons/${person.id}/settings"/>">Настройки</a></li>
                </ul>
            </li>
            <li class="dropdown"><a href="#" data-toggle="dropdown"><span class="glyphicon glyphicon-search"></span>
                Поиск <span class="caret"></span></a>
                <ul class="dropdown-menu">
                   <li><a href="#win1">Группы</a></li>
                    <li><a href="#win2">Пользователи</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>

<c:url value="/srcgroups" var="srcgroupsUrl"/>
<form:form method="post" action="${srcgroupsUrl}" modelAttribute="searchGroupsForm"
           enctype="multipart/form-data">
    <a href="#x" class="overlay" id="win1"></a>
    <div class="popup" >
        <label>Название</label>
        <spring:bind path="name">
            <div>
                <form:input class="form-control" path="name" placeholder="Введите название"/><span></span>
                <form:errors path="name"/>
            </div>
        </spring:bind>

        <label>Создатель</label>
        <spring:bind path="ownerName">
            <div>
                <form:input class="form-control" path="ownerName" placeholder="Укажите создателя группы"/><span></span>
                <form:errors path="ownerName"/>
            </div>
        </spring:bind>
        <button type="submit" class="btn" id="searchGroups"> Поиск </button>
        <a class="close" title="Закрыть" href="#close"></a>
    </div>
</form:form>
<!--<a href="#x" class="overlay" id="win1"></a>
    <div class="popup" id="win3">
    <p>Название:
    <p></p><input name="name"></p>
    <p>Создатель:
    <p></p><input name="ownerName"></p>
    <p>Категории:</p>
    <p>Здесь может быть организован поиск по группам</p>
    <button class="btn" onclick=""> Поиск</button>
    <a class="close" title="Закрыть" href="#close"></a>
</div>-->


<c:url value="/srcpersons" var="srcpersonsUrl"/>
<form:form method="post" action="${srcpersonsUrl}" modelAttribute="searchPersonsForm"
           enctype="multipart/form-data">
    <a href="#x" class="overlay" id="win2"></a>
    <div class="popup" >
      <label>Имя</label>
      <spring:bind path="name">
          <div>
              <form:input class="form-control" path="name" placeholder="Введите имя"/><span></span>
              <form:errors path="name"/>
          </div>
      </spring:bind>

      <label>Раса</label>
      <spring:bind path="race">
          <form:input path="race" list="races" placeholder="Укажите расу"/>
          <datalist id="races" >
              <c:forEach items="${races}" var="race">
                  <option value="${race}"></option>
              </c:forEach>
          </datalist>
          <form:errors path="race" cssClass="error"/>
      </spring:bind>

      <label>Возрастной диапазон</label>
      <spring:bind path="ageFrom">
          <div>
              <form:input class="form-control" path="ageFrom" placeholder="От"/><span></span>
              <form:errors path="ageFrom"/>
          </div>
      </spring:bind>

      <spring:bind path="ageTo">
          <div>
              <form:input class="form-control" path="ageTo" placeholder="До"/><span></span>
              <form:errors path="ageTo"/>
          </div>
      </spring:bind>

      <label>Пол</label>
      <spring:bind path="sex">
          <div>
              <form:input class="form-control" path="sex" placeholder="Укажите пол"/><span></span>
              <form:errors path="sex"/>
          </div>
      </spring:bind>
      <button type="submit" class="btn" id="searchPersons"> Поиск </button>
      <a class="close" title="Закрыть" href="#close"></a>
    </div>
</form:form>

<!--<a href="#x" class="overlay" id="win2"></a>
<div class="popup" id="win4">
    <p>Имя:
    <p></p><input name="name"></p>
    <p>Раса:
    <p></p><input name="race"></p>
    <p>Возраст:
    <p></p><input name="age"></p>
    <p>Пол:
    <p></p><input name="sex"></p>
    <button class="btn" onclick=""> Поиск</button>
    <a class="close" title="Закрыть" href="#close"></a>
</div>-->