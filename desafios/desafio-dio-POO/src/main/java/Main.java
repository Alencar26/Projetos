import io.alencar.desafio.dominio.Bootcamp;
import io.alencar.desafio.dominio.Curso;
import io.alencar.desafio.dominio.Dev;
import io.alencar.desafio.dominio.Mentoria;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Curso curso1 = new Curso("Curso Java","Descrição do Curso",8);
        Curso curso2 = new Curso("Curso GO","Descrição do Curso",6);

        Mentoria mentoria = new Mentoria("Nome Mentoria", "Descrição da Mentoria", LocalDate.now());

        Bootcamp bootcamp = new Bootcamp();
        bootcamp.setNome("Bootcamp Ifood");
        bootcamp.setDescricao("Descrição do bootcamp Ifood");

        bootcamp.getConteudos().add(curso1);
        bootcamp.getConteudos().add(curso2);
        bootcamp.getConteudos().add(mentoria);

        Dev dev1 = new Dev();
        dev1.setNome("André");
        dev1.inscreverBootcamp(bootcamp);
        System.out.println("Conteúdos Inscritos: " + dev1.getConteudosInscritos());
        System.out.println("Conteúdos Concluídos: " + dev1.getConteudosConcluidos());
        System.out.println("XP: " + dev1.calcularTotalXP());
        dev1.progredir();
        System.out.println("Conteúdos Inscritos: " + dev1.getConteudosInscritos());
        System.out.println("Conteúdos Concluídos: " + dev1.getConteudosConcluidos());
        System.out.println("XP: " + dev1.calcularTotalXP());

        Dev dev2 = new Dev();
        dev2.setNome("Ana");
        dev1.inscreverBootcamp(bootcamp);



    }
}
