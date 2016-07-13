import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.recommendation.ALS
import org.apache.spark.mllib.recommendation.MatrixFactorizationModel
import org.apache.spark.mllib.recommendation.Rating
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * Created by xym on 2016/7/3.
  */
object DateTest {


  //  val model = ALS.trainImplicit(ratings, rank, numIterations, lambda, alpha)
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder.master("local")
      .appName("RDDRelation")
      .getOrCreate()
val sc: SparkContext = spark.sparkContext
    // Load and parse the data
    val data = sc.parallelize(List(
      "1,100,2",
      "1,200,5",
      "1,300,4",
      "2,100,3",
      "2,300,5"))
    val ratings = data.map(_.split(',') match { case Array(user, item, rate) =>
      Rating(user.toInt, item.toInt, rate.toDouble)
    })

    // Build the recommendation model using ALS
    val rank = 10
    val numIterations = 10
    val model = ALS.train(ratings, rank, numIterations, 0.01)

    // Evaluate the model on rating data
    val usersProducts = ratings.map { case Rating(user, product, rate) =>
      (user, product)
    }
    val predictions =
      model.predict(usersProducts).map { case Rating(user, product, rate) =>
        ((user, product), rate)
      }
    val ratesAndPreds = ratings.map { case Rating(user, product, rate) =>
      ((user, product), rate)
    }.join(predictions)
    val MSE = ratesAndPreds.map { case ((user, product), (r1, r2)) =>
      val err = (r1 - r2)
      err * err
    }.mean()
    println("Mean Squared Error = " + MSE)
    val features: RDD[(Int, Array[Double])] = model.productFeatures
    features.foreach { case (x, y) =>
      println(x)
      y.foreach(println)
    }
    // Save and load model
    import spark.implicits._
    model.userFeatures.toDF("id", "features").show()
    model.productFeatures.toDF("id", "features").show()
//    model.save(sc, "e:/data/1/")
//    val sameModel = MatrixFactorizationModel.load(sc, "target/tmp/myCollaborativeFilter")
//    val alpha = 0.01
//    val lambda = 0.01
  }
}
