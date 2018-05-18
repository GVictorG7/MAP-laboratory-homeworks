package domain;

public class Tema {
    private int nr, deadline;
    private String descriere;

    /**
     * constructor pentru o Tema
     *
     * @param nr        numarul temei de laborator
     * @param deadline  termenul limita de predare (1..14)
     * @param descriere descrierea pe scurt a cerintei
     */
    public Tema(int nr, int deadline, String descriere) {
        this.nr = nr;
        this.deadline = deadline;
        this.descriere = descriere;
    }

    /**
     * getter pentru numarul temei
     *
     * @return nr
     */
    public int getNr() {
        return nr;
    }

    /**
     * setter pentru numarul temei
     *
     * @param nr noul numar
     */
    public void setNr(int nr) {
        this.nr = nr;
    }

    /**
     * getter pentru termenul de predare a temei
     *
     * @return deadline
     */
    public int getDeadline() {
        return deadline;
    }

    /**
     * setter pentru un nou deadline
     *
     * @param deadline noul deadline
     */
    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    /**
     * getter pentru descrierea temei
     *
     * @return descriere
     */
    public String getDescriere() {
        return descriere;
    }

    /**
     * setter pentru descrierea temei
     *
     * @param descriere noua descriere
     */
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString() {
        return Integer.toString(nr) + ";" + Integer.toString(deadline) + ";" + descriere;
    }
}
