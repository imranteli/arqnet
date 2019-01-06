<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href='http://localhost:8080/Spring-Monolithic/user/' >Get Users</a>

</br>
<body ng-app="myApp" class="ng-cloak">
<div ng-controller = "myCtrl">
    <input type="file" file-model="myFile"/>
    <button ng-click="uploadFile()">upload me</button>
</div>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/controller/fileUpload.js' />"></script>
      
      
      
