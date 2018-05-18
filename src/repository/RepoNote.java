package repository;

import domain.Nota;

import java.util.ArrayList;
import java.util.List;

public class RepoNote {
    protected List<Nota> repo;

    /**
     * constructor pentru un repo de note
     */
    public RepoNote() {
        repo = new ArrayList<Nota>();
    }

    public int size() {
        return repo.size();
    }

    public void save(Nota nota) {
        repo.add(nota);
    }

    public Nota delete(int idStud, int idTema) {
        return repo.remove(repo.indexOf(findOne(idStud, idTema)));
    }

    public Nota update(Nota nota) {
        return repo.set(repo.indexOf(findOne(nota.getStudent(), nota.getTema())), nota);
    }

    public Nota findOne(int idStud, int idTema) {
        int i = 0;
        for (Nota n : repo) {
            if (n.getStudent() == idStud & n.getTema() == idTema) {
                return repo.get(i);
            }
            i++;
        }
        return null;
    }

    public Iterable<Nota> findAll() {
        return repo;
    }
}
