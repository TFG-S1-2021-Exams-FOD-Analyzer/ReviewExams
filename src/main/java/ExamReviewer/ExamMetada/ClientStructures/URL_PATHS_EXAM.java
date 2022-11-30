package ExamReviewer.ExamMetada.ClientStructures;

public class URL_PATHS_EXAM extends URL_PATHS{
    public URL_PATHS_EXAM(){
        AGENT = "Client Connection to FOD-API-DataBase";
        BASE_LINK = "http://localhost:8080/";
        ITEM_BY_ID = "REitemsXExamen/";
        ITEM_SOLUTIONS = "RErespuestaItem/";
        ITEM_CONDITIONS = "REcondicionesItem/";
        ITEM_EXECUTION = "REvariableEsperada/";
        STUDENTS = "REestudiantesXExamen/";
        SOL_STUDENT_EXAM_ITEM = "RErespEstuXItemxExamen/";
        GRADE_TO_STUDENT = "REactualizarNota/";
    }
}
