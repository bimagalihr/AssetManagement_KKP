
window.theme = {};

// Navigation
(function( $ ) {

	'use strict';

	var $items = $( '.nav-main li.nav-parent' );

	function expand( $li ) {
		$li.children( 'ul.nav-children' ).slideDown( 'fast', function() {
			$li.addClass( 'nav-expanded' );
			$(this).css( 'display', '' );
			ensureVisible( $li );
		});
	}

	function collapse( $li ) {
		$li.children('ul.nav-children' ).slideUp( 'fast', function() {
			$(this).css( 'display', '' );
			$li.removeClass( 'nav-expanded' );
		});
	}

	function ensureVisible( $li ) {
		var scroller = $li.offsetParent();
		if ( !scroller.get(0) ) {
			return false;
		}

		var top = $li.position().top;
		if ( top < 0 ) {
			scroller.animate({
				scrollTop: scroller.scrollTop() + top
			}, 'fast');
		}
	}

	$items.find('> a').on('click', function( ev ) {

		var $anchor = $( this ),
			$prev = $anchor.closest('ul.nav').find('> li.nav-expanded' ),
			$next = $anchor.closest('li');

		if ( $anchor.prop('href') ) {
			var arrowWidth = parseInt(window.getComputedStyle($anchor.get(0), ':after').width, 10) || 0;
			if (ev.offsetX > $anchor.get(0).offsetWidth - arrowWidth) {
				ev.preventDefault();
			}
		}

		if ( $prev.get( 0 ) !== $next.get( 0 ) ) {
			collapse( $prev );
			expand( $next );
		} else {
			collapse( $prev );
		}
	});


}).apply( this, [ jQuery ]);

