package ExamReviewer.Analizer;

import ExamReviewer.Analizer.GlobalsSpark.SparkUtils;
import ExamReviewer.Analizer.RequestExams.RequestExams;
import ExamReviewer.Analizer.RequestExams.RequestExamsToLocal;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Exam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;
import ExamReviewer.ExamMetada.UTILS.UtilsStructure;
import gastmappers.misc.Misc;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

import java.io.IOException;
import java.util.ArrayList;

public class ReviewExam {

    public Exam exams;
    public static ArrayList<ArrayList<AnswerXStudentXItemXExam>> studentAns;
    private boolean fromClient;
    private RequestExams reqs;

    public ReviewExam(){
        //setting the compiler location
        String liePath = System.getenv(UtilsStructure.COMPILER_ROOT);
        UtilsStructure.COMPILER_ABSOLUTE_PATH = liePath + UtilsStructure.COMPILER_LOCATION;
    }

    /**
     * Method to read an exam and the student answers
     * from the local disk.
     * @param exam the path of the exam in the disk.
     * @param stuAns the path of the student in the disk.
     */
    public void reviewLocalExam(String exam, String stuAns){
        reqs = new RequestExamsToLocal();
        reqs.ExtractExam(exam, stuAns);
        exams = reqs.getExam(); studentAns = reqs.getStudentAnswers();
        fromClient = false;
    }

    /**
     * Method to review, in a parallel approach
     * using spark, the answers of the students
     * against one exam.
     */
    public static void reviewParallel(ArrayList<Item> items){
        //lets clean the data.
        SparkUtils.reset();
        SparkUtils.localItems.addAll(items);
        //mapping the code
        for(Item ii : SparkUtils.localItems){
            for (String ss : ii.getSolutions().Respuesta)
                ii.MappedSolutions.add(UtilsStructure.MapSingleCode(ss));
        }
        // configure spark
        JavaRDD<ArrayList<AnswerXStudentXItemXExam>> parallSourceCode =
                SparkUtils.getSparkContext().parallelize(studentAns);

        parallSourceCode.foreach(new VoidFunction<ArrayList<AnswerXStudentXItemXExam>>() {
            @Override
            public void call(ArrayList<AnswerXStudentXItemXExam> s) throws Exception {
                SparkUtils.reviewExamPerStudent(s);
            }
        });
        SparkUtils.getSparkContext().close();
        //adding the new review scores.
        studentAns.clear();
        studentAns.addAll(SparkUtils.localStudentsAns);
    }

    /**
     * Method to review, in a serial approach,
     * the answers of the students against one
     * exam.
     */
    public void reviewSerial(){
        //lets do the items mapping
        mapAllItems(exams.get_Items());

        // it's time to review all the students, 1x1
        // against the same exam, the mapping per student
        // answer it's done internally.
        Review1Student1Exam rr = new Review1Student1Exam();
        for(ArrayList<AnswerXStudentXItemXExam> ss : studentAns){
            rr.reviewSingleStudent(exams.get_Items(), ss);
        }
    }

    /**
     * This method it's made as an alternative, in case the
     * connection to the data base it's not currently working,
     * to save on the disk the answers of a student.
     * */
    public void saveStudentAnswers(String path2Save){
        //we're saving onto the disk.
        if(path2Save != null){
            try {
                Misc.writeInFile(UtilsStructure.gson.toJson(
                        reqs.getSimplifiedScore()), path2Save, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //let's see if we can sent the student answers
        if (fromClient)
            reqs.saveStudenAnswers(studentAns);
    }

    /**
     * Method to map the items solutions of an exam.
     * @param items the array list of the items from an exam.
     */
    private void mapAllItems(ArrayList<Item> items){
        for(Item ii : items){
            for (String ss : ii.getSolutions().Respuesta)
                ii.MappedSolutions.add(UtilsStructure.MapSingleCode(ss));
        }
    }
}
