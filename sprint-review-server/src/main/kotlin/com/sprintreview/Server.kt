package com.sprintreview

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.content.defaultResource
import io.ktor.content.files
import io.ktor.content.static
import io.ktor.content.staticBasePackage
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.route
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File

fun main(args: Array<String>) {
  val portVar: String = System.getenv("PORT") ?: "8080"
  embeddedServer(Netty, watchPaths = listOf("sprint-review-server"),
          port = portVar.toInt(), module = Application::server).apply {
    start(wait = true)
  }
}

fun Application.server() {
  val staticFilesDir = File("src/main/resources/static")
  routing {
    get("/plain") {
      call.respondText("Hello Plain!", ContentType.Text.Plain)
    }
    static {
      staticBasePackage = "static"
      defaultResource("index.html")
      route("static") {
        files(staticFilesDir)
      }
    }
  }
}