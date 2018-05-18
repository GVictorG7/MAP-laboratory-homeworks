package validator;

import domain.Student;

public class StudentValidator implements Validator<Student> {

    /**
     * se valideaza entitatea primita ca parametru
     *
     * @param entity
     * @return true daca entitatea e valida
     * @throws ValidationException daca entiatetea nu e valida
     */
    @Override
    public boolean validate(Student entity) throws ValidationException {
        if (entity.getEmail().isEmpty() | entity.getNume().isEmpty() | entity.getProf().isEmpty() | entity.getIdStudent() == 0 | entity.getGrupa() == 0) {
            throw new ValidationException("campurile nu pot fi goale sau 0");
        }
        return true;
    }
}
