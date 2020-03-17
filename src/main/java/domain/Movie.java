package domain;

public class Movie extends BaseEntity<Long>{
    private String type;
    private String name;
    private int rating;
    private int price;

    public Movie() {
    }

    public Movie(String type, String name, int rating, int price) {
        this.type = type;
        this.name = name;
        this.rating = rating;
        this.price=price;
    }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie Movie = (Movie) o;

        if (rating != Movie.rating) return false;
        if (!type.equals(Movie.type)) return false;
        if(price != Movie.price) return false;
        return name.equals(Movie.name);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rating;
        result = 31 * result + price;
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                '}';
    }
}
