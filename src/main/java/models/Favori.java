package Models;

public class Favori {

    int IDf;
    String noma;
    int IDA ;

    public Favori(){}

    public Favori(int IDf, String noma, int IDA) {
        this.IDf = IDf;
        this.noma = noma;
        this.IDA = IDA;
    }

    public Favori(String noma, int IDA) {
        this.noma = noma;
        this.IDA = IDA;
    }

    public Favori(String noma) {
        this.noma = noma;
    }

    public int getIDf() {
        return IDf;
    }

    public String getNoma() {
        return noma;
    }

    public int getIDA() {
        return IDA;
    }

    public void setIDf(int IDf) {
        this.IDf = IDf;
    }

    public void setNoma(String noma) {
        this.noma = noma;
    }

    public void setIDA(int IDA) {
        this.IDA = IDA;
    }

    @Override
    public String toString() {
        return "Favori{" +
                "IDf=" + IDf +
                ", noma='" + noma + '\'' +
                ", IDA=" + IDA +
                '}';
    }
}
