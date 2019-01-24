var app = angular.module('ivag');

app.controller("innerCategoryProducts", function ($scope, $location, $routeParams, $http, sessionService, $rootScope) {

    var innerCategoryId = $routeParams.id;

    var getProducts = function () {
        $http({
            url: "http://localhost:7377/product" + "/getInnerCategoryProducts",
            method: "GET",
            params: {"id": innerCategoryId}
        }).then(function (response) {
            $scope.products = response.data;
        }, function (error) {

        });
    };
    getProducts();
    $scope.hasProducts = true;
    $scope.informUser = false;


    $scope.addToCart = function (productId) {
        shoppingCart.addEntry(productId);
    };

    $rootScope.isAdmin = sessionService.isHeAdmin();
    $scope.orderByPriceAscending = function () {
        function compare(a,b) {
            if (a.price < b.price)
                return -1;
            if (a.price > b.price)
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };

    $scope.orderByPriceDescending = function () {
        function compare(a,b) {
            if (a.price > b.price)
                return -1;
            if (a.price < b.price)
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };

    $scope.orderByDiscountAscending = function () {
        function compare(a,b) {
            if (a.discount < b.discount)
                return -1;
            if (a.discount > b.discount)
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };

    $scope.orderByDiscountDescending = function () {
        function compare(a,b) {
            if (a.discount > b.discount)
                return -1;
            if (a.discount < b.discount)
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };
    $scope.orderByNameAscending = function () {
        function compare(a,b) {
            if (a.name.toLowerCase() < b.name.toLowerCase())
                return -1;
            if (a.name.toLowerCase() > b.name.toLowerCase())
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };

    $scope.orderByNameDescending = function () {
        function compare(a,b) {
            if (a.name.toLowerCase() > b.name.toLowerCase())
                return -1;
            if (a.name.toLowerCase() < b.name.toLowerCase())
                return 1;
            return 0;
        }

        $scope.products.sort(compare);
    };
    $scope.removeProduct = function (productId) {
        $http({
            url: "http://localhost:7377/admin" + "/removeProductById",
            method: "DELETE",
            params: {"id": productId}
        }).then(function (response) {
            getProducts();
        }, function (error) {

        });
    };

    $scope.goTo = function (productId) {

        $location.url("/product/" + productId);
    };

    $scope.editProduct = function (updateProduct) {
        $scope.updateProduct = jQuery.extend({}, updateProduct);
    };

    $scope.updProduct = function () {
        $scope.error = false;

        $http({
            url: "http://localhost:7377/admin" + "/updateProduct",
            method: "PUT",
            params: {
                "id": $scope.updateProduct.id,
                "name": $scope.updateProduct.name,
                "categoryId": $scope.updateProduct.innerCategoryId,
                "price": $scope.updateProduct.price,
                "quantity": $scope.updateProduct.quantity,
                "description": $scope.updateProduct.description,
                "discount": $scope.updateProduct.discount
            }
        }).then(function (response) {
            $('#myModal').modal('hide');
            $location.url("/");
        }, function (error) {
            $scope.error = true;
            $scope.value = error.data;
        });
    };


    var slider = document.getElementById("myRange");
    var output = document.getElementById("demo");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    }

    $scope.filterProducts = function () {
        var newArray = $scope.products.filter(function (el) {
            return el.price <= slider.value;
        });
        $scope.products = newArray;
    }


    $scope.getNewProductPrice = function (price, discount) {
        if (discount === 0) {
            return price;
        } else {
            var result = price - discount / 100 * price;
            result = result.toFixed(2);
            return result;
        }
    }
});