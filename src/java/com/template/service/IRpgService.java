package com.template.service;

import com.template.model.dto.RpgDTO;
import java.util.List;

public interface IRpgService {

    void cadastrarPersonagem(RpgDTO personagem) throws Exception;

    List<RpgDTO> listarPersonagens() throws Exception;

    void atualizarPersonagem(RpgDTO personagem) throws Exception;

    void excluirPersonagem(int id) throws Exception;
}
