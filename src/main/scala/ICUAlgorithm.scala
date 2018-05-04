package com.github.gzilt.tokenizer.thai

import org.apache.predictionio.controller.P2LAlgorithm
import org.apache.predictionio.controller.Params
import org.apache.spark.SparkContext
import grizzled.slf4j.Logger
import java.io.StringReader

//import org.apache.lucene.analysis.th.ThaiAnalyzer
//import org.apache.lucene.analysis.th.ThaiTokenizer
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.icu.segmentation.ICUTokenizer


import scala.collection.mutable

case class ICUAlgorithmParams(mult: Int) extends Params

class ICUAlgorithm(val ap: ICUAlgorithmParams)
  // extends PAlgorithm if Model contains RDD[]
  extends P2LAlgorithm[PreparedData, ICUModel, Query, PredictedResult] {

  @transient lazy val logger = Logger[this.type]

  def train(sc: SparkContext, data: PreparedData): ICUModel = {
    // this is just a dummy trainer since the engine does not require training
    val count = data.events.count().toInt * ap.mult
    new ICUModel(mc = count)
  }

  def predict(model: ICUModel, query: Query): PredictedResult = {

    val tReader = new StringReader(query.text)
    val tokenizer = new ICUTokenizer()

    tokenizer.setReader(tReader)
    tokenizer.addAttribute(classOf[CharTermAttribute])
    tokenizer.reset()

    val result = mutable.ArrayBuffer.empty[String]

    while (tokenizer.incrementToken()) {
      val termValue = tokenizer.getAttribute(classOf[CharTermAttribute]).toString
      result += termValue
    }

//    val result = mutable.ArrayBuffer.empty[String]
//    while (tStream.incrementToken()) {
//      val termValue = term.toString
//      logger.info(termValue)
//      result += term.toString
//    }

//    logger.info(query.text)
//    val tReader = new StringReader(query.text)
//
//    val analyzer = new ThaiAnalyzer()
//
//    val tStream = analyzer.tokenStream("contents", tReader)
//    val term = tStream.addAttribute(classOf[CharTermAttribute])
//    tStream.reset()
//
//    val result = mutable.ArrayBuffer.empty[String]
//    while (tStream.incrementToken()) {
//      val termValue = term.toString
//      logger.info(termValue)
//      result += term.toString
//    }
    PredictedResult(result.mkString(" "))
  }
}

class ICUModel(val mc: Int) extends Serializable {
  override def toString = s"mc=${mc}"
}
