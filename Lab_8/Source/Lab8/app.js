var app=angular.module("convertapp",[]);
app.controller("convertctrl",function ($scope,$http) {
    $scope.convertg = function () {
        var gen=$scope.F;

        var words = $http.get("http://localhost:8080/MyRestProject/convertfinal/"+gen)
        words.success(function (data) {
            console.log(data);

            $scope.convertcon={"Decimal":data.Decimal,"Binary":data.Binary,"Hexadecimal":data.Hexadecimal};

        });
    }
});
