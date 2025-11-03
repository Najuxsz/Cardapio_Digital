module com.example.cardapio_digital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cardapio_digital to javafx.fxml;
    exports com.example.cardapio_digital;
}