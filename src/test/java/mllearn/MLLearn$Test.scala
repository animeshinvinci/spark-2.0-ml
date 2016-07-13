package mllearn

import org.apache.spark.SparkContext
import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterEach, FunSuite}

import scala.collection.Map

/**
  * Created by xym on 2016/7/13.
  */
class MLLearn$Test extends FunSuite with BeforeAndAfterEach {
  val spark = SparkSession
    .builder.master("local")
    .appName("RDDRelation")
    .getOrCreate()
  val basePath = "E:/xym_worker/spark-2.0/src/main/resources/ml-100k/"
  val path = "E:/xym_worker/spark-2.0/src/main/resources/u.data"
  val sc: SparkContext = spark.sparkContext


  override def beforeEach() {

  }

  override def afterEach() {


  }

  test("top 10"){
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

    val rdd: RDD[String] = sc.textFile(basePath+"u.item")
    val asMap: Map[Int, String] = rdd.map(_.split("\\|").take(2)).map(array =>(array(0).toInt,array(1))).collectAsMap()
    val lookup: Seq[Rating] = ratings.keyBy(_.user).lookup(789)
    println(lookup.size)
    lookup.foreach(println)
    lookup.sortWith(_.rating >_.rating).take(10).map(rating=>(asMap(rating.product),rating.rating)).foreach(println)


    products.map(rating=>(asMap(rating.product),rating.rating)).foreach(println)
  }


  test("verify"){


  }
}
