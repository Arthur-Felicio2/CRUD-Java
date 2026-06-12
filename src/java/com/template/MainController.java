package com.template;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.List;

public class MainController {

    // Inputs de Texto
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

    // Tabela e Colunas
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

    private final RpgDAO rpgDAO = new RpgDAO();

    @FXML
    private void initialize() {
        System.out.println("FXML carregado com sucesso!");

        // Mapeia as propriedades do DTO para cada coluna correspondente (Passo 6)
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

        // Carrega os dados na abertura do software
        carregarPersonagens();
    }

    // Método para atualizar a visualização da tabela (Passo 5)
    private void carregarPersonagens() {
        List<RpgDTO> lista = rpgDAO.listar();
        tblPersonagens.setItems(FXCollections.observableArrayList(lista));
    }

    // Ação ao clicar em uma linha da tabela (Passo 9)
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

    // Botão Adicionar (Passo 7)
    @FXML
    private void btnAdicionarAction(ActionEvent event) {
        RpgDTO novo = pegarDadosDosCampos();
        rpgDAO.inserir(novo);
        carregarPersonagens();
        limparCampos();
    }

    // Botão Editar
    @FXML
    private void btnEditarAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            RpgDTO atualizado = pegarDadosDosCampos();
            atualizado.setId(Integer.parseInt(txtId.getText()));
            rpgDAO.atualizar(atualizado);
            carregarPersonagens();
            limparCampos();
        }
    }

    // Botão Excluir
    @FXML
    private void btnExcluirAction(ActionEvent event) {
        if (!txtId.getText().isEmpty()) {
            int id = Integer.parseInt(txtId.getText());
            rpgDAO.excluir(id);
            carregarPersonagens();
            limparCampos();
        }
    }

    // Botão Salvar (pode ser usado como alternativa para Editar/Atualizar)
    @FXML
    private void btnSalvarAction(ActionEvent event) {
        btnEditarAction(event);
    }

    // NOVO: Botão Limpar Campos
    @FXML
    private void btnLimparAction(ActionEvent event) {
        limparCampos();
    }

    // Auxiliar para ler os campos do formulário com segurança de tipos
    private RpgDTO pegarDadosDosCampos() {
        RpgDTO dto = new RpgDTO();
        dto.setNome(txtNome.getText());
        dto.setRaca(txtRaca.getText());
        dto.setClasse(txtClasse.getText());
        dto.setNivel(txtNivel.getText().isEmpty() ? 1 : Integer.parseInt(txtNivel.getText()));
        dto.setPontosVida(txtVida.getText().isEmpty() ? 0 : Integer.parseInt(txtVida.getText()));
        dto.setPontosMana(txtMana.getText().isEmpty() ? 0 : Integer.parseInt(txtMana.getText()));
        dto.setAtributoForca(txtForca.getText().isEmpty() ? 0 : Integer.parseInt(txtForca.getText()));
        dto.setAtributoDestreza(txtDestreza.getText().isEmpty() ? 0 : Integer.parseInt(txtDestreza.getText()));
        dto.setAlinhamento(txtAlinhamento.getText());
        dto.setAtributoInteligencia(txtInteligencia.getText().isEmpty() ? 0 : Integer.parseInt(txtInteligencia.getText()));
        return dto;
    }

    // Auxiliar para resetar a tela pós-operação
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
        txtNome.requestFocus();
    }
}