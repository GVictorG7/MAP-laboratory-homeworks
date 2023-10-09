package validator;

public interface Validator<E> {
    /**
     * se valideaza entitatea primita ca parametru
     *
     * @param entity entitatea validata
     * @throws ValidationException daca entiatetea nu e valida
     */
    void validate(E entity) throws ValidationException;
}
