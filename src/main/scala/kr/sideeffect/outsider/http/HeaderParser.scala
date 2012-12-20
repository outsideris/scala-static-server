package kr.sideeffect.outsider.http

import scala.util.parsing.combinator._

class MethodParser extends JavaTokenParsers {
  def header : Parser[(String, String, String)] = method~host~protocol ^^ {case m~h~p => (m, h, p)}
  def method : Parser[String] = "OPTIONS" | "GET" | "HEAD" | "POST" | "PUT" | "DELETE" | "TRACE" | "CONNECT"
  def host : Parser[String] = """\/([\/\w_\.-]*)*\/?""".r
  def protocol : Parser[String] = "HTTP/1.0" | "HTTP/1.1"
}

class MessageParser extends JavaTokenParsers {
  /*
   * message-header = field-name ":" [ field-value ]
   * field-name     = token
   * field-value    = *( field-content | LWS )
   * field-content  = <the OCTETs making up the field-value
   *                  and consisting of either *TEXT or combinations
   *                  of token, separators, and quoted-string>
   */
  def messageHeader : Parser[Map[String, List[String]]] = 
    fieldName~delimiter~repsep(fieldValue, " ") ^^ {case k~d~v => Map(k -> v)}
  def delimiter : Parser[String] = """\:\s+""".r
  def fieldName : Parser[String] = """[a-zA-Z]+\-?[a-zA-Z]+""".r
  def fieldValue : Parser[String] = """\([a-zA-Z0-9/\.\(\)\*\:\s;,_-]+?\)""".r | """[a-zA-Z0-9/\.\(\)\*\:\+\;=,_-]+""".r
  override val whiteSpace = "".r
}
