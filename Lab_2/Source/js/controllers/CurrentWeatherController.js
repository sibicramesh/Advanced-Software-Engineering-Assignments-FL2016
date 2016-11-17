'use strict';

weatherApp.controller('currentWeatherController', 

	function currentWeatherController($scope, currentWeatherAppData, $routeParams){
	
	$scope.searchCurrentWeatherByCity = function(city){
		currentWeatherAppData.getCurrentWeatherData(city).then(function(res){
   	  		$scope.weatherData = res;
   	  		$scope.weatherDate = res.dt * 1000; //receiving unix timestamp in seconds, convert to miliseconds
	 });
   };
    
});
