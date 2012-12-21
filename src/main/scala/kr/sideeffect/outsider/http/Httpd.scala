package kr.sideeffect.outsider.http

import java.io._
import java.net._
import java.io.FileReader

trait TCPServer {
  //  def open(port: Int):(InputStream, OutputStream) = {
  //    return (inputStream, outputStream)
  //  }
  //  
  //  def close(outputStream:OutputStream) {
  //    
  //  }
}

class StaticServer(location: String) extends TCPServer {
  def listen(port: Int) {
    val tcpServer = new ServerSocket(port)
    val socket = tcpServer.accept
    val inputStream = socket.getInputStream
    val outputStream = socket.getOutputStream

    val bufferReader = new BufferedReader(new InputStreamReader(inputStream))
    //val out = new DataOutputStream(socket.getOutputStream)
    var request = bufferReader.readLine
    val requestBuf = new StringBuilder

    while (request.length > 0) {
      requestBuf.append(request + "\n")
      request = bufferReader.readLine
    }

    // make Request instance
    val req = new Request(requestBuf.toString)

    // handle OPTIONS
    if (req.method == "OPTIONS") {

      //val response = "GET"
      val response = List("HTTP/1.1 200 OK",
        "Server: nginx/1.2.0",
        "Date: Fri, 21 Dec 2012 19:26:19 GMT",
        "Content-Type: text/html; charset=utf-8",
        "Content-Length: 3",
        "Allow: GET", "", "GET")
      outputStream.write(response.mkString("\n").getBytes)
    } else if (req.method == "GET") {
      try {
        val reader = new FileReader(location + req.host)
        val response = List("HTTP/1.1 200 OK",
          "Server: nginx/1.2.0",
          "Date: Fri, 21 Dec 2012 19:26:19 GMT",
          "Content-Type: text/html; charset=utf-8",
          "Content-Length: 3183",
          "Vary: Accept-Encoding")
        outputStream.write(response.mkString("\n").getBytes)
      } catch {
        case e: java.io.FileNotFoundException => {
          val response = List("HTTP/1.1 404 Not Found",
            "Server: nginx/1.2.0",
            "Date: Fri, 21 Dec 2012 19:26:19 GMT",
            "Content-Type: text/plain; charset=utf-8",
            "Transfer-Encoding: chunked",
            "Vary: Accept-Encoding")
          outputStream.write(response.mkString("\n").getBytes)
        }
      }
    } else {
      val response = List("HTTP/1.1 405 Not Allowed",
        "Server: nginx/1.2.0",
        "Date: Fri, 21 Dec 2012 19:36:01 GMT",
        "Content-Type: text/html; charset=utf-8",
        "Content-Length: 158",
        "Allow: GET",
        "Connection: close", "",
        "<html><head><title>405 Not Allowed</title></head><body bgcolor='white'><center><h1>405 Not Allowed</h1></center><hr><center>nginx/1.2.0</center></body></html>")

    }

    outputStream.flush
    socket.close
    tcpServer.close

    // run server again
    listen(port)
  }
}
