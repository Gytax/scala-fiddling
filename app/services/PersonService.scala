package services

import models.Person
import dao.PersonDAO
import play.api.libs.json.{ JsValue, Json, JsObject, JsString }

import scala.concurrent.{ Future, Await }
import scala.concurrent.duration._

object PersonService {

  def listAll(dao: PersonDAO): Seq[JsValue] =
    Await.result(dao.listAll, 1 second).map(_.toJsonapi)

  def newFromParams(params: Map[String, Seq[String]], dao: PersonDAO): JsValue = {
    val attributes:   Map[String, String] = formatParams(params)
    val person:       Person              = new Person(0, attributes("firstname"), attributes("infix"), attributes("lastname"))
    val affectedRows: Int                 = Await.result(dao.add(person), 1 second)
    var retVal: JsValue                   = Json.toJson(
      JsObject(
        Seq("empty" -> JsString("true"))
      )
    )

    if(affectedRows > 0) {
      retVal = Await.result(dao.getLast, 1 second).head.toJsonapi
    }

    retVal
  }

  def delete(id: Int, dao: PersonDAO): Boolean =
    Await.result(dao.delete(id), 1 second) > 0

  def find(id: Int, dao: PersonDAO): Option[Person] =
    Await.result(dao.find(id), 1 second)

  private def formatParams(params: Map[String, Seq[String]]): Map[String, String] = {
    Map(
      "firstname" -> params.getOrElse("firstname", Seq("")).head,
      "infix"     -> params.getOrElse("infix", Seq("")).head,
      "lastname"  -> params.getOrElse("lastname", Seq("")).head
    )
  }

}