// Skeleton
(function(theme, $) {

	'use strict';

	theme = theme || {};

	var $body		= $( 'body' ),
		$html		= $( 'html' ),
		$window		= $( window ),
		isAndroid	= navigator.userAgent.toLowerCase().indexOf('android') > -1;

	// mobile devices with fixed has a lot of issues when focus inputs and others...
	if ( typeof $.browser !== 'undefined' && $.browser.mobile && $html.hasClass('fixed') ) {
		$html.removeClass( 'fixed' ).addClass( 'scroll' );
	}

	var Skeleton = {

		options: {
			sidebars: {
				menu: '#content-menu',
				left: '#sidebar-left',
				right: '#sidebar-right'
			}
		},

		customScroll: ( !Modernizr.overflowscrolling && !isAndroid && $.fn.nanoScroller !== 'undefined'),

		initialize: function() {
			this
				.setVars()
				.build()
				.events();
		},

		setVars: function() {
			this.sidebars = {};

			this.sidebars.left = {
				$el: $( this.options.sidebars.left )
			};

			this.sidebars.right = {
				$el: $( this.options.sidebars.right ),
				isOpened: $html.hasClass( 'sidebar-right-opened' )
			};

			this.sidebars.menu = {
				$el: $( this.options.sidebars.menu ),
				isOpened: $html.hasClass( 'inner-menu-opened' )
			};

			return this;
		},

		build: function() {

			if ( typeof $.browser !== 'undefined' && $.browser.mobile ) {
				$html.addClass( 'mobile-device' );
			} else {
				$html.addClass( 'no-mobile-device' );
			}

			$html.addClass( 'custom-scroll' );
			if ( this.customScroll ) {
				this.buildSidebarLeft();
				this.buildContentMenu();
			}

			this.buildSidebarRight();

			return this;
		},

		events: function() {
			if ( this.customScroll ) {
				this.eventsSidebarLeft();
			}

			this.eventsSidebarRight();
			this.eventsContentMenu();

			if ( typeof $.browser !== 'undefined' && !this.customScroll && isAndroid ) {
				this.fixScroll();
			}

			return this;
		},

		fixScroll: function() {
			var _self = this;

			$window
				.on( 'sidebar-left-opened sidebar-right-toggle', function( e, data ) {
					_self.preventBodyScrollToggle( data.added );
				});
		},

		buildSidebarLeft: function() {
			this.sidebars.left.isOpened = !$html.hasClass( 'sidebar-left-collapsed' ) || $html.hasClass( 'sidebar-left-opened' );

			this.sidebars.left.$nano = this.sidebars.left.$el.find( '.nano' );

			this.sidebars.left.$nano.nanoScroller({
				alwaysVisible: true,
				preventPageScrolling: true
			});

			return this;
		},

		eventsSidebarLeft: function() {

			var _self = this,
				$nano = this.sidebars.left.$nano;

			var open = function() {
				if ( _self.sidebars.left.isOpened ) {
					return close();
				}

				_self.sidebars.left.isOpened = true;

				$html.addClass( 'sidebar-left-opened' );

				$window.trigger( 'sidebar-left-toggle', {
					added: true,
					removed: false
				});

				$html.on( 'click.close-left-sidebar', function(e) {
					e.stopPropagation();
					close(e);
				});


			};

			var close = function(e) {
				if ( !!e && !!e.target && ($(e.target).closest( '.sidebar-left' ).get(0) || !$(e.target).closest( 'html' ).get(0)) ) {
					e.preventDefault();
					return false;
				} else {

					$html.removeClass( 'sidebar-left-opened' );
					$html.off( 'click.close-left-sidebar' );

					$window.trigger( 'sidebar-left-toggle', {
						added: false,
						removed: true
					});

					_self.sidebars.left.isOpened = !$html.hasClass( 'sidebar-left-collapsed' );

				}
			};

			var updateNanoScroll = function() {
				if ( $.support.transition ) {
					$nano.nanoScroller();
					$nano
						.one('bsTransitionEnd', updateNanoScroll)
						.emulateTransitionEnd(150)
				} else {
					updateNanoScroll();
				}
			};

			var isToggler = function( element ) {
				return $(element).data('fire-event') === 'sidebar-left-toggle' || $(element).parents().data('fire-event') === 'sidebar-left-toggle';
			};

			this.sidebars.left.$el
				.on( 'click', function() {
					updateNanoScroll();
				})
				.on('touchend', function(e) {
					_self.sidebars.left.isOpened = !$html.hasClass( 'sidebar-left-collapsed' ) || $html.hasClass( 'sidebar-left-opened' );
					if ( !_self.sidebars.left.isOpened && !isToggler(e.target) ) {
						e.stopPropagation();
						e.preventDefault();
						open();
					}
				});

			$nano
				.on( 'mouseenter', function() {
					if ( $html.hasClass( 'sidebar-left-collapsed' ) ) {
						$nano.nanoScroller();
					}
				})
				.on( 'mouseleave', function() {
					if ( $html.hasClass( 'sidebar-left-collapsed' ) ) {
						$nano.nanoScroller();
					}
				});

			$window.on( 'sidebar-left-toggle', function(e, toggle) {
				if ( toggle.removed ) {
					$html.removeClass( 'sidebar-left-opened' );
					$html.off( 'click.close-left-sidebar' );
				}
			});

			return this;
		},

		buildSidebarRight: function() {
			this.sidebars.right.isOpened = $html.hasClass( 'sidebar-right-opened' );

			if ( this.customScroll ) {
				this.sidebars.right.$nano = this.sidebars.right.$el.find( '.nano' );

				this.sidebars.right.$nano.nanoScroller({
					alwaysVisible: true,
					preventPageScrolling: true
				});
			}

			return this;
		},

		eventsSidebarRight: function() {
			var _self = this;

			var open = function() {
				if ( _self.sidebars.right.isOpened ) {
					return close();
				}

				_self.sidebars.right.isOpened = true;

				$html.addClass( 'sidebar-right-opened' );

				$window.trigger( 'sidebar-right-toggle', {
					added: true,
					removed: false
				});

				$html.on( 'click.close-right-sidebar', function(e) {
					e.stopPropagation();
					close(e);
				});
			};

			var close = function(e) {
				if ( !!e && !!e.target && ($(e.target).closest( '.sidebar-right' ).get(0) || !$(e.target).closest( 'html' ).get(0)) ) {
					e.preventDefault();
					return false;
				}

				$html.removeClass( 'sidebar-right-opened' );
				$html.off( 'click.close-right-sidebar' );

				$window.trigger( 'sidebar-right-toggle', {
					added: false,
					removed: true
				});

				_self.sidebars.right.isOpened = false;
			};

			var bind = function() {
				$('[data-open="sidebar-right"]').on('click', function(e) {
					var $el = $(this);
					e.stopPropagation();

					if ( $el.is('a') )
						e.preventDefault();

					open();
				});
			};

			this.sidebars.right.$el.find( '.mobile-close' )
				.on( 'click', function( e ) {
					e.preventDefault();
					$html.trigger( 'click.close-right-sidebar' );
				});

			bind();

			return this;
		},

		buildContentMenu: function() {
			if ( !$html.hasClass( 'fixed' ) ) {
				return false;
			}

			this.sidebars.menu.$nano = this.sidebars.menu.$el.find( '.nano' );

			this.sidebars.menu.$nano.nanoScroller({
				alwaysVisible: true,
				preventPageScrolling: true
			});

			return this;
		},

		eventsContentMenu: function() {
			var _self = this;

			var open = function() {
				if ( _self.sidebars.menu.isOpened ) {
					return close();
				}

				_self.sidebars.menu.isOpened = true;

				$html.addClass( 'inner-menu-opened' );

				$window.trigger( 'inner-menu-toggle', {
					added: true,
					removed: false
				});

				$html.on( 'click.close-inner-menu', function(e) {

					close(e);
				});

			};

			var close = function(e) {
				if ( !!e && !!e.target && !$(e.target).closest( '.inner-menu-collapse' ).get(0) && ($(e.target).closest( '.inner-menu' ).get(0) || !$(e.target).closest( 'html' ).get(0)) ) {
					return false;
				}

				e.stopPropagation();

				$html.removeClass( 'inner-menu-opened' );
				$html.off( 'click.close-inner-menu' );

				$window.trigger( 'inner-menu-toggle', {
					added: false,
					removed: true
				});

				_self.sidebars.menu.isOpened = false;
			};

			var bind = function() {
				$('[data-open="inner-menu"]').on('click', function(e) {
					var $el = $(this);
					e.stopPropagation();

					if ( $el.is('a') )
						e.preventDefault();

					open();
				});
			};

			bind();

			/* Nano Scroll */
			if ( $html.hasClass( 'fixed' ) ) {
				var $nano = this.sidebars.menu.$nano;

				var updateNanoScroll = function() {
					if ( $.support.transition ) {
						$nano.nanoScroller();
						$nano
							.one('bsTransitionEnd', updateNanoScroll)
							.emulateTransitionEnd(150)
					} else {
						updateNanoScroll();
					}
				};

				this.sidebars.menu.$el
					.on( 'click', function() {
						updateNanoScroll();
					});
			}

			return this;
		},

		preventBodyScrollToggle: function( shouldPrevent, $el ) {
			setTimeout(function() {
				if ( shouldPrevent ) {
					$body
						.data( 'scrollTop', $body.get(0).scrollTop )
						.css({
							position: 'fixed',
							top: $body.get(0).scrollTop * -1
						})
				} else {
					$body
						.css({
							position: '',
							top: ''
						})
						.scrollTop( $body.data( 'scrollTop' ) );
				}
			}, 150);
		}

	};

	// expose to scope
	$.extend(theme, {
		Skeleton: Skeleton
	});

}).apply(this, [ window.theme, jQuery ]);

