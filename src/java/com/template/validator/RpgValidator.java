package com.template.validator;

import com.template.model.dto.RpgDTO;
import com.template.util.DialogUtil;

public class RpgValidator {

    public boolean validarPersonagem(RpgDTO dto) {
        if (dto.getNome().trim().isEmpty()
                || dto.getRaca().trim().isEmpty()
                || dto.getClasse().trim().isEmpty()
                || dto.getAlinhamento().trim().isEmpty()) {

            DialogUtil.showWarning("Atenção", "Faltam informações no pergaminho!", "Por favor, preencha todos os campos obrigatórios.");
            return false;
        }
        return true;
    }
}
