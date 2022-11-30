package ExamReviewer.Analizer;

import ExamReviewer.Analizer.Analysis.Analyze1StudentNItem;
import ExamReviewer.Analizer.Analysis.CloneCode.AnalyzeClone1StudentNItem;
import ExamReviewer.Analizer.Analysis.StructureCode.AnalyzeStructure1StudentNItems;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Condition;
import ExamReviewer.ExamMetada.DataBaseStructures.ExeAnswer;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;
import ExamReviewer.ExamMetada.UTILS.UtilsStructure;

import java.util.ArrayList;

public class Review1Student1Exam {

    /**
     * Method to review the student answers against every item
     * of the exam.
     * @param items the list of the items.
     * @param studentAns the student answers.
     * @return returns the list of the student answers.
     */
    public ArrayList<AnswerXStudentXItemXExam> reviewSingleStudent(ArrayList<Item> items,
                                                                   ArrayList<AnswerXStudentXItemXExam> studentAns){
        //let's start by mapping the solutions of the student
        for(AnswerXStudentXItemXExam ss :studentAns){
            ss.RespuestaMapeada = UtilsStructure.MapSingleCode(ss.Respuesta);
        }

        //and let's head into the analysis
        Analyze1StudentNItem structure = new AnalyzeStructure1StudentNItems(items, studentAns);
        Analyze1StudentNItem clones = new AnalyzeClone1StudentNItem(items, studentAns);
        structure.AnalizedContent();
        clones.AnalizedContent();

        //right after that, lets sum up the gotten scores.
        for(int i = 0; i<items.size() ; i++ )
            calculate1Score(items.get(i), studentAns.get(i));
        return studentAns;
    }

    /**
     * Method to calculate the score according the value of
     * the items.
     * @param item the item that has the score.
     * @param ans the answer of the student to that item.
     */
    private void calculate1Score(Item item, AnswerXStudentXItemXExam ans){
        double n1 = ans.respuestasXEtapa[UtilsStructure.FIRST_STATE];
        double n2 = ans.respuestasXEtapa[UtilsStructure.SECOND_STATE];
        double n3 = ans.respuestasXEtapa[UtilsStructure.THIRD_STATE];
        if ((n1 == -1) || (n2 == -1) || (n3 == -1)){
            ans.PuntajeObtenido = -1;
            return;
        }
        double totalScore = item.getSolutions().Peso_clon;
        for(ExeAnswer ex : item.getExeAnswers())
                totalScore += ex.peso;
        for(Condition cnd : item.getConditions())
            totalScore += cnd.Peso;
        if (item.Puntaje != 0)
            ans.PuntajeObtenido = ((n1+n2+n3)/totalScore)*item.Puntaje;
    }
}
