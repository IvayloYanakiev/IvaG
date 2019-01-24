var app = angular.module('ivag');

app.controller("addProductController", function ($rootScope, $q, $scope, $location, $routeParams, $http, sessionService, productUploadService) {

    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    if($rootScope.isAuthenticated){
        $scope.myFile = "";

        $scope.addProduct = function () {
            var file = $scope.myFile;
            $scope.success = false;
            $scope.error = false;
            var uploadUrl = "http://localhost:7377/admin/addProduct";
            productUploadService.uploadFileToUrl(file, uploadUrl, $scope.product).then(function (result) {
                $location.url("/");
            }, function (err) {
                $scope.error = true;
                $scope.value = err.data;
            })


        };
        $scope.product = {name:"",category:"",price:"",quantity:"",description:"", discount:""};
        $http({
            url: "http://localhost:7377/category" + "/getAllCategories",
            method: "GET"
        }).then(function (response) {
            $scope.categories = response.data;
        },function(error){

        });


        $(document).ready(function () {
            $(document).on('change', '.btn-file :file', function () {
                var input = $(this),
                    label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                input.trigger('fileselect', [label]);
            });

            $('.btn-file :file').on('fileselect', function (event, label) {

                var input = $(this).parents('.input-group').find(':text'),
                    log = label;

                if (input.length) {
                    input.val(log);
                } else {
                }

            });

            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#img-upload').attr('src', e.target.result);
                    };

                    reader.readAsDataURL(input.files[0]);
                }
            }

            $("#imgInp").change(function () {
                if (this.files && this.files[0]) {
                    $scope.error = false;
                    var file = this.files[0];
                    var fileType = file.type;
                    var ValidImageTypes = ["image/gif", "image/jpeg", "image/png"];
                    if ($.inArray(fileType, ValidImageTypes) < 0) {
                        $scope.error = true;
                        $scope.value = "Invalid file"
                    } else readURL(this);
                }
            });
        });
    } else $location.url("/login");

});