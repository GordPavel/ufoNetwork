<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="/resources/templates/includes.jsp"/>
    <title> Профиль пользователя</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
</head>

<body>
<%@include file="/resources/templates/header.jsp" %>
<div class="content">
    <div id="img3"><img src='images\u157.png'></div>
    <div id="table1">
        <label id="t1">Список групп</label>
        <div class="list-group">
            <a href="#" class="list-group-item">Группа1</a>
            <a href="#" class="list-group-item">Группа2</a>
            <a href="#" class="list-group-item">Группа3</a>
        </div>

    </div>
    <div id="name7">
        <p>Имя:</p>
    </div>
    <div id="table2">
        <table>
            <tr>
                <th align='center'>Личные данные</th>
            </tr> <!--ряд с ячейками заголовков-->
            <tr>
                <td align='left'>Возраст:</td>
            </tr>
            <tr>
                <td align='left'>Пол:</td>
            </tr>
            <tr>
                <td align='left'>Раса:</td>
            </tr>          <!--ряд с ячейками тела таблицы-->
        </table>
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
    </div>
</div>
<%@include file="/resources/templates/footer.jsp" %>
</body>
</html>