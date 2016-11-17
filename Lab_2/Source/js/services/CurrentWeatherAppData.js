'use strict';

weatherApp.factory('currentWeatherAppData', function($resource, $q){
	
	var resource = $resource('http://api.openweathermap.org/data/2.5/weather?id=524901&APPID=67fe529057e645a9e0d429aa1ac4fa2c&q=:city&units=metric', {city:'@city'});
		return {
			getCurrentWeatherData: function(city){
				var deferred = $q.defer();
				
				resource.get({city:city},
					function (data){
					deferred.resolve(data);
				},
				function(response){
					deferred.reject(response);
				});
				
				return deferred.promise;
			}
			
		};

});