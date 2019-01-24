var app = angular.module('ivag');

app.controller("subscribeController", function ($rootScope, $scope, $location, $http) {

        $scope.user = {email: ""};
        $scope.subscribe = function () {

            $http({
                url: "http://localhost:7377/sendEmail" + "/subscribeUser",
                method: "PUT",
                params: {
                    "email": $scope.user.email
                }
            }).then(function (response) {
                window.alert("You have successfully subscribed to eMAG.bg");
                $scope.value = response.data;
            }, function (error) {
                window.alert("You have to register first");
            });
        }
    }
);