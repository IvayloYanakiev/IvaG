var app = angular.module('ivag');

app.controller("userPersonalDataController", function ($rootScope, $q, $scope, $location, $routeParams, $http, sessionService, addProfilePictureService) {

    $rootScope.isAuthenticated = sessionService.isLoggedIn();
    if ($rootScope.isAuthenticated) {
        $scope.currentFile = {};
        $scope.dataUpload = true;
        $scope.myFile = {};
        $scope.pictureUrl = "";

        $scope.address = "";
        $scope.putGender = function (gender) {
            if (gender == 1) {
                $scope.user.gender = "Male";
            }
            else $scope.user.gender = "Female";

        };

        var getData = function () {
            $http({
                url: "http://localhost:7377/user" + "/getUserPersonalData",
                method: "GET",
                params: {"id": sessionService.getSession()}
            }).then(function (response) {
                $scope.user = response.data;
            });

        };
        getData();


        $scope.updateAddress = function () {
            $scope.error = false;
            $http({
                url: "http://localhost:7377/address" + "/updateAddress",
                method: "PUT",
                params: {
                    "addressId": $scope.currentAddress.id,
                    "receiverName": $scope.currentAddress.receiverName,
                    "receiverPhone": $scope.currentAddress.receiverPhone,
                    "city": $scope.currentAddress.city,
                    "street": $scope.currentAddress.street,
                    "floor": $scope.currentAddress.floor
                }
            }).then(function (response) {
                $('#myModal4').modal('hide');
                getAddresses();
                $scope.currentAddress = "";
            }, function (error) {
                $scope.error = true;
                $scope.value = error.data;
            });
        };

        $scope.getAddress = function (addressId) {
            $scope.error = false;
            $http({
                url: "http://localhost:7377/address" + "/getAddress",
                method: "GET",
                params: {
                    "addressId": addressId
                }
            }).then(function (response) {
                $scope.currentAddress = response.data
            });
        };


        $scope.deleteAddress = function (addressId) {
            $http({
                url: "http://localhost:7377/address" + "/deleteAddress",
                method: "DELETE",
                params: {
                    "addressId": addressId
                }
            }).then(function () {
                $scope.addresses = $scope.addresses.filter(function( obj ) {
                    return obj.id !== addressId;
                });
            },function (error) {

            });
        };

        var getAddresses = function () {
            $http({
                url: "http://localhost:7377/address" + "/getAllAddresses",
                method: "GET",
                params: {"userId": sessionService.getSession()}
            }).then(function (response) {
                $scope.addresses = response.data;
            });

        };
        getAddresses();

        $scope.updateUserProfilePicture = function () {
            var file = $scope.myFile;
            var id = $scope.user.id;
            var uploadUrl = "http://localhost:7377/user/updateUserProfilePicture";
            addProfilePictureService.uploadFileToUrl(file, uploadUrl, id).then(function (result) {
                var url = result.data;
                $scope.user.pictureUrl  = url;
                $('#myModal').modal('hide');
            }, function (err) {
                $scope.pictureError = true;
                $scope.value = err.data;
            })

        };

        $scope.updateUserPersonalData = function () {
            $http({
                url: "http://localhost:7377/user" + "/updateUserPersonalData",
                method: "PUT",
                params: {
                    "id": $scope.user.id,
                    "name": $scope.user.name,
                    "email": $scope.user.email,
                    "gender": $scope.user.gender,
                    "phone": $scope.user.phone

                }
            }).then(function (response) {
                $('#myModal2').modal('hide');
            }, function (error) {
                $scope.error = true;
                $scope.value = error.data;
            });

        };

        $scope.resetPersonalData = function () {
            $('#myModal2').modal('hide');
            getData();
        };

        $scope.resetAddresses = function () {
            $('#myModal3').modal('hide');
            $('#myModal4').modal('hide');
            getAddresses();
            $scope.address = "";
        };
        $scope.resetError = function () {
            $scope.error = false;
        };

        $scope.address = {receiverName: "", receiverPhone: "", city: "", street: "", floor: ""};
        $scope.addAddress = function () {
            $scope.error = false;
            $http({
                url: "http://localhost:7377/address" + "/addAddress",
                method: "POST",
                params: {
                    "userId": sessionService.getSession(),
                    "receiverName": $scope.address.receiverName,
                    "receiverPhone": $scope.address.receiverPhone,
                    "city": $scope.address.city,
                    "street": $scope.address.street,
                    "floor": $scope.address.floor
                }
            }).then(function (response) {
                $('#myModal3').modal('hide');
                $scope.addresses.push(response.data);
                $scope.address = "";
            }, function (error) {
                $scope.error = true;
                $scope.value = error.data;
            });
        };


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
            $scope.resetPictureError = function () {
                $scope.pictureError = false;
            };

            function readURL(input) {
                if (input.files && input.files[0]) {
                    var reader = new FileReader();

                    reader.onload = function (e) {
                        $('#img-upload').attr('src', e.target.result);
                    }

                    reader.readAsDataURL(input.files[0]);
                }
            }

            $("#profileImg").change(function () {
                if (this.files && this.files[0]) {
                    $scope.pictureError = false;
                    var file = this.files[0];
                    var fileType = file.type;
                    var ValidImageTypes = ["image/gif", "image/jpeg", "image/png"];
                    if ($.inArray(fileType, ValidImageTypes) < 0) {
                        $scope.pictureError = true;
                        $scope.value = "Invalid file"
                    } else readURL(this);
                }
            });
        });
    } else $location.url("/login")
});