package br.com.plataforma.musica.main;

import br.com.plataforma.musica.model.Artista;
import br.com.plataforma.musica.model.Musica;
import br.com.plataforma.musica.model.TipoArtista;
import br.com.plataforma.musica.repository.ArtistaRepository;
import br.com.plataforma.musica.service.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private Scanner leitura = new Scanner(System.in);

    public Main(ArtistaRepository repositorio){
        this.repositorio = repositorio;
    }

    private final ArtistaRepository repositorio;


    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    ********** MENU **********
                    
                    1 - Cadastrar artista
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artistas
                                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtistas();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }


//    private void pesquisarDadosArtista() {
//        System.out.println("Digite o nome do artista:");
//        var nome = leitura.nextLine();
//        var resposta = ConsultaChatGPT.pesquisarArtista(nome);
//        System.out.println(resposta.trim());
//
//    }

    private void buscarMusicasPorArtistas() {
        System.out.println("Digite o nome do artista:");
        var nome = leitura.nextLine();
        List<Musica> musicas = repositorio.buscarMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void cadastrarMusica() {
        System.out.println("Cadastrar música de qual artista?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);

        if (artista.isPresent()){
            System.out.println("Nome da música:");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else{
            System.out.println("Artista não encontrado!");
        }
    }

    private void cadastrarArtista() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("S")){
            System.out.println("Digite o nome do artista:");
            var nome = leitura.nextLine();
            System.out.println("Infome o tipo do artista (solo, dupla ou banda):");
            var tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
            System.out.println("Cadastrar novo artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
}
