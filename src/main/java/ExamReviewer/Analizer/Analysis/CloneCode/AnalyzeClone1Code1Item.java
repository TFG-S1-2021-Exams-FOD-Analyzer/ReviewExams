package ExamReviewer.Analizer.Analysis.CloneCode;

import ExamReviewer.Analizer.Analysis.Analyze1Code1Item;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Item;
import ExamReviewer.ExamMetada.UTILS.UtilsStructure;
import gastmappers.Language;
import metrics.cloneDetection.InputCloneDetection;
import metrics.cloneDetection.detector.Clone;
import metrics.cloneDetection.process.CloneProcessor;
import metrics.pathconfig.Path;
import metrics.pathconfig.PathFactory;

import java.io.IOException;
import java.util.ArrayList;

public class AnalyzeClone1Code1Item extends Analyze1Code1Item {

    /**
     * Method to analyzed the content of the code using the analyzer project,
     * maps the solution submitted and then assigns a grade to that code.
     * @param pItem the item that we are analyzing.
     * @param pStudent the student we want to give the grade.
     */
    public void AnalyzeContent(Item pItem, AnswerXStudentXItemXExam pStudent) {
        //lets add the name of the files.
        //the first it's going to be the student one,
        // and then the solutions of the items
        ArrayList<String> fileNames = new ArrayList<>();
        fileNames.add(UtilsStructure.STUD_CODE);

        //now we're going to insert the codes in the inputArray
        ArrayList<ArrayList<ArrayList<String>>> inArr =
                UtilsStructure.getInputArrayGast(pStudent.RespuestaMapeada);
        for (int j = 0; j <pItem.MappedSolutions.size(); j++){
            //lets add the name of the files.
            fileNames.add(UtilsStructure.ITEM_CODE+j);

            //now add the item solutions mapped in the array
            inArr.get(0).add(new ArrayList<>());
            inArr.get(0).get(j+1).add(pItem.MappedSolutions.get(j));
        }

        //now lets set the input clone detection.
        InputCloneDetection input = new InputCloneDetection(false);
        input.gastJsonInputs = inArr;
        input.language = Language.LIE;
        //making the path matched to the language specified in
        // the input clone detection.
        Path pathVar = PathFactory.generatePaths(input.language);

        ArrayList<Clone> clones = null;
        //step 4: lets set the parameters of the thresholds for the
        //        clone detection.
        //step 5: lets call and save the clone detection.
        try {
            clones = new CloneProcessor().processClones(input,
                    pathVar, fileNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //lets find the clones that are not pair with themselves,
        // and also include the student code.
        ArrayList<Clone> selectedClones = selectClones(clones);

        //printing the result of the analysis.
        System.out.println(selectedClones.toString());

        if(selectedClones.size() == 0){
            //let's set the final value
            pStudent.respuestasXEtapa[UtilsStructure.SECOND_STATE] = -1;
            return;
        }
        //let's set the final value, if there are any clone, regarding their type,
        // we give the full score.
        pStudent.respuestasXEtapa[UtilsStructure.SECOND_STATE] =
                pItem.getSolutions().Peso_clon;
    }

    /**
     * Method to discriminate between the clones and select the clones
     * that have the student code and the item, but not the same between
     * each other.
     * @param pClones the array that has the clones.
     * @return returns the array of the selected clones.
     */
    private ArrayList<Clone> selectClones(ArrayList<Clone> pClones){
        //in this array we're going to save the clones that don't come
        // from the same code and has the student code.
        ArrayList<Clone> outArray = new ArrayList<>();
        for (Clone cc : pClones){
            // example : clone (pair1:Item_CodeN, pair2:Student_Code)
            //lets check if the pair it's from different files.
            /*System.out.println(cc.getMethod1().second.getFile() +"-"+
                    cc.getCloneType() +"-"+
                    cc.getMethod2().second.getFile());*/
            boolean cond1 = cc.getMethod1().second.getFile().compareTo(
                    cc.getMethod2().second.getFile()) != 0;
            //lets check if the pair1 it's from student code.
            boolean cond21 = cc.getMethod1().second.getFile().
                    compareTo(UtilsStructure.STUD_CODE) == 0;
            //lets check if the pair2 it's from student code.
            boolean cond22 = cc.getMethod2().second.getFile().
                    compareTo(UtilsStructure.STUD_CODE) == 0;
            if (cond1 && (cond21||cond22)){
                //if the code it's not compare between the same, and, at least, one of
                // them it's from a student, we're interest in this clone.
                outArray.add(cc);
            }
        }
        return outArray;
    }
}
