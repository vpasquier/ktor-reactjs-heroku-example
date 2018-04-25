package com.sprintreview.test

import com.sprintreview.constants.Constants.Companion.SMOKE_TEST
import com.sprintreview.constants.Endpoints.Companion.SMOKE
import com.sprintreview.server
import io.ktor.application.Application
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class TestApplicationSmoke {

  @Test
  fun testRequest() = withTestApplication(Application::server) {
    with(handleRequest(HttpMethod.Get, SMOKE)) {
      assertEquals(response.status(), HttpStatusCode.OK)
      assertEquals(response.content, SMOKE_TEST)
    }
    with(handleRequest(HttpMethod.Get, "/")) {
      assertEquals(response.status(), HttpStatusCode.OK)
      assertTrue("We should have the index HTML in response", response.content!!.contains("html", true))
    }
  }
}