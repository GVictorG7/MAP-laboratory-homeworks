package service;

import domain.Student;
import repository.FileRepoStudenti;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;
import validator.StudentValidator;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class StudentService implements Observable<Student> {
    private ArrayList<Observer<Student>> studentObserver = new ArrayList<>();
    private FileRepoStudenti studenti;
    private StudentValidator valiStudent;

    /**
     * constructor pentru StudentService
     */
    public StudentService() {
        this.studenti = new FileRepoStudenti("studenti.txt");
        this.valiStudent = new StudentValidator();
    }

    /**
     * se salveaza un student in studenti
     *
     * @param student studentul salvat
     * @throws ValidationException daca informatiile entitatii Student sunt invalide
     */
    public void save(Student student) throws ValidationException {
        valiStudent.validate(student);
        for (Student st : getStudenti()) {
            if (st.getIdStudent() == student.getIdStudent()) {
                throw new ValidationException("Duplicate ID");
            }
        }
        studenti.save(student);
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        ListEvent<Student> ev = createEvent(ListEventType.ADD, student, st);
        notifyObservers(ev);
    }

    /**
     * se sterge studentul cu id-ul specificat
     *
     * @param id-ul stidentului sters
     * @return studentul sters
     */
    public Student deleteStudent(int id) {
//        Iterator<Nota> iter = note.findAll().iterator();
//        while (iter.hasNext()) {
//            Nota n = iter.next();
//            if (n.getStudent()==id)
//                iter.remove();
//        }
        //TODO metoda de delete notification pt note la stergerea unui st/teme
//        note.saveToFileAll();
        Student r = studenti.delete(id);
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        ListEvent<Student> ev = createEvent(ListEventType.REMOVE, r, st);
        notifyObservers(ev);
        return r;
    }

    /**
     * se inlocuieste un tudent
     *
     * @param id         id-ul stuentului de inlocuit
     * @param newStudent noul student
     * @return studentul vechi
     * @throws ValidationException daca noul student e invalid
     */
    public Student update(int id, Student newStudent) throws ValidationException {
        valiStudent.validate(newStudent);
        Student r = studenti.update(id, newStudent);
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        ListEvent<Student> ev = createEvent(ListEventType.UPDATE, r, st);
        notifyObservers(ev);
        return r;
    }

    /**
     * @return lista de studenti
     */
    public List<Student> getStudenti() {
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        return st;
    }

    private <E> List<E> filterSorter(List<E> lista, Predicate<E> p, Comparator<E> c) {
        List<E> rez = new ArrayList<>();
        for (E e : lista)
            if (p.test(e)) rez.add(e);
        rez.sort(c);
        return rez;
    }

    public List<Student> filtrareIR() {
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        Predicate<Student> p = x -> x.getGrupa() >= 200 & x.getGrupa() < 300;
        Comparator<Student> c = (x, y) -> x.getGrupa() - y.getGrupa();
        return filterSorter(st, p, c);
    }

    public List<Student> filtrareProf(String prof) {
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        Predicate<Student> p = x -> x.getProf().equals(prof);
        Comparator<Student> c = (x, y) -> x.getGrupa() - y.getGrupa();
        return filterSorter(st, p, c);
    }

    public List<Student> filtrareGrupa(int grupa) {
        List<Student> st = new ArrayList<>();
        studenti.findAll().forEach(st::add);
        Predicate<Student> p = x -> x.getGrupa() == grupa;
        Comparator<Student> c = (x, y) -> x.getNume().compareTo(y.getNume());
        return filterSorter(st, p, c);
    }

    @Override
    public void addObserver(Observer<Student> o) {
        studentObserver.add(o);
    }

    @Override
    public void removeObserver(Observer<Student> o) {
        studentObserver.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<Student> event) {
        studentObserver.forEach(x -> x.notifyEvent(event));
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
