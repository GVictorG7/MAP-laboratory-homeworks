package domain;

public class Student {
    private int idStudent, grupa;
    private String nume, email, prof;

    /**
     * constructor pentru un Student
     *
     * @param idStudent numarul matricol al studentului
     * @param nume
     * @param grupa
     * @param email
     * @param prof      cadrul didactic indrumator de la lab
     */
    public Student(int idStudent, String nume, int grupa, String email, String prof) {
        this.idStudent = idStudent;
        this.nume = nume;
        this.grupa = grupa;
        this.email = email;
        this.prof = prof;
    }

    /**
     * getter pentru ID-ul Studentului
     *
     * @return idStudent
     */
    public int getIdStudent() {
        return idStudent;
    }

    /**
     * getter pentru grupa studentului
     *
     * @return grupa
     */
    public int getGrupa() {
        return grupa;
    }

    /**
     * getter pentru numele studentului
     *
     * @return nume
     */
    public String getNume() {
        return nume;
    }

    /**
     * getter pentru email-ul studentului
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * getter pentru cadrul didactic de la laborator
     *
     * @return prof
     */
    public String getProf() {
        return prof;
    }

    @Override
    public String toString() {
        return Integer.toString(idStudent) + ";" + nume + ";" + Integer.toString(grupa) + ";" + email + ";" + prof;
    }
}
