package domain;

public class Nota {
    private int student;
    private int tema;
    private int valoare;

    /**
     * constructor pentru o nota
     *
     * @param student referina la studentul care primeste nota
     * @param tema    referinta la tema notata
     * @param valoare valoarea notei
     */
    public Nota(int student, int tema, int valoare) {
        this.student = student;
        this.tema = tema;
        this.valoare = valoare;
    }

    /**
     * getter pentru Student
     *
     * @return referinta la studentul cu nota respectiva
     */
    public int getStudent() {
        return student;
    }

    /**
     * setter pentru studentul notat
     *
     * @param student referinta la noul student
     */
    public void setStudent(int student) {
        this.student = student;
    }

    /**
     * getter pentru tema notata
     *
     * @return referinta la tema notata
     */
    public int getTema() {
        return tema;
    }

    /**
     * setter pentru tema notata
     *
     * @param tema referinta la noua tema
     */
    public void setTema(int tema) {
        this.tema = tema;
    }

    /**
     * getter pentru valoarea notei
     *
     * @return valoarea notei
     */
    public int getValoare() {
        return valoare;
    }

    /**
     * setter pentru valoarea notei
     *
     * @param valoare valoarea noii note
     */
    public void setValoare(int valoare) {
        this.valoare = valoare;
    }

    @Override
    public String toString() {
        return student + ";" + tema + ";" + valoare;
    }
}
