import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class Summer extends Actor {
  var sum = 0
  def receive = {
    case ints: Array[Int] =>
      sum += ints.reduceLeft((a, b) => (a+b) % 7)
    case "print" => println("Sum: "+ sum)
  }
}

val system = ActorSystem("SummerSystem")
val summer = system.actorOf(Props[Summer], name = "pepito")

//summer!"print"