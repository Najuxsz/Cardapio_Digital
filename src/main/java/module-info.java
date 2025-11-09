module com.example.cardapio_digital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.graal.compiler.management;


    opens com.cardapio_digital to javafx.fxml;
    exports com.cardapio_digital;
}