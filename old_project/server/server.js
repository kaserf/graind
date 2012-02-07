var http = require("http");

function start(route, handle) {
  function onRequest(request, response) {
    console.log("Request received on : " + request.url);

    route(handle, request, response);
  }

  http.createServer(onRequest).listen(8888);
  console.log("The graind server has been started!");
}

exports.start = start;

