import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

public class barang {
    private int id;
    private String nama_barang = null;
    private String merk_barang = null;
    private String seri_barang = null;
    private String harga = null;
    public Object conn;

    public barang(int inputId, String inputNama_Barang, String inputMerk_Barang, String inputSeri_Barang, String inputHarga) {
        this.id = inputId;
        this.nama_barang = inputNama_Barang;
        this.merk_barang = inputMerk_Barang;
        this.seri_barang = inputSeri_Barang;
        this.harga = inputHarga;
    }



    public int getId(){
        return id;
    }

    public String getNama_barang(){
        return nama_barang;
    }

    public String getMerk_barang(){
        return merk_barang;
    }

    public String getSeri_barang(){
        return seri_barang;
    }

    public String getHarga(){
        return harga;
    }


    public void setId(String text) {
    }


    public void setHarga() {
    }


    public void setHarga(String text) {
    }


    public void setHarga(double parseDouble) {
    }


    public void setNama_barang(String text) {
    }


    public void setMerk_barang(String text) {
    }


    public void setSeri_barang(String text) {
    }



}
