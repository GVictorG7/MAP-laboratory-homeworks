package repository;

import domain.Nota;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileRepoNote extends RepoNote {
    private final String filename;

    public FileRepoNote(String filename) {
        this.filename = filename;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get("note.txt");
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(ln -> {
                String[] s = ln.split(";");
                super.save(new Nota(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFileAll() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            for (Nota nota : super.repo) {
                out.write(nota.toString());
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Nota nota, String observatii, int saptamanaPredare, String status) {
        String filenameStudent = nota.getStudent() + ".txt";
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filenameStudent, true))) {
            out.write(status);
            out.newLine();
            out.write("Student: " + nota.getStudent() +
                    "; Tema: " + nota.getTema() +
                    "; Nota: " + nota.getValoare() +
                    "; Predata in saptamana: " + saptamanaPredare);
            out.newLine();
            if (!observatii.isEmpty()) {
                out.write("Observatii: " + observatii + ";");
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save(Nota nota, String observatii, int saptamanaPredare) {
        super.save(nota);
        saveToFileAll();
        saveToFile(nota, observatii, saptamanaPredare, "Adaugare nota");
    }

    public void update(Nota nota, String observatii, int saptamanaPredare) {
        super.update(nota);
        saveToFileAll();
        saveToFile(nota, observatii, saptamanaPredare, "Modificare nota");
    }
}
