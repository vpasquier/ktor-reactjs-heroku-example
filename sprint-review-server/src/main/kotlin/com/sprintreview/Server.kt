package com.sprintreview

import com.sprintreview.constants.Constants
import com.sprintreview.constants.Constants.Companion.RESOURCE_PATH
import com.sprintreview.constants.Endpoints.Companion.SMOKE
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
  val staticFilesDir = File(RESOURCE_PATH)
  routing {
    get(SMOKE) {
      call.respondText(Constants.SMOKE_TEST, ContentType.Text.Plain)
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

