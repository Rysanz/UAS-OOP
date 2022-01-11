import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.css.converter.InsetsConverter;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    Stage windowStage;
    TableView<barang> table;
    TableView<barang> tableView = new TableView<barang>();
    TextField idInput, nama_barangInput, merk_barangInput, seri_barangInput, hargaInput;

    @Override
    public void start(Stage stage) {

        // Menampilkan nama window
        windowStage = stage;
        windowStage.setTitle("DataBase - Barang");
       
        //Menampilkan tabel
        TableColumn<barang, String> columnID = new TableColumn<>("ID");
        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<barang, String> columnNama_Barang = new TableColumn<>("Nama Barang");
        columnNama_Barang.setCellValueFactory(new PropertyValueFactory<>("nama_barang"));

        TableColumn<barang, String> columnMerk_Barang = new TableColumn<>("Merk Barang");
        columnMerk_Barang.setCellValueFactory(new PropertyValueFactory<>("merk_barang"));

        TableColumn<barang, String> columnSeri_Barang = new TableColumn<>("Seri Barang");
        columnSeri_Barang.setCellValueFactory(new PropertyValueFactory<>("seri_barang"));

        TableColumn<barang, String> columnHarga = new TableColumn<>("Harga");
        columnHarga.setCellValueFactory(new PropertyValueFactory<>("harga"));
        
        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnNama_Barang);
        tableView.getColumns().add(columnMerk_Barang);
        tableView.getColumns().add(columnSeri_Barang);
        tableView.getColumns().add(columnHarga);

        //Input id
        idInput = new TextField();
        idInput.setPromptText("id");
        idInput.setMinWidth(10);

        //Input nama barang
        nama_barangInput = new TextField();
        nama_barangInput.setPromptText("Nama barang");
        nama_barangInput.setMinWidth(20);

        //Input merk barang
        merk_barangInput = new TextField();
        merk_barangInput.setPromptText("Merk barang");
        merk_barangInput.setMinWidth(20);

        //Input seri barang
        seri_barangInput = new TextField();
        seri_barangInput.setPromptText("Seri barang");
        seri_barangInput.setMinWidth(20);

        //Input harga
        hargaInput = new TextField();
        hargaInput.setPromptText("Harga");
        hargaInput.setMinWidth(20);

        //Button
        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> editButtonClicked());
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> updateButtonClicked());
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked());

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(idInput, nama_barangInput, merk_barangInput, seri_barangInput, hargaInput, editButton, updateButton, deleteButton);

        String url = "jdbc:mysql://localhost:3306/db_barang";
        String user = "root";
        String pass = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet record = stmt.executeQuery("select*from tb_kaliper");

            while (record.next()) {
                tableView.getItems().add(new barang(record.getInt("id"), record.getString("nama_barang"), record.getString("merk_barang"), record.getString("seri_barang"), record.getString("harga")));
            }
        }
        catch (SQLException e) {
            System.out.print("koneksi gagal");
        }
        

        VBox vbox = new VBox(tableView);
        vbox.getChildren().addAll(hBox);

        Scene scene = new Scene(vbox);

        stage.setScene(scene);
        stage.show();

    }




    //Update Button Clicked
    private void updateButtonClicked(){

        Database db = new Database();
                try {
                    Statement state = db.conn.createStatement();
                    String sql = "insert into tb_kaliper SET nama_barang='%s', merk_barang='%s', seri_barang='%s', harga='%s'";
                    sql = String.format(sql, nama_barangInput.getText(), merk_barangInput.getText(), seri_barangInput.getText(), hargaInput.getText());
                    state.execute(sql);
                    // idInput.clear();
                    nama_barangInput.clear();
                    merk_barangInput.clear();
                    seri_barangInput.clear();
                    hargaInput.clear();
                    loadData();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            
        };


    //Edit Button Clicked
    private void editButtonClicked(){

        Database db = new Database();
        try {
            Statement state = db.conn.createStatement();
            String sql = "update tb_kaliper set merk_barang = '%s' ,  harga = '%s' WHERE seri_barang ='%s'";
            sql = String.format(sql, merk_barangInput.getText(), hargaInput.getText(),seri_barangInput.getText());
            state.execute(sql);
            merk_barangInput.clear();
            hargaInput.clear();
            seri_barangInput.clear();
            loadData();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Delete button Clicked
    private void deleteButtonClicked(){

        Database db = new Database();
        try{
            Statement state = db.conn.createStatement();
            String sql = "delete from tb_kaliper where seri_barang ='%s';";
            sql = String.format(sql, seri_barangInput.getText());
            state.execute(sql);
            seri_barangInput.clear();
            loadData();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    

    public static void main(String[] args) {
        launch();
    }

    private void loadData() {
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_kaliper");
            tableView.getItems().clear();
            while(rs.next()){
                tableView.getItems().add(new barang(rs.getInt("id"), rs.getString("nama_barang"), rs.getString("merk_barang"), rs.getString("seri_barang"), rs.getString("harga")));
            }
            
            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    

}