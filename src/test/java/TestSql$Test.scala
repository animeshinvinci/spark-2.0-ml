import org.apache.spark
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.apache.spark.sql._

/**
  * Created by xym on 2016/7/3.
  */
class TestSql$Test extends FunSuite with BeforeAndAfterEach {
  var conf: SparkConf = _
  var spark:SparkSession = _
  val str = "file:/E:/open_source/spark-2.0.0-preview/examples/src/main/resources/people.json"
  val str2 = "file:/E:/open_source/spark-2.0.0-preview/examples/src/main/resources/people.txt"
  override def beforeEach() {
  conf= new SparkConf().setAppName("go").setMaster("local")
     spark = SparkSession
      .builder.master("local")
      .appName("RDDRelation")
      .getOrCreate()
  }

  override def afterEach() {

  }

  test("testMain") {
    spark.read.json("")
  }

}
