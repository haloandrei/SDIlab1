import domain.Movie;
import domain.validators.MovieValidator;
import domain.validators.Validator;
import repository.MovieFileRepository;
import repository.Repository;
import service.MovieService;
import ui.Console;

public class main {
    
        public static void main(String args[]) {

         Validator<Movie> MovieValidator = new MovieValidator();
         Repository<Long, Movie> MovieRepository = new MovieFileRepository(MovieValidator, "Movies.txt");
         MovieService MovieService = new MovieService(MovieRepository);
         Console console = new Console(MovieService);

         console.runConsole();
        }

}
