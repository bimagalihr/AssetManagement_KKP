<%@ include file="common/header.jsp" %>
<body>
    <section class="body">
        <%@ include file="common/menu.jsp" %>
        <!-- end: sidebar -->
        <section role="main" class="content-body">
            <header class="page-header">
                <h2>Asset > Tambah Barang</h2>
            </header>
            <!-- start: page    -->
            <section class="panel">
                <header class="panel-heading">
                    <div class="panel-actions"></div>
                    <h2 class="panel-title">Tambah Asset Barang</h2>
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
                                                        <b>Pilih Karyawan</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <select class="form-control" id="pilihKaryawan" name="pilihKaryawan">
                                                            <option value="">Pilih Nama Karyawan</option>                                                        
                                                        </select>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Nama Barang</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="namaBarang" name="namaBarang">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Merek</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="merek" name="merek">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:200px;" colspan="">
                                                        <b>Tipe</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="tipe" name="tipe">
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Nomor Seri</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input class="form-control" type="text" style="" id="noSeri" name="noSeri" >
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Jumlah</b>
                                                    </td>
                                                    <td style="padding-bottom:10px;padding-left:10px;width:400px;" colspan="">
                                                        <input style="width:100px;" class="form-control" type="number" style="" id="jumlah" name="jumlah" >
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>                                                
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="">
                                                        <b>Tanggal Kadaluarsa</b>
                                                    </td>
                                                    <td style="padding-bottom:15px;padding-left:10px;width:400px;" colspan="">
                                                        <div style="width: 200px;" class='input-group date' >
                                                            <input style="width: 200px;" type='text' class="form-control" id="tglKadaluarsa" name="tglKadaluarsa"/>
                                                            <span class="input-group-addon">
                                                                <span class="fa fa-calendar"></span>
                                                            </span>
                                                        </div>
                                                    </td>
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>                                                
                                                <tr style="height:50px;">
                                                    <td>&nbsp;</td>
                                                    <td>&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td style="padding-bottom:10px;padding-left:10px;" colspan="2">
                                                        <center>
                                                            <button type="submit" id="btnSubmit" onclick="doSave();" class="mb-xs mt-xs mr-xs btn btn-default">Submit</button>
                                                            <button type="button" onclick="location.href = 'Asset?url=list-asset'" class="mb-xs mt-xs mr-xs btn btn-warning">Back</button>
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
        
        $(function() {
            $('#tglKadaluarsa').datepicker();
        });
        
        function GetData(){
            attachFields =  {
                "doing" : "list-all-data-karyawan"
            }
            AJAXGET(
                function doSubmit(data) {
                    try {
                        var obj = jQuery.parseJSON(data);
                        var i;
                        switch(obj.status) {
                            case 1:
                                for(i = 0; i < obj.data.length; i++){                                
                                    var option      = document.createElement("option");
                                    var select      = document.getElementById("pilihKaryawan"); 
                                    option.text     = obj.data[i].namaLengkap;
                                    option.value    = obj.data[i].id;        
                                    select.appendChild(option);
                                }     
                                break;                            
                            default:
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
            Date.prototype.toYYYYMMDD = function (s) {return [this.getFullYear(), this.getMonth() < 9 ? '0' + (this.getMonth() + 1) : this.getMonth() + 1, this.getDate() < 10 ? '0' + this.getDate() : this.getDate()].join (s || '')}
            var tempTglKadaluarsa = $("#tglKadaluarsa").val();
            var tglKadaluarsa = new Date (tempTglKadaluarsa).toYYYYMMDD();
            console.log(tglKadaluarsa);
            
            $("#form-add").validate({
                rules: {
                    pilihKaryawan : {
                        required: true
                    },
                    namaBarang     : {
                        required: true
                    },
                    merek       : {
                        required: true
                    },
                    tipe  : {
                        required: true
                    },
                    noSeri    : {
                        required: true
                    },
                    jumlah   : {
                        required: true
                    },
                    tglKadaluarsa   : {
                        required: true
                    }
                    
                },
                errorPlacement: function(error, element) {
                    error.insertBefore(element);
                },
                submitHandler: function(form) {
                    var attachFields = {
                        "doing"         : "barang-add",
                        "pilihKaryawan" : $("#pilihKaryawan").val(),
                        "namaBarang"    : $("#namaBarang").val(),
                        "merek"         : $("#merek").val(),
                        "tipe"          : $("#tipe").val(),
                        "noSeri"        : $("#noSeri").val(),
                        "jumlah"        : $("#jumlah").val(),
                        "tglKadaluarsa" : tglKadaluarsa
                    };
                    AJAXPOST(
                        function doSubmit(data) {
                            try {
                                var obj = jQuery.parseJSON(data);                                
                                switch(obj.status){
                                    case 1:
                                        location.href = "Asset?url=list-asset";
                                        break;
                                    case -1:
                                        $("#ModalMessageConfirm").modal('hide');
                                        $("#ModalMessageTitle").html(kvlAppVer);
                                        $("#ModalMessageContent").html("<p>Tanggal kadaluarsa harus melebihi tanggal sekarang </p>");
                                        $("#ModalMessage").modal('show');
                                        break;
                                    default:
                                        $("#ModalMessageConfirm").modal('hide');
                                        $("#ModalMessageTitle").html(kvlAppVer);
                                        $("#ModalMessageContent").html("<p> Terjadi kesalahan pada saat penambahan barang </p>");
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