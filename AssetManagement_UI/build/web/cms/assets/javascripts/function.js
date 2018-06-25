$.fn.extend({

    //pass the options variable to the function
    jarvismenu : function(options) {

        var defaults = {
            accordion : 'true',
            speed : 200,
            closedSign : '[+]',
            openedSign : '[-]'
        };

        // Extend our default options with those provided.
        var opts = $.extend(defaults, options);
        //Assign current element to variable, in this case is UL element
        var $this = $(this);

        //add a mark [+] to a multilevel menu
        $this.find("li").each(function() {
            if ($(this).find("ul").size() != 0) {
                //add the multilevel sign next to the link
                $(this).find("a:first").append("<b class='collapse-sign'>" + opts.closedSign + "</b>");

                //avoid jumping to the top of the page when the href is an #
                if ($(this).find("a:first").attr('href') == "#") {
                    $(this).find("a:first").click(function() {
                        return false;
                    });
                }
            }
        });

        //open active level
        $this.find("li.active").each(function() {
            $(this).parents("ul").slideDown(opts.speed);
            $(this).parents("ul").parent("li").find("b:first").html(opts.openedSign);
            $(this).parents("ul").parent("li").addClass("open")
        });

        $this.find("li a").click(function() {

            if ($(this).parent().find("ul").size() != 0) {

                if (opts.accordion) {
                    //Do nothing when the list is open
                    if (!$(this).parent().find("ul").is(':visible')) {
                        parents = $(this).parent().parents("ul");
                        visible = $this.find("ul:visible");
                        visible.each(function(visibleIndex) {
                            var close = true;
                            parents.each(function(parentIndex) {
                                if (parents[parentIndex] == visible[visibleIndex]) {
                                    close = false;
                                    return false;
                                }
                            });
                            if (close) {
                                if ($(this).parent().find("ul") != visible[visibleIndex]) {
                                    $(visible[visibleIndex]).slideUp(opts.speed, function() {
                                        $(this).parent("li").find("b:first").html(opts.closedSign);
                                        $(this).parent("li").removeClass("open");
                                    });

                                }
                            }
                        });
                    }
                }// end if
                if ($(this).parent().find("ul:first").is(":visible") && !$(this).parent().find("ul:first").hasClass("active")) {
                    $(this).parent().find("ul:first").slideUp(opts.speed, function() {
                        $(this).parent("li").removeClass("open");
                        $(this).parent("li").find("b:first").delay(opts.speed).html(opts.closedSign);
                    });

                } else {
                    $(this).parent().find("ul:first").slideDown(opts.speed, function() {
                        /*$(this).effect("highlight", {color : '#616161'}, 500); - disabled due to CPU clocking on phones*/
                        $(this).parent("li").addClass("open");
                        $(this).parent("li").find("b:first").delay(opts.speed).html(opts.openedSign);
                    });
                } // end else
            } // end if
        });
    } // end function
});

function updateQueryStringParameter(uri, key, value) {
	var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
	var separator = uri.indexOf('?') !== -1 ? "&" : "?";
	if (uri.match(re)) {
		return uri.replace(re, '$1' + key + "=" + value + '$2');
	} else {
		return uri + separator + key + "=" + value;
	}
}

function AJAXGET(successFunc, module, attach, async, debug){
	try {
		if (typeof attach === "undefined"){
			attach="";
		}
		if (typeof async === "undefined"){
			async=true;
		}
		if (typeof debug === "undefined"){
			debug=false;
		}
		if (debug){
			console.log(kvlUrl+module+'?'+encodeURIComponent(attach));
		}
		jQuery.ajax({
			type: "GET",
			url: kvlUrl+module,
			data : attach,
			cache: false,
			async: async,
			beforeSend: function(data) {
				//Loading Modal
			},
			success: successFunc,
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				//Error Modal
				//alert(textStatus + " " + errorThrown);
			}
		});
	}catch(e){
		alert("AJAXGET -  " + e);
	}
}


function AJAXPOST(successFunc, module, attach, async, debug){
//    console.log(successFunc);
	try {
		if (typeof attach === "undefined"){
			attach="";
		}
		if (typeof async === "undefined"){
			async=true;
		}
		if (typeof debug === "undefined"){
			debug=false;
		}
		if (debug){
			console.log(kvlUrl+module+'?'+encodeURIComponent(attach))
		}
		jQuery.ajax({
			type: "POST",
			url: kvlUrl+module,
			data : attach,
			cache: false,
			async: async,
			beforeSend: function(data) {
				//Loading Modal
//                                alert('before send');
			},
			success: successFunc,
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				//Error Modal
				//alert(textStatus + " " + errorThrown);
			}
		});
	}catch(e){
		alert("AJAXPOST -  " + e);
	}
}

function getParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.split('?')[1]),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
}

function urlActionParameter(id) {
	var url = updateQueryStringParameter(window.location.href, "action", id);
	var page = kvlAppVer;
	if (typeof (history.pushState) != "undefined") {
		var obj = { Page: page, Url: url };
		history.pushState(obj, obj.Page, obj.Url);
	} else {

		/*$("#ModalMessageTitle").html(kvlAppVer);
		$("#ModalMessageContent").html("<p>urlActionParameter - Browser does not support HTML5</p>");
		$("#ModalMessage").modal('show');*/
	}
}



function pagerFilter(data){
	if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array
		data = {
			total: data.length,
			rows: data
		}
	}
	var dg = $(this);
	var opts = dg.datagrid('options');
	var pager = dg.datagrid('getPager');							
	pager.pagination({
		onSelectPage:function(pageNum, pageSize){
			opts.pageNumber = pageNum;
			opts.pageSize = pageSize;
			pager.pagination('refresh',{
				pageNumber:pageNum,
				pageSize:pageSize
			});
			//inventory_reloaditemgrid();
			dg.datagrid('loadData',data);
		}
	});
	if (!data.originalRows){
		data.originalRows = (data.rows);
	}
	var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
	var end = start + parseInt(opts.pageSize);
	data.rows = (data.originalRows.slice(start, end));
	return data;
}

function gridUpload(Location){

	window.location.href = Location;
}
function gridEdit(id, Location){
 	window.location.href = Location;
}


function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : evt.keyCode
    return !(charCode > 31 && (charCode < 48 || charCode > 57));
}

 