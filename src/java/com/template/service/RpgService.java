package com.template.service;

import com.template.model.dao.RpgDAO;
import com.template.model.dto.RpgDTO;
import java.util.List;

public class RpgService implements IRpgService {

    private final RpgDAO rpgDAO;

    public RpgService() {
        this.rpgDAO = new RpgDAO();
    }

    @Override
    public void cadastrarPersonagem(RpgDTO personagem) throws Exception {
        rpgDAO.inserir(personagem);
    }

    @Override
    public List<RpgDTO> listarPersonagens() throws Exception {
        return rpgDAO.listar();
    }

    @Override
    public void atualizarPersonagem(RpgDTO personagem) throws Exception {
        rpgDAO.atualizar(personagem);
    }

    @Override
    public void excluirPersonagem(int id) throws Exception {
        rpgDAO.excluir(id);
    }
}