// Base
(function(theme, $) {

	'use strict';

	theme = theme || {};

	theme.Skeleton.initialize();

}).apply(this, [ window.theme, jQuery ]);

/*
Name: 			Theme Admin Extension
Written by: 	Okler Themes - (http://www.okler.net)
Theme Version: 	1.4.1
*/

window.theme = {};


// Panels
(function( $ ) {

	$(function() {
		$('.panel')
			.on( 'panel:toggle', function() {
				var $this,
					direction;

				$this = $(this);
				direction = $this.hasClass( 'panel-collapsed' ) ? 'Down' : 'Up';

				$this.find('.panel-body, .panel-footer')[ 'slide' + direction ]( 200, function() {
					$this[ (direction === 'Up' ? 'add' : 'remove') + 'Class' ]( 'panel-collapsed' )
				});
			})
			.on( 'panel:dismiss', function() {
				var $this = $(this);

				if ( !!( $this.parent('div').attr('class') || '' ).match( /col-(xs|sm|md|lg)/g ) && $this.siblings().length === 0 ) {
					$row = $this.closest('.row');
					$this.parent('div').remove();
					if ( $row.children().length === 0 ) {
						$row.remove();
					}
				} else {
					$this.remove();
				}
			})
			.on( 'click', '[data-panel-toggle]', function( e ) {
				e.preventDefault();
				$(this).closest('.panel').trigger( 'panel:toggle' );
			})
			.on( 'click', '[data-panel-dismiss]', function( e ) {
				e.preventDefault();
				$(this).closest('.panel').trigger( 'panel:dismiss' );
			})
			/* Deprecated */
			.on( 'click', '.panel-actions a.fa-caret-up', function( e ) {
				e.preventDefault();
				var $this = $( this );

				$this
					.removeClass( 'fa-caret-up' )
					.addClass( 'fa-caret-down' );

				$this.closest('.panel').trigger( 'panel:toggle' );
			})
			.on( 'click', '.panel-actions a.fa-caret-down', function( e ) {
				e.preventDefault();
				var $this = $( this );

				$this
					.removeClass( 'fa-caret-down' )
					.addClass( 'fa-caret-up' );

				$this.closest('.panel').trigger( 'panel:toggle' );
			})
			.on( 'click', '.panel-actions a.fa-times', function( e ) {
				e.preventDefault();
				var $this = $( this );

				$this.closest('.panel').trigger( 'panel:dismiss' );
			});
	});

})( jQuery );

