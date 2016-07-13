package mllearn

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
  * Created by xym on 2016/7/13.
  */
object Verify {
  def main(args: Array[String]) {
    val spark = SparkSession
      .builder.master("local")
      .appName("RDDRelation")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

  }
}
