angular.module('europrices', []).controller('SearchController',
		[ '$scope', function($scope) {
			
			$scope.foundProductItems = [{name:'name#1'},{name:'name#2'}];
			
			$scope.onSearch = function() {
				$scope.foundProductItems.push({
					name : 'prodottino',
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
