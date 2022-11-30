package ExamReviewer.Analizer.RequestExams;

import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Exam;
import ExamReviewer.ExamMetada.DataBaseStructures.ScoresXStudentXItemXExam;

import java.util.ArrayList;

public abstract class RequestExams {

    protected Exam exam;
    protected ArrayList<ArrayList<AnswerXStudentXItemXExam>> StudentAnswers;

    public RequestExams(){
        StudentAnswers = new ArrayList<>();
    }

    /**
     * Method to be overwrite by the child subclasses.
     * With this method you can extract the exam and the student
     * answers.
     */
    public void ExtractExam(String examPath, String stuAnsPath){}

    /**
     * Method to save the score from the students.
     * @param studentScores the array list from the student scores.
     */
    public void saveStudenAnswers(ArrayList<ArrayList<AnswerXStudentXItemXExam>> studentScores){

    }

    /**
     * Method to get the exam that we requested.
     * @return returns the exam structure.
     */
    public Exam getExam() {
        return exam;
    }

    /**
     * Method to get the student answers that were collected once
     * we called the client.
     * @return return an array list that contain all the student
     * solutions.
     */
    public ArrayList<ArrayList<AnswerXStudentXItemXExam>> getStudentAnswers() {
        return StudentAnswers;
    }

    /**
     * Method to save only the id and the score assigned to a student to
     * be sent to the client.
     * @return the array with the simplified data.
     */
    public ArrayList<ArrayList<ScoresXStudentXItemXExam>> getSimplifiedScore(){
        ArrayList<ArrayList<ScoresXStudentXItemXExam>> outValue = new ArrayList<>();
        for(ArrayList<AnswerXStudentXItemXExam> ss : StudentAnswers){
            ArrayList<ScoresXStudentXItemXExam> out1 = new ArrayList<>();
            for (AnswerXStudentXItemXExam ans : ss){
                ScoresXStudentXItemXExam sc = new ScoresXStudentXItemXExam();
                sc.idRespuestaEstudianteXExamen = ans.idRespuestaEstudianteXExamen;
                sc.PuntajeObtenido = ans.PuntajeObtenido;
                out1.add(sc);
            }
            outValue.add(out1);
        }
        return outValue;
    }
}
