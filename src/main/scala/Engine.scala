package com.github.gzilt.tokenizer.thai

import org.apache.predictionio.controller.EngineFactory
import org.apache.predictionio.controller.Engine

case class Query(text: String)

case class PredictedResult(result: String) extends Serializable

object ThaiTokenizerEngine extends EngineFactory {
  def apply() = {
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("icu" -> classOf[ICUAlgorithm],
          "analyzer" -> classOf[AnalyzerAlgorithm]),
      classOf[Serving])
  }
}
