'use strict';

angular.module('myApp').controller('ElasticController', ['$scope', 'ElasticService', function($scope, ElasticService) {
    var self = this;
    self.product={id:null,title:'',category:'',price:'', author:''};
    self.products=[];

    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;


    fetchAllProducts();

    function fetchAllProducts(){
        ElasticService.fetchAllProducts()
            .then(
            function(d) {
                self.products = d;
            },
            function(errResponse){
                console.error('Error while fetching Products');
            }
        );
    }

    function createProduct(product){
        ElasticService.createProduct(product)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while creating Product');
            }
        );
    }

    function updateProduuct(product, id){
        ElasticService.updateProduuct(product, id)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while updating Product');
            }
        );
    }

    function deleteProduct(id){
        ElasticService.deleteProduct(id)
            .then(
            fetchAllProducts,
            function(errResponse){
                console.error('Error while deleting Product');
            }
        );
    }

    function submit() {
        /*if(self.product.id===null){
            console.log('Saving New Product', self.product);
            createProduct(self.product);
        }else{
        	updateProduuct(self.product, self.product.id);
            console.log('Product with id ', self.product.id);
        }*/
    	var isNewProduct = true;
    	for(var i = 0; i < self.products.length; i++){
            if(self.products[i].id === self.product.id) {
                //self.product = angular.copy(self.products[i]);
                isNewProduct = false;
                break;
            }
        }
    	if(isNewProduct) {
    		console.log('Saving New Product', self.product);
    		createProduct(self.product);
    	} else {
    		updateProduuct(self.product, self.product.id);
    		console.log('Updating Product with id ', self.product.id);
    	}
    	
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        //alert('self.products.length...'+self.products.length);
        for(var i = 0; i < self.products.length; i++){
            if(self.products[i].id === id) {
                self.product = angular.copy(self.products[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.product.id === id) {//clean form if the user to be deleted is shown there.
            reset();
        }
        deleteProduct(id);
    }


    function reset(){
        self.product={id:null,title:'',category:'',price:'', author:''};
        $scope.myForm.$setPristine(); //reset Form
    }

}]);


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
        var uploadUrl = "/fileUpload";
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };
    
}]);

