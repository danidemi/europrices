'use strict';

describe("Europrices", function() {
		    
	var $scope, $httpBackend;
	
	beforeEach( module('europricesMod') );
		
	beforeEach( inject(function($injector){
		$scope = $injector.get('$rootScope').$new();
		$httpBackend = $injector.get('$httpBackend');
		$injector.get('$controller')('SearchController', {'$scope':$scope});
	}) );
	
	afterEach(function() {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest(); 
	});
      	
  	it("should start with empty search", function(){
  		expect($scope.searchTerms).toBe("");
  	});
  	
  	it("should reset search term", function(){
  		
  		// given
  		$scope.searchTerms = "Nokia";
  		expect($scope.searchTerms).toBe("Nokia");
  		
  		// when
  		$scope.onClearSearch();
  		
  		// then
  		expect($scope.searchTerms).toBe("");
  		
  	});
  	  	
  	it("should search", function(){
  		
  		// given
  		$httpBackend
  			.when('GET', '/app/api/search?searchTerms=HTC')
  			.respond([{},{},{}]);
  		
  		$httpBackend
			.when('POST', '/app/api/getSessionKey')
			.respond({'Europrices-Session-Key':'sesskey', 'Europrices-Api-Key':'apiKey'});  		
  		
  		// when
  		$scope.searchTerms = "HTC";
  		$scope.onSearch();
  		$httpBackend.flush();
  		
  		// then
  		expect($scope.searchPlaceholder).toBe('');
  		expect($scope.foundProductItems.length).toBe(3);
  		
  	});
  	
  	it("should retrieve languages from service", function(){
  		expect($scope.languages.length).toBe(15); 
  	});
  	
  	it("should use inject", inject(function($rootScope){
  		expect($rootScope).not.toBe(null);
  	}));
  	
  	
});
