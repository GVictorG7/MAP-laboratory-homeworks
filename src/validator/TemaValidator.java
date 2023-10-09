package validator;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    /**
     * se valideaza entitatea primita ca parametru
     *
     * @param entity entitatea validata
     * @throws ValidationException daca entiatetea nu e valida
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        if (entity.getDeadline() == 0 || entity.getNr() == 0 || entity.getDescriere().isEmpty()) {
            throw new ValidationException("campurile temei nu pot fi goale sau 0");
        }
    }
}
