package repository;

import domain.Nota;

import java.util.ArrayList;
import java.util.List;

public class RepoNote {
    List<Nota> repo;

    /**
     * constructor pentru un repo de note
     */
    RepoNote() {
        repo = new ArrayList<>();
    }

    public void save(Nota nota) {
        repo.add(nota);
    }

    public Nota update(Nota nota) {
        return repo.set(repo.indexOf(findOne(nota.getStudent(), nota.getTema())), nota);
    }

    public Nota findOne(int idStud, int idTema) {
        return repo.stream().filter(nota -> nota.getStudent() == idStud && nota.getTema() == idTema).findFirst().orElse(null);
    }

    public Iterable<Nota> findAll() {
        return repo;
    }
}
