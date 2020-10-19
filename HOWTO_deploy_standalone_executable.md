This example is for the hello world example described in [HOWTO_hello_world_scala_project.md](https://github.com/mateuvillaret/actorsnightmare/blob/main/HOWTO_hello_world_scala_project.md) but can be applied to any project.

# Generating an executable for the project

To deploy and execute a binary for a Scala project an standard option is to add the [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/) plugin to the project build definition.

You have to edit (or create it) the project/plugins.sbt file and add the following line:
```
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.6")
```

And add the following line in build.sbt:
```
enablePlugins(JavaAppPackaging)
```

After these changes start SBT from project root folder:
```
$sbt
```

The sbt-native-packager tool adds new commands to SBT to package scala projects in different settings.
A simple example to generate a stand-alone executable is to execute from the SBT console:
```
sbt:hello-world> universal:dist
[info] Main Scala API documentation to hello-world-template/target/scala-2.13/api...
[info] Wrote hello-world-template/target/scala-2.13/hello-world_2.13-1.0.pom
model contains 2 documentable templates
[info] Main Scala API documentation successful.
[warn] [1] The maintainer is empty
[warn] Add this to your build.sbt
[warn]   maintainer := "your.name@company.org"
[success] All package validations passed
[info] Your package is ready in hello-world-template/target/universal/hello-world-1.0.zip
[success] Total time: 5 s, completed 18-oct-2020 18:43:39
```

Exit to the system prompt and inspect the output generated in:
```
hello-world-template/target/hello-world-1.0.zip
```
Uncompress it:
```
$ cd hello-world-template/target
$ unzip hello-world-1.0.zip
Archive:  hello-world-1.0.zip
  inflating: hello-world-1.0/lib/ch.epfl.scala.hello-world-1.0.jar  
  inflating: hello-world-1.0/lib/org.scala-lang.scala-library-2.13.1.jar  
  inflating: hello-world-1.0/lib/org.scala-lang.modules.scala-parser-combinators_2.13-1.1.2.jar  
  inflating: hello-world-1.0/bin/hello-world  
  inflating: hello-world-1.0/bin/hello-world.bat 
```
The folder contains the jars of the compiled source code (ch.epfl.scala.hello-world-1.0.jar) and the jars of the dependencies.
It also contains two scripts to run the project from command line hello-world (Unix flavor) and (hello-world.bat)

This binary package can be run on any system having JRE 1.8 (or JDK 1.8) installed (we don't need SBT to run it):

```
$ cd hello-world-1.0/
$ bin/hello-world 
Hello, World!
$ 
```
It prints "Hello, World!" as expected

This is a complex and flexible tool and a full description is beyond the scope of this document. 
Please refer to [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/) for futher details.

