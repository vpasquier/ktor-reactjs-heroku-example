package com.sprintreview

import com.sprintreview.constants.Constants.Companion.MODULE_SERVER
import com.sprintreview.constants.Constants.Companion.PORT
import com.sprintreview.constants.Constants.Companion.PORT_VALUE
import com.sprintreview.constants.Constants.Companion.SMOKE_TEST
import com.sprintreview.constants.Endpoints.Companion.SMOKE
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.files
import io.ktor.content.resource
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.DefaultHeaders
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
  val portVar: String = System.getenv(PORT) ?: PORT_VALUE
  embeddedServer(Netty, watchPaths = listOf(MODULE_SERVER),
          port = portVar.toInt(), module = Application::server).apply {
    start(wait = true)
  }
}

fun Application.server() {
  install(DefaultHeaders)
//  install(CallLogging)
  routing {
    //    trace { application.log.trace(it.buildText()) }
    endpoints()
    staticContent()
  }
}

fun Route.endpoints() {
  get(SMOKE) {
    call.respondText(SMOKE_TEST, ContentType.Text.Plain)
  }
}

fun Route.staticContent() {
  static {
    resource("/", "static/index.html")
    static("/static") {
      resources("static/static")
    }
  }

}
