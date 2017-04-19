package org.youngcapital.jsonapi

trait JsonapiWriter[I, O] {
  def toJsonapi(model: I): O
}
