(function() {

  "use strict";

  var App = angular.module("App.controllers", ['smart-table']);

  App.controller("MyCtrl1", ["$scope", function ($scope, UtilSrvc){
    $scope.aVariable = 'anExampleValueWithinScope';
    $scope.valueFromService = UtilSrvc.helloWorld("User");
  }]);

  App.controller('sortCtrl', ['$scope','$http', function ($scope, $http) {
	  
		//get Intial data
		
			$http.put('/TestBPM/api/webservice/getTasks').success(function(response){
		
			  
			//console.log(response);
			$scope.rowCollection = response.data.data;
			//$scope.normalTCount=response.normalTcount;
			//$scope.normalTCount=response.normalTcount;
			//$scope.highTcount=response.highTcount;
			//$scope.highTcount=response.highTcount;
			//$scope.rowCollection = response.totalData;
			  
		   });
	
		
		
			
	  
	  /*
	    scope.rowCollection = [
	        {firstName: 'Theon Greyjoy', lastName: 'Jon Snow', birthDate: new Date('1987-05-21'), balance: 102343, email: 'whatever@gmail.com'},
	        {firstName: 'Peter Bailesh', lastName: 'Sansa Stark', birthDate: new Date('1987-04-25'), balance: 232334, email: 'oufblandou@gmail.com'},
	        {firstName: 'Lord Varys', lastName: 'Tyrrion Lannister', birthDate: new Date('1955-08-27'), balance: 42343, email: 'raymondef@gmail.com'}
	    ];
	    */
	    //scope.userAuth="test data"
	    	//console.log($http.headers);

	    /*
	    $scope.getters={
	    		taskStatus: function (value) {
	            //this will sort by the length of the first name string
	            return value.taskStatus.length;
	        }
	    }
	    */
	    
	}]);
  
  App.controller("myCtrl", function($scope,$http,flowTypeService){
		 flowTypeService.processDropdown($scope,$http,"workflowType");   
		 flowTypeService.processDropdown($scope,$http,"serviceType");
		 flowTypeService.processDropdown($scope,$http,"requestType");
		 flowTypeService.processDropdown($scope,$http,"requestSubType");
		 flowTypeService.processDropdown($scope,$http,"group");
		 flowTypeService.processDropdown($scope,$http,"systemSourceType");
		 
		 $scope.changeValue = function(value,from){
			 if(from == 'wftype'){
				 if(value != 'Collateral'){
						$scope.sType = "Select";
						$scope.RType = "Select";
						$scope.RsType = "Select";
				 }
			 }
			 else if(from == 'stype'){
				 if(value != 'UCC'){
						$scope.RType = "Select";
						$scope.RsType = "Select";
				}
			 }else if(from == 'rtype'){
				  if(value != 'Search'){
						$scope.RsType = "Select";
				 }
			 }
			
		 };
	});
  
  App.controller("instCtrl",function($scope,$http){
	  
	 // console.log("test response");
	  
	 $scope.submit=function() {
		 
		 var requestorInfo={"requestorInfo":{
				"name":$scope.reqName,
				"email":$scope.reqEmail,
				"phone":$scope.reqPhone,
				"city":$scope.reqCity,
				"au":$scope.reqAu,
				"macAddress":$scope.reqMac,
				"state":$scope.reqState,
				"extUI":true
				}}
		 $http.post('/TestBPM/api/webservice/startInst?input='+JSON.stringify(requestorInfo)).success(function(response){
			
			 
			 console.log(requestorInfo);
			$scope.message=response.data.piid+"  instance is created"
	 });
		 
	 }
		
	  
  });

}());