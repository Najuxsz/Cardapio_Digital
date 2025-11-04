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

/*
 * Classe responsável pela tela principal do Cardápio Digital.
 * Aqui é montada toda a interface e a lógica visual (sem regras de negócio ou BD).
 * Exibe os pratos cadastrados e demonstra ordenação com diferentes algoritmos.
 */

public class CardapioView {

    // Lista principal de pratos exibidos na tabela
    private final ObservableList<Prato> pratos = FXCollections.observableArrayList();

    // Componentes principais
    private TableView<Prato> table;
    private VBox bubbleBox, insertionBox, quickBox;

    public void start(Stage stage) {

        // ===== TÍTULO =====
        Label titulo = new Label("Cardápio Digital");
        titulo.setFont(Font.font("Arial", 30));
        titulo.setTextFill(Color.web("#4E342E"));
        titulo.setEffect(new DropShadow(3, Color.GRAY));

        // ===== BOTÕES DE AÇÃO =====
        Button btnAdicionar = criarBotao("#8B0000", "Adicionar Prato", 16, e -> showAdicionarPrato());
        Button btnSair = criarBotao("#B71C1C", "Sair", 16, e -> stage.close());

        // ===== CAMPO DE BUSCA =====
        TextField txtBusca = new TextField();
        txtBusca.setPromptText("Buscar prato");

        // Botão para aplicar filtro de busca
        Button btnBuscar = criarBotao("#FF7043", "Buscar", 14, e -> filtrarTabela(txtBusca.getText()));

        // ===== OPÇÕES DE ORDENAÇÃO =====
        ComboBox<String> comboOrdenar = new ComboBox<>();
        comboOrdenar.getItems().addAll("Nome", "Preço", "Tempo de Preparo");
        comboOrdenar.setPromptText("Ordenar por");

        // Botão que aplica a ordenação conforme o critério escolhido
        Button btnOrdenar = criarBotao("#FFA726", "Ordenar", 14, e -> aplicarOrdenacao(comboOrdenar.getValue()));

        // ===== TABELA PRINCIPAL =====
        table = criarTabelaPratos();
        table.setItems(pratos);
        table.setPrefHeight(250); // deixa espaço para as tabelas de ordenação

        // ===== TABELAS DE ORDENAÇÃO =====
        bubbleBox = criarBoxOrdenacao("Bubble Sort");
        insertionBox = criarBoxOrdenacao("Insertion Sort");
        quickBox = criarBoxOrdenacao("Quick Sort");

        HBox ordenacoesBox = new HBox(15, bubbleBox, insertionBox, quickBox);
        ordenacoesBox.setPadding(new Insets(10));
        ordenacoesBox.setAlignment(Pos.CENTER);

        // ===== ORGANIZAÇÃO GERAL DA TELA =====
        HBox topo = new HBox(10, txtBusca, btnBuscar, comboOrdenar, btnOrdenar, btnAdicionar, btnSair);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));

        VBox root = new VBox(15, titulo, topo, table, ordenacoesBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#FFF7E6;");

        // Exibição da cena
        Scene scene = new Scene(root, 1100, 700);
        stage.setScene(scene);
        stage.setTitle("Cardápio Digital");
        stage.show();
    }

    // ==================== MÉTODOS AUXILIARES ====================

    // Cria botões com estilo padrão e ação definida
    private Button criarBotao(String cor, String texto, int fontSize, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color:" + cor + "; -fx-text-fill:white; -fx-background-radius: 10; -fx-padding: 6 12;");
        btn.setFont(Font.font(fontSize));
        btn.setEffect(new DropShadow(3, Color.GRAY));
        btn.setOnAction(acao);
        return btn;
    }

    // Cria e configura a tabela principal de pratos
    private TableView<Prato> criarTabelaPratos() {
        TableView<Prato> t = new TableView<>();
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Colunas da tabela principal
        TableColumn<Prato, String> colNome = criarColuna("Nome", "nome");
        TableColumn<Prato, Double> colPreco = criarColuna("Preço", "preco");
        TableColumn<Prato, Double> colTempo = criarColuna("Tempo (min)", "tempoPreparo");
        TableColumn<Prato, String> colDescricao = criarColuna("Descrição", "descricao");

        // Coluna com botão de remoção
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

    // Cria uma coluna sem permitir ordenação automática (sem setas)
    private <T> TableColumn<Prato, T> criarColuna(String titulo, String prop) {
        TableColumn<Prato, T> col = new TableColumn<>(titulo);
        col.setCellValueFactory(new PropertyValueFactory<>(prop));
        col.setSortable(false);
        return col;
    }

    // Cria as tabelas de demonstração de ordenação (Bubble, Insertion, Quick)
    private VBox criarBoxOrdenacao(String titulo) {
        Label label = new Label(titulo);
        label.setFont(Font.font(16));
        label.setTextFill(Color.web("#4E342E"));

        TableView<Prato> boxTable = new TableView<>();
        boxTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        boxTable.setPrefHeight(200);
        boxTable.setPrefWidth(320);

        // Exibe sempre Nome, Preço e Tempo
        TableColumn<Prato, String> colNome = criarColuna("Nome", "nome");
        TableColumn<Prato, Double> colPreco = criarColuna("Preço", "preco");
        TableColumn<Prato, Double> colTempo = criarColuna("Tempo", "tempoPreparo");

        boxTable.getColumns().addAll(colNome, colPreco, colTempo);

        VBox box = new VBox(8, label, boxTable);
        box.setPadding(new Insets(8));
        box.setStyle("-fx-background-color:#FFE0B2; -fx-border-color:#FFB74D; -fx-border-radius:10; -fx-background-radius:10;");
        return box;
    }

    // Filtra os pratos pelo nome
    private void filtrarTabela(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            table.setItems(pratos);
        } else {
            table.setItems(FXCollections.observableArrayList(
                    pratos.filtered(p -> p.getNome().toLowerCase().contains(filtro.toLowerCase()))
            ));
        }
    }

    // Janela modal para adicionar novo prato
    private void showAdicionarPrato() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Adicionar Prato");

        // Campos de entrada
        TextField txtNome = new TextField(); txtNome.setPromptText("Nome");
        TextField txtPreco = new TextField(); txtPreco.setPromptText("Preço");
        TextField txtTempo = new TextField(); txtTempo.setPromptText("Tempo de Preparo");
        TextArea txtDescricao = new TextArea(); txtDescricao.setPromptText("Descrição");

        // Botões de salvar/cancelar
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
                new Alert(Alert.AlertType.WARNING, "Erro: Nome obrigatório ou valores inválidos").showAndWait();
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

    // ==================== ORDENAÇÃO ====================

    // Aplica ordenação usando os 3 algoritmos e destaca a coluna usada
    private void aplicarOrdenacao(String criterio) {
        if (criterio == null) return;

        List<Prato> lista = new ArrayList<>(pratos);

        // Bubble Sort
        List<Prato> bubble = new ArrayList<>(lista);
        bubbleSort(bubble, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) bubbleBox.getChildren().get(1), bubble, criterio);

        // Insertion Sort
        List<Prato> insertion = new ArrayList<>(lista);
        insertionSort(insertion, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) insertionBox.getChildren().get(1), insertion, criterio);

        // Quick Sort
        List<Prato> quick = new ArrayList<>(lista);
        quickSort(quick, 0, quick.size() - 1, criterio);
        atualizarTabelaOrdenacao((TableView<Prato>) quickBox.getChildren().get(1), quick, criterio);
    }

    // Atualiza tabela de cada algoritmo e pinta a coluna filtrada
    private void atualizarTabelaOrdenacao(TableView<Prato> tableOrdenacao, List<Prato> lista, String criterio) {
        tableOrdenacao.setItems(FXCollections.observableArrayList(lista));
        tableOrdenacao.getColumns().forEach(c -> c.setStyle("-fx-background-color: transparent;"));
        for (TableColumn<?, ?> col : tableOrdenacao.getColumns()) {
            if (col.getText().contains(criterio.split(" ")[0])) {
                col.setStyle("-fx-background-color: #FFD180; -fx-text-fill: #4E342E;");
            }
        }
    }

    // Define como comparar dois pratos conforme o critério
    private int comparar(Prato a, Prato b, String criterio) {
        return switch (criterio) {
            case "Nome" -> a.getNome().compareToIgnoreCase(b.getNome());
            case "Preço" -> Double.compare(a.getPreco(), b.getPreco());
            case "Tempo de Preparo" -> Double.compare(a.getTempoPreparo(), b.getTempoPreparo());
            default -> 0;
        };
    }

    // Implementação dos algoritmos de ordenação
    private void bubbleSort(List<Prato> lista, String criterio) {
        for (int i = 0; i < lista.size() - 1; i++)
            for (int j = 0; j < lista.size() - i - 1; j++)
                if (comparar(lista.get(j), lista.get(j + 1), criterio) > 0) {
                    Prato temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);
                }
    }

    private void insertionSort(List<Prato> lista, String criterio) {
        for (int i = 1; i < lista.size(); i++) {
            Prato key = lista.get(i);
            int j = i - 1;
            while (j >= 0 && comparar(lista.get(j), key, criterio) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, key);
        }
    }

    private void quickSort(List<Prato> lista, int low, int high, String criterio) {
        if (low < high) {
            int pi = partition(lista, low, high, criterio);
            quickSort(lista, low, pi - 1, criterio);
            quickSort(lista, pi + 1, high, criterio);
        }
    }

    private int partition(List<Prato> lista, int low, int high, String criterio) {
        Prato pivot = lista.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparar(lista.get(j), pivot, criterio) <= 0) {
                i++;
                Prato temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Prato temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);
        return i + 1;
    }
}
