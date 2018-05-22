<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    function show(state) {
        document.getElementById('window').style.display = state;
        document.getElementById('wrap').style.display = state;
    }
</script>
<div class="footer">
    <div id="rectangle"></div>
    <label id="t1" style="color: black">SpaceNet © 2018</label>
    <label id="t2" style="color: black">About project |</label>
    <label id="t3"><a href="#win6" style="color: black">About team</a></label>
    <a href="#x" class="overlay" id="win6"></a>
    <div class="popup" id="aboutTeam">

        <label>About team:</label>
        <p>
            <em>
                <div>
                    Pavel Gordeev
                </div>
                <div>
                    Arseniy Sobolev
                </div>
                <div>
                    Nina Ukhanova
                </div>
            </em>
        </p>
        <a class="close" title="Close" href="#close"></a>
    </div>
</div>