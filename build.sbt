name := "palindrome"

version := "0.1"

scalaVersion := "2.12.4"

val akkaVersion     = "2.5.8"
val akkaHttpVersion = "10.0.11"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
libraryDependencies += "joda-time" % "joda-time" % "2.9.4"
libraryDependencies += "com.google.inject" % "guice" % "4.2.0"

libraryDependencies += "org.scalatest"     %% "scalatest"            % "3.0.3"         % Test
libraryDependencies += "org.mockito"       % "mockito-core"          % "2.7.22"        % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit"    % "10.1.1"        % Test