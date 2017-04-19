package controllers

import javax.inject._

import play.api.mvc._
import play.api.libs.json._
import services.PersonService
import dao.PersonDAO
import models.Person

class PersonController @Inject() (personDao: PersonDAO) extends Controller {

  def index = Action { implicit request =>
    val persons: JsArray = JsArray(PersonService.listAll(personDao))

    Ok(persons)
  }

  def add = Action(parse.tolerantFormUrlEncoded) { implicit request =>
    val person: JsValue = PersonService.newFromParams(request.queryString, personDao)

    Ok(person)
  }
//
//  def update(id: Int) = Action { implicit request =>
//    Ok()
//  }
//
  def delete(id: Int) = Action { implicit request =>
    val person: Option[Person] = PersonService.find(id, personDao)
    val deleteresult: Boolean  = PersonService.delete(id, personDao)

    val js: JsValue = if(person.isDefined) {
      Json.toJson(
        JsObject(
          Seq(
            "deleted" -> JsBoolean(deleteresult),
            "person"  -> person.get.toJsonapi
          )
        )
      )
    } else {
      Json.toJson(
        JsObject(
          Seq("empty" -> JsString("true"))
        )
      )
    }

    Ok(js)
  }

}
