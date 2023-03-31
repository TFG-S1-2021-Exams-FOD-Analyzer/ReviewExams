package ExamReviewer.Analizer.Analysis.StructureCode;

import ExamReviewer.Analizer.Analysis.Analyze1Code1Item;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Condition;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;
import ExamReviewer.ExamMetada.UTILS.UtilsStructure;
import gastmappers.Language;
import metrics.examcompletemetric.MetricClass;
import metrics.examcompletemetric.MetricMethod;
import metrics.structure.contolstatement.ControlStatementMetric;
import metrics.structure.contolstatement.InputControlStatement;

public class AnalyzeStructure1Code1Item extends Analyze1Code1Item {
    private double[] halfWayGrade;

    /**
     * Method to analyzed the content of the code using the analyzer project,
     * maps the solution submitted and then assigns a grade to that code.
     * @param pItem the item that we are analyzing.
     * @param pStudent the student we want to give the grade.
     */
    public void AnalyzeContent(Item pItem, AnswerXStudentXItemXExam pStudent){
        //we need 3 locations (if, while & for)
        //it's instantiated every time since we need to clean the data.
        this.halfWayGrade = new double[3];

        //setting the input of the analyzing
        InputControlStatement input = new InputControlStatement();
        input.gastJsonInputs = UtilsStructure.getInputArrayGast(pStudent.RespuestaMapeada);
        input.language = Language.LIE;
        input.WODB =  true;

        //doing the analyze of the code.
        ControlStatementMetric metric = new ControlStatementMetric();
        metric.start(input);

        /* we always ask for the first one, of the map,
        since LIE++ don't use classes,
        and then we saved into the maps.*/
        MetricClass mmClass = metric.exportOutput().getMetricResult().get(0);

        //printing the result of the analysis.
        System.out.println(mmClass.toString());

        //looping through the code searching for the ask conditions.
        for (Condition cond : pItem.getConditions()){
            //finding uses of IF in the code.
            if (cond.Nombre.compareToIgnoreCase(UtilsStructure.COND_IF)==0){
                analyzeIf(mmClass, cond);
            }
            //finding uses of FOR in the code.
            else if (cond.Nombre.compareToIgnoreCase(UtilsStructure.COND_FOR)==0){
                analyzeFor(mmClass, cond);
            }
            //finding uses of WHILE in the code.
            else if (cond.Nombre.compareToIgnoreCase(UtilsStructure.COND_WHILE)==0){
                analyzeWhile(mmClass, cond);
            }
            //else -> not supported operation
        }
        
        double tt = halfWayGrade[UtilsStructure.GRADE_IF] +
                halfWayGrade[UtilsStructure.GRADE_FOR] +
                halfWayGrade[UtilsStructure.GRADE_WHILE];
        pStudent.respuestasXEtapa[UtilsStructure.FIRST_STATE] = tt;
    }

    /**
     * Method to find the amount of If used in the whole code,
     * uses the metric of classes, as a starting point, to calculate
     * part of the grade. It saves the grade on the array of
     * "halfWayGrade", first space.
     * @param codeAnalyzed object metric class from the analyzer project.
     * @param cond the conditions from the item that we're analyzing.
     */
    private void analyzeIf(MetricClass codeAnalyzed, Condition cond){
        //lets count the amount of ifs
        int countFound = 0;
        for (MetricMethod mmMeth : codeAnalyzed.getMethods())
            countFound += mmMeth.getStatements().getIfStatement();
        calculateHalfWayGrade(UtilsStructure.GRADE_IF, cond, countFound);
    }

    /**
     * Method to find the amount of Fors used in the whole code,
     * uses the metric of classes, as a starting point, to calculate
     * part of the grade. It saves the grade on the array of
     * "halfWayGrade", second space.
     * @param mmClass object metric class from the analyzer project.
     * @param cond the conditions from the item that we're analyzing.
     */
    private void analyzeFor(MetricClass mmClass, Condition cond){
        //lets count the amount of fors
        int countFound = 0;
        for (MetricMethod mmMeth : mmClass.getMethods())
            countFound += mmMeth.getStatements().getForStatement();
        calculateHalfWayGrade(UtilsStructure.GRADE_FOR, cond, countFound);
    }

    /**
     * Method to find the amount of whiles used in the whole code,
     * uses the metric of classes, as a starting point, to calculate
     * part of the grade. It saves the grade on the array of
     * "halfWayGrade", third space.
     * @param mmClass object metric class from the analyzer project.
     * @param cond the conditions from the item that we're analyzing.
     */
    private void analyzeWhile(MetricClass mmClass, Condition cond){
        //lets count the amount of whiles
        int countFound = 0;
        for (MetricMethod mmMeth : mmClass.getMethods())
            countFound += mmMeth.getStatements().getWhileStatement();
        calculateHalfWayGrade(UtilsStructure.GRADE_WHILE, cond, countFound);
    }

    /**
     * Method to calculate the value of the amount of conditions
     * found in the metrics.
     * @param indexHalfWay the index of the halfway array in which
     *                     we're going to store the value.
     * @param cond the object condition that holds all the information
     *            from the condition.
     * @param countFound the amount of conditions that were found in
     *                   the code.
     */
    private void calculateHalfWayGrade(int indexHalfWay, Condition cond, int countFound){
        //let's check if the conditions found in the code it's
        // more that the expected.
        if (countFound > cond.Cantidad){
            countFound = cond.Cantidad;
        }
        //let's calculate the amount ifs found.
        //it's going to be store on the if grade.
        halfWayGrade[indexHalfWay] = ((double)countFound/cond.Cantidad)
                *cond.Peso*UtilsStructure.GRADE_PORCENTAGE;
    }
}