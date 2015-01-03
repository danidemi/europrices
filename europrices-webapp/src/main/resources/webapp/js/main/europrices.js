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

// I provide a request-transformation method that is used to prepare the outgoing
// request as a FORM post instead of a JSON packet.
europricesMod.factory("transformRequestAsFormPost", function() {

        // I prepare the request data for the form post.
        function transformRequest( data, getHeaders ) {
            var headers = getHeaders();
            headers[ "Content-Type" ] = "application/x-www-form-urlencoded; charset=utf-8";
            return( serializeData( data ) );
        }

        // Return the factory value.
        return( transformRequest );

        // ---
        // PRVIATE METHODS.
        // ---

        // I serialize the given Object into a key-value pair string. This
        // method expects an object and will default to the toString() method.
        // --
        // NOTE: This is an atered version of the jQuery.param() method which
        // will serialize a data collection for Form posting.
        // --
        // https://github.com/jquery/jquery/blob/master/src/serialize.js#L45
        function serializeData( data ) {

            // If this is not an object, defer to native stringification.
            if ( ! angular.isObject( data ) ) {

                return( ( data == null ) ? "" : data.toString() );

            }

            var buffer = [];

            // Serialize each key in the object.
            for ( var name in data ) {

                if ( ! data.hasOwnProperty( name ) ) {

                    continue;

                }

                var value = data[ name ];

                buffer.push(
                    encodeURIComponent( name ) +
                    "=" +
                    encodeURIComponent( ( value == null ) ? "" : value )
                );

            }

            // Serialize the buffer and clean it up for transportation.
            var source = buffer
                .join( "&" )
                .replace( /%20/g, "+" )
            ;

            return( source );

        }

    }
);

/** "epApi" service, providing meaningful interface to ep rest api. */
europricesMod.factory('epApi', ['$http', 'transformRequestAsFormPost', function($http, transformRequestAsFormPost){
	
	/** This function just "enhance" the basic $http service with meaningful methods. */
	return(function($http){
		
		var headers = null;

		var getHeaders = function(){
			
			if(headers == null){
				// /app/api/getSessionKey
				// requires Europrices-Api-Key param
				$http(
						{
							method:'POST',
							url:'/app/api/getSessionKey',
							data: {'Europrices-Api-Key':'weuhd923eu'},
							transformRequest: transformRequestAsFormPost	
						}
					)
					.success(function(data){ headers = data; })
					.error(function(){ headers = null; })
				;
			}
			
			return headers;
		}
		
		$http.getProducts = function(searchText){
			return $http.get('/app/api/search?searchTerms=' + searchText, {headers:getHeaders()});
		}
		
		$http.getProductsForUser = function(searchText, userId){
			return $http.get('/app/api/search?searchTerms=' + searchText + "&userId=" + userId, {headers:getHeaders()});
		}		
		
		$http.toggleFavourite = function(favouriteId, userId){
			return $http.post('/app/api/toggleFavourite?favouriteId=' + favouriteId + '&userId=' + userId, {headers:getHeaders()});
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
				var theValue = loggedUser.value 
				return theValue == "null" ? null : theValue;
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
			
			var api = null; 
			if(epUser.isUserLogged()){
				api = epApi.getProductsForUser($scope.searchTerms, epUser.getLoggedUserId());
			}else{
				api = epApi.getProducts($scope.searchTerms);
			}
			
				api.success(function(data){
										
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
