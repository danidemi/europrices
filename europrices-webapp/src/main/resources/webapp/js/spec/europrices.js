'use strict';

describe("Europrices", function() {
	
    var scope, controller;

		
//    beforeEach(function () {
//        module('europrices');
//    });	
    
//    describe('MyCtrl', function () {
//    	beforeEach(
//    			inject(function ($rootScope, $controller) {
//    				scope = $rootScope.$new();
//    				controller = $controller('MyCtrl', {
//    					'$scope': scope    				
//    				}
//    			});
//        })); 
//    	
//    	it("contains spec with an expectation", function() {
//    		expect(true).toBe(true);
//    	});  
//	}
	
//    beforeEach(inject(function(_$rootScope_, _$controller_){
//        $rootScope = _$rootScope_;
//        $scope = $rootScope.$new();
//        $controller = _$controller_;
//
//        $controller('SearchController', {'$rootScope' : $rootScope, '$scope': $scope});
//    }));	
	
//    beforeEach( inject(function($controller){
//    	
//    }) );
//    var _$rootScope_ = null;
//    var _$controller_ = null;
//    alert(_$rootScope_);
//    alert(_$controller_);
    beforeEach( inject( function($controller, $rootScope){
    	console.log("hello");
    	var controller = $controller('SearchController', {'$scope' : $rootScope });
    } ) );
    
	it("contains spec with an expectation", function() {
    		expect(true).toBe(true);
  	});
  	it("and the opposite", function() {
    		expect(true).not.toBe(false);
  	});
});
