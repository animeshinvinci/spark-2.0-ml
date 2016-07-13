import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by xym on 2016/7/3.
  */
object Test{
  def main(args: Array[String]) {
    val conf: SparkConf = new SparkConf().setAppName("go").setMaster("local")

    val sc: SparkContext = new SparkContext(conf)
    val data = Array(1, 2, 3, 4, 5,1)
    val rdd: RDD[Int] = sc.parallelize(data)
    rdd.foreach(println)
  }
}