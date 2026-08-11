package com.template.model.dao;

import com.template.model.Conexao;
import com.template.model.dto.RpgDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RpgDAO {

    private final Conexao conexaoBanco = new Conexao();

    public void inserir(RpgDTO personagem) throws SQLException {
        String sql = "INSERT INTO Personagens (Nome, Raca, Classe, Nivel, Pontos_Vida, "
                + "Pontos_Mana, Atributo_Forca, Atributo_Destreza, Atributo_Inteligencia, Alinhamento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexaoBanco.conectar(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            configurarPreparedStatement(pstm, personagem);
            pstm.executeUpdate();
        }
    }

    public List<RpgDTO> listar() throws SQLException {
        String sql = "SELECT * FROM Personagens ORDER BY Id";
        List<RpgDTO> lista = new ArrayList<>();

        try (Connection conn = conexaoBanco.conectar(); PreparedStatement pstm = conn.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                RpgDTO personagem = new RpgDTO();
                personagem.setId(rs.getInt("Id"));
                personagem.setNome(rs.getString("Nome"));
                personagem.setRaca(rs.getString("Raca"));
                personagem.setClasse(rs.getString("Classe"));
                personagem.setNivel(rs.getInt("Nivel"));
                personagem.setPontosVida(rs.getInt("Pontos_Vida"));
                personagem.setPontosMana(rs.getInt("Pontos_Mana"));
                personagem.setAtributoForca(rs.getInt("Atributo_Forca"));
                personagem.setAtributoDestreza(rs.getInt("Atributo_Destreza"));
                personagem.setAtributoInteligencia(rs.getInt("Atributo_Inteligencia"));
                personagem.setAlinhamento(rs.getString("Alinhamento"));
                lista.add(personagem);
            }
        }
        return lista;
    }

    public void atualizar(RpgDTO personagem) throws SQLException {
        String sql = "UPDATE Personagens SET Nome=?, Raca=?, Classe=?, Nivel=?, Pontos_Vida=?, "
                + "Pontos_Mana=?, Atributo_Forca=?, Atributo_Destreza=?, Atributo_Inteligencia=?, "
                + "Alinhamento=? WHERE Id=?";

        try (Connection conn = conexaoBanco.conectar(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            configurarPreparedStatement(pstm, personagem);
            pstm.setInt(11, personagem.getId());
            pstm.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM Personagens WHERE Id=?";

        try (Connection conn = conexaoBanco.conectar(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, id);
            pstm.executeUpdate();
        }
    }

    private void configurarPreparedStatement(PreparedStatement pstm, RpgDTO personagem) throws SQLException {
        pstm.setString(1, personagem.getNome());
        pstm.setString(2, personagem.getRaca());
        pstm.setString(3, personagem.getClasse());
        pstm.setInt(4, personagem.getNivel());
        pstm.setInt(5, personagem.getPontosVida());
        pstm.setInt(6, personagem.getPontosMana());
        pstm.setInt(7, personagem.getAtributoForca());
        pstm.setInt(8, personagem.getAtributoDestreza());
        pstm.setInt(9, personagem.getAtributoInteligencia());
        pstm.setString(10, personagem.getAlinhamento());
    }
}
