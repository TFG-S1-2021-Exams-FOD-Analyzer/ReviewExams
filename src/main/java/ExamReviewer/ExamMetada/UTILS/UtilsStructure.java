package ExamReviewer.ExamMetada.UTILS;

import ASTMCore.ASTMSource.CompilationUnit;
import ExamReviewer.ExamMetada.DataBaseStructures.AnswerXStudentXItemXExam;
import ExamReviewer.ExamMetada.DataBaseStructures.Exam;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gastmappers.liemapper.LIEMapper;
import gastmappers.misc.Misc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class UtilsStructure {

    public static final int AMOUNT_OF_GRADE = 3;

    /**
     * Constants for the client project
     */
    public static final String OUT_VAR = "out";
    public static final String SPLIT_VALUE = "\n";

    /**
     * Constants for the code analyzer project.
     */
    public static final int FIRST_STATE = 0;
    public static final String COND_IF = "if";
    public static final String COND_FOR = "for";
    public static final String COND_WHILE = "while";
    public static final int GRADE_IF = 0;
    public static final int GRADE_FOR = 1;
    public static final int GRADE_WHILE = 2;
    public static final int GRADE_PORCENTAGE = 1;

    /**
     * Constants for the code clone analyzer project.
     */
    public static final int SECOND_STATE = 1;
    public static final String STUD_CODE = "StudentCode";
    public static final String ITEM_CODE = "ItemCode";

    /**
     * Constants for the code executor project.
     */
    public static final int THIRD_STATE = 2;
    public static final int SOURCE = 0;
    public static final int REVIEW_EXAM = 1;
    public static final String BAD_QUOTE = "\"";
    public static final String TYPE_QUOTE1 = "\'";
    public static final String TYPE_QUOTE2 = "\"\"";
    public static final String REGEX_PATTERN = "[\t \n]";
    public static final String PYTHON_CALL = "python";
    public static final String COMPILER_ROOT = "LIE_HOME";
    public static final String COMPILER_LOCATION = "\\lang_tools\\compiler.py";
    public static String COMPILER_ABSOLUTE_PATH ;

    /**
     * Constants for the exam reviewer project.
     */
    public static final int MAKE_CONNECTION = 0;
    public static final int LOCAL_REVIEW = 1;
    public static final int SERIAL_REVIEW = 0;
    public static final int PARALLEL_REVIEW = 1;

    public static final boolean printFlag = true;
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Method to print based on test,
     * if the printFlag is true, prints the testing.
     * @param data data that we want to print.
     */
    public static void test_print(String data){
        if(printFlag)
            System.out.println(data);
    }

    /**
     * Method to print an object.
     * @param data the data that we want to print.
     */
    public static void printStructure(Object data){
        test_print(gson.toJson(data));
    }

    /**
     * Method to insert the mapped code into the input array that needs
     * the analyzer project.
     * @param inpuGast string of text that's the mapped code.
     * @return returns the three dimensional arraylist that has the mapped code.
     */
    public static ArrayList<ArrayList<ArrayList<String>>> getInputArrayGast (String inpuGast){
        ArrayList<ArrayList<ArrayList<String>>> tt1 =  new ArrayList<>();
        tt1.add(new ArrayList<>());
        tt1.get(0).add(new ArrayList<>());
        tt1.get(0).get(0).add(inpuGast);
        return tt1;
    }

    /**
     * This method has to be moved to the 4th stage.
     *
     * Method to map a students code using the mapper, converts the
     * code to a compilation unit, from the gast an then serialized
     * into a json string.
     * @return returns the string, either way, the map it's save into
     * the field "RespuestaMapeada".
     */
    public static String MapSingleCode(String studentCode){
        LIEMapper map = new LIEMapper();
        String studentCodeMapped = null;
        try {
            ArrayList<CompilationUnit> tt = map.getGastCompilationUnitInMemory(studentCode);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            studentCodeMapped = gson.toJson(tt.get(0));
        } catch (IOException e) {
            System.out.println("Error in mapping solution");
            e.printStackTrace();
        }
        return studentCodeMapped;
    }

    /**
     * Method to load a json file with the student answers
     * @param asie the file path to student answers.
     * @return returns an array of answers submitted by the student.
     */
    public static ArrayList<AnswerXStudentXItemXExam> asieJson (String asie){
        String jsonData = null;
        try {jsonData = Misc.readFileToString(asie);}
        catch (IOException e) { e.printStackTrace(); }
        ArrayList<AnswerXStudentXItemXExam> out_Value = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        out_Value = new ArrayList<>(Arrays.asList(gson.fromJson(jsonData,
                AnswerXStudentXItemXExam[].class)));
        return out_Value;
    }

    /**
     * Method to load a json file with the student answers
     * @param asie the file path to student answers.
     * @return returns an array of answers submitted by the student.
     */
    public static ArrayList<ArrayList<AnswerXStudentXItemXExam>> asieAllJson (String asie){
        String jsonData = null;
        try {jsonData = Misc.readFileToString(asie);}
        catch (IOException e) { e.printStackTrace(); }
        AnswerXStudentXItemXExam[][] tt = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        tt = gson.fromJson(jsonData,AnswerXStudentXItemXExam[][].class);
        ArrayList<ArrayList<AnswerXStudentXItemXExam>> out_Value = new ArrayList<>();
        for(AnswerXStudentXItemXExam[] ss: tt){
            out_Value.add(new ArrayList<>(Arrays.asList(ss)));
        }
        return out_Value;
    }

    /**
     * Method to load a json file with an exam information
     * @param exam the file path to exam.
     * @return returns an Exam object.
     */
    public static Exam examJson (String exam){
        String jsonData = null;
        try {jsonData = Misc.readFileToString(exam);}
        catch (IOException e) { e.printStackTrace(); }
        Exam out_Value = null;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        out_Value = gson.fromJson(jsonData,Exam.class);
        return out_Value;
    }

}
