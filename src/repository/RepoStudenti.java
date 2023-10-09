package repository;

import domain.Student;

public class RepoStudenti extends RepoGeneric<Student> {
    /**
     * contructor pentru un repo de studenti
     */
    RepoStudenti() {
        super();
    }

    /**
     * functia gaseste entitatea cu id-ul specificat
     *
     * @param id id-ul entitatii cautate
     * @return entitatea cu id-ul specificat
     */
    @Override
    public Student findOne(int id) {
        return repo.stream().filter(student -> student.getIdStudent() == id).findFirst().orElse(null);
    }
}
