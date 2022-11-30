package ExamReviewer.Analizer.Analysis.StructureCode;

import ExamReviewer.Analizer.Analysis.Analyze1StudentNItem;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;

import java.util.ArrayList;

public class AnalyzeStructure1StudentNItems extends Analyze1StudentNItem {

    /**
     * the constructor.
     * @param pItems the set of the items that we want to analyze.
     * @param pSingleSol the set of a answers of a single student.
     */
    public AnalyzeStructure1StudentNItems(ArrayList<Item> pItems,
                                          ArrayList<AnswerXStudentXItemXExam> pSingleSol){
        super(pItems, pSingleSol);
        super.analyzeSingle = new AnalyzeStructure1Code1Item();
    }

    /**
     * Method to run a the set of items from an exam against the
     * solutions of a student.
     */
    public void AnalizedContent(){
        //lets run the security checks
        //lets see if the arrays have the same size.
        if(items.size() != SingleStudentSolutions.size()){
            System.out.println("Error: Phase 1 - Arrays in different size.");
            return;
        }
        //lets check the an item against the corresponding solution
        Item ttItem = null; AnswerXStudentXItemXExam ttStudSoli = null;
        for(int i = 0; i<items.size(); i++){
            ttItem = items.get(i); ttStudSoli = SingleStudentSolutions.get(i);

            //second security check
            if (ttItem.idItem != ttStudSoli.idItem){
                System.out.println("Error: Phase 1 - Solution and Item doesn't match.");
                return;
            }

            //lets start the analyzing.
            analyzeSingle.AnalyzeContent(ttItem,ttStudSoli);
        }
    }

}
