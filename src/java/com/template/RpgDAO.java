package com.template;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object para operações de CRUD na tabela Personagens.
 */
public class RpgDAO {

    private final Conexao conexaoBanco = new Conexao();
    private static final Logger LOGGER = System.getLogger(RpgDAO.class.getName());

    public void inserir(RpgDTO personagem) {
        String sql = "INSERT INTO Personagens (Nome, Raca, Classe, Nivel, Pontos_Vida, "
                + "Pontos_Mana, Atributo_Forca, Atributo_Destreza, Atributo_Inteligencia, Alinhamento) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = conexaoBanco.conectar()) {
            if (conn == null) {
                System.err.println("[ERRO] Sem conexão com o banco de dados. Não foi possível inserir.");
                return;
            }
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                configurarPreparedStatement(pstm, personagem);
                pstm.executeUpdate();
                System.out.println("\n[SISTEMA] Personagem '" + personagem.getNome() + "' conjurado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir personagem: " + e.getMessage());
            LOGGER.log(Level.ERROR, "Erro ao inserir personagem: " + e.getMessage(), e);
        }
    }

    public List<RpgDTO> listar() {
        String sql = "SELECT * FROM Personagens ORDER BY Id";
        List<RpgDTO> lista = new ArrayList<>();

        try (Connection conn = conexaoBanco.conectar()) {
            if (conn == null) {
                System.err.println("[ERRO] Sem conexão com o banco de dados. Retornando lista vazia.");
                return lista;
            }
            try (PreparedStatement pstm = conn.prepareStatement(sql);
                 ResultSet rs = pstm.executeQuery()) {

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
        } catch (SQLException e) {
            System.err.println("Erro ao listar personagens: " + e.getMessage());
            LOGGER.log(Level.ERROR, "Erro ao listar personagens do banco.", e);
        }
        return lista;
    }

    public void atualizar(RpgDTO personagem) {
        String sql = "UPDATE Personagens SET Nome=?, Raca=?, Classe=?, Nivel=?, Pontos_Vida=?, "
                + "Pontos_Mana=?, Atributo_Forca=?, Atributo_Destreza=?, Atributo_Inteligencia=?, "
                + "Alinhamento=? WHERE Id=?";

        try (Connection conn = conexaoBanco.conectar()) {
            if (conn == null) {
                System.err.println("[ERRO] Sem conexão com o banco de dados. Não foi possível atualizar.");
                return;
            }
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                configurarPreparedStatement(pstm, personagem);
                pstm.setInt(11, personagem.getId());

                int linhasAfetadas = pstm.executeUpdate();
                if (linhasAfetadas > 0) {
                    System.out.println("\n[SISTEMA] Personagem atualizado com sucesso!");
                } else {
                    System.out.println("\n[AVISO] ID não encontrado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar personagem: " + e.getMessage());
            LOGGER.log(Level.ERROR, "Erro ao atualizar personagem ID: " + personagem.getId(), e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM Personagens WHERE Id=?";

        try (Connection conn = conexaoBanco.conectar()) {
            if (conn == null) {
                System.err.println("[ERRO] Sem conexão com o banco de dados. Não foi possível excluir.");
                return;
            }
            try (PreparedStatement pstm = conn.prepareStatement(sql)) {
                pstm.setInt(1, id);
                pstm.executeUpdate();
                System.out.println("\n[SISTEMA] Personagem banido do reino!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir personagem: " + e.getMessage());
            LOGGER.log(Level.ERROR, "Erro ao excluir personagem ID: " + id, e);
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