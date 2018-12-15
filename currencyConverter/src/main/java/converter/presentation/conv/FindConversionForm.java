package converter.presentation.conv;

import converter.util.Util;

class FindConversionForm {
    private int number;
    private double rate;
    private String from;
    private String to;
    private String conversionResult;
    private int totalCount;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTotalCount() { return totalCount; }

    public void setTotalCount(int count) {this.totalCount = count; }

    public void setConversionResult(double conversionResult) {
        this.conversionResult = conversionResult + " " + this.to;
    }

    public String getConversionResult() {
        return conversionResult;
    }

    @Override
    public String toString() {
        return Util.toString(this);
    }
}
