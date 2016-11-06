<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Topic was sent</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    </head>
    <body>
        </br>
        </br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.request_uri']=='/topic'}">
                        <h3><p class="text-center">Topic has been sent</p></h3>
                    </c:when>    
                    <c:otherwise>
                        <h3><p class="text-center">Queue has been sent</p></h3>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col-md-4"></div>
        </div>
        </br>
        </br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                ${success}
            </div>
            <div class="col-md-4"></div>

        </div>
        </br>
        </br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                        <a href='/topic'/><p class="text-center">Send topic</p></a>
                        <a href='/queue'/><p class="text-center">Send queue</p></a>
            </div>
            <div class="col-md-4"></div>
        </div>   
    </body>
</html>