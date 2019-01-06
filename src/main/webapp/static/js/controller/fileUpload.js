'use strict';

angular.module('myApp').directive('fileModel', ['$parse', function ($parse) {
	alert('in directive');
	return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

angular.module('myApp').service('fileUpload', ['$http', function ($http) {
	alert('in service');
    this.uploadFileToUrl = function(file, uploadUrl){
    	alert('file ::'+file+ ' ::upload url is:: '+uploadUrl );
        var fd = new FormData();
        fd.append('file', file);
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        	alert('success');
        })
        .error(function(){
        	alert('fail');
        });
    }
}]);

angular.module('myApp').controller('myCtrl', ['$scope', 'fileUpload', function($scope, fileUpload){
	alert('in controller');
    $scope.uploadFile = function(){
        var file = $scope.myFile;
        alert('file is '+file );
        //console.dir(file);
        var uploadUrl = "/Spring-Monolithic/fileUpload";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
    
}]);

