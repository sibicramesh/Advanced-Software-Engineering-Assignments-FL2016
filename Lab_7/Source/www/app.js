var app=angular.module("Analyze",['ngCordova']);
app.controller("analyzectrl",function ($scope,$http,$cordovaVibration) {
    $scope.analyzeg = function () {
        var gen=$scope.text;
        var words = $http.get("https://api.uclassify.com/v1/uclassify/genderanalyzer_v5/Classify?readkey=OcM9GwGXd54G&text=" + gen)
        words.success(function (data) {
            console.log(data);
            $scope.analyzelang={"female":data.female*100,"male":data.male*100};
            $cordovaVibration.vibrate(70); 
            
        });
    }


});

app.controller("languagectrl",function ($scope,$http,$cordovaVibration,$cordovaInAppBrowser) {
    $scope.language1 = function () {
        var gen=$scope.text;
        var tar=$scope.lang2;
        var words1 = $http.get("https://www.googleapis.com/language/translate/v2?key=AIzaSyCxmnnpz7-qk5BZzKMyssCdPZ1D6uYi7I0&source=en&target="+tar+"&q=" + gen)
        words1.success(function (data1) {
            console.log(data1);
            $scope.langlang={"translatedText":data1.data.translations["0"].translatedText};
             $cordovaVibration.vibrate(70);   
        });
    }
    
    $scope.inapp1=function(){
        var options = {
      location: 'yes',
      clearcache: 'yes',
      toolbar: 'no'
   };
         var gen=$scope.text;
    var url="http://www.dictionary.com/browse/"+gen+"?s=t";
        $cordovaInAppBrowser.open(url,'_blank',options)
    
   }
    
    

});
app.controller('vibratectrl', function ($scope, $cordovaVibration) {

  $scope.vibrate1=function(){
  $cordovaVibration.vibrate(1000);
  }
      
        
});

app.controller('splash', function($scope, $cordovaSplashscreen) {
setTimeout(function() {
navigator.splashscreen.hide();
}, 2000);
  $cordovaSplashscreen.show();

});


               
               
