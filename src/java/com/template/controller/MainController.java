package com.template.controller;

import com.template.model.dto.RpgDTO;
import com.template.service.IRpgService;
import com.template.service.RpgService;
import com.template.util.DialogUtil;
import com.template.validator.RpgValidator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class MainController {

    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtRaca;
    @FXML private TextField txtClasse;
    @FXML private TextField txtNivel;
    @FXML private TextField txtVida;
    @FXML private TextField txtMana;
    @FXML private TextField txtForca;
    @FXML private TextField txtDestreza;
    @FXML private TextField txtInteligencia;
    @FXML private TextField txtAlinhamento;

    @FXML private TableView<RpgDTO> tblPersonagens;
    @FXML private TableColumn<RpgDTO, Integer> colId;
    @FXML private TableColumn<RpgDTO, String> colNome;
    @FXML private TableColumn<RpgDTO, String> colRaca;
    @FXML private TableColumn<RpgDTO, String> colClasse;
    @FXML private TableColumn<RpgDTO, Integer> colNivel;
    @FXML private TableColumn<RpgDTO, Integer> colVida;
    @FXML private TableColumn<RpgDTO, Integer> colMana;
    @FXML private TableColumn<RpgDTO, Integer> colForca;
    @FXML private TableColumn<RpgDTO, Integer> colDestreza;
    @FXML private TableColumn<RpgDTO, String> colAlinhamento;
    @FXML private TableColumn<RpgDTO, Integer> colInteligencia;

    private final IRpgService rpgService;
    private final RpgValidator rpgValidator;

    // Construtor padrão (pode ser expandido para injeção via fábrica/FXMLLoader)
    public MainController() {
        this.rpgService = new RpgService();
        this.rpgValidator = new RpgValidator();
    }

    @FXML
    private void initialize() {
        configurarColunas();
        carregarPersonagens();
    }

    private void configurarColunas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colRaca.setCellValueFactory(new PropertyValueFactory<>("raca"));
        colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
        colNivel.setCellValueFactory(new PropertyValueFactory<>("nivel"));
        colVida.setCellValueFactory(new PropertyValueFactory<>("pontosVida"));
        colMana.setCellValueFactory(new PropertyValueFactory<>("pontosMana"));
        colForca.setCellValueFactory(new PropertyValueFactory<>("atributoForca"));
        colDestreza.setCellValueFactory(new PropertyValueFactory<>("atributoDestreza"));
        colAlinhamento.setCellValueFactory(new PropertyValueFactory<>("alinhamento"));
        colInteligencia.setCellValueFactory(new PropertyValueFactory<>("atributoInteligencia"));
    }

    private void carregarPersonagens() {
        try {
            List<RpgDTO> lista = rpgService.listarPersonagens();
            tblPersonagens.setItems(FXCollections.observableArrayList(lista));
        } catch (Exception e) {
            DialogUtil.showError("Erro ao carregar a lista de personagens do banco de dados.");
        }
    }

    @FXML
    private void carregarCampos(MouseEvent event) {
        RpgDTO selecionado = tblPersonagens.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            txtId.setText(String.valueOf(selecionado.getId()));
            txtNome.setText(selecionado.getNome());
            txtRaca.setText(selecionado.getRaca());
            txtClasse.setText(selecionado.getClasse());
            txtNivel.setText(String.valueOf(selecionado.getNivel()));
            txtVida.setText(String.valueOf(selecionado.getPontosVida()));
            txtMana.setText(String.valueOf(selecionado.getPontosMana()));
            txtForca.setText(String.valueOf(selecionado.getAtributoForca()));
            txtDestreza.setText(String.valueOf(selecionado.getAtributoDestreza()));
            txtAlinhamento.setText(selecionado.getAlinhamento());
            txtInteligencia.setText(String.valueOf(selecionado.getAtributoInteligencia()));
        }
    }

    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        RpgDTO novo = pegarDadosDosCampos();

        if (rpgValidator.validarPersonagem(novo)) {
            try {
                rpgService.cadastrarPersonagem(novo);
                DialogUtil.showInfo("Personagem '" + novo.getNome() + "' conjurado com sucesso!");
                atualizarTela();
            } catch (Exception e) {
                DialogUtil.showError("Erro ao inserir personagem no banco.");
            }
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            DialogUtil.showWarning("Atenção", "Nenhum aventureiro selecionado", "Selecione alguém na tabela para revisar o contrato.");
            return;
        }

        RpgDTO atualizado = pegarDadosDosCampos();
        atualizado.setId(Integer.parseInt(txtId.getText()));

        if (rpgValidator.validarPersonagem(atualizado)) {
            try {
                rpgService.atualizarPersonagem(atualizado);
                DialogUtil.showInfo("Contrato do aventureiro revisado com sucesso!");
                atualizarTela();
            } catch (Exception e) {
                DialogUtil.showError("Erro ao atualizar personagem.");
            }
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            DialogUtil.showWarning("Atenção", "Nenhum aventureiro selecionado", "Selecione alguém na tabela para expulsar da taverna.");
            return;
        }

        boolean confirmacao = DialogUtil.showConfirmation(
                "Expulsar da Taverna",
                "Tem certeza disso?",
                "O aventureiro será banido do registro. Esta ação não pode ser desfeita."
        );

        if (confirmacao) {
            try {
                int id = Integer.parseInt(txtId.getText());
                rpgService.excluirPersonagem(id);
                DialogUtil.showInfo("Personagem banido do reino!");
                atualizarTela();
            } catch (Exception e) {
                DialogUtil.showError("Erro ao excluir personagem.");
            }
        }
    }

    @FXML
    private void btnSalvarAction(ActionEvent event) {
        btnEditarAction(event);
    }

    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    private void atualizarTela() {
        carregarPersonagens();
        limparCampos();
    }

    private RpgDTO pegarDadosDosCampos() {
        RpgDTO dto = new RpgDTO();
        dto.setNome(txtNome.getText());
        dto.setRaca(txtRaca.getText());
        dto.setClasse(txtClasse.getText());
        dto.setNivel(parseIntegerOrDefault(txtNivel.getText(), 1));
        dto.setPontosVida(parseIntegerOrDefault(txtVida.getText(), 0));
        dto.setPontosMana(parseIntegerOrDefault(txtMana.getText(), 0));
        dto.setAtributoForca(parseIntegerOrDefault(txtForca.getText(), 0));
        dto.setAtributoDestreza(parseIntegerOrDefault(txtDestreza.getText(), 0));
        dto.setAlinhamento(txtAlinhamento.getText());
        dto.setAtributoInteligencia(parseIntegerOrDefault(txtInteligencia.getText(), 0));
        return dto;
    }

    private int parseIntegerOrDefault(String text, int defaultValue) {
        if (text == null || text.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtRaca.clear();
        txtClasse.clear();
        txtNivel.clear();
        txtVida.clear();
        txtMana.clear();
        txtForca.clear();
        txtDestreza.clear();
        txtAlinhamento.clear();
        txtInteligencia.clear();
        tblPersonagens.getSelectionModel().clearSelection();
    }
}