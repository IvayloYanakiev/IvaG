var app = angular.module('ivag');

app.controller("registerController", function ($scope, $location, $http) {
    $scope.success = false;
    $scope.error = false;
    $scope.value = " ";
    $scope.user = {name: "", email: "", password: "", confirmPassword: ""};

    $scope.register = function () {

        $scope.error = false;
        $scope.success = false;

        $http({
            url: "http://localhost:7377/register/" + "/createUser",
            method: "POST",
            params: {
                "name": $scope.user.name,
                "email": $scope.user.email,
                "password": $scope.user.password,
                "confirmPassword": $scope.user.confirmPassword
            }
        }).then(function (response) {
            $scope.success = true;
            $scope.value = response.data;
            $scope.user = {name: "", email: "", password: "", confirmPassword: ""};
        }, function (error) {
            $scope.error = true;
            $scope.value = error.data;
        });

    }

});