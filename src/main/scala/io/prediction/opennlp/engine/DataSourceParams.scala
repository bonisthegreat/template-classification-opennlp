package io.prediction.opennlp.engine

import org.apache.predictionio.controller.Params

case class DataSourceParams(
  appId: Int,
  cutoff: Int) extends Params
