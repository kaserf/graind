function googleReadyCallback() {
  console.log("define callbacks");

  //var feedUrl = "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full"; //?alt=json-in-script";
  var feedUrl = "http://www.google.com/calendar/feeds/fl3x888@gmail.com/private/full";

  var query = new google.gdata.calendar.CalendarEventQuery(feedUrl);
  var startMin = google.gdata.DateTime.fromIso8601('2012-01-01T00:00:00.000-00:00');
  var startMax = google.gdata.DateTime.fromIso8601('2012-02-01T00:00:00.000-00:00');
  query.setMinimumStartTime(startMin);
  query.setMaximumStartTime(startMax);

  function logMeIn() {
    scope = "http://www.google.com/calendar/feeds/";
    var token = google.accounts.user.login(scope);
  }

  function setupMyService() {
    var myService = new google.gdata.calendar.CalendarService('foo-bar');
    return myService;
  }

  function handleMyFeed(root) {
    // Obtain the array of matched CalendarEventEntry
    var eventEntries = root.feed.getEntries();

    // If there is matches for the date query
    if (eventEntries.length > 0) {
      for (var i = 0; i < eventEntries.length; i++) {
        var event = eventEntries[i];
        // Print the event title of the matches
        console.log('Event title = ' + event.getTitle().getText(), event);
      }
    } else {
      // No match is found for the date query
      console.log('no events are matched from the query!');
    }

    // debug
    feed = root.feed;
  }

  function handleError(e) {
    alert("There was an error!");
    alert(e.cause ? e.cause.statusText : e.message);
  }

  function magic() {
    myService.getEventsFeed(query, handleMyFeed, handleError);
  }

  function doStuff() {
    console.log("do stuff");

    myService = setupMyService();
    magic();
  }

  doStuff();
}
