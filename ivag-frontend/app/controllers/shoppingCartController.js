var app = angular.module('ivag');

app.controller("shoppingCartController", function ($scope, $location, $routeParams, $http, sessionService, $rootScope, shoppingCart) {


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
    $scope.goTo = function (productId) {
        $location.url("/product/" + productId);
    };

    getShoppingCart();
    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    $scope.addToCart = function (productId) {
        shoppingCart.addEntry(productId);
        getShoppingCart();
    };
    $scope.removeFromCart = function (productId) {
        shoppingCart.removeEntry(productId);

        getShoppingCart();
    };

    $scope.getTotal = function () {
        var total = 0;
        if ($scope.products.length === 0) {
            $scope.hasProducts = false;
            return 0;
        }
        for (var i = 0; i < $scope.products.length; i++) {
            var product = $scope.products[i];
            if (product.discount === 0) total += (product.price * product.quantity);
            else total += (product.price - product.discount / 100 * product.price) * product.quantity;
        }
        total = total.toFixed(2);
        $scope.hasProducts = true;
        return total;
    };
    $scope.goToCheckOut = function () {
        if ($rootScope.isAuthenticated) {
            if ($scope.hasProducts) {
                $location.url("/checkoutShoppingCart");
            }
            else $location.url("/home");

        }
        else $location.url("/login");
    }

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