// Bootstrap Toggle
(function( $ ) {

	'use strict';

	var $window = $( window );

	var toggleClass = function( $el ) {
		if ( !!$el.data('toggleClassBinded') ) {
			return false;
		}

		var $target,
			className,
			eventName;

		$target = $( $el.attr('data-target') );
		className = $el.attr('data-toggle-class');
		eventName = $el.attr('data-fire-event');


		$el.on('click.toggleClass', function(e) {
			e.preventDefault();
			$target.toggleClass( className );

			var hasClass = $target.hasClass( className );

			if ( !!eventName ) {
				$window.trigger( eventName, {
					added: hasClass,
					removed: !hasClass
				});
			}
		});

		$el.data('toggleClassBinded', true);

		return true;
	};

	$(function() {
		$('[data-toggle-class][data-target]').each(function() {
			toggleClass( $(this) );
		});
	});

}).apply( this, [ jQuery ]);









// Animate
(function(theme, $) {

	theme = theme || {};

	var instanceName = '__animate';

	var PluginAnimate = function($el, opts) {
		return this.initialize($el, opts);
	};

	PluginAnimate.defaults = {
		accX: 0,
		accY: -150,
		delay: 1
	};

	PluginAnimate.prototype = {
		initialize: function($el, opts) {
			if ( $el.data( instanceName ) ) {
				return this;
			}

			this.$el = $el;

			this
				.setData()
				.setOptions(opts)
				.build();

			return this;
		},

		setData: function() {
			this.$el.data(instanceName, this);

			return this;
		},

		setOptions: function(opts) {
			this.options = $.extend(true, {}, PluginAnimate.defaults, opts, {
				wrapper: this.$el
			});

			return this;
		},

		build: function() {
			var self = this,
				$el = this.options.wrapper,
				delay = 0;

			$el.addClass('appear-animation');

			if(!$('html').hasClass('no-csstransitions') && $(window).width() > 767) {

				$el.appear(function() {

					delay = ($el.attr('data-appear-animation-delay') ? $el.attr('data-appear-animation-delay') : self.options.delay);

					if(delay > 1) {
						$el.css('animation-delay', delay + 'ms');
					}

					$el.addClass($el.attr('data-appear-animation'));

					setTimeout(function() {
						$el.addClass('appear-animation-visible');
					}, delay);

				}, {accX: self.options.accX, accY: self.options.accY});

			} else {

				$el.addClass('appear-animation-visible');

			}

			return this;
		}
	};

	// expose to scope
	$.extend(theme, {
		PluginAnimate: PluginAnimate
	});

	// jquery plugin
	$.fn.themePluginAnimate = function(opts) {
		return this.map(function() {
			var $this = $(this);

			if ($this.data(instanceName)) {
				return $this.data(instanceName);
			} else {
				return new PluginAnimate($this, opts);
			}

		});
	};

}).apply(this, [ window.theme, jQuery ]);


