<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bootstrap.min.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/1.css"/>"/>
    <title> Страничка входа</title>
    <script type="text/javascript">
        function show(state) {
            document.getElementById('window').style.display = state;
            document.getElementById('wrap').style.display = state;
        }
    </script>
</head>
<body>
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

<div class="content">
    <div onclick="show('none')" id="wrap"></div>
    <!-- Всплывающее окно-->
    <%--todo Окно регистрации--%>
    <div id="window">
        <!-- Крестик-->
        <span class="close" onclick="show('none')">X</span>
        <p>Логин:
        <p></p><input name="user"></p>
        <p>Имя:
        <p></p><input name="nam"></p>
        <p>Раса:
        <p></p><input name="race"></p>
        <p>Возраст:
        <p></p><input name="age"></p>
        <p>Пол:
        <p></p><input name="sex"></p>
        <p>Пароль:
        <p></p><input name="pass" type="password"></p>
        <p>Подтверждение пароля:
        <p></p><input name="sub_pass" type="password"></p>
        <p> &nbsp; </p>
        <p> &nbsp; </p>
        <button class="btn" onclick="show('none')" align="left"> Отмена</button>
        <button class="btn" onclick="" id="right"> Зарегистрироваться</button>
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
    <form:form method="post" id="login" autocomplete="on">
        <p>
            <ins>Логин:</ins>
        <p> &nbsp; </p> <input name="user"/>
        <p>
            <ins>Пароль:</ins>
        <p> &nbsp; </p> <input name="pass" type="password"/>
        <p> &nbsp; </p>
        <button class="btn" type="submit" id="enter"> Войти</button>
        <p> &nbsp; </p>
        <p> &nbsp; </p>
    </form:form>
    <!--Кнопка-->
    <button class="btn" id="register" onclick="show('block')" align="center"> Зарегистрироваться</button>

    <!--<p><input type="submit" value="Войти"></p>-->
</div>


<div class="footer">
    <div id="rectangle"></div>
    <label id="t1">SpaceNet © 2018</label>
    <label id="t2">О проекте |</label>
    <label id="t3">О разработчиках</label>
</div>
</body>
</html>