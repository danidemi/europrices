angular.module('europrices', []).controller('SearchController',
		[ '$scope','$http', function($scope, $http) {
			
			$scope.foundProductItems = [];
			$scope.searchTerms;
			
			$scope.productItemsHaveBeenFound = function() {
				return $scope.foundProductItems.length > 0;
			}
			
			$scope.onSearch = function() {
				
				
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
