<%@ include file="common/header.jsp" %>
<body>
    <section class="body">
        <%@ include file="common/menu.jsp" %>
        <!-- end: sidebar -->
        <section role="main" class="content-body">
            <header class="page-header">
                <h2>Karyawan > Tambah Karyawan</h2>
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
                                                        <input class="form-control" type="text" style="" id="namaLengkap" name="namaLengkap">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Nomor HP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="nomorHp" name="nomorHp">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Email</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="email" style="" id="email" name="email">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>ID Karyawan</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="idKaryawan" name="idKaryawan">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Nomor KTP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="nomorKtp" name="nomorKtp" >
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Alamat KTP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="2">
                                                        <textarea style="height:100px;" class="form-control" id="alamatKtp" name="alamatKtp"></textarea>
                                                    </td>
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
                                                            <button type="button" onclick="location.href = 'Karyawan?url=list-karyawan'" class="mb-xs mt-xs mr-xs btn btn-warning">Back</button>
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
        
    var getId = getParameter("id");
            
        function GetData(){
            attachFields =  {
                "doing" : "data-edit-karyawan",
                "id" : getId
            }
            AJAXGET(
                function doSubmit(data) {
                    try {
                        var obj = jQuery.parseJSON(data);
                        if(obj.status==1){
                            $("#namaLengkap").val(obj.data[0].namaLengkap);
                            $("#nomorHp").val(obj.data[0].nomorHp);
                            $("#email").val(obj.data[0].email);
                            $("#idKaryawan").val(obj.data[0].idKaryawan);
                            $("#nomorKtp").val(obj.data[0].nomorKtp);
                            $("#alamatKtp").val(obj.data[0].alamatKtp);
                        }else{
                            $("#ModalMessageConfirm").modal('hide');
                            $("#ModalMessageTitle").html(kvlAppVer);
                            $("#ModalMessageContent").html("<p>Tidak ada data atau terjadi kesalahan </p>");
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
        GetData();
        
        function doSave() {
            $("#form-add").validate({
                rules: {
                    namaLengkap : {
                        required: true
                    },
                    nomorHp     : {
                        required: true
                    },
                    email       : {
                        required: true,
                        email   : true
                    },
                    idKaryawan  : {
                        required: true
                    },
                    nomorKtp    : {
                        required: true
                    },
                    alamatKtp   : {
                        required: true
                    }
                    
                },
                errorPlacement: function(error, element) {
                    error.insertBefore(element);
                },
                submitHandler: function(form) {
                    var attachFields = {
                        "id"            : getId,
                        "doing"         : "karyawan-edit",
                        "namaLengkap"   : $("#namaLengkap").val(),
                        "nomorHp"       : $("#nomorHp").val(),
                        "email"         : $("#email").val(),
                        "idKaryawan"    : $("#idKaryawan").val(),
                        "nomorKtp"      : $("#nomorKtp").val(),
                        "alamatKtp"     : $("#alamatKtp").val()
                    };
                    AJAXPOST(
                        function doSubmit(data) {
                            try {
                                var obj = jQuery.parseJSON(data);
                                if(obj.status==1){
                                    location.href = "Karyawan?url=list-karyawan";
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