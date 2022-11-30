package ExamReviewer.Analizer.Analysis.CloneCode;

import ExamReviewer.Analizer.Analysis.Analyze1StudentNItem;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;

import java.util.ArrayList;

public class AnalyzeClone1StudentNItem extends Analyze1StudentNItem {

    /**
     * The constructor, the item's solutions and student answers has to be
     * mapped and the
     * @param pItems the items of the exam.
     * @param pStudent the student answers for the items.
     */
    public AnalyzeClone1StudentNItem(ArrayList<Item> pItems, ArrayList<AnswerXStudentXItemXExam> pStudent){
        super(pItems, pStudent);
        analyzeSingle = new AnalyzeClone1Code1Item();
    }

    /**
     * Method to do the clone analysis of the solutions
     * for all the items of the exam, that we want to check,
     * with all the answers of a student.
     */
    public void AnalizedContent(){
        //lets run the security checks
        //lets see if the arrays have the same size.
        if(items.size() != SingleStudentSolutions.size()){
            System.out.println("Error: Phase 2 - Arrays in different size.");
            return;
        }
        //lets check the an item against the corresponding solution
        Item ttItem = null; AnswerXStudentXItemXExam ttStudSoli = null;
        for(int i = 0; i<items.size(); i++){
            //lets get the item and the student solution to give a score.
            ttItem = items.get(i); ttStudSoli = SingleStudentSolutions.get(i);

            //second security check
            if (ttItem.idItem != ttStudSoli.idItem){
                System.out.println("Error: Phase 2 - Solution and Item doesn't match.");
                return;
            }
            //lets start the analyzing of the clones.
            analyzeSingle.AnalyzeContent(ttItem,ttStudSoli);
        }
    }
}
