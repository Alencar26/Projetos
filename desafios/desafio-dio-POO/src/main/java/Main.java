import io.alencar.desafio.dominio.Curso;
import io.alencar.desafio.dominio.Mentoria;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Curso curso = new Curso("Curso Java","Descrição do Curso",8);
        System.out.println(curso);

        Mentoria mentoria = new Mentoria("Nome Mentoria", "Descrição da Mentoria", LocalDate.now());
        System.out.println(mentoria);
    }
}
