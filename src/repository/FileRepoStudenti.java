package repository;

import domain.Student;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileRepoStudenti extends RepoStudenti {
    private String filename;

    public FileRepoStudenti(String filename) {
        this.filename = filename;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get("studenti.txt");
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(ln -> {
                String[] s = ln.split(";");
                super.save(new Student(Integer.parseInt(s[0]), s[1], Integer.parseInt(s[2]), s[3], s[4]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            for (Student st : super.repo) {
                out.write(st.toString());
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Student student) {
        super.save(student);
        saveToFile();
    }

    @Override
    public Student delete(int id) {
        Student s = super.delete(id);
        saveToFile();
        return s;
    }

    @Override
    public Student update(int id, Student newSt) {
        Student st = super.update(id, newSt);
        saveToFile();
        return st;
    }
}
