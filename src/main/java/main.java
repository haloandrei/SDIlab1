import com.haloandrei.domain.Acquisition;
import com.haloandrei.domain.Movie;
import com.haloandrei.domain.Client;
import com.haloandrei.domain.validators.AcquisitionValidator;
import com.haloandrei.domain.validators.MovieValidator;
import com.haloandrei.domain.validators.ClientValidator;
import com.haloandrei.domain.validators.Validator;

import com.haloandrei.domain.Pair;

import org.xml.sax.SAXException;
import com.haloandrei.repository.AcquisitionFileRepository;
import com.haloandrei.repository.MovieFileRepository;
import com.haloandrei.repository.ClientFileRepository;
import com.haloandrei.repository.Repository;
import com.haloandrei.service.AcquisitionService;
import com.haloandrei.service.ClientService;
import com.haloandrei.service.MovieService;
import com.haloandrei.ui.Console;
import com.haloandrei.ui.DataBase;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class main {

        public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException, TransformerException {

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
          DataBase dataBase = new DataBase(MovieService,ClientService,AcquisitionService);
          Console console = new Console(MovieService, ClientService, AcquisitionService);
          //console.addMovies();
          dataBase.run();
          console.runConsole();
        }

}
