package repository;

import java.util.ArrayList;
import java.util.List;

public abstract class RepoGeneric<E> implements RepoInterface<E> {
    protected List<E> repo;

    /**
     * Constructor pt un repo generic cu elemente de tip E
     */
    public RepoGeneric() {
        repo = new ArrayList<>();
    }

    /**
     * returneaza numarul de elemente din repo
     */
    public int size() {
        return repo.size();
    }

    /**
     * functie care salveaza o noua entitate de tip generic in repo
     *
     * @param entity
     */
    @Override
    public void save(E entity) {
        repo.add(entity);
    }

    /**
     * functia sterge entitatea cu id-ul specificat
     *
     * @param id id-ul entitatii care va fi stearsa
     * @return entitatea stearsa
     */
    @Override
    public E delete(int id) {
        return repo.remove(repo.indexOf(findOne(id)));
    }

    /**
     * functia inlocuieste entitatea cu id-ul specificat cu o noua entitate
     *
     * @param id     id-ul entitatii de modificat
     * @param entity entatatea cu care sa va incui cea veche
     * @return vechea entitate
     */
    @Override
    public E update(int id, E entity) {
        return repo.set(repo.indexOf(findOne(id)), entity);
    }

    /**
     * @return repository-ul iterabil
     */
    @Override
    public Iterable<E> findAll() {
        return repo;
    }
}
