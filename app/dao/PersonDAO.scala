package dao

import java.sql.Date
import javax.inject.Inject

import models.Person
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider

import scala.concurrent.{ Future, Await }
import slick.jdbc.JdbcProfile
import slick.lifted.ProvenShape

class PersonDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends HasDatabaseConfigProvider[JdbcProfile] {
  import profile.api._

  private val Persons = TableQuery[PersonsTable]

  def add(person: Person): Future[Int] =
    db.run(Persons += person)


  def delete(id: Int): Future[Int] =
    db.run(Persons.filter(_.id === id).delete)

  // def update(id: Int): Person = {}

  def find(id: Int): Future[Option[Person]] =
    db.run(Persons.filter(_.id === id).result.headOption)

  def listAll: Future[Seq[Person]] =
    db.run(Persons.result)

  def getLast: Future[Seq[Person]] =
    db.run(Persons.sortBy(_.id.desc).take(1).result)


  private class PersonsTable(tag: Tag) extends Table[Person](tag, "persons") {
    def id:         Rep[Int]    = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def firstname:  Rep[String] = column[String]("firstname")
    def infix:      Rep[String] = column[String]("infix")
    def lastname:   Rep[String] = column[String]("lastname")
    def created_at: Rep[Date]   = column[Date]("created_at")
    def updated_at: Rep[Date]   = column[Date]("updated_at")
    def * :         ProvenShape[Person] = (id, firstname, infix, lastname) <> (Person.tupled, Person.unapply)

  }

}
