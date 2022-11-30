package ExamReviewer.ExamMetada.DataBaseStructures;


import ExamReviewer.ExamMetada.UTILS.UtilsStructure;

import java.io.Serializable;

public class AnswerXStudentXItemXExam implements Serializable {
    public int idRespuestaEstudianteXExamen;
    public int idItem;
    public String Respuesta;
    public String RespuestaMapeada;
    public double[] respuestasXEtapa = new double[UtilsStructure.AMOUNT_OF_GRADE];;
    public double PuntajeObtenido;
}
