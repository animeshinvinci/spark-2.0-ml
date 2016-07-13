import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}

/**
  * Created by xym on 2016/7/3.
  */
object TestSql {
  case class Person(name: String, age: Long)
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder.master("local")
      .appName("RDDRelation")
      .getOrCreate()
    val path = "src/main/resources/people.json"
    val path2 = "src/main/resources/users.parquet"
    val json = spark.read.json(path)
    json.show()
    json.select("age").show()
    import spark.implicits._
    val ds: Dataset[Int] = Seq(1, 2, 3).toDS()
    ds.map(_ + 1).collect().foreach(println) // Returns: Array(2, 3, 4)
    val people: Dataset[Person] = spark.read.json(path).as[Person]
    val df: DataFrame = people.toDF()
    df.createOrReplaceTempView("people")
    val sql: DataFrame = spark.sql("SELECT name, age FROM people WHERE age >= 13 AND age <= 19")
    sql.show()

    val sql1: DataFrame = spark.sql("SELECT * FROM parquet.`src/main/resources/users.parquet`")
    sql1.show()
  val file: RDD[String] = spark.sparkContext.textFile("src/main/resources/people.txt")
    file.foreach(println)
    file.map(line =>{
      val split :Array[String]= line.split(",")
      Person(split(0),split(1).trim.toLong)
    }).toDF("name","age").show()






  }
}
