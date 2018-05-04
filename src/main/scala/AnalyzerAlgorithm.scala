package com.github.gzilt.tokenizer.thai

import org.apache.predictionio.controller.P2LAlgorithm
import org.apache.predictionio.controller.Params
import org.apache.spark.SparkContext
import grizzled.slf4j.Logger
import java.io.StringReader

import org.apache.lucene.analysis.th.ThaiAnalyzer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute


import scala.collection.mutable

case class AnalyzerAlgorithmParams(mult: Int) extends Params

class AnalyzerAlgorithm(val ap: AnalyzerAlgorithmParams)
  // extends PAlgorithm if Model contains RDD[]
  extends P2LAlgorithm[PreparedData, AnalyzerModel, Query, PredictedResult] {

  @transient lazy val logger = Logger[this.type]

  def train(sc: SparkContext, data: PreparedData): AnalyzerModel = {
    // this is just a dummy trainer since the engine does not require training
    val count = data.events.count().toInt * ap.mult
    new AnalyzerModel(mc = count)
  }

  def predict(model: AnalyzerModel, query: Query): PredictedResult = {

    logger.info(query.text)
    val tReader = new StringReader(query.text)

    val analyzer = new ThaiAnalyzer()

    val tStream = analyzer.tokenStream("contents", tReader)
    val term = tStream.addAttribute(classOf[CharTermAttribute])
    tStream.reset()

    val result = mutable.ArrayBuffer.empty[String]
    while (tStream.incrementToken()) {
      val termValue = term.toString
      logger.info(termValue)
      result += term.toString
    }
    PredictedResult(result.mkString(" "))
  }
}

class AnalyzerModel(val mc: Int) extends Serializable {
  override def toString = s"mc=${mc}"
}
