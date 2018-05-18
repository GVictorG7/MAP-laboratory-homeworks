package test;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.FileRepoNote;
import repository.FileRepoStudenti;
import repository.FileRepoTeme;

public class Test {
    public Test() {
    }

    public void runAllTests() {
        FileRepoStudenti studenti = new FileRepoStudenti("studenti.txt");
        FileRepoTeme teme = new FileRepoTeme("teme.txt");
        FileRepoNote note = new FileRepoNote("note.txt");

        assert (studenti.size() == 2);
        assert (studenti.findOne(1) == new Student(1, "nume1", 224, "email1", "prof"));
        studenti.save(new Student(3, "nume3", 224, "email3", "prof"));
        assert (studenti.size() == 3);
        studenti.delete(3);

        assert (teme.size() == 2);
        assert (teme.findOne(1) == new Tema(1, 3, "desc1"));
        teme.save(new Tema(3, 9, "desc3"));
        assert (teme.size() == 3);
        teme.delete(3);

        Nota n1 = new Nota(1, 1, 9);
        note.save(n1, "obs", 2);
        n1.setValoare(10);
        note.update(n1, "studentul a refacut tema", 3);
    }
}
