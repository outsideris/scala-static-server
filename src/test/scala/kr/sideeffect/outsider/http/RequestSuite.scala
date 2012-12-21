package kr.sideeffect.outsider.http

import org.scalatest.FunSuite

class RequestSuite extends FunSuite {
  test("get method, host and protocol from request instance") {
    // given 
    val requestHeader = """GET / HTTP/1.1
                        |User-Agent: curl/7.24.0 (x86_64-apple-darwin12.0) libcurl/7.24.0 OpenSSL/0.9.8r zlib/1.2.5
                        |Host: localhost:4000
                        |Accept: */*""".stripMargin
    
    // when
    val req = new Request(requestHeader)
    
    // then
    assert("GET" == req.method)
    assert("/" == req.host)
    assert("HTTP/1.1" == req.protocol)
  }

  test("get header's value from request instance") {
    // given
    val requestHeader = """GET / HTTP/1.1
                          |User-Agent: curl/7.24.0 (x86_64-apple-darwin12.0) libcurl/7.24.0 OpenSSL/0.9.8r zlib/1.2.5
                          |Host: localhost:4000
                          |Accept: */*""".stripMargin
    
    // when
    val req = new Request(requestHeader)
    
    // then
    assert(Some(List("curl/7.24.0","(x86_64-apple-darwin12.0)","libcurl/7.24.0","OpenSSL/0.9.8r","zlib/1.2.5"))
             == req.header("User-Agent"))
    assert(Some(List("localhost:4000")) == req.header("Host"))
    assert(Some(List("*/*")) == req.header("Accept"))
  }
  
  test("get None when the header doesn't exist") {
    // given
    val requestHeader = """GET / HTTP/1.1
                          |User-Agent: curl/7.24.0 (x86_64-apple-darwin12.0) libcurl/7.24.0 OpenSSL/0.9.8r zlib/1.2.5
                          |Host: localhost:4000
                          |Accept: */*""".stripMargin
    
    // when
    val req = new Request(requestHeader)
    
    // then
    assert(None == req.header("Allow"))
  }
  

  ignore("test2") {
    val requestHeader = """GET / HTTP/1.1
                        |Host: localhost:4000
                        |Connection: keep-alive
                        |User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11
                        |Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
                        |Accept-Encoding: gzip,deflate,sdch
                        |Accept-Language: ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4
                        |Accept-Charset: windows-949,utf-8;q=0.7,*;q=0.3
                        |Cookie: oauth2-token=; connect.sid=s%3APUoAGrS2FG6ECJdvbxa%2B3mYN.nbxCpi%2Fe5fjNcgeQgQC1tnN2wNAxifd4nKdmWhgzLo4""".stripMargin
    println(requestHeader)
  }
}