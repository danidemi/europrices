var europricesMod = angular.module('europricesMod', []);

europricesMod.directive('myEnterpress', function () {
	return function (scope, element, attrs) {
		element.bind("keydown keypress", function (event) {
			if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.myEnterpress);
                });
                event.preventDefault();
            }
        });
    };
});

//// <div ep-fav></div>
//europricesMod.directive('epFav', function(){ 
//	return {
//		restrict: 	'AE', //E = element, A = attribute, C = class, M = comment 
//		template: 	'<div>heart</div>',
//		scope: 		{ startAs: '@' },
//		link: function (scope, element, attrs) { 
//			element.bind("click", function(event){
//				var nextFavourited;
//				if(scope.favourited === undefined){
//					//nextFavourited = false;
//					nextFavourited = (startAs === 'true');
//				}else{
//					nextFavourited = !scope.favourited;					
//				}
//				
//				if(nextFavourited){
//					element.addClass("favourited");
//				}else{
//					element.removeClass("favourited");
//				}
//				
//				scope.favourited = nextFavourited;
//			});
//		}
//	}
//});

/** "languages" service, providing all langages a product form can be translated to. */
europricesMod.factory('languages', function(){
	return [
	        // {name:"aragonés", iso:"an"},
	        // {name:"català", iso:"ca"},
        	{name:"Deutsch", iso:"de"},
        	{name:"eesti keel", iso:"et"},
        	{name:"English", iso:"en"},
        	{name:"español", iso:"es"},
        	// {name:"Euskara", iso:"eu"},
        	// {name:"Gaeilge", iso:"ga"},
        	// {name:"galego", iso:"gl"},
        	{name:"Italiano", iso:"it"},
        	{name:"latviešu valoda", iso:"lv"},
        	{name:"le français", iso:"fr"},
        	// {name:"Lëtzebuergesch", iso:"lb"},
        	{name:"Malti", iso:"mt"},
        	{name:"Nederlands", iso:"nl"},
        	// {name:"occitan", iso:"oc"},
        	{name:"português", iso:"pt"},
        	{name:"slovenščina", iso:"sl"},
        	{name:"slovenský jazyk", iso:"sk"},
        	{name:"suomi", iso:"fi"},
        	{name:"svenska", iso:"sv"},
        	// {name:"Türkçe", iso:"tr"},
        	{name:"ελληνικά", iso:"el"}
        	];
});

/** "epApi" service, providing meaningful interface to ep rest api. */
europricesMod.factory('epApi', ['$http', function($http){
	
	/** This function just "enhance" the basic $http service with meaningful methods. */
	return(function($http){
		
		$http.getProducts = function(searchText){
			return $http.get('/app/api/search?searchTerms=' + searchText);
		}
		
		$http.toggleFavourite = function(favouriteId, userId){
			return $http.post('/app/api/toggleFavourite?favouriteId=' + favouriteId + '&userId=' + userId);
		}
				
		return $http;
		
	}($http));
	
}]);



europricesMod.factory('epUser', [function(){ 

	var constructor = function(){
		
		var m = function(){
			alert("hello!");
		}
		
		var getLoggedUserId = function() {
			var loggedUser = window.document.getElementById("loggedUserId");
			if (typeof loggedUser !== 'undefined' && loggedUser!=null) {
				return loggedUser.value;
			}else{
				return null;
			}
		}
		
		var isUserLogged = function() {
			return this.getLoggedUserId() != null ? true : false;
		}
		
		return {
			m:               m,
			getLoggedUserId: getLoggedUserId,
			isUserLogged:	 isUserLogged
		}
	};
	return constructor();

}]);

europricesMod.controller('SearchController', [ '$scope','languages', 'epApi', 'epUser', function($scope, languages, epApi, epUser) {
			
		$scope.foundProductItems = [];
		$scope.searchTerms = '';
		$scope.firstSearchExecuted = false;
		$scope.showSearchPlaceholder = false;
		$scope.showSearchResult = false;
		$scope.xxx = 'xxx';
				
		$scope.languages = languages;
				
		$scope.destinationLanguage = $scope.languages[2];
		$scope.isUserLogged = epUser.isUserLogged();
		
		$scope.onFavouriteToggle = function(item) {
			
			//item.favourite = !item.favourite;
			
			var userId = epUser.getLoggedUserId();
			var favouriteId = item.id;
			
			if(userId === null) {
				alert("Sign In To Manage Your Favs.")
				return;
			}
			
			epApi
				.toggleFavourite(favouriteId, userId)
				.success(function(data){
					item.favourite = (data === 'true');
				})
				.error(function(e){
					alert("an error occurred");
				});
		}
							
		$scope.onClearSearch = function() {
			$scope.searchTerms = '';
		}
			
		$scope.onSearch = function() {
			
			if($scope.searchTerms.length < 3) return;
			
			$scope.firstSearchExecuted = true;
			
			$scope.showSearchResult = false;
			$scope.showSearchPlaceholder = true;
			$scope.searchPlaceholder = 'Searching...';
			
			$scope.foundProductItems.length = 0;
			epApi
				.getProducts($scope.searchTerms)
				.success(function(data){
										
					data.forEach(function(item){
						$scope.foundProductItems.push(item);
					});
					if($scope.foundProductItems.length > 0) {
						$scope.showSearchResult = true;
						$scope.showSearchPlaceholder = false;
						$scope.searchPlaceholder = '';
					}else{
						$scope.showSearchResult = false;
						$scope.showSearchPlaceholder = true;
						$scope.searchPlaceholder = 'No products have been found for "' + $scope.searchTerms + '".';
					}
				})
				.error(function(data, status){
					$scope.searchPlaceholder = 'Sorry, an error occurred.';
				});
			
			
		}
			
	} 
]);
