package kr.sideeffect.outsider.http

object Httpd extends App {
  println("server listening")
  val server = new TCPServer(4000)
  server start
}
