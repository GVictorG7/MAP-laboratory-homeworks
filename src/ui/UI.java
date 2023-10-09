//package ui;
//
//import domain.Student;
//import domain.Tema;
//import service.StudentService;
//import validator.ValidationException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class UI {
//    private StudentService studentService;
//
//    public UI() {
//        this.studentService = new StudentService();
//    }
//
//    public void showMenu() {
//        while (true) {
//            try {
//                System.out.println("0. Iesire din aplicatie" + '\n' +
//                        "1. Adauga un nou Student" + '\n' +
//                        "2. Adauga o Tema" + '\n' +
//                        "3. Sterge un Student" + '\n' +
//                        "4. Sterge o Tema" + '\n' +
//                        "5. Modifica un Student" + '\n' +
//                        "6. Modifica o Tema" + '\n' +
//                        "7. Modificare deadine" + '\n' +
//                        "8. Afiseaza toti Studentii" + '\n' +
//                        "9. Afiseaza toate Temele" + '\n' +
//                        "10. Acordare nota" + '\n' +
//                        "11. Toti studentii de la info romana sortati dupa grupa" + '\n' +
//                        "12. Toti studentii care fac cu un anumit profesor, ordonati dupa grupda" + '\n' +
//                        "13. Toti studentii dintr-o anumita grupa, sortati alfabetic" + '\n' +
//                        "14. Temele cu un anumit deadline, sortate dupa numar" + '\n' +
//                        "15. Temele cu un anumit cuvant in descriere, sortate dupa deadline" + '\n' +
//                        "16. Temele cu deadline in urmatoarele 2 saptamani" + '\n' +
//                        "17. Notele unui anumit student, ordonate dupa valoare" + '\n' +
//                        "18. Notele la o anumita tema, sortate dupa valoare" + '\n' +
//                        "19. Notele sub 5, ordonate dupa valoare");
//                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//                int cmd = Integer.parseInt(br.readLine());
//                switch (cmd) {
//                    case 0:
//                        return;
//                    case 1:
//                        System.out.println("id-ul studentului: ");
//                        int id = Integer.parseInt(br.readLine());
//                        System.out.printf("numele studentului: ");
//                        String nume = br.readLine();
//                        System.out.printf("grupa studentului: ");
//                        int grupa = Integer.parseInt(br.readLine());
//                        System.out.println("emailui stuentului: ");
//                        String email = br.readLine();
//                        System.out.println("profesorul studentului: ");
//                        String prof = br.readLine();
//                        studentService.save(new Student(id, nume, grupa, email, prof));
//                        break;
//                    case 2:
//                        System.out.println("numarul temei: ");
//                        int nr = Integer.parseInt(br.readLine());
//                        System.out.println("deadline-ul temei: ");
//                        int deadline = Integer.parseInt((br.readLine()));
//                        System.out.println("descrierea temei: ");
//                        String desc = br.readLine();
//                        // studentService.save(new Tema(nr, deadline, desc));
//                        break;
//                    case 3:
//                        System.out.println("id-ul studentului sters: ");
//                        int deleteId = Integer.parseInt(br.readLine());
//                        studentService.deleteStudent(deleteId);
//                        break;
//                    case 4:
////                        studentService.deleteTema();
//                        break;
//                    case 5:
////                        studentService.update();
//                        break;
//                    case 6:
//                        //studentService.update();
//                        break;
//                    case 7:
//                        //studentService.modifyDeadline();
//                        break;
//                    case 8:
//                        studentService.getStudenti().forEach(System.out::println);
//                        break;
//                    case 9:
//                        studentService.getTeme().forEach(System.out::println);
//                        break;
//                    case 10:
//                        System.out.println("id-ul studentului notat: ");
//                        int idSt = Integer.parseInt(br.readLine());
//                        System.out.printf("id-ul temei: ");
//                        int idTema = Integer.parseInt(br.readLine());
//                        System.out.println("valoarea notei: ");
//                        int valoare = Integer.parseInt(br.readLine());
//                        System.out.printf("observatii la tema: ");
//                        String obs = br.readLine();
//                        System.out.printf("saptamana predarii temei: ");
//                        int saptamana = Integer.parseInt(br.readLine());
//                        studentService.notare(idSt, idTema, valoare, obs, saptamana);
//                        break;
//                    case 11:
//                        studentService.filtrareIR().forEach(System.out::println);
//                        break;
//                    case 12:
//                        System.out.print("numele profesorului: ");
//                        String profSort = br.readLine();
//                        studentService.filtrareProf(profSort).forEach(System.out::println);
//                        break;
//                    case 13:
//                        System.out.println("numarul grupei: ");
//                        int grupaSort = Integer.parseInt(br.readLine());
//                        studentService.filtrareGrupa(grupaSort).forEach(System.out::println);
//                        break;
//                    case 14:
//                        System.out.println("deadline-ul temei: ");
//                        int deadlineSort = Integer.parseInt(br.readLine());
//                        studentService.filtrareDeadline(deadlineSort).forEach(System.out::println);
//                        break;
//                    case 15:
//                        System.out.println("cuvantul cautat: ");
//                        String cuv = br.readLine();
//                        studentService.filtrareDesc(cuv).forEach(System.out::println);
//                        break;
//                    case 16:
//                        System.out.println("saptamana curenta: ");
//                        int saptamanaSort = Integer.parseInt(br.readLine());
//                        studentService.filtrareNext2Weeks(saptamanaSort).forEach(System.out::println);
//                        break;
//                    case 17:
//                        System.out.println("id-ul studentului: ");
//                        int stId = Integer.parseInt(br.readLine());
//                        studentService.filtrareNoteStudent(stId).forEach(System.out::println);
//                        break;
//                    case 18:
//                        System.out.println("numarul temei: ");
//                        int temaId = Integer.parseInt(br.readLine());
//                        studentService.filtrareNoteTema(temaId).forEach(System.out::println);
//                        break;
//                    case 19:
//                        studentService.filtrareNoteSub5().forEach(System.out::println);
//                        break;
//                }
//            } catch (ValidationException e) {
//                System.out.println(e);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
