package ExamReviewer.Analizer.GlobalsSpark;

import ExamReviewer.Analizer.Review1Student1Exam;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.ArrayList;

public final class SparkUtils {
    private static SparkConf sparkConf;
    private static JavaSparkContext sc;
    private static final Review1Student1Exam rr = new Review1Student1Exam();
    public static final ArrayList<Item> localItems = new ArrayList<>();
    public static final ArrayList<ArrayList<AnswerXStudentXItemXExam>> localStudentsAns = new ArrayList<>();

    /**
     * Method to initialized the spark context to create the local clustering
     * this method can run with as many core as the machine has.(local[*])
     * @return the context for the spark multiprocessing initialized
     */
    public static JavaSparkContext getSparkContext() {
        // configure spark
        if (sparkConf == null) {
            sparkConf = new SparkConf().setAppName("ExamReviewer").setMaster("local[*]").set("spark.executor.memory", "4g").
                    set("spark.driver.host", "127.0.0.1").set("spark.driver.bindAddress", "127.0.0.1");
            // start a spark context
            sc = new JavaSparkContext(sparkConf);
        }
        return sc;
    }

    /**
     * Method to reset the variables
     */
    public static void reset() {
        localItems.clear();
        localStudentsAns.clear();
        if(sc != null) {
            sc.close();
            sc = null;
            sparkConf = null;
        }
    }

    /**
     * Method to review one student answers against one exam.
     * This method receives the array of items of an exam and also the
     * answers of a student.
     * This one it's parallelized by Spark, all the values are supposed
     * to be handled by reference so none value can return.
     * //@param items the array list of the items.
     * @param ans the answers of a student to be review.
     */
    public static void reviewExamPerStudent(ArrayList<AnswerXStudentXItemXExam> ans){
        localStudentsAns.add(rr.reviewSingleStudent(localItems,ans));
    }
}