// Scrollable
(function(theme, $) {

	theme = theme || {};

	var instanceName = '__scrollable';

	var PluginScrollable = function($el, opts) {
		return this.initialize($el, opts);
	};

	PluginScrollable.defaults = {
		contentClass: 'scrollable-content',
		paneClass: 'scrollable-pane',
		sliderClass: 'scrollable-slider',
		alwaysVisible: true,
		preventPageScrolling: true
	};

	PluginScrollable.prototype = {
		initialize: function($el, opts) {
			if ( $el.data( instanceName ) ) {
				return this;
			}

			this.$el = $el;

			this
				.setData()
				.setOptions(opts)
				.build();

			return this;
		},

		setData: function() {
			this.$el.data(instanceName, this);

			return this;
		},

		setOptions: function(opts) {
			this.options = $.extend(true, {}, PluginScrollable.defaults, opts, {
				wrapper: this.$el
			});

			return this;
		},

		build: function() {
			this.options.wrapper.nanoScroller(this.options);

			return this;
		}
	};

	// expose to scope
	$.extend(theme, {
		PluginScrollable: PluginScrollable
	});

	// jquery plugin
	$.fn.themePluginScrollable = function(opts) {
		return this.each(function() {
			var $this = $(this);

			if ($this.data(instanceName)) {
				return $this.data(instanceName);
			} else {
				return new PluginScrollable($this, opts);
			}

		});
	}

}).apply(this, [ window.theme, jQuery ]);


