package com.template.validator;

import com.template.model.dto.RpgDTO;
import com.template.util.DialogUtil;
import java.util.ArrayList;
import java.util.List;

public class RpgValidator {

    public boolean validarPersonagem(RpgDTO dto) {
        List<Validator<String>> validadores = new ArrayList<>();

        validadores.add(new ValidatorCampNecessary("Nome", dto.getNome()));
        validadores.add(new ValidatorCampNecessary("Raça", dto.getRaca()));
        validadores.add(new ValidatorCampNecessary("Classe", dto.getClasse()));
        validadores.add(new ValidatorCampNecessary("Alinhamento", dto.getAlinhamento()));

        for (Validator<String> validador : validadores) {
            if (!validador.validar(validador.getValor())) {
                DialogUtil.showWarning("Atenção", "Faltam informações no pergaminho!", validador.getMensagemErro());
                return false;
            }
        }
        return true;
    }
}