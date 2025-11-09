module com.example.cardapio_digital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.example.cardapio_digital;


    opens com.example.cardapio_digital to javafx.fxml;
    exports com.cardapio_digital;
}