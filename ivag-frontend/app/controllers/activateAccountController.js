var app = angular.module('ivag');

app.controller("activateAccountController", function ($http, $scope, $location, $routeParams) {

    var token = $routeParams.token;

    var activateAccount = function () {
        $http({
            url: "http://localhost:7377/register/activateAccount",
            method: "POST",
            params: {"token": token}
        }).then(function (response) {
            alert(response.data);
            $location.url("/login");
        }, function (error) {
        })
    };
    activateAccount();
});