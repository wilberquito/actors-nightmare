
package totil

/*import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
*/
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

sealed trait BankAccountMessage

case class Deposit(amount: Int)  extends BankAccountMessage
case class Withdraw(amount: Int) extends BankAccountMessage
case object PrintBalance         extends BankAccountMessage

sealed trait Clients
case class balanc(pasta:Int) extends Clients
case object pastaTotal extends Clients

object Main extends App {

  /*
  def behavior(balance: Int): Behavior[BankAccountMessage] = Behaviors.receiveMessage {
    case Deposit(amount)  => behavior(balance + amount)
    case Withdraw(amount) => behavior(balance - amount)
    case PrintBalance =>
      println(s"balance = $balance")
      behavior(balance)
  }


  def bank(total: Int): Behavior[Clients] = Behaviors.receiveMessage {
    case balanc(p)  => bank(total + p)
    case pastaTotal => println(s"La pasta del band es $total")
      bank(total)
  }


  val banc = akka.actor.typed.ActorSystem(bank(total=0), name = "MyBankActorSystem")
  val account1: ActorRef[BankAccountMessage] = banc.systemActorOf(behavior(balance = 0), "account1")
  val account2: ActorRef[BankAccountMessage] = banc.systemActorOf(behavior(balance = 0), "account2")


  println(account1)

  account1 ! PrintBalance
  account1 ! Deposit(200)
  banc!balanc(200)
  account1 ! Withdraw(50)
  banc!balanc(-50)
  account1 ! PrintBalance

  account2 ! Deposit(1000)
  banc!balanc(1000)

  account2 ! PrintBalance

  banc!pastaTotal
  banc.terminate()
*/


  class SimpleActor extends Actor {
    def receive = {
      case s: String => println("String: " + s)
      case i: Int => println("Number: " + i)
    }
  }


  val systema = ActorSystem("simplesystem")
  val actor1 = systema.actorOf(Props[SimpleActor], "cutre")


  println("HOOOOOOLAAAAAAA")

  actor1 ! "hhhh"
  actor1 ! 23



  class Summer extends Actor {
    var sum = 0
    def receive = {
      case ints: Array[Int] =>
        sum += ints.reduceLeft((a, b) => (a+b) % 7)
      case "print" => println("Sum: "+ sum)
    }
  }

  val system = ActorSystem("SummerSystem")
  val summer = system.actorOf(Props[Summer], name = "summer")

  summer!"print"
  summer!(1 to 3).toArray
  print((1 to 1000000).toList.sum)
  summer!"print"
  summer!"print"
}

