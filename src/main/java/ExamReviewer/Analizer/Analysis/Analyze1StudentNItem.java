package ExamReviewer.Analizer.Analysis;


import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;

import java.util.ArrayList;

public abstract class Analyze1StudentNItem {
    protected ArrayList<Item> items;
    protected ArrayList<AnswerXStudentXItemXExam> SingleStudentSolutions;
    protected Analyze1Code1Item analyzeSingle;

    public Analyze1StudentNItem(ArrayList<Item> pItems,
                                ArrayList<AnswerXStudentXItemXExam> pSingleSol){
        this.items = pItems;
        this.SingleStudentSolutions = pSingleSol;
    }

    public void AnalizedContent(){

    }
}
