package me.dio.sacola.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

    private String cep;
    private String complemento;
}
