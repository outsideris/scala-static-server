package kr.sideeffect.outsider.http

object Server extends App {
  val defaultPath = "/Users/outsider/projects/scala/static-server/public"
  val server = new StaticServer(defaultPath)
  println("server listening")
  server.listen(4000)
}
