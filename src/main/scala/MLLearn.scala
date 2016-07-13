import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by xym on 2016/7/13.
  */
class MLLearn {

}

object MLLearn {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder.master("local")
      .appName("RDDRelation")
      .getOrCreate()
    val basePath = "E:/xym_worker/spark-2.0/src/main/resources/ml-100k/"
    val path = "E:/xym_worker/spark-2.0/src/main/resources/u.data"
    val sc: SparkContext = spark.sparkContext
    val rawData: RDD[String] = sc.textFile(path)
    val rawRatings: RDD[Array[String]] = rawData.map(_.split("\t").take(3))
    val ratings = rawRatings.map {
      case Array(user, item, rate) =>
        Rating(user.toInt, item.toInt, rate.toDouble)
    }
    val model: MatrixFactorizationModel = ALS.train(ratings,50,10,0.01)
    val predict: Double = model.predict(789,123)
    println(predict)
    val products: Array[Rating] = model.recommendProducts(789,10)
    products.foreach(println)




  }
}