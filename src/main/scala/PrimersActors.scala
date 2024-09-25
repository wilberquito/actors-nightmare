/*
 * This program demonstrates communication between actors.
 * The program uses actors to count how many primes there are in
 * a given range of integers.
 *
 * The PrimeCounter sends messages to a continuation actor that
 * is passed in the Compute method.
 *
 * The Summarizer actor is constructed with an actor argument. It
 * sends messages to that actor.
*/

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

case class Compute(data: Seq[Int], cont: ActorRef)
case class Count(n: Int, t: Long)
case class Done(count: Int)

class PrimeCounter extends Actor {
  def isPrime(n: Int): Boolean = new java.math.BigInteger("" + n).isProbablePrime(20)

  def receive: Receive = {
    case Compute(data, c) =>
      val nPrimes = data.count(isPrime)
      c ! Done(nPrimes)
  }
}


class Summarizer extends Actor {
  var nPrimes = 0
  var waitingGroups = 0
  var t0: Long = 0

  def updateCount(n: Int) {
    waitingGroups += n
    if (waitingGroups == 0) {
      println("Hi ha " + nPrimes + " primers")
      println("S'ha trigat: " + (System.nanoTime - t0) / 1e9d + " segons")
    }
  }

  def receive: Receive = {
    case Count(n, t) =>
      updateCount(n)
      t0 = t
    case Done(np) =>
      nPrimes += np
      updateCount(-1)
  }
}


object PrimersActors extends App {
  val max = 10000000
  val nActors = 10
  val groupSize = max / nActors / 10
  val groups = (2 to max).grouped(groupSize).zipWithIndex.toSeq

  val actorSystem = ActorSystem("sistema")

  val seqPrimeCounterActors =
    for (i <- 0 until nActors) yield actorSystem.actorOf(Props[PrimeCounter], "cutre" + i)
  val summarizerActor = actorSystem.actorOf(Props[Summarizer], "suma")

  val t0 = System.nanoTime // denotes the computation start

  summarizerActor ! Count(groups.length, t0)

  for ((g, i) <- groups) {
    val primeCounterActor = seqPrimeCounterActors(i % seqPrimeCounterActors.length)
    primeCounterActor ! Compute(g, summarizerActor)
  }

  println("tot enviat, esperant... a veure si triga en PACO")
}