/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     vpasquier
 */
package com.sprintreview

import com.beust.klaxon.Klaxon
import com.sprintreview.constants.Configuration
import com.sprintreview.constants.Configuration.Companion.MONGODB_LOCAL
import com.sprintreview.constants.Constants.Companion.INDEX
import com.sprintreview.constants.Constants.Companion.MODULE_SERVER
import com.sprintreview.constants.Constants.Companion.PORT
import com.sprintreview.constants.Constants.Companion.PORT_VALUE
import com.sprintreview.constants.Constants.Companion.REMOTE_STATIC
import com.sprintreview.constants.Constants.Companion.RESOURCE_STATIC
import com.sprintreview.constants.Constants.Companion.SMOKE_TEST
import com.sprintreview.constants.Endpoints.Companion.QUERY
import com.sprintreview.constants.Endpoints.Companion.ROOT
import com.sprintreview.constants.Endpoints.Companion.SMOKE
import com.sprintreview.persistence.GraphQLQuery
import com.sprintreview.persistence.Mongo
import com.sprintreview.persistence.schema
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.content.resource
import io.ktor.content.resources
import io.ktor.content.static
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.gson.gson
import io.ktor.http.ContentType
import io.ktor.request.receive
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

lateinit var mongo: Mongo

fun main(args: Array<String>) {
  val portVar: String = System.getenv(PORT) ?: PORT_VALUE
  embeddedServer(Netty, watchPaths = listOf(MODULE_SERVER),
          port = portVar.toInt(), module = Application::server).apply {
    start(wait = true)
  }
}

fun Application.server() {
  default()
  mongoInit()
}

fun Application.tomcat() {
  default()
}

fun Application.default() {
  //logging()
  install(DefaultHeaders)
  install(ContentNegotiation) {
    gson {
      setPrettyPrinting()
    }
  }
  routing {
    // traceHTTP()
    endpoints()
    staticContent()
  }
}


fun logging() {
  //install(CallLogging)
}

fun traceHTTP() {
  // trace { application.log.trace(it.buildText()) }
}


fun Route.endpoints() {
  get(SMOKE) {
    call.respondText(SMOKE_TEST, ContentType.Text.Plain)
  }
  post(QUERY) {
    val request = call.receive<GraphQLQuery>()
    call.respondText(schema.execute(request.query, Klaxon().toJsonString(request.variables)), ContentType.Application.Json)
  }
}

fun Route.staticContent() {
  static {
    resource(ROOT, INDEX)
    static(REMOTE_STATIC) {
      resources(RESOURCE_STATIC)
    }
    // TODO externalize those resources in static/static
//    static(ROOT){
//      resource("/service-worker.js", "static/service-worker.js")
//      resource("/favicon.ico", "static/favicon.ico")
//      resource("/manifest.json", "static/manifest.json")
//      resources("static")
//    }
  }
}

fun mongoInit(localUrl: String = MONGODB_LOCAL) {
  val uri: String = System.getenv(Configuration.MONGODB_URI) ?: localUrl
  mongo = Mongo(uri)
  mongo.punch()
}
