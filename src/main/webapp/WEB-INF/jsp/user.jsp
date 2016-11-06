<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page import="com.example.models.Topic"%>
<html lang="en" xmlns:th="http://www.springframework.org/schema/mvc">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>User Form</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    </head>
    <body>
        </br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="row">
                    <div class="col-md-12 text-center">
                        <div class="btn-group" role="group" aria-label="...">
                            <a href="/topic" class="btn btn-info" role="button">Topic</a>
                            <a href="/queue" class="btn btn-info" role="button">Queue</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4"></div>
        </div>
        </br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${requestScope['javax.servlet.forward.request_uri']=='/topic'}">
                        <h3><p class="text-center">JMS topic</p></h3>
                    </c:when>    
                    <c:otherwise>
                        <h3><p class="text-center">JMS queue</p></h3>
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
                <form:form method="POST" modelAttribute="user">
                    <div class="row">
                        <div class="input-group">
                            <span class="input-group-addon" id="basic-addon1">User name:</span>
                            <form:input type="text" path="name" id="name" required="required" class="form-control" aria-describedby="basic-addon1"/>
                        </div>
                        <div>
                            <form:errors path="email"/>
                        </div>
                    </div>
                    </br>
                    <div class="row">
                        <c:choose>
                            <c:when test="${requestScope['javax.servlet.forward.request_uri']=='/topic'}">
                                <div class="form-group text-center">
                                    <label class="text-center" for="sel1">Choose user category:</label>
                                    <select name="topic" class="form-control" id="sel1">
                                        <c:forEach items="<%=Topic.values()%>" var="entry">
                                            <option>${entry}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </c:when>    
                        </c:choose>
                        
                    </div>
                    <br>
                    <div class="row">
                        <div class="text-center">
                            <button type="submit" class="btn btn-default">Send user</button>
                        </div>
                    </div>    
                </form:form>
            </div>
            <div class="col-md-4"></div>
        </div>
    </body>
</html>