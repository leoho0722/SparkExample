package org.leoho.sparkexample;

// import Spark Java Library

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

// import Java Standard Library

import java.util.Arrays;
import java.util.List;

public class Main {

    // test.txt 檔案路徑
    private static String testTxtFilePath = "/Users/leoho/Desktop/SparkExample/src/main/testFile/test.txt";

    public static void main(String[] args) {
        // 宣告一個新的 SparkConf 物件
        SparkConf conf = new SparkConf().setAppName("appName").setMaster("local");

        // 宣告一個新的 JavaSparkContext 物件
        JavaSparkContext sc = new JavaSparkContext(conf);

        // map 練習
        practice(PracticeItems.map, sc, testTxtFilePath);

        // flatMap 練習
        practice(PracticeItems.flatMap, sc, testTxtFilePath);

        // filter 練習
        practice(PracticeItems.filter, sc, testTxtFilePath);

        // mapToPair 練習
        practice(PracticeItems.mapToPair, sc, testTxtFilePath);

        // flatMapToPair 練習
        practice(PracticeItems.flatMapToPair, sc, testTxtFilePath);
    }

    public enum PracticeItems {
        map,
        flatMap,
        filter,
        mapToPair,
        flatMapToPair,
        reduce,
        reduceByKey
    }

    /**
     * @param items    PracticeItems，要練習的項目
     * @param context  JavaSparkContext
     * @param filePath String，檔案路徑
     */
    public static void practice(PracticeItems items, JavaSparkContext context, String filePath) {
        // 讀檔
        JavaRDD<String> txtFile = context.textFile(filePath).cache();

        switch (items) {
            case map:
                // map 運算
                JavaRDD<String[]> txtMap = txtFile.map(s -> s.split(","));

                System.out.println("map 運算：");
                txtMap.foreach(strings ->
                        System.out.println(strings[0])
                );
            case flatMap:
                // flatMap 運算
                JavaRDD<String> txtFlatMap = txtFile.flatMap(s ->
                        Arrays.stream(s.split(",")).iterator()
                );

                System.out.println("flatMap 運算：");
                txtFlatMap.foreach(s ->
                        System.out.println(s)
                );
            case filter:
                // filter 運算
                JavaRDD<String> txtFilter = txtFile.flatMap(s ->
                        Arrays.stream(s.split(",")).iterator()
                );

                List<String> txtFilterCollect = txtFilter.collect();

                System.out.println("filter 運算：");
                for (int i = 0; i < txtFilterCollect.size(); i++) {
                    if (txtFilterCollect.get(i).contains("123") || txtFilterCollect.get(i).contains("456")) {
                        System.out.println(txtFilterCollect.get(i));
                    }
                }
            case mapToPair:
                // mapToPair 運算
                JavaRDD<String> txtMapToPair = txtFile.flatMap(s ->
                        Arrays.stream(s.split(",")).iterator()
                );
                JavaPairRDD<String, Integer> mapToPair = txtMapToPair.mapToPair(s ->
                        new Tuple2<>(s, 1)
                );
                System.out.println("mapToPair 運算：");
                mapToPair.foreach(stringIntegerTuple2 ->
                        System.out.println(stringIntegerTuple2)
                );
            case flatMapToPair:
                // flatMapToPair 運算
                JavaRDD<String[]> txtFlatMapToPair = txtFile.map(s -> s.split(","));

                System.out.println("flatMapToPair 運算：");
                txtFlatMapToPair.mapToPair(strings ->
                        new Tuple2<>(strings[0], Utilities.strArrayPlusAll(strings, 1, strings.length))
                ).foreach(s1 ->
                    System.out.println(s1)
                );
            case reduce:
                break;
            case reduceByKey:
                break;
        }
    }


}