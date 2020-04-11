import domain.Acquisition;
import domain.Movie;
import domain.Client;
import domain.validators.AcquisitionValidator;
import domain.validators.MovieValidator;
import domain.validators.ClientValidator;
import domain.validators.Validator;

import domain.Pair;

import repository.AcquisitionFileRepository;
import repository.MovieFileRepository;
import repository.ClientFileRepository;
import repository.Repository;
import service.AcquisitionService;
import service.ClientService;
import service.MovieService;
import ui.Console;

public class main {

        public static void main(String args[]) {

          Pair<Long,Long> t = new Pair<>(1L,22L);

          Validator<Client> ClientValidator = new ClientValidator();
          Validator<Movie> MovieValidator = new MovieValidator();
          Validator<Acquisition> AcquisitionValidator = new AcquisitionValidator();
          Repository<Long, Movie> MovieRepository = new MovieFileRepository(MovieValidator, "Movies.txt");
          Repository<Long, Client> ClientRepository = new ClientFileRepository(ClientValidator, "Clients.txt");
          Repository<Pair<Long, Long>, Acquisition> AcquisitionRepository = new AcquisitionFileRepository(AcquisitionValidator, "Acquisitions.txt");
          MovieService MovieService = new MovieService(MovieRepository);
          ClientService ClientService= new ClientService(ClientRepository);
          AcquisitionService AcquisitionService= new AcquisitionService(AcquisitionRepository);
          Console console = new Console(MovieService, ClientService, AcquisitionService);
          //console.addMovies();
          console.runConsole();
        }

}
