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

import com.github.pgutkowski.kgraphql.KGraphQL

data class Jedi(var name: String = "default", var age: Int = 0)

data class GraphQLQuery(val query: String = "", val variables: Map<String, Any> = emptyMap())

// FIXME with issues 160 Klaxon
//data class Results<T>(val data: List<T>)
//data class Result<T>(val data: T)

data class ListData(val jedis: List<Jedi>)
data class Data(val jedi: Jedi)
data class Results(val data: ListData)
data class Result(val data: Data)

val luke = Jedi("Luke Skywalker", 17)
val leila = Jedi("Leila", 56)

val schema = KGraphQL.schema {
  type<Jedi> {}
  configure {
    useDefaultPrettyPrinter = true
  }
  query("jedis") {
    resolver { -> listOf(luke, leila) }
  }
  query("jedi") {
    resolver { phase: String ->
      when (phase) {
        "adult" -> leila
        else -> luke
      }
    }.withArgs {
      arg<String> { name = "phase"; defaultValue = "young"; description = "The phase of the jedi" }
    }
  }
}
