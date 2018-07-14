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
package com.sprintreview.test

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.beust.klaxon.Klaxon
import com.sprintreview.*
import com.sprintreview.constants.Configuration.Companion.ES_HTTP
import com.sprintreview.constants.Configuration.Companion.ES_TEST_PORT
import com.sprintreview.constants.Configuration.Companion.ES_TEST_VERSION
import com.sprintreview.constants.Configuration.Companion.MONGODB_TEST_PORT
import com.sprintreview.constants.Configuration.Companion.MONGODB_TEST_VERSION
import com.sprintreview.constants.Constants.Companion.SMOKE_TEST
import com.sprintreview.constants.Endpoints.Companion.QUERY
import com.sprintreview.constants.Endpoints.Companion.SMOKE
import com.sprintreview.persistence.ES
import com.sprintreview.persistence.Jedi
import com.sprintreview.persistence.Result
import com.sprintreview.persistence.Results
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.testcontainers.containers.GenericContainer
import org.testcontainers.images.builder.ImageFromDockerfile


class SmokeTest {

  class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
  class KGenericContainerFromFile(dockerfile: ImageFromDockerfile) : GenericContainer<KGenericContainerFromFile>(dockerfile)

  companion object {
    @get:ClassRule
    val mongoContainer = KGenericContainer(MONGODB_TEST_VERSION).withExposedPorts(MONGODB_TEST_PORT)
    val esContainer = KGenericContainerFromFile(ImageFromDockerfile()
            .withFileFromClasspath("elasticsearch.yml", "es/elasticsearch.yml")
            .withFileFromClasspath("Dockerfile", "es/Dockerfile"))
            .withExposedPorts(ES_TEST_PORT.toInt())

    @BeforeClass
    @JvmStatic
    fun setup() {
      Companion.mongoContainer.start()
      val mongoURL = mongoContainer.containerIpAddress ?: MONGODB_TEST_VERSION
      val mongoPORT = mongoContainer.firstMappedPort ?: MONGODB_TEST_PORT
      mongoInit("$mongoURL:$mongoPORT")
      mongo.punch()

      Companion.esContainer.start()
      val esHost = esContainer.containerIpAddress ?: ES_TEST_VERSION
      val esPORT = esContainer.firstMappedPort ?: ES_TEST_PORT
      esInit(esHost, esPORT.toString(), ES_HTTP)
    }

    @AfterClass
    @JvmStatic
    fun tearDown() {
      Companion.mongoContainer.stop()
      Companion.esContainer.stop()
    }
  }

  @Test
  fun iCanHitServer() = withTestApplication(Application::tomcat) {
    with(handleRequest(HttpMethod.Get, SMOKE)) {
      assert(response.status()).isEqualTo(HttpStatusCode.OK)
      assert(response.content).isEqualTo(SMOKE_TEST)
    }
    with(handleRequest(HttpMethod.Get, "/")) {
      assert(response.status()).isEqualTo(HttpStatusCode.OK)
      assert(response.content!!.contains("html")).isTrue()
    }
  }

  @Test
  fun iCanHitMongo() {
    val luke: Jedi? = mongo.sprintCollection.findOne(Jedi::name eq "Luke Skywalker")
    assert(luke).isNotNull()
    assert(luke!!.age).isEqualTo(19)
  }

  @Test
  fun iCanHitES() {
    val clusterHealth: ES.ClusterHealth = es.checkInfos()!!
    assert(clusterHealth.status).isEqualTo("green")
  }

  @Test
  fun iCanUseGraphQL() = withTestApplication(Application::tomcat) {
    // Just to fetch one entry
    with(handleRequest(HttpMethod.Post, QUERY) {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      setBody("{\"query\":\"{jedis{age}}\"}")
    }) {
      assertk.assert(response.status()).isEqualTo(io.ktor.http.HttpStatusCode.OK)
      val results = Klaxon().parse<Results>(response.content!!)
      val data = results!!.data
      val jedis = data.jedis
      assertk.assert(jedis!!.size).isEqualTo(2)
      assertk.assert(jedis!![0].age).isEqualTo(17)
    }
    // Just to run a simple query
    with(handleRequest(HttpMethod.Post, QUERY) {
      addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
      setBody("{\"query\":\"{jedi(phase:\\\"adult\\\"){name}}\"}")
    }) {
      assertk.assert(response.status()).isEqualTo(io.ktor.http.HttpStatusCode.OK)
      val result = Klaxon().parse<Result>(response.content!!)
      val data = result!!.data
      val jedi = data.jedi
      assertk.assert(jedi!!.name).isEqualTo("Leila")
    }
  }

}
