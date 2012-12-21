package kr.sideeffect.outsider.http

import scala.util.parsing.combinator._
import MethodParser._
import MessageParser._

class Request(val rawRequest:String) {
  val headerLines = rawRequest.split("\n").toList
  
  // parse method
  val (method, host, protocol) = MethodParser.parseAll(MethodParser.header, headerLines(0)).get
  
  // parse headers
  private val _headers = headerLines.drop(1)
                          .map(x => 
                            MessageParser.parseAll(MessageParser.messageHeader, x).get
                          ) reduce (_ ++ _)
  
  def headers = _headers
  
  def header(name:String) = _headers.get(name)
}