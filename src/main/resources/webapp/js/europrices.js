angular.module('europrices', []).controller('SearchController',
		[ '$scope','$http', function($scope, $http) {
			
			$scope.foundProductItems = [];
			$scope.searchTerms;
			
			$scope.languages = [
			        {name:"aragonés", iso:"an"},
			        {name:"català", iso:"ca"},
			        {name:"Deutsch", iso:"de"},
			        {name:"eesti keel", iso:"et"},
			        {name:"English", iso:"en"},
			        {name:"español", iso:"es"},
			        {name:"Euskara", iso:"eu"},
			        {name:"Gaeilge", iso:"ga"},
			        {name:"galego", iso:"gl"},
			        {name:"Italiano", iso:"it"},
			        {name:"latviešu valoda", iso:"lv"},
			        {name:"le français", iso:"fr"},
			        {name:"Lëtzebuergesch", iso:"lb"},
			        {name:"Malti", iso:"mt"},
			        {name:"Nederlands", iso:"nl"},
			        {name:"occitan", iso:"oc"},
			        {name:"português", iso:"pt"},
			        {name:"slovenščina", iso:"sl"},
			        {name:"slovenský jazyk", iso:"sk"},
			        {name:"suomi", iso:"fi"},
			        {name:"svenska", iso:"sv"},
			        {name:"Türkçe", iso:"tr"},
			        {name:"ελληνικά", iso:"el"}
			];
			
			$scope.productItemsHaveBeenFound = function() {
				return $scope.foundProductItems.length > 0;
			}
			
			$scope.onSearch = function() {
				
				$scope.foundProductItems = [];
				
				$http
					.get('/app/api/search?searchTerms=' + $scope.searchTerms)
					.success(function(data){
						data.forEach(function(item){
							$scope.foundProductItems.push(item);
						});
					})
					.error(function(data, status){
						alert(status);
					});
				
				

			};
			
			$scope.todos = [ {
				text : 'learn angular',
				done : true
			}, {
				text : 'build an angular app',
				done : false
			} ];

			$scope.oldsearch = function() {
				$scope.todos.push({
					text : $scope.todoText,
					done : false
				});
				$scope.todoText = '';
			};

			$scope.remaining = function() {
				var count = 0;
				angular.forEach($scope.todos, function(todo) {
					count += todo.done ? 0 : 1;
				});
				return count;
			};

			$scope.archive = function() {
				var oldTodos = $scope.todos;
				$scope.todos = [];
				angular.forEach(oldTodos, function(todo) {
					if (!todo.done)
						$scope.todos.push(todo);
				});
			};
		} ]);
