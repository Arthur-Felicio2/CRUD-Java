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

    // Inputs de Texto
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtRaca;
    @FXML
    private TextField txtClasse;
    @FXML
    private TextField txtNivel;
    @FXML
    private TextField txtVida;
    @FXML
    private TextField txtMana;
    @FXML
    private TextField txtForca;
    @FXML
    private TextField txtDestreza;
    @FXML
    private TextField txtInteligencia;
    @FXML
    private TextField txtAlinhamento;

    // Tabela e Colunas
    @FXML
    private TableView<RpgDTO> tblPersonagens;
    @FXML
    private TableColumn<RpgDTO, Integer> colId;
    @FXML
    private TableColumn<RpgDTO, String> colNome;
    @FXML
    private TableColumn<RpgDTO, String> colRaca;
    @FXML
    private TableColumn<RpgDTO, String> colClasse;
    @FXML
    private TableColumn<RpgDTO, Integer> colNivel;
    @FXML
    private TableColumn<RpgDTO, Integer> colVida;
    @FXML
    private TableColumn<RpgDTO, Integer> colMana;
    @FXML
    private TableColumn<RpgDTO, Integer> colForca;
    @FXML
    private TableColumn<RpgDTO, Integer> colDestreza;
    @FXML
    private TableColumn<RpgDTO, String> colAlinhamento;
    @FXML
    private TableColumn<RpgDTO, Integer> colInteligencia;

    // Instância baseada em interfaces[cite: 1]
    private final IRpgService rpgService = new RpgService();
    private final RpgValidator rpgValidator = new RpgValidator();

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
                carregarPersonagens();
                limparCampos();
            } catch (Exception e) {
                DialogUtil.showError("Erro ao inserir personagem no banco.");
            }
        }
    }

    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            RpgDTO atualizado = pegarDadosDosCampos();
            atualizado.setId(Integer.parseInt(txtId.getText()));

            if (rpgValidator.validarPersonagem(atualizado)) {
                try {
                    rpgService.atualizarPersonagem(atualizado);
                    DialogUtil.showInfo("Contrato do aventureiro revisado com sucesso!");
                    carregarPersonagens();
                    limparCampos();
                } catch (Exception e) {
                    DialogUtil.showError("Erro ao atualizar personagem.");
                }
            }
        } else {
            DialogUtil.showWarning("Atenção", "Nenhum aventureiro selecionado", "Selecione alguém na tabela para revisar o contrato.");
        }
    }

    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
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
                    carregarPersonagens();
                    limparCampos();
                } catch (Exception e) {
                    DialogUtil.showError("Erro ao excluir personagem.");
                }
            }
        } else {
            DialogUtil.showWarning("Atenção", "Nenhum aventureiro selecionado", "Selecione alguém na tabela para expulsar da taverna.");
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

    private RpgDTO pegarDadosDosCampos() {
        RpgDTO dto = new RpgDTO();
        dto.setNome(txtNome.getText());
        dto.setRaca(txtRaca.getText());
        dto.setClasse(txtClasse.getText());
        // Utilizando parsing com fallback padrão para os numéricos em caso de campos vazios
        dto.setNivel(txtNivel.getText().isEmpty() ? 1 : Integer.parseInt(txtNivel.getText()));
        dto.setPontosVida(txtVida.getText().isEmpty() ? 0 : Integer.parseInt(txtVida.getText()));
        dto.setPontosMana(txtMana.getText().isEmpty() ? 0 : Integer.parseInt(txtMana.getText()));
        dto.setAtributoForca(txtForca.getText().isEmpty() ? 0 : Integer.parseInt(txtForca.getText()));
        dto.setAtributoDestreza(txtDestreza.getText().isEmpty() ? 0 : Integer.parseInt(txtDestreza.getText()));
        dto.setAlinhamento(txtAlinhamento.getText());
        dto.setAtributoInteligencia(txtInteligencia.getText().isEmpty() ? 0 : Integer.parseInt(txtInteligencia.getText()));
        return dto;
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
