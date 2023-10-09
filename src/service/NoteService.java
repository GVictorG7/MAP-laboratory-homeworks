package service;

import domain.Nota;
import domain.Tema;
import repository.FileRepoNote;
import repository.FileRepoTeme;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class NoteService implements Observable<Nota> {
    private final ArrayList<Observer<Nota>> notaObserver = new ArrayList<>();
    private final FileRepoNote note;

    public NoteService() {
        this.note = new FileRepoNote("note.txt");
    }

    /**
     * se acorda o nota unui student
     *
     * @param idStud           id-ul studentului notat
     * @param idTema           id-ul temei care se noteaza
     * @param valoare          nota pe tema
     * @param observatii       asupra acordarii notei
     * @param saptamanaCurenta saptamana in care a fost predata tema
     */
    public void notare(int idStud, int idTema, int valoare, String observatii, int saptamanaCurenta) {
        FileRepoTeme teme = new FileRepoTeme("teme.txt");
        Tema tema = teme.findOne(idTema);
        if (saptamanaCurenta > tema.getDeadline()) {
            valoare -= 2 * (saptamanaCurenta - tema.getDeadline());
        }
        Nota nota = new Nota(idStud, idTema, valoare);
        if (note.findOne(idStud, idTema) == null) {
            note.save(nota, observatii, saptamanaCurenta);
        } else {
            note.update(nota, observatii, saptamanaCurenta);
        }
        List<Nota> not = new ArrayList<>();
        note.findAll().forEach(not::add);
        ListEvent<Nota> ev = createEvent(ListEventType.ADD, nota, not);
        notifyObservers(ev);
    }

    public List<Nota> getNote() {
        List<Nota> not = new ArrayList<>();
        note.findAll().forEach(not::add);
        return not;
    }

    private <E> List<E> filterSorter(List<E> lista, Predicate<E> p, Comparator<E> c) {
        List<E> rez = new ArrayList<>();
        for (E e : lista)
            if (p.test(e)) rez.add(e);
        rez.sort(c);
        return rez;
    }

    public List<Nota> filtrareNoteStudent(int stId) {
        List<Nota> not = new ArrayList<>();
        note.findAll().forEach(not::add);
        Predicate<Nota> p = x -> x.getStudent() == stId;
        Comparator<Nota> c = Comparator.comparingInt(Nota::getValoare);
        return filterSorter(not, p, c);
    }

    public List<Nota> filtrareNoteTema(int temaId) {
        List<Nota> not = new ArrayList<>();
        note.findAll().forEach(not::add);
        Predicate<Nota> p = x -> x.getTema() == temaId;
        Comparator<Nota> c = Comparator.comparingInt(Nota::getValoare);
        return filterSorter(not, p, c);
    }

    public List<Nota> filtrareNoteSub5() {
        List<Nota> not = new ArrayList<>();
        note.findAll().forEach(not::add);
        Predicate<Nota> p = x -> x.getValoare() < 5;
        Comparator<Nota> c = Comparator.comparingInt(Nota::getValoare);
        return filterSorter(not, p, c);
    }

    @Override
    public void addObserver(Observer<Nota> o) {
        notaObserver.add(o);
    }

    public void removeObserver(Observer<Nota> o) {
        notaObserver.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<Nota> event) {
        notaObserver.forEach(x -> x.notifyEvent(event));
    }

    private <E> ListEvent<E> createEvent(ListEventType type, final E elem, final List<E> l) {
        return new ListEvent<>(type) {
            @Override
            public List<E> getList() {
                return l;
            }

            @Override
            public E getElement() {
                return elem;
            }
        };
    }
}
