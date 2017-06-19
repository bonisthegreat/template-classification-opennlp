package io.prediction.opennlp.engine

import org.apache.predictionio.controller.{Engine, IEngineFactory}

object EngineFactory extends IEngineFactory {
  def apply() = {
    new Engine(
      classOf[DataSource],
      classOf[Preparator],
      Map("algo" -> classOf[Algorithm]),
      classOf[Serving])
  }
}
