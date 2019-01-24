var app = angular.module('ivag');

app.controller("logoutController", function ($rootScope,$scope,$location,sessionService) {

    var logoutUser = function () {
        sessionService.logout();
        $location.url("/login");
        $rootScope.isAuthenticated = sessionService.isLoggedIn();
    };
    logoutUser();

});