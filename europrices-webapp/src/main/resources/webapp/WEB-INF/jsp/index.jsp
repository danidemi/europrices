<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html >
<html lang="en" ng-app="europricesMod">
	<head>
		<meta charset="UTF-8">
		<!--  
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.min.js"></script>
 		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-resource.min.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.min.js"></script>		
		-->
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular.js"></script>
 		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-resource.js"></script>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.25/angular-route.js"></script>
		<script src="/js/main/europrices.js"></script>		
		<link href='http://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet' type='text/css'>
		<link href="/css/style.css" rel="stylesheet" type="text/css" >
	</head>
	<body>
		<div ng-app="europricesMod">
		
			<div ng-controller="SearchController">
		
				<div id="logo" >
					<div>europrices</div>
					<div>spot the best mobile prices in the eurozone</div>
				</div>
				
				<div id="social">
					<sec:authorize access="isAnonymous()">
						<a href="/auth/twitter">Sign In With Twitter</a>
						<a href="/auth/facebook">Sign In With Facebook</a>
					</sec:authorize>
					<sec:authorize access="isAuthenticated()">
						
						<% String imageUrl = (String)request.getAttribute("imageUrl");
						if(imageUrl!=null){ %>
						<img alt="profile" src="<%= imageUrl %>"/>
						<% } %>
						
						<% String socialDisplayName = (String)request.getAttribute("socialDisplayName");
						if(socialDisplayName!=null){ %>
							<%=socialDisplayName%>
						<% } %>						

						<a href="/j_spring_security_logout">Sign Out</a>
					</sec:authorize> 
				</div>
				


				<div id="search-box">
					<div id="search">
						<input type="search" placeholder="samsung, galaxy..."  ng-model="searchTerms" my-enterpress="onSearch()" />
						<button id="search-launch" ng-click="onSearch()" ng-disabled="searchTerms.length<3">search</button>
					</div>
				</div>


				<div id="results">
									
					<div id="placeholder" ng-if="showSearchPlaceholder">
						{{searchPlaceholder}}
					</div>
					
					<div id="resultLang" ng-show="showSearchResult">
						Read product details in <select ng-model="destinationLanguage" ng-options="language as language.name for language in languages">
							<option value="">original language</option>									
						</select>					 
					</div>
					
					<div id="resultTable" ng-if="showSearchResult">
						<table>
							<thead>
								<tr>
									<th>Product</th>
									<th>Shop</th>
									<th>Price</th>
									<th>Details</th>
								</tr>
							</thead>
							<tbody>
								<tr ng-repeat="item in foundProductItems">
									<td>
										{{item.name}}
									</td>
									<td>
										{{item.shop.name}}
									</td>
									<td>
										{{item.priceInEuroCent/100 | currency:"€"}}
									</td>
									<td>
										<div ng-if="destinationLanguage!=null && item.languageIsoCode != destinationLanguage.iso" style="display: inline;">
											<a target="_blank" href="http://translate.google.com/translate?js=n&sl=auto&tl={{destinationLanguage.iso}}&u={{item.detailsUrl}}">details</a>									
										</div>
										<div ng-if="!(destinationLanguage!=null && item.languageIsoCode != destinationLanguage.iso)" style="display: inline;">
											<a target="_blank" href="{{item.detailsUrl}}">details</a>
										</div>	
									</td>
								</tr>
							</tbody>
							<tfoot>
								<tr>
									<th colspan="4">&nbsp;</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
			
		</div>
		
		
		
		<footer>
			Brought to you by <a target="_blank" href="http://www.danidemi.com">Studio Danidemi</a>
		</footer>
		
	</body>
</html>