# actorsnightmare
playing with sbt intellij akka SF4J dependencies

How to build a standalone executable:

```
    sbt universal:dist
```

the binary is packed in:

```
    target/universal/scala-actors-example-0.1.zip
```
unzip it:

```
cd target/universal
unzip scala-actors-example-0.1.zip
```

Execute from command line:

```
scala-actors-example-0.1/bin/scala-actors-example
```