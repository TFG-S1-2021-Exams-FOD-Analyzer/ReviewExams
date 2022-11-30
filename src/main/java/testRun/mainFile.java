package testRun;

import ExamReviewer.Analizer.ReviewExam;
import ExamReviewer.ExamMetada.ClientStructures.URL_PATHS_EXAM;
import ExamReviewer.ExamMetada.UTILS.UtilsStructure;
import scala.sys.process.processInternal;

public class mainFile {

    public void testing(){
        String examTest = "C:\\Users\\ellio\\Documents\\GitHub\\test\\testAnalyzerTFG\\testStructures\\ExamJsonExample3.json";
        String answersAllTest = "C:\\Users\\ellio\\Documents\\GitHub\\test\\testAnalyzerTFG\\testStructures\\AnswerStudentsJsonExampleAll2.json";
        ReviewExam rr = new ReviewExam();
        rr.reviewLocalExam(examTest, answersAllTest);
        rr.reviewSerial();
        //rr.reviewParallel(rr.exams.get_Items());
        //rr.saveStudentAnswers(answersAllTest);
        UtilsStructure.printStructure(rr.studentAns);
    }

    public void finalExe(String[] args){
        if (args.length < 3){
            System.out.println("Error: to few arguments!!");
            return;
        }
        /**
         * Arguments Example
         * from client exam and serial execution
         *      0 0 <NumberOfExam> mySavedAnswers.txt(optional)
         * from client exam and parallel execution
         *      0 1 <NumberOfExam> mySavedAnswers.txt(optional)
         * local and serial execution
         *      1 0 <ExamFilePath> <AnswersFilePath> <ToSaveAnswerPath>(optional)
         * local and parallel execution
         *      1 1 <ExamFilePath> <AnswersFilePath> <ToSaveAnswerPath>(optional)
         */
        int callData = Integer.parseInt(args[0]);
        int typeOfExe = Integer.parseInt(args[1]);
        String examTest = args[2];
        String savePath = null;
        ReviewExam rr = new ReviewExam();
        //checking through all the cases
        switch (callData){
            case UtilsStructure.LOCAL_REVIEW:
                switch (args.length){
                    case 5:
                        rr.reviewLocalExam(examTest, args[3]);
                        savePath = args[4];
                        break;
                    case 4:
                        rr.reviewLocalExam(examTest, args[3]);
                        savePath = args[3];
                        break;
                }
                break;
        }
        //long start = System.nanoTime();
        //checking the type of execution that we had to do.
        switch (typeOfExe){
            case UtilsStructure.SERIAL_REVIEW:
                rr.reviewSerial();
                break;
            case UtilsStructure.PARALLEL_REVIEW:
                rr.reviewParallel(rr.exams.get_Items());
                break;
        }
        //long finish = System.nanoTime();
        //long timeElapsed = finish - start;
        //System.out.println(timeElapsed*(1e-9));
        //trying to save the file, whether it would be on the client
        // or on disk.
        rr.saveStudentAnswers(savePath);
    }

    public static void main(String[] args) {
        UtilsStructure.printStructure(new URL_PATHS_EXAM());
        mainFile mm = new mainFile();
        //mm.testing();
        //UtilsStructure.printStructure(args);
        mm.finalExe(args);
    }
}
