<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="header">
    <div id="mainmenu">
        <ul class="nav navbar-nav">
            <img id="icon1" src='<c:url value="/resources/images/favicon.ico"/>' width='30'
                 height='30'
                 align='left'>
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
<a href="#x" class="overlay" id="win1"></a>
<div class="popup" id="win3">
    <p>Название:
    <p></p><input name="name"></p>
    <p>Создатель:
    <p></p><input name="creator"></p>
    <p>Категории:</p>
    <p>Здесь может быть организован поиск по группам</p>
    <button class="btn" onclick=""> Поиск</button>
    <a class="close" title="Закрыть" href="#close"></a>
</div>
<a href="#x" class="overlay" id="win2"></a>
<div class="popup" id="win4">
    <p>Имя:
    <p></p><input name="nam"></p>
    <p>Раса:
    <p></p><input name="race"></p>
    <p>Возраст:
    <p></p><input name="age"></p>
    <p>Пол:
    <p></p><input name="sex"></p>
    <button class="btn" onclick=""> Поиск</button>
    <a class="close" title="Закрыть" href="#close"></a>
</div>