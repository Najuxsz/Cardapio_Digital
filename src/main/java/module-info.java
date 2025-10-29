module com.example.cardapio_digital {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cardapio_digital to javafx.fxml;
    exports com.example.cardapio_digital;
}