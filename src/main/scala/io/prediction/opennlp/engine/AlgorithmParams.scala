package io.prediction.opennlp.engine

import org.apache.predictionio.controller.Params

case class AlgorithmParams(iteration: Int, smoothing: Boolean) extends Params
