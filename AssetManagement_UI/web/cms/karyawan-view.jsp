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
                                                        <input class="form-control" type="text" style="" id="namaLengkap" name="namaLengkap" disabled>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Nomor HP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="nomorHp" name="nomorHp" disabled>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Email</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="email" style="" id="email" name="email" disabled>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>ID Karyawan</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="idKaryawan" name="idKaryawan" disabled>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Nomor KTP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="number" style="" id="nomorKtp" name="nomorKtp" disabled>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Alamat KTP</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="2">
                                                        <textarea style="height:100px;" class="form-control" id="alamatKtp" name="alamatKtp" disabled></textarea>
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
                                                    <td style="padding-bottom:5px;padding-left:10px;" colspan="2">
                                                        <center>
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
                            $("#id").val(obj.data[0].id);
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
        


    </script>