package com.sprintreview.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

data class Jedi(val name: String, val age: Int)

val client = KMongo.createClient() //get com.mongodb.MongoClient new instance
val database = client.getDatabase("test") //normal java driver usage
val collection = database.getCollection<Jedi>() //KMongo extension method
//here the name of the collection by convention is "jedi"
//you can use getCollection<Jedi>("otherjedi") if the collection name is different

class LightSaber1(val _id: String?)
class LightSaber2(val _id: org.bson.types.ObjectId?)
class LightSaber3(val _id: Int)
class LightSaber4(@BsonId val key: org.bson.types.ObjectId?)

val lightSaber1 = LightSaber1(null)
database.getCollection<LightSaber1>().insertOne(lightSaber1)
//(may be) surprisingly lightSaber1._id is now not null
