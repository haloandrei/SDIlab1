package domain;


import domain.Pair;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Acquisition extends BaseEntity<Pair<Long,Long>> {
    private double priceBought;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); //normalise date
    private Date date;
    public Acquisition(){

    }
    public Acquisition(double price, Date date){
        priceBought = price;
        this.date = date;
    }


    public double getPriceBought() {
        return priceBought;
    }

    public SimpleDateFormat getDate() {
        return dateFormat;
    }

    public void setPriceBought(double priceBought) {
        this.priceBought = priceBought;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Acquisition{" +
                "priceBought=" + priceBought +

                ", date=" + date +
                '}';
    }
}
