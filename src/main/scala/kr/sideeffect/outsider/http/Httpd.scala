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
      val headers = List("Content-Type: text/html; charset=utf-8",
          "Content-Length: 0",
          "Connection: close",
          "Allow: GET")
      
      Ok.send(headers, outputStream, "GET".getBytes)
    } else if (req.method == "GET") {
      try {
        val reader = new FileReader(location + req.host)
        
        val headers = List("Content-Type: text/html; charset=utf-8",
          "Content-Length: 3183",
          "Vary: Accept-Encoding")
          
        //Ok.send(headers, outputStream, reader)
      } catch {
        case e: java.io.FileNotFoundException => {
          NotFound.send(outputStream, null)
        }
      }
    } else {
      NotAllowed.send(outputStream, null)
    }

    outputStream.flush
    socket.close
    tcpServer.close

    // run server again
    listen(port)
  }
}
