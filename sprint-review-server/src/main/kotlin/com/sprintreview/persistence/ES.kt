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
package com.sprintreview.persistence

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import org.apache.http.HttpHost
import org.apache.http.util.EntityUtils
import org.elasticsearch.client.RestClient


class ES(host: String?, port: String?, method: String?) {

  private var client: RestClient = RestClient.builder(HttpHost(host, port!!.toInt(), method)).build()

  data class ClusterHealth(
          @Json(name = "cluster_name")
          val clusterName: String,
          val status: String,
          @Json(name = "number_of_nodes")
          val numberOfNodes: Int)

  fun checkInfos(): ClusterHealth? {
    val response = client.performRequest("GET", "/_cluster/health")
    return Klaxon().parse<ClusterHealth>(EntityUtils.toString(response.entity))
  }
}

