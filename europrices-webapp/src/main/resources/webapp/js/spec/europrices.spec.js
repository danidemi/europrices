'use strict';

describe("Europrices", function() {
		    
	var $scope, $httpBackend;
	
	beforeEach( module('europricesMod') );
	
//    beforeEach( inject( function(_$controller_, _$rootScope_){
//    	$scope = _$rootScope_;
//    	$controller = _$controller_;
//    	$controller('SearchController', {'$rootScope' : _$rootScope_, '$scope': $scope});
//    } ) );
	
//	beforeEach( inject(function($injector){
//		$scope = $injector.get('$rootScope').$new((var isolate = false));
//		//$scope = $injector.get('$controller')('SearchController', {});
//	}) );
	
	beforeEach( inject(function($injector){
		$scope = $injector.get('$rootScope').$new();
		$httpBackend = $injector.get('$httpBackend');
		$injector.get('$controller')('SearchController', {'$scope':$scope});
	}) );
	
	afterEach(function() {
		$httpBackend.verifyNoOutstandingExpectation();
		$httpBackend.verifyNoOutstandingRequest();
	});
    
	it("contains spec with an expectation", function() {
    		expect(true).toBe(true);
  	});
	
  	it("and the opposite", function() {
    		expect(true).not.toBe(false);
  	});
  	
  	it("should start with empty search", function(){
  		expect($scope.searchTerms).toBe("");
  	});
  	
  	it("should reset search term", function(){
  		$scope.searchTerms = "Nokia";
  		expect($scope.searchTerms).toBe("Nokia");
  		$scope.onClearSearch();
  		expect($scope.searchTerms).toBe("");
  	});
  	
  	it("should use inject", inject(function($rootScope){
  		expect($rootScope).not.toBe(null);
  	}));
  	
  	it("should search", function(){
  		
  		$httpBackend
  			.when('GET', '/app/api/search?searchTerms=HTC')
  			.respond([{},{},{}]);
  		
  		$scope.searchTerms = "HTC";
  		$scope.onSearch();
  		
  		$httpBackend.flush();
  		
  		expect($scope.searchPlaceholder).toBe('');
  		expect($scope.foundProductItems.length).toBe(3);
  	});
  	
  	it("should retrieve languages from service", function(){
  		expect($scope.languages.length).toBe(15); 
  	});
  	
  	
});
