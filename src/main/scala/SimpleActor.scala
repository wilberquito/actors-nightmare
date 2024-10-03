// actors are objects that have threds attached to it
// instead of calling methods because it can generates state problems if many
// are accesing to it at the same time, actors receive messages
// there is this concept of inbox, so any received message is processed
// from this inbox queue. THERE IS NO POSSABILITY TO RAISE CONDITIONS HERE.
//
// There are some rules to follow.
// 1) Actors can have their own mutable data, but it can not being shared between actos
// 2) You never send messages with mutable data, messages are inmutable
//

import akka.actor.{Actor, ActorSystem, Props}

object SimpleActor extends App {
  class SimpleActor extends Actor {
    def receive = {
      case s: String => println("String: " + s)
      case i: Int => println("Number: " + i)
    }

    def foo = println("Normal method")
  }

  val system = ActorSystem("SympleSystem")
  val actor = system.actorOf(Props[SimpleActor], "SimpleActor")

  // `actor` is an actorRef and not the actor itself.
  // that is why I can not access to function definitions as follows
  // > actor.foo
  // The actorRef wraps the actor so it provides a message box handling

  println("Before messages")
  actor ! "Hi there"
  println("After string")
  actor ! 32
  println("After int")
  // This message will not be handled correctly
  actor ! 'a'
  println("After char")

  // This program will not terminate itself
  // otherwise we say so
  system.terminate()
}
