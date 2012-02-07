(function($){
  /* HELPER FUNCTIONS */
  /**
   * Fades from one div to another, and calls the opt_callback method once
   * the fade is done (if opt_callback exists).
   * @param {Object} fromdiv The div to fade out.
   * @param {Object} todiv The div to fade in.
   * @param {Function} opt_callback Optional method to call once the fade is
   *     done.
   */
  var transitionDiv = function(fromdiv, todiv, opt_callback) {
    $('#' + fromdiv).fadeOut('fast', function() {
      $('#' + todiv).fadeIn('fast', function() {
        if (opt_callback) {
          opt_callback();
        }
      });
    });
  };

  /* USER MODULE */
  var User = Backbone.Model.extend({
    defaults: {
      firstName: '',
      lastName: '',
      googleID: '',
      status: 0
    },

    initialize: function() {
      google.load('gdata', '2.x');
    },

    getStatus: function() {
      return google.accounts.user.getStatus();
    }
  });

  var UserStatusView = Backbone.View.extend({
    container: $('#userStatus'),

    initialize: function() {
      _.bindAll(this, 'render');

      this.render();
    },

    render: function() {
      console.log("model: ", this.model);
      if (this.model.status == google.accounts.AuthSubStatus.LOGGING_IN) {
        console.log("logging in...");
        // TODO: show spinner
        return;
      } else if (this.model.status == google.accounts.AuthSubStatus.LOGGED_OUT) {
        // User is logged out, display the "login" link.
        transitionDiv('logoutDiv', 'loginDiv');
      } else {
        // User is logged in, load the user's data.

        transitionDiv('loginDiv', 'logoutDiv', loadData);
      }
    }
  });

  // instanciate main parts of the app.
  var userView = new UserStatusView();      
})(jQuery);
