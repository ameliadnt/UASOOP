import javafx.application.Application;
import javafx.scene.Scene;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.scene.control.*;
import javafx.scene.control.Button;

public class App extends Application {
    TableView<Terminal> tableView = new TableView<Terminal>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UAS OOP");
        TableColumn<Terminal, String> columnID = new TableColumn<>("Id");
        columnID.setCellValueFactory(new PropertyValueFactory<>("Id"));

        TableColumn<Terminal, String> columnMerekbus = new TableColumn<>("Merek bus");
        columnMerekbus.setCellValueFactory(new PropertyValueFactory<>("Merek_bus"));

        TableColumn<Terminal, String> columnTahunpembuatan = new TableColumn<>("Tahun pembuatan");
        columnTahunpembuatan.setCellValueFactory(new PropertyValueFactory<>("Tahun_pembuatan"));

        TableColumn<Terminal, String> columnWarna = new TableColumn<>("Warna");
        columnWarna.setCellValueFactory(new PropertyValueFactory<>("Warna"));

        TableColumn<Terminal, String> columnKapasitas = new TableColumn<>("Kapasitas");
        columnKapasitas.setCellValueFactory(new PropertyValueFactory<>("Kapasitas"));

        tableView.getColumns().add(columnID);
        tableView.getColumns().add(columnMerekbus);
        tableView.getColumns().add(columnTahunpembuatan);
        tableView.getColumns().add(columnWarna);
        tableView.getColumns().add(columnKapasitas);

        ToolBar toolBar = new ToolBar();

        Button button1 = new Button("Add Menu");
        toolBar.getItems().add(button1);
        button1.setOnAction(e -> add());

        Button button2 = new Button("Delete");
        toolBar.getItems().add(button2);
        button2.setOnAction(e -> delete());

        Button button3 = new Button("EDIT");
        toolBar.getItems().add(button3);
        button3.setOnAction(e -> edit());

        VBox vbox = new VBox(tableView, toolBar);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
        load();
        Statement stmt;
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_bus");
            tableView.getItems().clear();
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Terminal(rs.getInt("Id"), rs.getString("Merek_bus"), rs.getString("Tahun_pembuatan"), rs.getString("Warna"), rs.getString("Kapasitas")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Stage addStage = new Stage();
        Button save = new Button("simpan");

        addStage.setTitle("add data Menu");

        TextField merekbusField = new TextField();
        TextField tahunpembuatanField = new TextField();
        TextField warnaField = new TextField();
        TextField kapasitasField = new TextField();
        Label labelMerekbus = new Label("Merekbus");
        Label labelTahunpembuatan = new Label("Tahunpembuatan");
        Label labelWarna = new Label("Warna");
        Label labelKapasitas = new Label("Kapasitas");

        VBox hbox1 = new VBox(5, labelMerekbus, merekbusField);
        VBox hbox2 = new VBox(5, labelTahunpembuatan, tahunpembuatanField);
        VBox hbox3 = new VBox(5, labelWarna, warnaField);
        VBox hbox4 = new VBox(5, labelKapasitas, kapasitasField);
        VBox vbox = new VBox(20, hbox1, hbox2, hbox3, hbox4, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "insert into tb_bus SET merek_bus='%s', tahun_pembuatan='%s', warna='%s', kapasitas='%s'";
                sql = String.format(sql, merekbusField.getText(), tahunpembuatanField.getText(), warnaField.getText(), kapasitasField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void delete() {
        Stage addStage = new Stage();
        Button save = new Button("DELETE");

        addStage.setTitle("Delete Data");

        TextField noField = new TextField();
        Label labelNo = new Label("Merekbus");

        VBox hbox1 = new VBox(5, labelNo, noField);
        VBox vbox = new VBox(20, hbox1, save);

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "delete from tb_bus WHERE merek_bus='%s'";
                sql = String.format(sql, noField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
                System.out.println();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void edit() {
        Stage addStage = new Stage();
        Button save = new Button("Simpan");

        addStage.setTitle("Edit Data");

        TextField warnaField = new TextField();
        TextField merekbusField = new TextField();
        Label labelWarna = new Label("warna");
        Label labelMerekbus = new Label("merekbus");

        VBox hbox1 = new VBox(5, labelWarna, warnaField);
        VBox hbox2 = new VBox(5, labelMerekbus, merekbusField);
        VBox vbox = new VBox(20, hbox1, hbox2, save);
        

        Scene scene = new Scene(vbox, 400, 400);

        save.setOnAction(e -> {
            Database db = new Database();
            try {
                Statement state = db.conn.createStatement();
                String sql = "UPDATE tb_bus SET Warna ='%s' WHERE merek_bus='%s'";
                sql = String.format(sql, warnaField.getText(), merekbusField.getText());
                state.execute(sql);
                addStage.close();
                load();
            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }

    public void load() {
        Statement stmt;
        tableView.getItems().clear();
        try {
            Database db = new Database();
            stmt = db.conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tb_bus");
            // tampilkan hasil query
            while (rs.next()) {
                tableView.getItems().add(new Terminal(rs.getInt("Id"), rs.getString("Merek_bus"), rs.getString("Tahun_pembuatan"), rs.getString("Warna"), rs.getString("Kapasitas")));
            }

            stmt.close();
            db.conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}