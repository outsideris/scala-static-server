package kr.sideeffect.outsider.http

import java.io._
import java.net._

class TCPServer(val port: Int) {
  val tcpServer = new ServerSocket(port)

  def start:Unit = {
    val socket = tcpServer.accept
    val input = socket.getInputStream
    val output = socket.getOutputStream
    val bufferReader = new BufferedReader(new InputStreamReader(input))
    //val out = new DataOutputStream(socket.getOutputStream)
    var request = bufferReader.readLine
    val requestBuf = new StringBuilder
    
    while (request.length > 0) {
      requestBuf.append(request + "\n")
      request = bufferReader.readLine
    }
    println(requestBuf.toString)

    // handle response
    val response = "hello world"
    output.write(response.getBytes)
    output.flush
    socket.close

    // run server again
    start
  }
}
