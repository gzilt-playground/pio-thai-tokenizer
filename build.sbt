name := "com.github.gzilt.tokenizer.thai"

scalaVersion := "2.11.8"
libraryDependencies ++= Seq(
  "org.apache.predictionio"     %% "apache-predictionio-core" % "0.12.0-incubating" % "provided",
  "org.apache.spark"            %% "spark-core"               % "2.1.1" % "provided",
  "org.apache.spark"            %% "spark-mllib"              % "2.1.1" % "provided",
  "org.apache.lucene"           % "lucene-core"               % "6.5.1",
  "org.apache.lucene"           % "lucene-analyzers-common"   % "6.5.1",
  "org.apache.lucene"           % "lucene-analyzers-icu"      % "6.5.1",
  "org.codehaus.plexus"         % "plexus-utils"              % "1.4.6",
  "org.scalatest"               %% "scalatest"                % "3.0.5" % "test"
)
