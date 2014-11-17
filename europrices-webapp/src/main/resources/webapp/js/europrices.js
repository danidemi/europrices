/*
This directive allows us to pass a function in on an enter key to do what we want.
 */

angular
	.module('europrices', [])
	.controller('SearchController', [ '$scope','$http', function($scope, $http) {
			
		$scope.foundProductItems = [];
		$scope.searchTerms = '';
		$scope.firstSearchExecuted = false;
		$scope.showSearchPlaceholder = false;
		$scope.showSearchResult = false;
//		var showSearchPlaceholder = false;
//		var showSearchResult = false;
		$scope.xxx = 'xxx';
		
			
		$scope.languages = [
		        //{name:"aragonés", iso:"an"},
		        //{name:"català", iso:"ca"},
		        {name:"Deutsch", iso:"de"},
		        {name:"eesti keel", iso:"et"},
		        {name:"English", iso:"en"},
		        {name:"español", iso:"es"},
		        //{name:"Euskara", iso:"eu"},
		        //{name:"Gaeilge", iso:"ga"},
		        //{name:"galego", iso:"gl"},
		        {name:"Italiano", iso:"it"},
		        {name:"latviešu valoda", iso:"lv"},
		        {name:"le français", iso:"fr"},
		        //{name:"Lëtzebuergesch", iso:"lb"},
		        {name:"Malti", iso:"mt"},
		        {name:"Nederlands", iso:"nl"},
		        //{name:"occitan", iso:"oc"},
		        {name:"português", iso:"pt"},
		        {name:"slovenščina", iso:"sl"},
		        {name:"slovenský jazyk", iso:"sk"},
		        {name:"suomi", iso:"fi"},
		        {name:"svenska", iso:"sv"},
		        //{name:"Türkçe", iso:"tr"},
		        {name:"ελληνικά", iso:"el"}
		];
		
		//$scope.destinationLanguage = $scope.languages[2];		
		$scope.destinationLanguage = $scope.languages[2];
			
//		$scope.showSearchPlaceholder = function() {
//			//return $scope.showSearchPlaceholder;
//			//return showSearchPlaceholder;
//		}
//			
//		$scope.showSearchResult = function() {
//			//return $scope.showSearchResult;
//			//return showSearchResult;
//		}
		
		$scope.onClearSearch = function() {
			$scope.searchTerms = '';
		}
			
		$scope.onSearch = function() {
			
			$scope.firstSearchExecuted = true;
			
			$scope.showSearchResult = false;
			$scope.showSearchPlaceholder = true;
			$scope.searchPlaceholder = 'Searching...';
			
			$scope.foundProductItems.length = 0;
			$http
				.get('/app/api/search?searchTerms=' + $scope.searchTerms)
				.success(function(data){
					
//					$scope.foundProductItems = $scope.foundProductItems.concat(data);		
					
					data.forEach(function(item){
						$scope.foundProductItems.push(item);
						//window.alert(item);
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
