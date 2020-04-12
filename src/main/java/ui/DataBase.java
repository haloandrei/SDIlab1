package ui;

import service.AcquisitionService;
import service.ClientService;
import service.MovieService;
import domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBase {

    private static MovieService movieService;
    private static ClientService clientService;
    private static AcquisitionService acquisitionService;
    private static final String URL = "jdbc:postgresql://localhost:5432/catalog";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";


    public DataBase(MovieService movieService, ClientService clientService,AcquisitionService acquisitionService) {
        this.movieService = movieService;
        this.clientService = clientService;
        this.acquisitionService = acquisitionService;
    }
    public MovieService getMovieService() {
        return movieService;
    }
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
    public ClientService getClientService() {
        return clientService;
    }
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }
    public AcquisitionService getAcquisitionService() {
        return acquisitionService;
    }
    public void setAcquisitionService(AcquisitionService acquisitionService) { this.acquisitionService = acquisitionService; }

    public void addMovies() throws SQLException {
        String sql1 = "delete from movie";
        Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        preparedStatement1.executeUpdate();
        for(Movie movie : movieService.getAllMovies()) {
            String sql = "insert into movie (id, name, type, rating, price) values(?,?,?,?,?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, movie.getId());
            preparedStatement.setString(2, movie.getName());
            preparedStatement.setString(3, movie.getType());
            preparedStatement.setInt(4,movie.getRating());
            preparedStatement.setInt(5,movie.getPrice());
            preparedStatement.executeUpdate();
        }
    }

    public void addClients() throws SQLException{
        String sql1 = "delete from client";
        Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        preparedStatement1.executeUpdate();
        for(Client client : clientService.getAllClients()) {
            String sql = "insert into client (id, name, moneySpent) values(?,?,?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, client.getId());
            preparedStatement.setString(2, client.getName());
            preparedStatement.setDouble(3, client.getMoneySpent());
            preparedStatement.executeUpdate();
        }
    }

    public void addAcquisitions() throws SQLException{
        String sql1 = "delete from acquisition";
        Connection connection1 = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement1 = connection1.prepareStatement(sql1);
        preparedStatement1.executeUpdate();
        for(Acquisition acquisition : acquisitionService.getAllAcquisition()) {
            String sql = "insert into acquisition (clientid, movieid, pricebought, date) values(?,?,?,?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, acquisition.getId().first);
            preparedStatement.setLong(2, acquisition.getId().second);
            preparedStatement.setDouble(3, acquisition.getPriceBought());
            java.sql.Date sqlDate = new java.sql.Date(acquisition.getDate().getTime());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.executeUpdate();
        }
    }
    private static List<Movie> getAllMovies() throws SQLException {
        List<Movie> result = new ArrayList<>();
        String sql = "select * from movie";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String type = resultSet.getString("type");
            int rating = resultSet.getInt("rating");
            int price = resultSet.getInt("price");
            Movie movie = new Movie(type,name,rating,price);
            movie.setId(id);
            result.add(movie);
        }

        return result;
    }

    private static List<Client> getAllClients() throws SQLException {
        List<Client> result = new ArrayList<>();
        String sql = "select * from client";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            double moneySpent = resultSet.getDouble("moneyspent");
            Client client = new Client(name, moneySpent);
            client.setId(id);
            result.add(client);
        }

        return result;
    }

    private static List<Acquisition> getAllAcquisitions() throws SQLException {
        List<Acquisition> result = new ArrayList<>();
        String sql = "select * from acquisition";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long clientId = resultSet.getLong("clientid");
            Long movieId = resultSet.getLong("movieid");
            double priceBought = resultSet.getDouble("pricebought");
            Date date = resultSet.getDate("date");
            java.util.Date utilDate = new java.util.Date(date.getTime());
            Acquisition acquisition = new Acquisition(priceBought,date);
            Pair<Long, Long> pair=new Pair<>(clientId,movieId);
            acquisition.setId(pair);
            result.add(acquisition);
        }
        return result;
    }

    private static void deleteMovieById(Long id) throws SQLException {
        String sql = "delete from movie where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    private static void deleteClientById(Long id) throws SQLException {
        String sql = "delete from client where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    private static void deleteAcquisitionById(Long clientId, Long movieId) throws SQLException {
        String sql = "delete from acquisition where clientid=? and movieid=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setLong(1, clientId);
        preparedStatement.setLong(2,movieId);
        preparedStatement.executeUpdate();
    }

    private static void updateMovie(Movie movie) throws SQLException {
        String sql = "update movie set name=?, type=?, rating=?, price=? where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setString(1, movie.getName());
        preparedStatement.setString(2, movie.getType());
        preparedStatement.setInt(3, movie.getRating());
        preparedStatement.setInt(4, movie.getPrice());
        preparedStatement.setLong(5, movie.getId());
        preparedStatement.executeUpdate();
    }

    private static void updateClient(Client client) throws SQLException {
        String sql = "update client set name=?, moneyspent=? where id=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setString(1, client.getName());
        preparedStatement.setDouble(2, client.getMoneySpent());
        preparedStatement.setLong(3, client.getId());
        preparedStatement.executeUpdate();
    }

    private static void updateAcquisition(Acquisition acquisition) throws SQLException {
        String sql = "update client set pricebought=?, date=? where clientid=? and movieid=?";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        preparedStatement.setDouble(1, acquisition.getPriceBought());
        java.sql.Date sqlDate = new java.sql.Date(acquisition.getDate().getTime());
        preparedStatement.setDate(2, sqlDate);
        preparedStatement.setLong(3, acquisition.getId().first);
        preparedStatement.setLong(3, acquisition.getId().second);
        preparedStatement.executeUpdate();
    }

    public void run(){
        try {
            addMovies();
            addClients();
            addAcquisitions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


