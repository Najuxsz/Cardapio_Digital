package com.cardapio_digital.view.telas;

import com.cardapio_digital.model.Prato;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CardapioView {

    private final ObservableList<Prato> pratos = FXCollections.observableArrayList();
    private TableView<Prato> table;
    private VBox bubbleBox, insertionBox, quickBox;

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Cardápio Digital");
        titulo.setFont(Font.font("Arial", 30));
        titulo.setTextFill(Color.web("#4E342E"));
        titulo.setEffect(new DropShadow(3, Color.GRAY));

        // ===== BOTÕES =====
        Button btnAdicionar = criarBotao("#8B0000", "Adicionar Prato", 16, e -> showAdicionarPrato());
        Button btnSair = criarBotao("#B71C1C", "Sair", 16, e -> stage.close());

        // ===== BUSCA =====
        TextField txtBusca = new TextField();
        txtBusca.setPromptText("Buscar prato");
        Button btnBuscar = criarBotao("#FF7043", "Buscar", 14, e -> filtrarTabela(txtBusca.getText()));

        // ===== ORDENAÇÃO =====
        ComboBox<String> comboOrdenar = new ComboBox<>();
        comboOrdenar.getItems().addAll("Nome", "Preço", "Tempo de Preparo");
        comboOrdenar.setPromptText("Ordenar por");

        Button btnOrdenar = criarBotao("#FFA726", "Ordenar", 14, e -> aplicarOrdenacao(comboOrdenar.getValue()));

        // ===== TABELA =====
        table = criarTabelaPratos();
        table.setItems(pratos);

        // ===== DIVS DE ORDENAÇÃO =====
        bubbleBox = criarBoxOrdenacao("Bubble Sort");
        insertionBox = criarBoxOrdenacao("Insertion Sort");
        quickBox = criarBoxOrdenacao("Quick Sort");

        HBox ordenacoesBox = new HBox(10, bubbleBox, insertionBox, quickBox);
        ordenacoesBox.setPadding(new Insets(10));
        ordenacoesBox.setAlignment(Pos.CENTER);

        // ===== LAYOUT PRINCIPAL =====
        HBox topo = new HBox(10, txtBusca, btnBuscar, comboOrdenar, btnOrdenar, btnAdicionar, btnSair);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        VBox root = new VBox(10, titulo, topo, table, ordenacoesBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#FFF7E6;");

        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setTitle("Cardápio Digital");
        stage.show();
    }

    // ================= MÉTODOS AUXILIARES =================
    private Button criarBotao(String cor, String texto, int fontSize, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color:" + cor + "; -fx-text-fill:white; -fx-background-radius: 10; -fx-padding: 6 12;");
        btn.setFont(Font.font(fontSize));
        btn.setEffect(new DropShadow(3, Color.GRAY));
        btn.setOnAction(acao);
        return btn;
    }

    private TableView<Prato> criarTabelaPratos() {
        TableView<Prato> t = new TableView<>();
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Prato, String> colNome = criarColuna("Nome", "nome");
        TableColumn<Prato, Double> colPreco = criarColuna("Preço", "preco");
        TableColumn<Prato, Double> colTempo = criarColuna("Tempo", "tempoPreparo");
        TableColumn<Prato, String> colDescricao = criarColuna("Descrição", "descricao");

        TableColumn<Prato, Void> colAcao = new TableColumn<>("Ação");
        colAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btnRemover = criarBotao("#FF7043", "Remover", 12, e -> {
                Prato prato = getTableView().getItems().get(getIndex());
                pratos.remove(prato);
            });
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemover);
            }
        });

        t.getColumns().addAll(colNome, colPreco, colTempo, colDescricao, colAcao);
        return t;
    }

    private <T> TableColumn<Prato, T> criarColuna(String titulo, String prop) {
        TableColumn<Prato, T> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(prop));
        col.setSortable(false); // remove setinha nativa
        return col;
    }

    private VBox criarBoxOrdenacao(String titulo) {
        Label label = new Label(titulo);
        label.setFont(Font.font(16));
        TableView<Prato> boxTable = new TableView<>();
        boxTable.setPrefHeight(150);
        TableColumn<Prato, String> colNome = criarColuna("Nome", "nome");
        boxTable.getColumns().add(colNome);

        VBox box = new VBox(5, label, boxTable);
        box.setPadding(new Insets(5));
        box.setStyle("-fx-background-color:#FFE0B2; -fx-border-color:#FFB74D; -fx-border-radius:5; -fx-background-radius:5;");
        return box;
    }

    private void filtrarTabela(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            table.setItems(pratos);
        } else {
            table.setItems(FXCollections.observableArrayList(
                    pratos.filtered(p -> p.getNome().toLowerCase().contains(filtro.toLowerCase()))
            ));
        }
        // Ordenações permanecem visíveis
    }

    private void showAdicionarPrato() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Adicionar Prato");

        TextField txtNome = new TextField(); txtNome.setPromptText("Nome");
        TextField txtPreco = new TextField(); txtPreco.setPromptText("Preço");
        TextField txtTempo = new TextField(); txtTempo.setPromptText("Tempo de Preparo");
        TextArea txtDescricao = new TextArea(); txtDescricao.setPromptText("Descrição");

        Button btnSalvar = criarBotao("#8B0000", "Salvar", 14, e -> {
            try {
                if(txtNome.getText().isEmpty()) throw new Exception("Nome obrigatório");
                pratos.add(new Prato(
                        txtNome.getText(),
                        Double.parseDouble(txtPreco.getText()),
                        Double.parseDouble(txtTempo.getText()),
                        txtDescricao.getText()
                ));
                stage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Erro: Nome obrigatório ou valores inválidos");
                alert.showAndWait();
            }
        });

        Button btnCancelar = criarBotao("#B71C1C", "Cancelar", 14, e -> stage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox root = new VBox(10, txtNome, txtPreco, txtTempo, txtDescricao, botoes);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#FFF7E6;");

        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    private void aplicarOrdenacao(String criterio) {
        if (criterio == null) return;

        List<Prato> lista = new ArrayList<>(pratos);

        // Bubble
        List<Prato> bubble = new ArrayList<>(lista);
        bubbleSort(bubble, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) bubbleBox.getChildren().get(1), bubble, criterio);

        // Insertion
        List<Prato> insertion = new ArrayList<>(lista);
        insertionSort(insertion, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) insertionBox.getChildren().get(1), insertion, criterio);

        // Quick
        List<Prato> quick = new ArrayList<>(lista);
        quickSort(quick, 0, quick.size()-1, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) quickBox.getChildren().get(1), quick, criterio);
    }

    private void atualizarTabelaOrdenacao(TableView<Prato> tableOrdenacao, List<Prato> lista, String criterio) {
        tableOrdenacao.setItems(FXCollections.observableArrayList(lista));
        if(!tableOrdenacao.getColumns().isEmpty()) {
            tableOrdenacao.getColumns().get(0).setText(criterio); // atualiza cabeçalho
        }
    }

    // ===== ORDENAÇÕES =====
    private int comparar(Prato a, Prato b, String criterio) {
        return switch (criterio) {
            case "Nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "Preço" -> Double.compare(a.getPreco(), b.getPreco());
            case "Tempo de Preparo" -> Double.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }

    private void bubbleSort(List<Prato> lista, String criterio) {
        for(int i=0;i<lista.size()-1;i++)
            for(int j=0;j<lista.size()-i-1;j++)
                if(comparar(lista.get(j), lista.get(j+1), criterio)>0){
                    Prato temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                }
    }

    private void insertionSort(List<Prato> lista, String criterio) {
        for(int i=1;i<lista.size();i++){
            Prato key = lista.get(i);
            int j = i-1;
            while(j>=0 && comparar(lista.get(j), key, criterio)>0){
                lista.set(j+1, lista.get(j));
                j--;
            }
            lista.set(j+1, key);
        }
    }

    private void quickSort(List<Prato> lista, int low, int high, String criterio){
        if(low<high){
            int pi = partition(lista, low, high, criterio);
            quickSort(lista, low, pi-1, criterio);
            quickSort(lista, pi+1, high, criterio);
        }
    }

    private int partition(List<Prato> lista, int low, int high, String criterio){
        Prato pivot = lista.get(high);
        int i = low-1;
        for(int j=low;j<high;j++){
            if(comparar(lista.get(j), pivot, criterio)<=0){
                i++;
                Prato temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Prato temp = lista.get(i+1);
        lista.set(i+1, lista.get(high));
        lista.set(high, temp);
        return i+1;
    }
}
