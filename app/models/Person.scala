package models

import play.api.libs.json.{JsNumber, JsObject, JsString, JsValue, Json}
import org.youngcapital.jsonapi.{JsonapiReader, JsonapiWriter}

case class Person(id: Int, firstname: String, infix: String, lastname: String) {
  val fullname: String = List[String](firstname, infix, lastname).filter(_.nonEmpty).mkString(" ")
  val name:     String = fullname

  def toJsonapi: JsValue = {
    Person.personJsonWriter.toJsonapi(this)
  }

}

object Person {
  def tupled = (Person.apply _).tupled

  implicit val personJsonWriter: JsonapiWriter[Person, JsValue] = new JsonapiWriter[Person, JsValue] {
    override def toJsonapi(person: Person): JsValue = {
      Json.toJson(
        JsObject(Seq(
          "data" -> JsObject(Seq(
            "type" -> JsString("person"),
            "id"   -> JsNumber(person.id),
            "attributes" -> JsObject(Seq(
              "firstname" -> JsString(person.firstname),
              "infix"     -> JsString(person.infix),
              "lastname"  -> JsString(person.lastname),
              "fullname"  -> JsString(person.fullname)
            ))
          ))
        ))
      )
    }
  }

  implicit val personJsonReader: JsonapiReader[JsValue, Person] = new JsonapiReader[JsValue, Person] {
    override def readJsonapiId(json: JsValue): Int = {
      ( json \ "data" \ "id").as[Int]
    }

    override def readJsonapiAttributes(json: JsValue): Map[String, JsValue] = {
      ( json \ "data" \ "attributes").as[Map[String, JsValue]]
    }

    override def fromJsonapi(json: JsValue): Person = {
      new Person(
        readJsonapiId(json),
        readJsonapiAttribute(json, "firstname").as[String],
        readJsonapiAttribute(json, "infix").as[String],
        readJsonapiAttribute(json, "lastname").as[String]
      )
    }
  }

  def fromJsonapi(json: JsValue): Person = {
    Person.personJsonReader.fromJsonapi(json)
  }

  def readJsonapiId(json: JsValue): Int = {
    Person.personJsonReader.readJsonapiId(json)
  }

  def readJsonapiAttributes(json: JsValue): Map[String, JsValue] = {
    Person.personJsonReader.readJsonapiAttributes(json)
  }

  def readJsonapiAttribute(json: JsValue, attribute: String): String = {
    Person.personJsonReader.readJsonapiAttribute(json, attribute).as[String]
  }

}