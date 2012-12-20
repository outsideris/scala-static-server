package kr.sideeffect.outsider.http

import org.scalatest.FunSuite

class HeaderParserSuite extends FunSuite {
  test("GET METHOD") {
    // given
    val requestHeader = "GET / HTTP/1.1"

    // when
    val methodParser = new MethodParser
    val (method, host, protocol) = methodParser.parseAll(methodParser.header, requestHeader).get

    // then
    assert("GET" == method)
    assert("/" == host)
    assert("HTTP/1.1" == protocol)
  }

  test("POST METHOD") {
    // given
    val requestHeader = "POST /pub/WWW/ HTTP/1.1"

    // when
    val methodParser = new MethodParser
    val (method, host, protocol) = methodParser.parseAll(methodParser.header, requestHeader).get

    // then
    assert("POST" == method)
    assert("/pub/WWW/" == host)
    assert("HTTP/1.1" == protocol)
  }

  test("case 1 for HTTP header parsing") {
    // given
    //    val requestHeader = """User-Agent: curl/7.24.0 (x86_64-apple-darwin12.0) libcurl/7.24.0 OpenSSL/0.9.8r zlib/1.2.5
    //                          |Host: localhost:4000
    //                          |Accept: */*""".stripMargin
    val requestHeader = "User-Agent: curl/7.24.0 (x86_64-apple-darwin12.0) libcurl/7.24.0 OpenSSL/0.9.8r zlib/1.2.5"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("User-Agent") == headers.keys)
    assert(Some(List("curl/7.24.0", "(x86_64-apple-darwin12.0)", "libcurl/7.24.0", "OpenSSL/0.9.8r", "zlib/1.2.5")) == headers.get("User-Agent"))
  }

  test("case 2 for HTTP header parsing") {
    val requestHeader = "Host: localhost:4000"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("Host") == headers.keys)
    assert(Some(List("localhost:4000")) == headers.get("Host"))
  }

  test("case 3 for HTTP header parsing") {
    val requestHeader = "Accept: */*"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("Accept") == headers.keys)
    assert(Some(List("*/*")) == headers.get("Accept"))
  }

  test("case 4 for HTTP header parsing") {
    val requestHeader = "Connection: keep-alive"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("Connection") == headers.keys)
    assert(Some(List("keep-alive")) == headers.get("Connection"))
  }

  test("case 5 for HTTP header parsing") {
    val requestHeader = "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("User-Agent") == headers.keys)
    assert(Some(List("Mozilla/5.0", "(Macintosh; Intel Mac OS X 10_8_2)", "AppleWebKit/537.11", "(KHTML, like Gecko)", "Chrome/23.0.1271.95", "Safari/537.11")) == headers.get("User-Agent"))
  }

  test("case 6 for HTTP header parsing") {
    // given
    val requestHeader = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    println(headers)
    assert(Set("Accept") == headers.keys)
    assert(Some(List("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")) == headers.get("Accept"))
  }
  
  // warning: not good!
  test("case 7 for HTTP header parsing") {
    // given
    val requestHeader = "Accept: audio/*; q=0.2, audio/basic"

    // when
    val messageParser = new MessageParser
    val headers = messageParser.parseAll(messageParser.messageHeader, requestHeader).get

    // then
    assert(Set("Accept") == headers.keys)
    assert(Some(List("audio/*;","q=0.2,","audio/basic")) == headers.get("Accept"))
  }

}