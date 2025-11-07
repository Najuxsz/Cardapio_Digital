package com.cardapio_digital.view.telas;

import com.cardapio_digital.model.Prato;
import com.cardapio_digital.controller.PratoController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class CardapioView {

    private final PratoController controller;
    private TableView<Prato> table;
    private VBox bubbleBox, insertionBox, quickBox;
    private Label tempoBubble, tempoInsertion, tempoQuick;

    public CardapioView() {
        this.controller = new PratoController();
    }

    public void start(Stage stage) {
        Label titulo = criarLabel("Cardápio Digital", 30, "#4E342E");

        Button btnAdicionar = criarBotao("#8B0000", "Adicionar Prato", 16, e -> showAdicionarPrato());
        Button btnSair = criarBotao("#B71C1C", "Sair", 16, e -> stage.close());

        TextField txtBusca = new TextField();
        txtBusca.setPromptText("Buscar prato pelo nome");
        txtBusca.setPrefWidth(200);

        Button btnBuscar = criarBotao("#FF7043", "Buscar", 14, e -> filtrarTabela(txtBusca.getText()));
        Button btnLimpar = criarBotao("#FF7043", "Limpar", 14, e -> {
            txtBusca.clear();
            filtrarTabela("");
        });

        ComboBox<String> comboOrdenar = new ComboBox<>();
        comboOrdenar.getItems().addAll("Nome", "Preço", "Tempo de Preparo");
        comboOrdenar.setPromptText("Ordenar por");
        comboOrdenar.setPrefWidth(150);

        Button btnOrdenar = criarBotao("#FFA726", "Ordenar", 14, e -> {
            String criterio = comboOrdenar.getValue();
            if (criterio != null) {
                aplicarOrdenacao(criterio);
            } else {
                showAlert("Atenção", "Selecione um critério de ordenação");
            }
        });

        table = criarTabelaPratos();
        table.setItems(controller.getPratosObservable());
        table.setPrefHeight(250);

        bubbleBox = criarBoxOrdenacao("Bubble Sort");
        insertionBox = criarBoxOrdenacao("Insertion Sort");
        quickBox = criarBoxOrdenacao("Quick Sort");

        HBox ordenacoesBox = new HBox(15, bubbleBox, insertionBox, quickBox);
        ordenacoesBox.setPadding(new Insets(10));
        ordenacoesBox.setAlignment(Pos.CENTER);

        HBox buscaBox = new HBox(10, txtBusca, btnBuscar, btnLimpar);
        buscaBox.setAlignment(Pos.CENTER_LEFT);

        HBox ordenarBox = new HBox(10, comboOrdenar, btnOrdenar);
        ordenarBox.setAlignment(Pos.CENTER_LEFT);

        HBox acoesBox = new HBox(10, btnAdicionar, btnSair);
        acoesBox.setAlignment(Pos.CENTER_RIGHT);

        HBox topo = new HBox(20, buscaBox, ordenarBox, acoesBox);
        topo.setAlignment(Pos.CENTER);
        topo.setPadding(new Insets(10));
        HBox.setHgrow(acoesBox, Priority.ALWAYS);

        VBox root = new VBox(15, titulo, topo, table, ordenacoesBox);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#FFF7E6;");

        stage.setScene(new Scene(root, 1100, 700));
        stage.setTitle("Cardápio Digital");
        stage.show();
    }

    private Button criarBotao(String cor, String texto, int fontSize, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color:" + cor + "; -fx-text-fill:white; -fx-background-radius:10; -fx-padding:6 12;");
        btn.setFont(Font.font(fontSize));
        btn.setEffect(new DropShadow(3, Color.GRAY));
        btn.setOnAction(acao);
        btn.setCursor(javafx.scene.Cursor.HAND);
        return btn;
    }

    private Label criarLabel(String texto, int tamanho, String cor) {
        Label label = new Label(texto);
        label.setFont(Font.font(tamanho));
        label.setTextFill(Color.web(cor));
        label.setEffect(new DropShadow(3, Color.GRAY));
        return label;
    }

    private TableView<Prato> criarTabelaPratos() {
        TableView<Prato> t = new TableView<>();
        t.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Coluna ID
        TableColumn<Prato, Number> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colId.setPrefWidth(50);
        colId.setSortable(false);

        // Coluna Nome
        TableColumn<Prato, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colNome.setPrefWidth(150);
        colNome.setSortable(false);

        // Coluna Preço
        TableColumn<Prato, Number> colPreco = new TableColumn<>("Preço (R$)");
        colPreco.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPreco()));
        colPreco.setPrefWidth(100);
        colPreco.setSortable(false);

        // Coluna Tempo
        TableColumn<Prato, Number> colTempo = new TableColumn<>("Tempo (min)");
        colTempo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTempoPreparo()));
        colTempo.setPrefWidth(100);
        colTempo.setSortable(false);

        // Coluna Descrição
        TableColumn<Prato, String> colDescricao = new TableColumn<>("Descrição");
        colDescricao.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescricao()));
        colDescricao.setPrefWidth(250);
        colDescricao.setSortable(false);

        // Coluna Ação
        TableColumn<Prato, Void> colAcao = criarColunaAcao();

        t.getColumns().addAll(colId, colNome, colPreco, colTempo, colDescricao, colAcao);
        return t;
    }

    private TableColumn<Prato, Void> criarColunaAcao() {
        TableColumn<Prato, Void> colAcao = new TableColumn<>("Ação");
        colAcao.setPrefWidth(100);
        colAcao.setCellFactory(param -> new TableCell<>() {
            private final Button btnRemover = criarBotao("#FF7043", "Remover", 12, e -> {
                Prato prato = getTableView().getItems().get(getIndex());
                if (showConfirmacao("Confirmar", "Deseja realmente remover " + prato.getNome() + "?")) {
                    controller.removerPrato(prato);
                }
            });

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemover);
                setAlignment(Pos.CENTER);
            }
        });
        return colAcao;
    }

    private VBox criarBoxOrdenacao(String titulo) {
        Label label = new Label(titulo);
        label.setFont(Font.font(16));
        label.setTextFill(Color.web("#4E342E"));

        TableView<Prato> boxTable = new TableView<>();
        boxTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        boxTable.setPrefHeight(200);
        boxTable.setPrefWidth(320);

        // Coluna Nome
        TableColumn<Prato, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colNome.setSortable(false);

        // Coluna Preço
        TableColumn<Prato, Number> colPreco = new TableColumn<>("Preço");
        colPreco.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPreco()));
        colPreco.setSortable(false);

        // Coluna Tempo
        TableColumn<Prato, Number> colTempo = new TableColumn<>("Tempo");
        colTempo.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTempoPreparo()));
        colTempo.setSortable(false);

        boxTable.getColumns().addAll(colNome, colPreco, colTempo);

        Label tempoLabel = new Label("Tempo: -- ms");
        tempoLabel.setFont(Font.font(13));
        tempoLabel.setTextFill(Color.web("#5D4037"));
        tempoLabel.setAlignment(Pos.CENTER);

        VBox box = new VBox(8, label, boxTable, tempoLabel);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(8));
        box.setStyle("-fx-background-color:#FFE0B2; -fx-border-color:#FFB74D; -fx-border-radius:10; -fx-background-radius:10;");

        switch (titulo) {
            case "Bubble Sort" -> tempoBubble = tempoLabel;
            case "Insertion Sort" -> tempoInsertion = tempoLabel;
            case "Quick Sort" -> tempoQuick = tempoLabel;
        }

        return box;
    }

    private void filtrarTabela(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            table.setItems(controller.getPratosObservable());
        } else {
            String filtroLower = filtro.toLowerCase().trim();
            table.setItems(FXCollections.observableArrayList(
                    controller.getPratosObservable().filtered(p ->
                            p.getNome().toLowerCase().contains(filtroLower) ||
                                    p.getDescricao().toLowerCase().contains(filtroLower)
                    )
            ));
        }
    }

    private void showAdicionarPrato() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Adicionar Prato");

        Label lblTitulo = criarLabel("Novo Prato", 20, "#4E342E");
        lblTitulo.setAlignment(Pos.CENTER);

        TextField txtNome = new TextField();
        txtNome.setPromptText("Nome do prato");

        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Preço (em R$)");

        TextField txtTempo = new TextField();
        txtTempo.setPromptText("Tempo de preparo (em minutos)");

        TextArea txtDescricao = new TextArea();
        txtDescricao.setPromptText("Descrição do prato");
        txtDescricao.setPrefRowCount(4);

        Button btnSalvar = criarBotao("#8B0000", "Salvar", 14, e -> {
            try {
                String nome = txtNome.getText().trim();
                String precoStr = txtPreco.getText().trim();
                String tempoStr = txtTempo.getText().trim();
                String descricao = txtDescricao.getText().trim();

                if (nome.isEmpty()) {
                    showAlert("Erro", "O nome do prato é obrigatório");
                    return;
                }

                int preco = Integer.parseInt(precoStr);
                int tempo = Integer.parseInt(tempoStr);

                if (preco < 0) {
                    showAlert("Erro", "O preço não pode ser negativo");
                    return;
                }

                if (tempo < 0) {
                    showAlert("Erro", "O tempo não pode ser negativo");
                    return;
                }

                Prato novoPrato = new Prato(nome, preco, tempo, descricao);
                controller.adicionarPrato(novoPrato);
                stage.close();
                showAlert("Sucesso", "Prato adicionado com sucesso!");
            } catch (NumberFormatException ex) {
                showAlert("Erro", "Preço e tempo devem ser números válidos");
            } catch (Exception ex) {
                showAlert("Erro", "Erro ao adicionar prato: " + ex.getMessage());
            }
        });

        Button btnCancelar = criarBotao("#B71C1C", "Cancelar", 14, e -> stage.close());

        HBox botoes = new HBox(10, btnSalvar, btnCancelar);
        botoes.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, lblTitulo, txtNome, txtPreco, txtTempo, txtDescricao, botoes);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color:#FFF7E6;");

        stage.setScene(new Scene(root, 400, 450));
        stage.show();
    }

    private void aplicarOrdenacao(String criterio) {
        if (controller.getPratosObservable().isEmpty()) {
            showAlert("Atenção", "Não há pratos para ordenar");
            return;
        }

        List<Prato> lista = new ArrayList<>(controller.getPratosObservable());

        // Bubble Sort
        List<Prato> bubble = new ArrayList<>(lista);
        long inicio = System.nanoTime();
        controller.bubbleSort(bubble, criterio);
        long tempo = System.nanoTime() - inicio;
        tempoBubble.setText(String.format("Tempo: %.3f ms", tempo / 1_000_000.0));
        atualizarTabelaOrdenacao((TableView<Prato>) bubbleBox.getChildren().get(1), bubble, criterio);

        // Insertion Sort
        List<Prato> insertion = new ArrayList<>(lista);
        inicio = System.nanoTime();
        controller.insertionSort(insertion, criterio);
        tempo = System.nanoTime() - inicio;
        tempoInsertion.setText(String.format("Tempo: %.3f ms", tempo / 1_000_000.0));
        atualizarTabelaOrdenacao((TableView<Prato>) insertionBox.getChildren().get(1), insertion, criterio);

        // Quick Sort
        List<Prato> quick = new ArrayList<>(lista);
        inicio = System.nanoTime();
        controller.quickSort(quick, 0, quick.size() - 1, criterio);
        tempo = System.nanoTime() - inicio;
        tempoQuick.setText(String.format("Tempo: %.3f ms", tempo / 1_000_000.0));
        atualizarTabelaOrdenacao((TableView<Prato>) quickBox.getChildren().get(1), quick, criterio);
    }

    @SuppressWarnings("unchecked")
    private void atualizarTabelaOrdenacao(TableView<Prato> tableOrdenacao, List<Prato> lista, String criterio) {
        tableOrdenacao.setItems(FXCollections.observableArrayList(lista));

        // Reset de estilos
        tableOrdenacao.getColumns().forEach(c -> c.setStyle("-fx-background-color: transparent;"));

        // Destacar coluna ordenada
        for (TableColumn<?, ?> col : tableOrdenacao.getColumns()) {
            String headerText = col.getText();
            if ((criterio.equals("Nome") && headerText.equals("Nome")) ||
                    (criterio.equals("Preço") && headerText.equals("Preço")) ||
                    (criterio.equals("Tempo de Preparo") && headerText.equals("Tempo"))) {
                col.setStyle("-fx-background-color: #FFD180; -fx-text-fill: #4E342E;");
            }
        }
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean showConfirmacao(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        return alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK;
    }
}