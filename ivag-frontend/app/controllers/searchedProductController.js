var app = angular.module('ivag');

app.controller("searchedProductController", function ($rootScope, $scope, $location, $routeParams, $http, shoppingCart, sessionService) {

    $scope.addToCart = function (productId) {
        shoppingCart.addEntry(productId);
    };

    $rootScope.isAdmin = sessionService.isHeAdmin();

    $scope.hasProducts = true;
    $scope.informUser = false;

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

    var search = function () {
        var input = $routeParams.value;
        if (input === "") {
            $scope.hasProducts = false;
            $scope.informUser = true;
        } else {
            $scope.hasProducts = true;
            $scope.informUser = false;

            $http({
                url: "http://localhost:7377/product" + "/getProductsFilteredByName",
                method: "GET",
                params: {"searchInput": input}
            }).then(function (response) {


                var result = response.data;

                if (typeof result != "undefined" && result != null && result.length != null && result.length > 0) {
                    $scope.hasProducts = true;
                    $scope.informUser = false;
                    $scope.products = response.data;
                } else {
                    $scope.hasProducts = false;
                    $scope.informUser = true;
                }
            });
        }
    };
    search();

    var slider = document.getElementById("myRange");
    var output = document.getElementById("demo");
    output.innerHTML = slider.value;

    slider.oninput = function () {
        output.innerHTML = this.value;
    };
    $scope.filterProducts = function () {

        var newArray = $scope.products.filter(function (el) {
            return el.price <= slider.value;
        });
        $scope.products = newArray;
        if($scope.products.length===0){
            $scope.hasProducts = false;
            $scope.informUser = true;
        }
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

    $scope.getNewProductPrice = function (priceOfProduct, discountOfProduct) {
        if(discountOfProduct === 0){
            return priceOfProduct;
        } else {
            var result = priceOfProduct - discountOfProduct / 100 * priceOfProduct;
            result = result.toFixed(2);
            return result;
        }
    }
});