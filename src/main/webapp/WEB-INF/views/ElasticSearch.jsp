<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>  
    <title>AngularJS $http Example</title>  
    <style>
      .username.ng-valid {
          background-color: lightgreen;
      }
      .username.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .username.ng-dirty.ng-invalid-minlength {
          background-color: yellow;
      }

      .email.ng-valid {
          background-color: lightgreen;
      }
      .email.ng-dirty.ng-invalid-required {
          background-color: red;
      }
      .email.ng-dirty.ng-invalid-email {
          background-color: yellow;
      }

    </style>
     <link rel="stylesheet" href="<c:url value='/static/css/bootstrap.css' />">
     <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
  </head>
  <body ng-app="myApp" class="ng-cloak">
      <div class="generic-container" ng-controller="ElasticController as ctrl">
          <div class="panel panel-default">
              <div class="panel-heading"><span class="lead">User Registration Form </span></div>
              <div class="formcontainer">
                  <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                      <!-- <input type="hidden" ng-model="ctrl.product.id" /> -->
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">ID</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.product.id" name="id" class="username form-control input-sm" placeholder="Enter ID" required ng-minlength="4"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.id.$error.required">This is a required field</span>
                                      <span ng-show="myForm.id.$error.minlength">Minimum length required is 4</span>
                                      <span ng-show="myForm.id.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Title</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.product.title" name="title" class="username form-control input-sm" placeholder="Enter Title" required ng-minlength="3"/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.title.$error.required">This is a required field</span>
                                      <span ng-show="myForm.title.$error.minlength">Minimum length required is 3</span>
                                      <span ng-show="myForm.title.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                        
                      
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Category</label>
                              <div class="col-md-7">
                                  <input type="text" ng-model="ctrl.product.category" class="form-control input-sm" placeholder="Enter Category. [This field is validation free]"/>
                              </div>
                          </div>
                      </div>

                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Aauthor</label>
                              <div class="col-md-7">
                                  <input type="author" ng-model="ctrl.product.author" name="author" class="email form-control input-sm" placeholder="Enter Author" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.author.$error.required">This is a required field</span>
                                      <span ng-show="myForm.author.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          <div class="form-group col-md-12">
                              <label class="col-md-2 control-lable" for="file">Price</label>
                              <div class="col-md-7">
                                  <input type="price" ng-model="ctrl.product.price" name="price" class="email form-control input-sm" placeholder="Enter Price" required/>
                                  <div class="has-error" ng-show="myForm.$dirty">
                                      <span ng-show="myForm.price.$error.required">This is a required field</span>
                                      <span ng-show="myForm.price.$invalid">This field is invalid </span>
                                  </div>
                              </div>
                          </div>
                      </div>
                      
                      
                      
                      <div class="row">
                          <div class="form-actions floatRight">
                              <input type="submit"  value="Submit" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
                              <button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Reset Form</button>
                          </div>
                      </div>
                      
                      
                  </form>
              </div>
          </div>
          <div class="panel panel-default">
                <!-- Default panel contents -->
              <div class="panel-heading"><span class="lead">List of Products </span></div>
              <div class="tablecontainer">
                  <table class="table table-hover">
                      <thead>
                          <tr>
                              <th>ID.</th>
                              <th>Title</th>
                              <th>Category</th>
                              <th>Price</th>
                              <th>Author</th>
                              <th width="30%"></th>
                          </tr>
                      </thead>
                      <tbody>
                          <tr ng-repeat="u in ctrl.products">
                              <td><span ng-bind="u.id"></span></td>
                              <td><span ng-bind="u.title"></span></td>
                              <td><span ng-bind="u.category"></span></td>
                              <td><span ng-bind="u.price"></span></td>
                              <td><span ng-bind="u.author"></span></td>
                              <td>
                              <button type="button" ng-click="ctrl.edit(u.id)" class="btn btn-success custom-width">Edit</button>  <button type="button" ng-click="ctrl.remove(u.id)" class="btn btn-danger custom-width">Remove</button>
                              </td>
                          </tr>
                      </tbody>
                  </table>
              </div>
          </div>
      </div>
      
      <script src="<c:url value='/static/js/angular.js' />"></script>
      <script src="<c:url value='/static/js/app.js' />"></script>
      <script src="<c:url value='/static/js/service/elastic_service.js' />"></script>
      <script src="<c:url value='/static/js/controller/elastic_controller.js' />"></script>
  </body>
</html>