var app = angular.module('ivag', ['angularUtils.directives.dirPagination', 'ngRoute']);

app.config(function ($routeProvider) {

    $routeProvider
        .when('/logout', {
            templateUrl: 'views/logout.html',
            controller: 'logoutController'
        })
        .when('/home', {
            templateUrl: 'views/home.html',
            controller: 'homeController',
            css: 'css/home.css'
        })
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'loginController'
        })
        .when('/register', {
            templateUrl: 'views/register.html',
            controller: 'registerController'
        })
        .when('/userPersonalData', {
            templateUrl: 'views/userPersonalData.html',
            controller: 'userPersonalDataController',
            css: 'css/userPersonalData.css'
        })
        .when('/activateAccount/:token', {
            templateUrl: 'views/activateAccount.html',
            controller: 'activateAccountController'
        })
        .when('/shoppingCart', {
            templateUrl: 'views/shoppingCart.html',
            controller: 'shoppingCartController',
            css: 'css/shoppingCart.css'
        })
        .when('/addProduct', {
            templateUrl: 'views/addProduct.html',
            controller: 'addProductController',
            css: 'css/addProduct.css'
        })
        .when('/products/:id', {
            templateUrl: 'views/innerCategoryProducts.html',
            controller: 'innerCategoryProducts'
        })
        .when('/product/:id', {
            templateUrl: 'views/productDetails.html',
            controller: 'productDetailsController'
        })
        .when('/checkoutShoppingCart', {
            templateUrl: 'views/checkoutShoppingCart.html',
            controller: 'checkoutShoppingCartController'
        })
        .when('/searchedProduct/:value', {
            templateUrl: 'views/searchedProduct.html',
            controller: 'searchedProductController'
        })
        .when('/info', {
            templateUrl: 'views/ivagInfo.html'
        })
        .otherwise('/home');


});

app.factory('sessionService', function () {

    var session = {};
    session.getSession = function () {
        return localStorage.getItem("session");
    };
    session.login = function (userId) {
        localStorage.setItem("session", userId);
    };

    session.setAdmin = function (user) {
        if (user.type == 1) {
            localStorage.setItem("isAdmin", "1");
        }

    };
    session.isHeAdmin = function () {
        return localStorage.getItem("isAdmin") != null;
    };

    session.logout = function () {
        localStorage.removeItem("session");
        localStorage.removeItem("isAdmin");
    };

    session.isLoggedIn = function () {
        return localStorage.getItem("session") != null;
    };

    return session;
});


app.factory('shoppingCart', function () {

    var existingEntries = [];
    existingEntries.getEntries = function () {
        return localStorage.getItem("allEntries");
    };
    existingEntries.isNotEmpty = function () {
        return localStorage.getItem("allEntries")!=null && localStorage.getItem("allEntries").length > 2;
    };
    existingEntries.addEntry = function (productId) {
        // Parse any JSON previously stored in allEntries
        var existingEntries = JSON.parse(localStorage.getItem("allEntries"));
        if (existingEntries == null) existingEntries = [];
        localStorage.setItem("entry", JSON.stringify(productId));
        // Save allEntries back to local storage
        existingEntries.push(productId);
        localStorage.setItem("allEntries", JSON.stringify(existingEntries));
    };

    existingEntries.cleanShoppingCart = function () {
        var existingEntries = [];
        localStorage.set("allEntries", JSON.stringify(existingEntries));
    };

    existingEntries.removeEntry = function (productId) {

        var arr = JSON.parse(localStorage.getItem("allEntries"));
        if (arr != null) {
            var index = arr.indexOf(productId);
            if (index > -1) {

                arr.splice(index, 1);//remove first
            }
            localStorage.setItem("allEntries", JSON.stringify(arr));
        }


    };

    return existingEntries;

})
;

app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function () {
                scope.$apply(function () {
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

app.service('productUploadService', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl, product) {
        var fd = new FormData();
        fd.append('name', product.name);
        fd.append('categoryId', product.category);
        fd.append('price', product.price);
        fd.append('quantity', product.quantity);
        fd.append('description', product.description);
        fd.append('picture', file);
        fd.append('discount', product.discount);
        return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function (response) {

                responseData = response;
                deffered.resolve(response);
                return deffered.promise;
            })
            .error(function (error) {
                deffered.reject(error);
                return deffered.promise;
            });

    };

    this.getResponse = function () {
        return responseData;
    }

}]);

app.service('addProfilePictureService', ['$q', '$http', function ($q, $http) {
    var deffered = $q.defer();
    var responseData;
    this.uploadFileToUrl = function (file, uploadUrl, id) {
        var fd = new FormData();
        fd.append('id', id);
        fd.append('picture', file);
        return $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .success(function (response) {

                responseData = response;
                deffered.resolve(response);
                return deffered.promise;
            })
            .error(function (error) {
                deffered.reject(error);
                return deffered.promise;
            });

    };

    this.getResponse = function () {
        return responseData;
    }

}]);
