package repository;

public interface RepoInterface<E> {
    /**
     * returneaza numarul de elemente din repo
     */
    int size();

    /**
     * functie care salveaza o noua entitate de tip generic in repo
     *
     * @param entity
     */
    void save(E entity);

    /**
     * functia sterge entitatea cu id-ul specificat
     *
     * @param id id-ul entitatii care va fi stearsa
     * @return entitatea stearsa
     */
    E delete(int id);

    /**
     * functia inlocuieste entitatea cu id-ul specificat cu o noua entitate
     *
     * @param id     id-ul entitatii de modificat
     * @param entity entatatea cu care sa va incui cea veche
     * @return vechea entitate
     */
    E update(int id, E entity);

    /**
     * functia gaseste entitatea cu id-ul specificat
     *
     * @param id id-ul entitatii cautate
     * @return entitatea cu id-ul specificat
     */
    E findOne(int id);

    /**
     * @return repository-ul iterabil
     */
    Iterable<E> findAll();
}
