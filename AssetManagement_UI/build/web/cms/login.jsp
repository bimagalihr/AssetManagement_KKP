<%@ include file="common/header.jsp" %>
<body>
    <!-- start: page -->
    <section class="body-sign">
        <div class="center-sign">
            <div class="panel panel-sign">
                <div class="panel-body">
                    <div class="am-logo-dashboard"></div>
                    <div class="header-title">
                        <h2>Asset Management</h2>
                    </div>
                    <form novalidate="novalidate" method="post" id="login-form" autocomplete="off">
                        <input id="action" type="hidden" name="action" value="login">
                        <div class="form-group mb-lg">
                            <label>Email</label>
                            <div class="input-group input-group-icon">
                                <input name="username" type="text" id="username" class="form-control input-lg" value=""/>
                                <span class="input-group-addon">
                                    <span class="icon icon-lg">
                                        <i class="fa fa-envelope"></i>
                                    </span>
                                </span>
                            </div>
                        </div>

                        <div class="form-group mb-lg">
                            <div class="clearfix">
                                <label class="pull-left">Password</label>
                            </div>
                            <div class="input-group input-group-icon">
                                <input name="pwd" type="password" id="pwd" class="form-control input-lg" value=""/>
                                <span class="input-group-addon">
                                    <span class="icon icon-lg">
                                        <i class="fa fa-lock"></i>
                                    </span>
                                </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <button type="submit" class="btn btn-primary hidden-xs">Sign In</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <p class="text-center text-muted mt-md mb-md">&copy; Copyright 2018. AssetManagement, All Rights Reserved.</p>
        </div>
    </section>
    <!-- end: page -->
    <!-- Modal -->
    <div id="ModalMessage" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="ModalMessageTitle"></h4>
                </div>
                <div class="modal-body" id="ModalMessageContent">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal Confirm -->
    <div id="ModalMessageConfirm" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" id="ModalMessageConfirmTitle"></h4>
                </div>
                <div class="modal-body" id="ModalMessageConfirmContent">
                </div>
                <div class="modal-footer">
                    <button id="ModalMessageConfirmButton" type="button" class="btn btn-default">Yes</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Vendor -->
    <script src="assets/vendor/jquery/jquery.js"></script>
    <script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
    <script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
    <script src="assets/vendor/jquery-validate/jquery.validate.min.js"></script>
    <script src="assets/javascripts/conf.js"></script>
    <script src="assets/javascripts/function.js"></script>
    
    <script type="text/javascript"> 
        $(function() {
            $("#login-form").submit(function() {
                return false;
            });

            $("#login-form").validate({
                rules: {
                    username: {
                        required: true
                    },
                    pwd: {
                        required: true
                    }
                },
                messages: {
                    username: {
                        required: 'Username tidak boleh kosong !'
                    },
                    pwd: {
                        required: 'Password tidak boleh kosong !'
                    }
                },
                errorPlacement: function(error, element) {
                    error.insertBefore(element.parent());
                },
                submitHandler: function(form) {
                    var attachFields = {
                        "doing": "login",
                        "emailLogin": $("#username").val(),
                        "passwordLogin": $("#pwd").val()
                    }
                    AJAXPOST(
                            function doSubmit(data) {
                                var obj = jQuery.parseJSON(data);
                                var message = obj.message;
                                var description = obj.description;
                                if (obj.status == 1) {
                                    location.href = "./";
                                } else {
                                    $("#ModalMessageTitle").html("<center><font color='red'>Login Error</font></center>");
                                    $("#ModalMessageContent").html("<center><p> User email atau password salah</p></center>");
                                    $("#ModalMessage").modal('show');
                                }
                            },
                                "WebService", attachFields, false, true
                            );

                }
            });
        });
    </script>
</body>
</html>