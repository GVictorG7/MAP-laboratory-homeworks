package repository;

import domain.Student;

public class RepoStudenti extends RepoGeneric<Student> {
    /**
     * contructor pentru un repo de studenti
     */
    public RepoStudenti() {
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
        int i = 0;
        for (Student st : super.repo) {
            if (st.getIdStudent() == id) {
                return super.repo.get(i);
            }
            i++;
        }
        return null;
    }
}
