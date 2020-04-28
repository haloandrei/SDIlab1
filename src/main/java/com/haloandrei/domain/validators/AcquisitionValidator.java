package com.haloandrei.domain.validators;

import com.haloandrei.domain.Acquisition;

public class AcquisitionValidator implements Validator<Acquisition>{
    @Override
    public void validate(Acquisition entity) throws ValidatorException {
        if(entity.getPriceBought()<0)
        {
            throw new ValidatorException("Money spent can't be negative!");
        }
    }
}
