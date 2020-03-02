import domain.Movie;
import domain.validators.MovieValidator;
import domain.validators.Validator;
import repository.InMemoryRepository;
import repository.Repository;
import service.MovieService;
import ui.Console;

public class main {
    
        public static void main(String args[]) {
            //in-memory repo
         Validator<Movie> MovieValidator = new MovieValidator();
         Repository<Long, Movie> MovieRepository = new InMemoryRepository<>(MovieValidator);
         MovieService MovieService = new MovieService(MovieRepository);
         Console console = new Console(MovieService);

         console.runConsole();

            //file repo
//        try {
//            System.out.println(new File(".").getCanonicalPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //in file repo
//            Validator<Movie> MovieValidator = new MovieValidator();
//            Repository<Long, Movie> MovieRepository = new MovieFileRepository(MovieValidator, "./data/Movies");
//            MovieService MovieService = new MovieService(MovieRepository);
//            Console console = new Console(MovieService);
//            console.runConsole();
//


            System.out.println("Hello World!");
        }

}
