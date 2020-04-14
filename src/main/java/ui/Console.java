package ui;

import domain.Acquisition;
import domain.Client;
import domain.Movie;
import domain.validators.ValidatorException;
import org.xml.sax.SAXException;
import repository.XmlAcquisitionRepository;
import service.AcquisitionService;
import service.ClientService;
import service.MovieService;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Console {
    private MovieService movieService;
    private ClientService clientService;
    private AcquisitionService acquisitionService;

    public Console(MovieService movieService, ClientService clientService,AcquisitionService acquisitionService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.acquisitionService = acquisitionService;
    }

    public void runConsole() throws ParserConfigurationException, TransformerException, SAXException, IOException {

        printAllMovies();
        printAllClients();
        printAllAcquisitions();
        Scanner myInput = new Scanner( System.in );
        //int rating = myInput.nextInt();
        //printFilteredMovie(rating);
        System.out.println("da");
        Acquisition acquisition = acquisitionService.getAllAcquisition().iterator().next();
        System.out.println("da");
        XmlAcquisitionRepository.saveacquisition(acquisition);

    }

    private void printAllMovies() {
        Set<Movie> Movies = movieService.getAllMovies();
        Movies.stream().forEach(System.out::println);
    }
    private void printAllClients() {
        Set<Client> Clients = clientService.getAllClients();
        Clients.stream().forEach(System.out::println);
    }
    private void printAllAcquisitions() {
        Set<Acquisition> Acquisitions = acquisitionService.getAllAcquisition();
        Acquisitions.stream().forEach(System.out::println);
    }

    private void printFilteredMovie(int rating){
        Set<Movie> set= (Set<Movie>) movieService.findFilteredRatingMovies(rating);
        set.stream().forEach(System.out::println);
    }
    private void printFilteredMovies(int rating){
        Set<Movie> movies = movieService.getAllMovies();
        int i=-1;
        Movie[] list_of_movies = new Movie[movies.size()];
        for (Movie movie : movies) {
            if (movie.getRating() >= rating) {
                i++;
                list_of_movies[i] = movie;
            }
        }
        System.out.println(Arrays.toString(list_of_movies));
    }

    public void addMovies() {
        int count = 1;
        while (count > 0) {
            count--;
            Movie Movie = readMovie();
            if (Movie == null || Movie.getId() < 0) {
                break;
            }
            try {
                movieService.addMovie(Movie);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }
    private void addClients() {
        int count = 1;
        while (count > 0) {
            count--;
            Client Client = readClient();
            if (Client == null || Client.getId() < 0) {
                break;
            }
            try {
                clientService.addClient(Client);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO - Add Acquisitions?? or just make a function that a client bought book X?? no, stick with addAcquisition

    private Movie readMovie() {
        System.out.println("Read Movie {id,serialNumber, name, rating, price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            int rating = Integer.parseInt(bufferRead.readLine());// ...
            int price = Integer.parseInt(bufferRead.readLine());// ...

            Movie Movie = new Movie(serialNumber, name, rating, price);
            Movie.setId(id);

            return Movie;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Client readClient() {
        System.out.println("Read Client {id, name, rented_movies, money_spent}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());// ...
            String name = bufferRead.readLine();
            String rented_movies_string=bufferRead.readLine();
            List<String> rented_movies = Arrays.asList(rented_movies_string.split(";"));
            int money_spent = Integer.parseInt(bufferRead.readLine());// ...

            Client Client = new Client(name,  money_spent);
            Client.setId(id);

            return Client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
