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

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.ServerAddress
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.sprintreview.constants.Configuration
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

class Mongo(uri: String?) {

  private var client: MongoClient
  private val database: MongoDatabase
  val sprintCollection: MongoCollection<Jedi>

  init {
    try {
      client = KMongo.createClient(MongoClientURI(uri))
    } catch (e: IllegalArgumentException) {
      client = KMongo.createClient(ServerAddress(uri))
    }
    val name: String? = System.getenv(Configuration.MONGODB_NAME) ?: "sprintreview"
    database = client.getDatabase(name)
    sprintCollection = database.getCollection()
  }

  fun punch() {
    if (sprintCollection.count() == 0L)
      inject()
  }

  private fun inject() {
    injectSprint()
  }

  private fun injectSprint() {
    sprintCollection.insertOne(luke)
  }
}

