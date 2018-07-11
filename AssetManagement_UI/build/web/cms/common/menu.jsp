<!-- start: header -->
<header class="header">
    <div class="logo-container">
        <div  class="logo">
            <img src="assets/img/logo.png" height="35" />
        </div>
        <div class="title-dashboard"><h2>AssetManagement</h2></div>
        <div class="visible-xs toggle-sidebar-left" data-toggle-class="sidebar-left-opened" data-target="html" data-fire-event="sidebar-left-opened">
            <i class="fa fa-bars" aria-label="Toggle sidebar"></i>
        </div>
    </div>
    <!-- start: search & user box -->
    <div class="header-right">
        <div id="userbox" class="userbox">
            <a href="business-planner/#" data-toggle="dropdown">
                <div class="profile-info">
                    <span class="name" id="loginName"></span>
                </div>
                <i class="fa custom-caret"></i>
            </a>
            <div class="dropdown-menu">
                <ul class="list-unstyled">
                    <li class="divider"></li>                   
                    <li>
                        <a role="menuitem" tabindex="-1" href="./login.jsp" onclick="doLogout();"><i class="fa fa-power-off"></i> Logout</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <!-- end: search & user box -->
</header>
<!-- end: header -->

<div class="inner-wrapper">
    <!-- start: sidebar -->

    <aside id="sidebar-left" class="sidebar-left">
        <div class="sidebar-header">
            <div class="sidebar-title">
                Navigation
            </div>
            <div class="sidebar-toggle hidden-xs" >
            </div>
        </div>

        <div class="nano">
            <div class="nano-content">
                <nav id="menu" class="nav-main" role="navigation">
                    <ul class="nav nav-main">
                        <li class="nav-parent" id="agentProfile">
                            <a>
                                <i class="fa fa-user" aria-hidden="true"></i>
                                <span>KARYAWAN</span>
                            </a>
                            <ul class="nav nav-children">
                                <li> 
                                    <a href="Karyawan?url=add-karyawan">
                                        <i class="fa fa-border" aria-hidden="true">
                                        </i>Tambah Karyawan
                                    </a> 
                                </li>
                            </ul>
                            <ul class="nav nav-children">
                                <li> 
                                    <a href="Karyawan?url=list-karyawan">
                                        <i class="fa fa-border" aria-hidden="true">
                                        </i>List Karyawan
                                    </a> 
                                </li>
                            </ul>
                        </li> 
                        <li class="nav-parent" id="agentProfile">
                            <a>
                                <i class="fa fa-user" aria-hidden="true"></i>
                                <span>ASSET</span>
                            </a>
                            <ul class="nav nav-children">
                                <li>
                                    <a href="Asset?url=add-asset" id="user">
                                        <i class="fa fa-border" aria-hidden="true">
                                        </i>Tambah Barang
                                    </a>
                                </li> 
                                <li>
                                    <a href="Asset?url=list-asset" id="user">
                                        <i class="fa fa-border" aria-hidden="true">
                                        </i>List Barang
                                    </a>
                                </li> 
                            </ul>
                        </li>
                        <li class="nav-parent" id="setting">
                            <a>
                                <i class="fa SETTING" aria-hidden="true"></i>
                                <span>SETTING</span>
                            </a>
                            <ul class="nav nav-children">
                                <li> 
                                    <a href="User?url=list-user">
                                        <i class="fa User" aria-hidden="true">
                                        </i>User Login
                                    </a> 
                                </li>
                            </ul>
                        </li>

                    </ul>
                </nav>
            </div>
        </div>
    </aside>