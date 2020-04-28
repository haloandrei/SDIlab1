package com.haloandrei.socket.common.domain.validators;


import com.haloandrei.socket.common.domain.Movie;

public class MovieValidator implements Validator<Movie> {
    @Override
    public void validate(Movie entity) throws ValidatorException {
        if(entity.getRating()<1 && entity.getRating()>10)
        {
            throw new ValidatorException("Rating should be between 1 and 10!");
        }
    }
}