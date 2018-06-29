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
import com.sprintreview.constants.Configuration.Companion.MONGODB_TEST_PORT
import com.sprintreview.constants.Configuration.Companion.MONGODB_TEST_VERSION
import com.sprintreview.constants.Constants.Companion.SMOKE_TEST
import com.sprintreview.constants.Endpoints.Companion.SMOKE
import com.sprintreview.mongo
import com.sprintreview.mongoInit
import com.sprintreview.persistence.Sprint
import com.sprintreview.tomcat
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.testcontainers.containers.GenericContainer


class SmokeTest {

  class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

  companion object {
    @get:ClassRule
    val mongoContainer = KGenericContainer(MONGODB_TEST_VERSION).withExposedPorts(MONGODB_TEST_PORT)

    @BeforeClass
    @JvmStatic
    fun setup() {
      Companion.mongoContainer.start()
      val mongoURL = mongoContainer.containerIpAddress ?: MONGODB_TEST_VERSION
      val mongoPORT = mongoContainer.firstMappedPort ?: MONGODB_TEST_PORT
      mongoInit("$mongoURL:$mongoPORT")
      mongo.punch()
    }

    @AfterClass
    @JvmStatic
    fun tearDown() {
      Companion.mongoContainer.stop()
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
    val luke: Sprint? = mongo.sprintCollection.findOne(Sprint::name eq "Luke Skywalker")
    assert(luke).isNotNull()
    assert(luke!!.age).isEqualTo(19)
  }

}
