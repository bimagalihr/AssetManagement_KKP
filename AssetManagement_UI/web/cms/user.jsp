<%@ include file="common/header.jsp" %>
<body>
    <section class="body">
        <%@ include file="common/menu.jsp" %>
        <section role="main" class="content-body">
            <header class="page-header">
                <h2>Setting > User</h2>
            </header>
            <!-- start: page    -->
            <section class="panel"> 
                <header class="panel-heading">
                    <div class="panel-actions"></div>
                    <h2 class="panel-title">List Data User</h2>
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
    //console.log('tokenssssssss : '+token);
    var UserOverviewColumns = 
        [{field:'fullName', title:'NAMA LENGKAP', width:50, align: 'center', sortable: true
        },{field:'email', title:'EMAIL', width:50, align: 'center', sortable: true
        },{field:'createDate', title:'TANGGAL BUAT', width:50, align: 'center', sortable: true
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
            [{text:'Tambah User Login', iconCls:'icon-add',
                handler:function() {
                    gridUpload("./user-add.jsp");
                }
            }
        ];

        $("#UserOverview").datagrid({
            url:kvlUrl+"WebService?doing=list-user-login",
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
            remoteFilter: true, 
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
        
    function TableUserReload(){    
        $("#UserOverview").datagrid({
                url:kvlUrl+"WebService?doing=list-user-login"
            });
    }
    
    function ButtonEdit(index){
        getId =  $('#UserOverview').datagrid("getRows")[index]["id"];   
        window.location.href= "user-edit.jsp?id="+getId;
    }
    
    function ButtonView(index){
        getId =  $('#UserOverview').datagrid("getRows")[index]["id"];   
        window.location.href= "User?url=view-user&id="+getId;
    }

    function ButtonDelete(index){
        $("#ModalMessageConfirmTitle").html(kvlAppVer);
        $("#ModalMessageConfirmContent").html("<p> Apakah anda yakin ingin hapus user login? </p>");
        $("#ModalMessageConfirm").modal('show');
        getId = $('#UserOverview').datagrid("getRows")[index]["id"];
    }
        
        $("#ModalMessageConfirmButton").click(function() {
            attachFields =  {
                "doing" : "user-delete",
                "id"    : getId
            }
            AJAXPOST(
                function doSubmit(data) {
                    try {
                        var obj = jQuery.parseJSON(data);
                        console.log('test suksesnya : '+obj.status)
                        if(obj.status == 1){
                            TableUserReload();
                            $("#ModalMessageConfirm").modal('hide');
                        }else{
                            $("#ModalMessageConfirm").modal('hide');
                            $("#ModalMessageTitle").html(kvlAppVer);
                            $("#ModalMessageContent").html("<p> Gagal hapus user </p>");
                            $("#ModalMessage").modal('show');
                            TableUserReload();
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
    