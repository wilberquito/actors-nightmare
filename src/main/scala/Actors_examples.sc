
val max = 10000000
val nActors = 10
val groupSize = max / nActors / 10
val groups = (2 to max).grouped(groupSize).zipWithIndex.toSeq
