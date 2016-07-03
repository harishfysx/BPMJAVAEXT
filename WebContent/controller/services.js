(function() {

  "use strict";

  var App = angular.module("App.services",[]);

  App.value('version', '0.1');

  // here is a declaration of simple utility function to know if an given param is a String.
  App.service("UtilSrvc", function () {
    return {
      isAString: function(o) {
          return typeof o == "string" || (typeof o == "object" && o.constructor === String);
      },
      helloWorld : function(name) {
      	var result = "Hum, Hello you, but your name is too weird...";
      	if (this.isAString(name)) {
      		result = "Hello, " + name;
      	}
      	return result;
      }
    }
  });
  
  
  App.service("flowTypeService", function(){
		return  {
			processDropdown : function ($scope,$http,input){
				  var url;
				  if(input === "serviceType"){
					  url = "http://10.89.31.193:8080/mzrt-ucc-web/getAttributeByCode/"+input+"?sort=true"
				  }else{
					  url = "http://10.89.31.193:8080/mzrt-ucc-web/getAttributeByCode/"+input
				  }
				
					  $http({
						  method : "GET",
						  url:url
						  
					  }).then(function success(response){
						  if(input === "workflowType"){
							  $scope.workflowtype =  response.data.returnValue;
						  }
						  else if(input === "serviceType"){
							  $scope.serviceType =  response.data.returnValue;
						  }
						  else if(input === "requestType"){
							  $scope.requestType =  response.data.returnValue;
						  }
						  else if(input === "requestSubType"){
							  $scope.requestSubType =  response.data.returnValue;
						  }
						  else if(input === "group"){
							  $scope.group =  response.data.returnValue;
						  }
						  else if(input === "systemSourceType"){
							  $scope.systemSourceType =  response.data.returnValue;
						  }
					  },function error(response){
							 $scope.error =  response.data;
					  });
			
			}
		};
	});

}());