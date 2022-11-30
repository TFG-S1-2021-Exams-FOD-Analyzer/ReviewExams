package ExamReviewer.ExamMetada.DataBaseStructures;

import java.util.ArrayList;

public class Exam {
    private int _Id;
    private ArrayList<Student> Students;
    private ArrayList<Item> Items;

    public Exam(int pId){ this._Id = pId; }

    public int get_Id() { return _Id; }

    public ArrayList<Item> get_Items() { return Items; }

    public ArrayList<Student> getStudents() { return Students; }

    public void setStudents_Items(ArrayList<Item> items, ArrayList<Student> students) {
        Students = students;
        Items = items;
    }
}
