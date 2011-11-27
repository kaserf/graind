var static = require("node-static"),
    url = require("url");

function route(handle, request, response) {
  var pathname = url.parse(request.url).pathname;

  console.log("About to route a request for " + pathname);
  if (typeof handle[pathname] === 'function') {
    // handling REST calls should happen here,
    // either by api or in requrestHandlers.js (like it is now)
    handle[pathname](response);
  } else {
    // handle static files
    var staticServer = new(static.Server)("../public");
    
    staticServer.serve(request, response, function (e, res) {
      if (e && (e.status === 404)) { // If the file wasn't found
        console.log("404 - not found on: " + pathname);
        staticServer.serveFile('/404.html', 404, {}, request, response);
      }
    });
  }
}

exports.route = route;
