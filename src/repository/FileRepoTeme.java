package repository;

import domain.Tema;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileRepoTeme extends RepoTeme {
    private String filename;

    public FileRepoTeme(String filename) {
        this.filename = filename;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get("teme.txt");
        Stream<String> lines;
        try {
            lines = Files.lines(path);
            lines.forEach(ln -> {
                String[] s = ln.split(";");
                super.save(new Tema(Integer.parseInt(s[0]), Integer.parseInt(s[1]), s[2]));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile(Tema t) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename, true))) {
            out.newLine();
            out.write(t.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFileAll() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            for (Tema nota : super.repo) {
                out.write(nota.toString());
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Tema t) {
        super.save(t);
        saveToFileAll();
    }

    @Override
    public Tema delete(int id) {
        Tema t = super.delete(id);
        saveToFileAll();
        return t;
    }

    @Override
    public Tema update(int id, Tema entity) {
        Tema t = super.update(id, entity);
        saveToFileAll();
        return t;
    }
}
