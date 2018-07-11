<%@ include file="common/header.jsp" %>
<body>
    <section class="body">
        <%@ include file="common/menu.jsp" %>
        <section role="main" class="content-body">
            <header class="page-header">
                <h2>Karyawan > List Karyawan</h2>
            </header>
            <!-- start: page    -->
            <section class="panel"> 
                <header class="panel-heading">
                    <div class="panel-actions"></div>
                    <h2 class="panel-title">List Data Karyawan</h2>
                </header>
                <div id="inbox-content" class="inbox-body">
                    <div class="row" style="padding:10px;">
                        <table id="UserOverview"></table>
                    </div>
                </div>
                <div id="tableDownload" style="display:none"></div>
            </section>
        </section>
    </section>
    <%@ include file="common/footer.jsp" %>
    <script type="text/javascript">
    var getId;        
    var UserOverviewColumns = 
        [{field:'namaLengkap', title:'NAMA LENGKAP', width:50, align: 'center', sortable: true
        },{field:'idKaryawan', title:'ID KARYAWAN', width:50, align: 'center', sortable: true
        },{field:'email', title:'EMAIL', width:50, align: 'center', sortable: true
        },{field:'createDate', title:'TANGGAL UPDATE', width:50, align: 'center', sortable: true
        },{field:'id',title:'KONSOL',width:50,align:'center', sortable: true,
        formatter:function(value,row,index){
            
            var a = "<img title=\"View\" src=\""+kvlEasyuiIconURL+"_search.png\" height=16 width=16 onclick=\"ButtonView('"+index+"');\" style=\"cursor:pointer;\">&nbsp;";
            var b = "<img title=\"Edit\" src=\""+kvlEasyuiIconURL+"pencil.png\" height=16 width=16 onclick=\"ButtonEdit("+index+");\" style=\"cursor:pointer;\">&nbsp;";
            var c = "<img title=\"Delete\" src=\""+kvlEasyuiIconURL+"cancel.png\" height=16 width=16 onclick=\"ButtonDelete("+index+");\" style=\"cursor:pointer;\">&nbsp;";
            
        return a+b+c;   
        }
        
        }];

    var UserOverviewToolbar;
        UserOverviewToolbar = 
            [{text:'Tambah Karyawan', iconCls:'icon-add',
                handler:function() {
                    gridUpload("Karyawan?url=add-karyawan");
                }
            }
        ];

        $("#UserOverview").datagrid({
            url:kvlUrl+"WebService?doing=list-data-karyawan",
            method: 'GET',
            columns:[UserOverviewColumns],
            toolbar: UserOverviewToolbar,
            remoteSort: false,
            selectOnCheck: true,
            singleSelect: true,
            fitColumns: true,
            rownumbers: true,
            autoRowHeight: false,
            pagination: true,
            pageSize: 50,
            width: "100%",
            remoteFilter: true, //disini
            rownumbers: true
        });

        $('#UserOverview').datagrid('enableFilter', [{
            field: 'createDate',
            type: 'label'
        }, {
            field: 'id',
            type: 'label'
        }]
            );
        
    function TableKaryawanReload(){    
        $("#UserOverview").datagrid({
                url:kvlUrl+"WebService?doing=list-data-karyawan"
            });
    }
    
    function ButtonEdit(index){
        getId =  $('#UserOverview').datagrid("getRows")[index]["id"];   
        window.location.href= "Karyawan?url=edit-karyawan&id="+getId;
    }
    
    function ButtonView(index){
        getId =  $('#UserOverview').datagrid("getRows")[index]["id"];   
        window.location.href= "Karyawan?url=view-karyawan&id="+getId;
    }

    function ButtonDelete(index){
        $("#ModalMessageConfirmTitle").html(kvlAppVer);
        $("#ModalMessageConfirmContent").html("<p> Apakah anda yakin ingin hapus data karyawan? </p>");
        $("#ModalMessageConfirm").modal('show');
        getId = $('#UserOverview').datagrid("getRows")[index]["id"];
    }
        
        $("#ModalMessageConfirmButton").click(function() {
            console.log("getId ::: "+getId);
            attachFields =  {
                "doing" : "karyawan-delete",
                "id"    : getId
            }
            AJAXPOST(
                function doSubmit(data) {
                    try {
                        var obj = jQuery.parseJSON(data);
                        if(obj.status == 1){
                            TableKaryawanReload();
                            $("#ModalMessageConfirm").modal('hide');
                        }else{
                            $("#ModalMessageConfirm").modal('hide');
                            $("#ModalMessageTitle").html(kvlAppVer);
                            $("#ModalMessageContent").html("<p> Gagal hapus data karyawan </p>");
                            $("#ModalMessage").modal('show');
                            TableKaryawanReload();
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
        });
        
    </script>