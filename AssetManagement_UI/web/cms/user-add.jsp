<%@ include file="common/header.jsp" %>
<body>
    <section class="body">
        <%@ include file="common/menu.jsp" %>
        <!-- end: sidebar -->
        <section role="main" class="content-body">
            <header class="page-header">
                <h2>User Setting</h2>
            </header>
            <!-- start: page    -->
            <section class="panel">
                <header class="panel-heading">
                    <div class="panel-actions"></div>
                    <h2 class="panel-title">Tambah User Login</h2>
                </header>
                <div role="content">
                    <div class="row" >
                        <div class="col-sm-12">
                            <div class="well" style="padding:0px">
                                <div id="smart-form-register" class="smart-form" >
                                    <form class="form-horizontal form-bordered" method="POST" name="form-add" id="form-add" enctype="multipart/form-data">
                                        <table width="100%" style=""  border="0" cellpadding="0">
                                            <tbody>
                                                <tr>
                                                    <th>&nbsp;</th>
                                                    <th>&nbsp;</th>
                                                    <th>&nbsp;</th>
                                                    <th>&nbsp;</th>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Nama Lengkap</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="fullname" name="fullname">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Email</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="email" name="email" >
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Password</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="password" style="" id="pass" name="pass" value="">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr style="height:50px;">
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="2">
                                                        <center>
                                                            <button type="submit" id="btnSubmit" onclick="doSave();" class="mb-xs mt-xs mr-xs btn btn-default">Submit</button>
                                                            <button type="button" onclick="location.href = './user.jsp'" class="mb-xs mt-xs mr-xs btn btn-warning">Back</button>
                                                        </center>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </section>
    </section>
    <%@ include file="common/footer.jsp" %>
    <script type="text/javascript">              
        
        function doSave() {
            console.log("masuk function doSave");            
            $("#form-add").validate({
                rules: {
                    fullname : {
                        required: true
                    },
                    email : {
                        required: true,
                        email: true
                    },
                    pass : {
                        required: true
                    }
                },
                errorPlacement: function(error, element) {
                    error.insertBefore(element);
                },
                submitHandler: function(form) {
                    var attachFields = {
                        "doing"     : "user-add",
                        "fullName"  : $("#fullname").val(),
                        "email"     : $("#email").val(),
                        "pass"      : $("#pass").val()
                    };
                    AJAXPOST(
                        function doSubmit(data) {
                            try {
                                var obj = jQuery.parseJSON(data);
                                if(obj.status==1){
                                    location.href = "./user.jsp";
                                }else{
                                    $("#ModalMessageConfirm").modal('hide');
                                    $("#ModalMessageTitle").html(kvlAppVer);
                                    $("#ModalMessageContent").html("<p> Terjadi kesalahan pada saat penambahan user login </p>");
                                    $("#ModalMessage").modal('show');
                                }
                            }catch(e){
                                $("#ModalMessageConfirm").modal('hide');
                                $("#ModalMessageTitle").html(kvlAppVer);
                                $("#ModalMessageContent").html("<p> Error ! : " + e + " </p>");
                                $("#ModalMessage").modal('show');
                            }
                        },
                            "WebService", attachFields, false, true
                    );
                }
            });

        }
        ;


    </script>