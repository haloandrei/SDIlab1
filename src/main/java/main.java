import domain.Movie;
import domain.Client;
import domain.validators.MovieValidator;
import domain.validators.ClientValidator;
import domain.validators.Validator;
import repository.InMemoryRepository;
import repository.MovieFileRepository;
import repository.ClientFileRepository;
import repository.Repository;
import service.ClientService;
import service.MovieService;
import ui.Console;

public class main {

        public static void main(String args[]) {

         Validator<Client> ClientValidator = new ClientValidator();
         Validator<Movie> MovieValidator = new MovieValidator();
         Repository<Long, Movie> MovieRepository = new MovieFileRepository(MovieValidator, "Movies.txt");
         Repository<Long, Client> ClientRepository = new ClientFileRepository(ClientValidator, "Clients.txt");
         MovieService MovieService = new MovieService(MovieRepository);
         ClientService ClientService= new ClientService(ClientRepository);
         Console console = new Console(MovieService, ClientService);

         console.runConsole();

        }

}
