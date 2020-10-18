# Hello world Scala project

[Reference sbt site](https://docs.scala-lang.org/getting-started/sbt-track/getting-started-with-scala-and-sbt-on-the-command-line.html)

Create a hello world project. From an empty folder:

```
$ sbt new scala/hello-world.g8
[info] welcome to sbt 1.4.0 (Amazon.com Inc. Java 1.8.0_265)
[info] set current project to emptyfolder (in build file:/home/oriol/IdeaProjects/emptyfolder/)
[info] set current project to emptyfolder (in build file:/home/oriol/IdeaProjects/emptyfolder/)

A template to demonstrate a minimal Scala application 

name [Hello World template]: 
Template applied in emptyfolder/./hello-world-template

$ cd hello-world-template/
```
Inspect the source code generated in hello-world-template.

The file hello-world-template/build.sbt contais the build definition such as the Scala version and the project dependencies.
The folder hello-wold-template/src contains the source code of the project. In this case a single files with a single class:
```
object Main extends App {
  println("Hello, World!")
}
```

The hello world example simply has a Main object that prints a message through the console

# Running hello world template

From the folder of the project you start SBT:
```
    $ cd hello-world-template
    $ sbt
```

To run the project type "run" in the SBT console.
This command triggers the downloading of dependencies of the project, the compilation and then executes the code by finding an object with a main method defined.
In this example this is accomplished by defining an object Main that extends App trait.

```
   sbt> run
   [info] Compiling 1 Scala source to /home/oriol/IdeaProjects/emptyfolder/hello-world-template/target/scala-2.13/classes ...
   [info] running Main 
   Hello, World!
   [success] Total time: 12 s, completed 18-oct-2020 18:10:19
   sbt:hello-world> 
```

Quitting SBT and returning to the operating system prompt:
```
sbt:hello-world> exit
[info] shutting down sbt server
$ 
```

# SBT REPL

In SBT you can also invoke the REPL (Read Evaluation Print Loop) tool by using "console" command:

```
    $ cd hello-world-template
    $ sbt
    sbt> console
```

From the REPL you can evaluate and execute any object or method defined in the project for exammple:
```
 sbt:hello-world> console
 [info] Starting scala interpreter...
 Welcome to Scala 2.13.1 (OpenJDK 64-Bit Server VM, Java 1.8.0_265).
 Type in expressions for evaluation. Or try :help.
 scala> Main.main(Array())
 Hello, World!
```

In this previous command you run the main method of the singleton object Main with an empty list of Strings as argument.
This empty list mimics the list arguments entered by command line as in argv/argc used by the C programming language for example.
It has the same effect as running the command "run" from SBT console (explained before)

From the REPL you can get the type of an expression by using the :type command:
```
scala> :type Main
Main.type
```

Obtaing the type of the main method of the Main singleton object:
```
scala> :type Main.main(_)
Array[String] => Unit
```
(Note the use of "_" to transform the method main in function) 

Other examples:
```
scala> :type 1
Int

scala> :type 1.0
Double

scala> :type "Y combinator"
String
```

Quitting REPL and returning the SBT console:
```
scala> :quit

[success] Total time: 1436 s (23:56), completed 18-oct-2020 18:38:28
[...]
[warn] Automatically reload the build when source changes are detected by setting `Global / onChangedBuildSource := ReloadOnSourceChanges`.
[warn] Disable this warning by setting `Global / onChangedBuildSource := IgnoreSourceChanges`.
sbt:hello-world> 
```

# Generating an executable for the project

To deploy and execute a binary for a Scala project an standard option is to add the [sbt-native-packager](https://www.scala-sbt.org/sbt-native-packager/) to the project build definition.

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
A simple example to generate a stand-alone executable is executing from sbt console:
```
sbt:hello-world> universal:dist
[info] Main Scala API documentation to /home/oriol/IdeaProjects/emptyfolder/hello-world-template/target/scala-2.13/api...
[info] Wrote /home/oriol/IdeaProjects/emptyfolder/hello-world-template/target/scala-2.13/hello-world_2.13-1.0.pom
model contains 2 documentable templates
[info] Main Scala API documentation successful.
[warn] [1] The maintainer is empty
[warn] Add this to your build.sbt
[warn]   maintainer := "your.name@company.org"
[success] All package validations passed
[info] Your package is ready in /home/oriol/IdeaProjects/emptyfolder/hello-world-template/target/universal/hello-world-1.0.zip
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

