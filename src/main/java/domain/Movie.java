package domain;

public class Movie extends BaseEntity<Long>{
    private String type;
    private String name;
    private int rating;

    public Movie() {
    }

    public Movie(String type, String name, int rating) {
        this.type = type;
        this.name = name;
        this.rating = rating;
    }

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
        return name.equals(Movie.name);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rating;
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                "} " + super.toString();
    }
}
