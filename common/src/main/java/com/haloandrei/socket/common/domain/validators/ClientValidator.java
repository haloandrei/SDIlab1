package com.haloandrei.socket.common.domain.validators;


import com.haloandrei.socket.common.domain.Client;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getMoneySpent()<0)
        {
            throw new ValidatorException("Money spent can't be negative!");
        }
    }
}