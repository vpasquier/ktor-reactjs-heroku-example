package com.sprintreview.persistence

import com.sprintreview.mongo

class ModelStorageImpl : ModelStorage {
  override fun close() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSprint(name: String): Jedi {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createSprint(sprint: Jedi): Jedi {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }


  override fun getAll(): Long {
    return mongo.sprintCollection.count()
  }
}
