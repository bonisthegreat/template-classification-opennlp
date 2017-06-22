import org.apache.predictionio.controller.P2LAlgorithm
import opennlp.maxent.GIS
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.predictionio.data.storage.DataMap

class Algorithm(val ap: AlgorithmParams)
  extends P2LAlgorithm[PreparedData, Model, Query, PredictedResult] {

  def train(sc: SparkContext, data: PreparedData): Model = {
   Model(GIS.trainModel(ap.iteration, data.dataIndexer, ap.smoothing))

  }

  def predict(model: Model, query: Query): PredictedResult = {
    val interest = model.gis.getAllOutcomes(model.gis.eval(query.sentence.split(" ")))
    println(interest)
    //interest.split("  |\t")
    val array = interest.split("\\]")
    //array.foreach{println}
    val listOfTuples = scala.collection.mutable.ListBuffer[(String, Double)]()

    array.foreach{
        x => val splitted = x.split("\\[")
        val thisTuple = (splitted(0).trim, splitted(1).toDouble)
        listOfTuples += thisTuple
    }
    //listOfTuples.foreach(println)
    val count = 0
    val topThreePredictions = new StringBuilder()
    for(count <- 1 to 3){

        val thisTuple = listOfTuples.maxBy(_._2)
        topThreePredictions.append(thisTuple._1)
        topThreePredictions.append("|")
        topThreePredictions.append(thisTuple._2.toString)
        topThreePredictions.append("|")
        listOfTuples -= thisTuple
    }

    //println(topThreePredictions.stripSuffix("|"))
    val finalResult = topThreePredictions.stripSuffix("|")
    PredictedResult(finalResult)
  }

  override def batchPredict(model: Model, qs: RDD[(Long, Query)]): RDD[(Long, PredictedResult)] = {
    qs.sparkContext.parallelize(
      qs.collect().map { case (index, query) =>
        (index, predict(model, query))
      })
  }

}
