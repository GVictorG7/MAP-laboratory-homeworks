package domain;

public class Tema {
    private final int nr;
    private int deadline;
    private final String descriere;

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

    @Override
    public String toString() {
        return nr + ";" + deadline + ";" + descriere;
    }
}
