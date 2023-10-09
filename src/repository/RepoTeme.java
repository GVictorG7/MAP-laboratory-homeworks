package repository;

import domain.Tema;

public class RepoTeme extends RepoGeneric<Tema> {
    /**
     * contructor pentu un repo de teme
     */
    RepoTeme() {
        super();
    }

    /**
     * functia gaseste entitatea cu id-ul specificat
     *
     * @param id id-ul entitatii cautate
     * @return entitatea cu id-ul specificat
     */
    public Tema findOne(int id) {
        return repo.stream().filter(tema -> tema.getNr() == id).findFirst().orElse(null);
    }

    /**
     * se modifica termenul de predare a temei cu id-ul specificat cu unul nou
     *
     * @param id          id-ul temei careia ie se modifica deadline-ul
     * @param newDeadline noul deadline
     * @return vechea tema
     */
    public Tema modifyDeadline(int id, int newDeadline) {
        Tema t = findOne(id);
        t.setDeadline(newDeadline);
        return t;
    }
}
