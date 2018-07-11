
<!-- Vendor -->
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
<script src="assets/vendor/jquery-browser-mobile/jquery.browser.mobile.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.file-input.js"></script>
<script src="assets/vendor/nanoscroller/nanoscroller.js"></script>
<script src="assets/vendor/jquery-validate/jquery.validate.min.js"></script>
<script src="assets/javascripts/conf.js"></script>
<script src="assets/javascripts/function.js"></script>
<script src="assets/javascripts/accounting.js"></script>
<script type="text/javascript" src="assets/vendor/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="assets/vendor/jeasyui/jquery.easyui.mobile.js"></script>
<script type="text/javascript" src="assets/vendor/jeasyui/datagrid-filter.js"></script>
<script type="text/javascript" src="assets/javascripts/moment.js"></script>
<script type="text/javascript" src="assets/vendor/modernizr/modernizr.js"></script>
<script type="text/javascript" src="assets/vendor/bootstrap-datepicker/bootstrap-datepicker.js"></script>

<!-- Theme Base, Components and Settings -->
<script src="assets/javascripts/theme.js"></script>
</body>
</html>
<script>         
    var getToken;
    function doGetUser() {
        var attachFields = {
            "doing": "infoLogin"
        }
        AJAXPOST(
                function doSubmit(data) {
                    var obj = jQuery.parseJSON(data);
                    if (obj.status == 1) {
                        console.log(obj);
                        for (var i = obj.data.length - 1; i >= 0; i--) {
                            var name = obj.data[i].fullNameLogin;
                            getToken = obj.data[i].token;
                            console.log('getToken : '+getToken);
                            console.log(obj.data[i]);
                            if (name != "") {
                                $("#loginName").html(name);
                            }
                        }
                    } else {
                        location.href="./login.jsp";
                    }

                },
                    "WebService", attachFields, false, true
                );

    }

    function doLogout() {
        console.log("masuk doLogout");
        var attachFields = {
            "doing": "logout"
        }
        AJAXPOST(
                function doSubmit(data) {
                    var obj = jQuery.parseJSON(data);
                    if (obj.status == 1) {
                        location.href = "./login.jsp";
                    } else {
                        alert("Error Logout");
                    }
                },
                    "WebService", attachFields, false, true
                );

    }

    $(document).ready(function () {
        doGetUser();
    });
</script>