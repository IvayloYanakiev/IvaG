var app = angular.module('ivag');

app.controller("checkoutShoppingCartController", function ($rootScope, $q, $scope, $location, $routeParams, $http, sessionService, shoppingCart) {

    var allAddresses = function () {
        $http({
            url: "http://localhost:7377/address" + "/getAllAddresses",
            method: "GET",
            params: {"userId": sessionService.getSession()}
        }).then(function (response) {
            $scope.addresses = response.data;
        });

    };
    allAddresses();

    var getData = function () {
        $http({
            url: "http://localhost:7377/user" + "/getUserPersonalData",
            method: "GET",
            params: {"id": sessionService.getSession()}
        }).then(function (response) {
            $scope.user = response.data;
            if ($scope.user.pictureUrl != null) {
                $scope.pictureUrl = $scope.user.pictureUrl;
            } else $scope.pictureUrl = "http://127.0.0.1:8887/nopicture.jpg";
        });

    };
    getData();
    var addressId = "";
    var paying = "";
    $scope.user = {email: ""};
    $scope.makeOrder = function () {
        $scope.error = false;
        var radioButtons = document.getElementsByName('address');
        for (var i = 0; i < radioButtons.length; i++) {
            if (radioButtons[i].checked) {
                addressId = radioButtons[i].value;
                break;
            }
        }

        var radioButtonsForPayin = document.getElementsByName('payingOption');
        for (var i = 0; i < radioButtonsForPayin.length; i++) {
            if (radioButtonsForPayin[i].checked) {
                paying = radioButtonsForPayin[i].value;
                break;
            }
        }
        if (addressId !== "" && paying !== "") {


            var totalSum = $scope.getTotal();
            $http({
                url: "http://localhost:7377/sendEmail" + "/informUserForOrder",
                method: "PUT",
                params: {
                    "email": $scope.user.email,
                    "addressId": addressId,
                    "payingMethod": paying,
                    "totalSum": totalSum,
                    "shoppingCart": shoppingCart.getEntries()
                }
            }).then(function (response) {
                window.alert("Your order was successful");
                // $scope.value = response.data;
                $location.url("/home");
            }, function (error) {
                $scope.error = true;
                $scope.value = error.data;
            });
            shoppingCart.cleanShoppingCart();
        }
        else {
            $scope.error = true;
            $scope.value = "Error making order.Check your info!";
        }
    };

    var getShoppingCart = function () {
        var entries = shoppingCart.getEntries();
        var isNotEmpty = shoppingCart.isNotEmpty();
        if (isNotEmpty) {
            $http({
                url: "http://localhost:7377/shoppingCart" + "/getProductsFromShoppingCart",
                method: "GET",
                params: {"products": entries}
            }).then(function (response) {
                $scope.products = response.data;
            }, function (error) {

            });
        } else {
            $scope.products = [];
        }
    };
    getShoppingCart();

    $scope.getTotal = function () {
        var total = 0;

        for (var i = 0; i < $scope.products.length; i++) {
            var product = $scope.products[i];

            if (product.discount === 0) {
                total += (product.price * product.quantity);
            } else {
                total += (product.price - product.discount / 100 * product.price) * product.quantity;
            }
        }
        total = total.toFixed(2);
        return total;
    };

    $scope.getSubTotal = function (price, quantity, discount) {
        var subtotal = 0;
        if (discount === 0) {
            subtotal = price * quantity;
            return subtotal;
        } else {
            subtotal = (price - discount / 100 * price) * quantity;
            subtotal = subtotal.toFixed(2);
            return subtotal;
        }
    }
});