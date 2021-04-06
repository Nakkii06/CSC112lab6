public class Iris {
    private double sepal_length, sepal_width;
    private double petal_length, petal_width;
    private String spiecies;

    //constructors
    public Iris (){
        sepal_length = 0;
        sepal_width = 0;
        petal_length = 0;
        petal_width = 0;
        spiecies = "Unknown";
    }
    public Iris(double sl, double sw, double pl, double pw, String name){
        sepal_length = sl;
        sepal_width = sw;
        petal_length = pl;
        petal_width = pw;
        spiecies = name;
    }

    @Override
    public String toString(){
        return String.format(spiecies+ ": sepal length/width "+ sepal_length + "/" + sepal_width +
                " petal length/width " + petal_length + "/" + petal_width);
    }

    public int compareTo(Iris I2){
        if(sepal_length < I2.sepal_length)
            return 2;
        if (sepal_length == I2.sepal_length && petal_length < I2.petal_length)
            return 2;
        else
            return 1;
    }

    public boolean isLessThan(Iris I2){
        if(compareTo(I2) == 2)
            return true;
        else
            return false;
    }
}
