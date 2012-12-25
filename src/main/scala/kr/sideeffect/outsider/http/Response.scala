package kr.sideeffect.outsider.http

import scala.util.parsing.combinator._
import MethodParser._
import MessageParser._
import org.scala_tools.time.Imports._
import java.io._

trait Response {
  def defaultHeader = List("Server: scala server", "Date: Fri, 21 Dec 2012 19:26:19 GMT")

  def _send(additionalHeaders: List[String], res: OutputStream, body: Array[Byte]) {
    val headers = (defaultHeader ++ additionalHeaders ++ List("")).sorted

    res.write(headers.mkString("\n").getBytes)

    if (body != null) {
      res.write(body)
    }
  }
}

object NotFound extends Response {
  def send(res: OutputStream, body: Array[Byte]) {
    res.write("HTTP/1.1 404 Not Found\n".getBytes)

    val headers = List("Content-Type: text/plain; charset=utf-8",
      "Transfer-Encoding: chunked",
      "Vary: Accept-Encoding")

    _send(headers, res, body)
  }
}

object NotAllowed extends Response {
  def send(res: OutputStream, body: Array[Byte]) {
    res.write("HTTP/1.1 405 Not Allowed\n".getBytes)

    val headers = List("Content-Type: text/html; charset=utf-8",
      "Content-Length: 3",
      "Connection: close",
      "Allow: GET")

    _send(headers, res, body)
  }
}

object Ok extends Response {
  def send(additionalHeaders: List[String], res: OutputStream, body: Array[Byte]) {
    res.write("HTTP/1.1 200 OK\n".getBytes)

    _send(additionalHeaders, res, body)
  }
}