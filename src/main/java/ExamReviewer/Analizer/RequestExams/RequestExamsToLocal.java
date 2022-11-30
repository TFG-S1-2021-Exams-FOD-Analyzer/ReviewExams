package ExamReviewer.Analizer.RequestExams;


import ExamReviewer.ExamMetada.UTILS.UtilsStructure;

public class RequestExamsToLocal extends RequestExams{

    public RequestExamsToLocal(){
        super();
    }

    /**
     * Method to extract the exam and the student answers form
     * a local path.
     * @param examPath the path in which is located the exam.
     * @param stuAnsPath the path in which is located the
     *                   student answers.
     */
    @Override
    public void ExtractExam(String examPath, String stuAnsPath){
        //setting the exam
        exam = UtilsStructure.examJson(examPath);

        //setting the student answers, the student it's
        // going to map himself.
        StudentAnswers = UtilsStructure.asieAllJson(stuAnsPath);
    }
}
