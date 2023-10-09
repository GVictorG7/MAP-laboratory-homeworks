package service;

import domain.Tema;
import repository.FileRepoTeme;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;
import validator.TemaValidator;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class TemeService implements Observable<Tema> {
    private final ArrayList<Observer<Tema>> temaObserver = new ArrayList<>();
    private final FileRepoTeme teme;
    private final TemaValidator valiTema;

    public TemeService() {
        this.teme = new FileRepoTeme("teme.txt");
        this.valiTema = new TemaValidator();
    }

    /**
     * se salveaza o tema in teme
     *
     * @param tema tema salvata
     * @throws ValidationException daca informatiile entitatii Tema sunt invalide
     */
    public void save(Tema tema) throws ValidationException {
        valiTema.validate(tema);
        teme.save(tema);
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        ListEvent<Tema> ev = createEvent(ListEventType.ADD, tema, tem);
        notifyObservers(ev);
    }

    /**
     * se sterge tema cu id-ul specificat
     *
     * @param id id-ul temei sterse
     */
    public void deleteTema(int id) {
        Tema t = teme.delete(id);
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        ListEvent<Tema> ev = createEvent(ListEventType.REMOVE, t, tem);
        notifyObservers(ev);
    }

    /**
     * se inlocuieste o tema
     *
     * @param id      id-ul temei de inlocuit
     * @param newTema noua tema
     * @return vechea tema
     * @throws ValidationException daca noua tema e invalida
     */
    public Tema update(int id, Tema newTema) throws ValidationException {
        valiTema.validate(newTema);
        Tema t = teme.update(id, newTema);
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        ListEvent<Tema> ev = createEvent(ListEventType.UPDATE, t, tem);
        notifyObservers(ev);
        return t;
    }

    /**
     * @return lista de teme
     */
    public List<Tema> getTeme() {
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        return tem;
    }

    private <E> List<E> filterSorter(List<E> lista, Predicate<E> p, Comparator<E> c) {
        List<E> rez = new ArrayList<>();
        for (E e : lista) {
            if (p.test(e)) rez.add(e);
        }
        rez.sort(c);
        return rez;
    }

    public List<Tema> filtrareDeadline(int deadline) {
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        Predicate<Tema> p = x -> x.getDeadline() == deadline;
        Comparator<Tema> c = Comparator.comparingInt(Tema::getNr);
        return filterSorter(tem, p, c);
    }

    public List<Tema> filtrareDesc(String cuv) {
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        Predicate<Tema> p = x -> x.getDescriere().toLowerCase().contains(cuv.toLowerCase());
        Comparator<Tema> c = Comparator.comparingInt(Tema::getDeadline);
        return filterSorter(tem, p, c);
    }

    public List<Tema> filtrareNext2Weeks(int saptamana) {
        List<Tema> tem = new ArrayList<>();
        teme.findAll().forEach(tem::add);
        Predicate<Tema> p = x -> x.getDeadline() >= saptamana && x.getDeadline() <= saptamana + 2;
        Comparator<Tema> c = Comparator.comparingInt(Tema::getDeadline);
        return filterSorter(tem, p, c);
    }

    @Override
    public void addObserver(Observer<Tema> o) {
        temaObserver.add(o);
    }

    public void removeObserver(Observer<Tema> o) {
        temaObserver.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<Tema> event) {
        temaObserver.forEach(x -> x.notifyEvent(event));
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
