package converter.domain;

import converter.util.Util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CONVERSION")
public class Conversion implements ConversionDTO {
    private static final String SEQUENCE_NAME_KEY = "SEQ_NAME";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME_KEY)
    @SequenceGenerator(name = SEQUENCE_NAME_KEY, sequenceName = "CONVERSION_SEQUENCE")

    @Column(name = "CON_FROMTO")
    private String fromto;

    @Column(name = "CON_RATE")
    private double rate;

    @Column(name = "CON_COUNT")
    private int count;

    public Conversion(String fromto, double rate, int count) {
        this.fromto = fromto;
        this.rate = rate;
        this.count = count;
    }

    protected Conversion() {
    }

    @Override
    public double getRate() {
        return rate;
    }

    @Override
    public void setNewRate(double newRate) {
        this.rate = newRate;
    }

    @Override
    public int getCount() { return count; }

    @Override
    public String toString() {
        return Util.toString(this);
    }
}