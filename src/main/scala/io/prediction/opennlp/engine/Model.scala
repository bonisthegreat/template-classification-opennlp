package io.prediction.opennlp.engine

import java.io.File
import java.io.FileInputStream
import java.util.zip.GZIPInputStream

import org.apache.predictionio.controller.IPersistentModel
import org.apache.predictionio.controller.IPersistentModelLoader
import opennlp.maxent.io.GISModelReader
import opennlp.maxent.io.SuffixSensitiveGISModelWriter
import opennlp.model.AbstractModel
import opennlp.model.PlainTextFileDataReader
import org.apache.spark.SparkContext

case class Model(gis: AbstractModel)