// Toggle
(function(theme, $) {

	theme = theme || {};

	var instanceName = '__toggle';

	var PluginToggle = function($el, opts) {
		return this.initialize($el, opts);
	};

	PluginToggle.defaults = {
		duration: 350,
		isAccordion: false,
		addIcons: true
	};

	PluginToggle.prototype = {
		initialize: function($el, opts) {
			if ( $el.data( instanceName ) ) {
				return this;
			}

			this.$el = $el;

			this
				.setData()
				.setOptions(opts)
				.build();

			return this;
		},

		setData: function() {
			this.$el.data(instanceName, this);

			return this;
		},

		setOptions: function(opts) {
			this.options = $.extend(true, {}, PluginToggle.defaults, opts, {
				wrapper: this.$el
			});

			return this;
		},

		build: function() {
			var self = this,
				$wrapper = this.options.wrapper,
				$items = $wrapper.find('.toggle'),
				$el = null;

			$items.each(function() {
				$el = $(this);

				if(self.options.addIcons) {
					$el.find('> label').prepend(
						$('<i />').addClass('fa fa-plus'),
						$('<i />').addClass('fa fa-minus')
					);
				}

				if($el.hasClass('active')) {
					$el.find('> p').addClass('preview-active');
					$el.find('> .toggle-content').slideDown(self.options.duration);
				}

				self.events($el);
			});

			if(self.options.isAccordion) {
				self.options.duration = self.options.duration/2;
			}

			return this;
		},

		events: function($el) {
			var self = this,
				previewParCurrentHeight = 0,
				previewParAnimateHeight = 0,
				toggleContent = null;

			$el.find('> label').click(function(e) {

				var $this = $(this),
					parentSection = $this.parent(),
					parentWrapper = $this.parents('.toggle'),
					previewPar = null,
					closeElement = null;

				if(self.options.isAccordion && typeof(e.originalEvent) != 'undefined') {
					closeElement = parentWrapper.find('.toggle.active > label');

					if(closeElement[0] == $this[0]) {
						return;
					}
				}

				parentSection.toggleClass('active');

				// Preview Paragraph
				if(parentSection.find('> p').get(0)) {

					previewPar = parentSection.find('> p');
					previewParCurrentHeight = previewPar.css('height');
					previewPar.css('height', 'auto');
					previewParAnimateHeight = previewPar.css('height');
					previewPar.css('height', previewParCurrentHeight);

				}

				// Content
				toggleContent = parentSection.find('> .toggle-content');

				if(parentSection.hasClass('active')) {

					$(previewPar).animate({
						height: previewParAnimateHeight
					}, self.options.duration, function() {
						$(this).addClass('preview-active');
					});

					toggleContent.slideDown(self.options.duration, function() {
						if(closeElement) {
							closeElement.trigger('click');
						}
					});

				} else {

					$(previewPar).animate({
						height: 0
					}, self.options.duration, function() {
						$(this).removeClass('preview-active');
					});

					toggleContent.slideUp(self.options.duration);

				}

			});
		}
	};

	// expose to scope
	$.extend(theme, {
		PluginToggle: PluginToggle
	});

	// jquery plugin
	$.fn.themePluginToggle = function(opts) {
		return this.map(function() {
			var $this = $(this);

			if ($this.data(instanceName)) {
				return $this.data(instanceName);
			} else {
				return new PluginToggle($this, opts);
			}

		});
	}

}).apply(this, [ window.theme, jQuery ]);

// Widget - Toggle
(function(theme, $) {

	theme = theme || {};

	var instanceName = '__widgetToggleExpand';

	var WidgetToggleExpand = function($el, opts) {
		return this.initialize($el, opts);
	};

	WidgetToggleExpand.defaults = {
	};

	WidgetToggleExpand.prototype = {
		initialize: function($el, opts) {
			if ( $el.data( instanceName ) ) {
				return this;
			}

			this.$el = $el;

			this
				.setData()
				.setOptions(opts)
				.build()
				.events();

			return this;
		},

		setData: function() {
			this.$el.data(instanceName, this);

			return this;
		},

		setOptions: function(opts) {
			this.options = $.extend( true, {}, WidgetToggleExpand.defaults, opts );

			return this;
		},

		build: function() {
			return this;
		},

		events: function() {
			var _self = this,
				$toggler = this.$el.find( '.widget-toggle' );

			$toggler.on('click.widget-toggler', function() {
				_self.$el.hasClass('widget-collapsed') ? _self.expand( _self.$el ) : _self.collapse( _self.$el );
			});

			return this;
		},

		expand: function( content ) {
			content.children( '.widget-content-expanded' ).slideDown( 'fast', function() {
				$(this).css( 'display', '' );
				content.removeClass( 'widget-collapsed' );
			});
		},

		collapse: function( content ) {
			content.children('.widget-content-expanded' ).slideUp( 'fast', function() {
				content.addClass( 'widget-collapsed' );
				$(this).css( 'display', '' );
			});
		}
	};

	// expose to scope
	$.extend(theme, {
		WidgetToggleExpand: WidgetToggleExpand
	});

	// jquery plugin
	$.fn.themePluginWidgetToggleExpand = function(opts) {
		return this.each(function() {
			var $this = $(this);

			if ($this.data(instanceName)) {
				return $this.data(instanceName);
			} else {
				return new WidgetToggleExpand($this, opts);
			}

		});
	}

}).apply(this, [ window.theme, jQuery ]);

