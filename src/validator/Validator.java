package validator;

public interface Validator<E> {
    /**
     * se valideaza entitatea primita ca parametru
     *
     * @param entity
     * @return true daca entitatea e valida
     * @throws ValidationException daca entiatetea nu e valida
     */
    boolean validate(E entity) throws ValidationException;
}
