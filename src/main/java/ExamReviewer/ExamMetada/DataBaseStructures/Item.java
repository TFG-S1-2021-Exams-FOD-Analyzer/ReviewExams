package ExamReviewer.ExamMetada.DataBaseStructures;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
    public int idItem;
    public int Puntaje;
    private ArrayList<Condition> Conditions;
    private Solution Solutions;
    private ArrayList<ExeAnswer> ExeAnswers;
    public ArrayList<String> MappedSolutions = new ArrayList<>();

    public void setCondSoluAnsw(ArrayList<Condition> pCond, Solution pSol, ArrayList<ExeAnswer> exe){
        this.Conditions = pCond;
        this.Solutions = pSol;
        this.ExeAnswers = exe;
    }

    public ArrayList<Condition> getConditions() {
        return Conditions;
    }

    public Solution getSolutions() {
        return Solutions;
    }

    public ArrayList<ExeAnswer> getExeAnswers() {
        return ExeAnswers;
    }
}
