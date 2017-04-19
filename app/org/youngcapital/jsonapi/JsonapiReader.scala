package org.youngcapital.jsonapi

trait JsonapiReader[I, O] {

  def fromJsonapi(json: I): O

  def readJsonapiId(json: I): Int

  def readJsonapiAttributes(json: I): Map[String, I]

  def readJsonapiAttribute(json: I, attribute: String): I = {
    val attrs = readJsonapiAttributes(json)
    attrs(attribute)
  }

}