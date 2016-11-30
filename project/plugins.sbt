addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.3")

resolvers += "Local Maven Repository" at "file:///"+Path.userHome.absolutePath+"/.m2/repository"
