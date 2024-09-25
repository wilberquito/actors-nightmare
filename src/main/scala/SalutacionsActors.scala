/*
* This programs shows an example of how actors can communicate between them.
*/

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class HolaCadena(nom: String, cont: ActorRef)
case class Hola(nom: String)
case class Adeu()

class Saludador extends Actor {
  var salutacions = 0

  def receive: Receive = {
    case HolaCadena(nom, c) =>
      println(s"Soc en " + self.path.name + s" hola $nom" + ". Vaig a saludar a en " + c.path.name)
      salutacions += 1
      c ! Hola(self.path.name)
    case Hola(nom) =>
      println(s"Soc en " + self.path.name + s" hola $nom" + " FI SALUTACIONS")
      salutacions += 1
    case Adeu =>
      println(s"Soc en " + self.path.name + ": Adeu si t'en vas... He rebut " + salutacions + " salutacions!")
  }
}

object SalutacionsActors extends App {
  val actorSystem = ActorSystem("sistema")

  val fulano = actorSystem.actorOf(Props[Saludador], name = "fulanito")
  val mengano = actorSystem.actorOf(Props[Saludador], name = "menganito")

  fulano ! HolaCadena("Main", mengano)
  mengano ! HolaCadena("Main", fulano)

  println("MAIN: Ja he enviat les salutacions encadenades... i esperare 1 segon abans de dir adeu...")

  Thread.sleep(1000)

  fulano ! Adeu
  mengano ! Adeu
}




