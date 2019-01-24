var app = angular.module('ivag');

app.controller("loginController", function ($rootScope, $scope, $location, $http, sessionService) {
    $scope.error = false;

    $scope.user = {email: "", password: ""};


    $scope.loginU = function () {

        $scope.error = false;

        $http({
            url: "http://localhost:7377/login" + "/user",
            method: "POST",
            params: {
                "email": $scope.user.email,
                "password": $scope.user.password
            }
        }).then(function (response) {
            var userId = response.data.id;
            sessionService.login(userId);
            $rootScope.isAuthenticated = sessionService.isLoggedIn();
            sessionService.setAdmin(response.data);
            $rootScope.isAdmin = sessionService.isHeAdmin();
            $location.url("/");
        }, function (error) {
            $scope.error = true;
            $scope.user = {email: "", password: ""};
            $scope.value = error.data;
        });

    }


});