'use strict';

var weatherApp = angular.module('weatherApp', ['ngResource', 'ngRoute'])
	.config(function($routeProvider){
		$routeProvider.when('/currentWeather', {
			templateUrl:'templates/currentWeather.html',
			controller:'currentWeatherController'
		});
		
		$routeProvider.when('/sixteenDayWeather', {
			templateUrl:'templates/sixteenDayWeather.html',
			controller:'sixteenDayWeatherController'
		});
		
		$routeProvider.otherwise({redirectTo:'/currentWeather'});
		
	});
