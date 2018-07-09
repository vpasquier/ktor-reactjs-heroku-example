package com.sprintreview.persistence

import java.io.Closeable

interface ModelStorage : Closeable {
  fun createSprint(sprint: Jedi): Jedi
  fun getSprint(name: String): Jedi
  fun getAll(): Long
}
