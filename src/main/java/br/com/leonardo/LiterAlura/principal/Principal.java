package br.com.leonardo.LiterAlura.principal;

import br.com.leonardo.LiterAlura.modelo.Livro;
import br.com.leonardo.LiterAlura.modelo.ListaLivro;
import br.com.leonardo.LiterAlura.modelo.Autor;
import br.com.leonardo.LiterAlura.repositorio.RepositorioLivros;
import br.com.leonardo.LiterAlura.repositorio.RepositorioAutores;
import br.com.leonardo.LiterAlura.servico.ConsumoApi;
import br.com.leonardo.LiterAlura.servico.ConverteDados;

import java.util.*;

public class Principal {
    private RepositorioLivros repositorioLivros;
    private RepositorioAutores repositorioAutores;
    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);
    String json = "";
    String confirmacao = "N";

    public Principal(RepositorioLivros repositorioLivros, RepositorioAutores repositorioAutores) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioAutores = repositorioAutores;
    }

    private void menu() {
        String menu = """
        ------------------------------------------------
        Menu de Opções:
        ------------------------------------------------
        1️ - Buscar livro pelo título
        2 - Listar livros cadastrados
        3 - Listar autores cadastrados
        4️ - Listar autores vivos em determinado ano
        5 - Listar livros em um determinado idioma
        6 - Top 10 Livros (Mais baixados)
        7 - Buscar autor por nome
        8 - Estatísticas autor
        
        0 - Sair
        ------------------------------------------------
        Escolha uma opção:
        ------------------------------------------------
        """;
        System.out.println(menu);
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            menu();

            try {
                opcao = leitura.nextInt();
                leitura.nextLine(); // Limpa buffer após leitura numérica
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
                leitura.nextLine(); // Limpa entrada incorreta
                continue;
            }

            switch (opcao) {
                case 1:
                    buscarLivroNaAPIEGravarBD();
                    break;
                case 2:
                    listarLivrosCadastrados();
                    break;
                case 3:
                    listarAutoresCadastrados();
                    break;
                case 4:
                    buscarAutoresPorAno();
                    break;
                case 5:
                    menuIdioma();
                    buscarLivrosPorIdioma();
                    break;
                case 6:
                    buscarTop10LivrosBD();
                    break;
                case 7:
                    buscarAutorPorNomeBD();
                    break;
                case 8:
                    buscarEstatisticasAutoresAno();
                    break;
                case 0:
                    System.out.println("Saindo do Sistema...\nPrograma encerrado.");
                    break;
                default:
                    System.out.println("Opção Inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }


    private void buscarLivroNaAPIEGravarBD() {
        System.out.print("Digite o nome do livro: ");
        String livro = leitura.nextLine().trim().replaceAll(" ", "%20");
        desenhaLinha();
        json = consumoApi.obterDados(ENDERECO + livro);
        ListaLivro listBook = converteDados.obterDados(json, ListaLivro.class);

        if (listBook.getResults() != null && !listBook.getResults().isEmpty()) {
            Livro book = listBook.getResults().get(0);
            System.out.println(book);

            System.out.println("Deseja gravar livro no Banco de Dados? (S/N)");
            confirmacao = leitura.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                // Verifica se o livro já está no banco antes de salvar
                Optional<Livro> bookExistente = repositorioLivros.findByTitle(book.getTitle());
                if (bookExistente.isPresent()) {
                    System.out.println("Livro já cadastrado! Atualizando informações...");
                    Livro livroAtualizado = bookExistente.get();
                    livroAtualizado.setDownloadCount(book.getDownloadCount());
                    repositorioLivros.save(livroAtualizado);
                    System.out.println("Dados atualizados com sucesso! ");
                } else {
                    repositorioLivros.save(book);
                    System.out.println("Livro gravado com sucesso! ");
                }
            }
        } else {
            System.out.println("Nenhum livro encontrado com esse nome.");
        }
    }

    private void listarLivrosCadastrados() {
        List<Livro> lista = repositorioLivros.findAll();
        System.out.println(lista);
    }

    private void listarAutoresCadastrados() {
        List<Autor> lista = repositorioAutores.findAll();
        System.out.println(lista);
    }

    private void buscarAutoresPorAno() {
        int ano = -1;

        while (ano < 0) {
            try {
                System.out.println("Digite um ano para pesquisa:");
                ano = Integer.parseInt(leitura.nextLine().trim());

                if (ano < 0) {
                    System.out.println("Ano inválido! Digite um valor maior ou igual a zero.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números inteiros.");
            }
        }
        desenhaLinha();
        List<Autor> lista = repositorioAutores.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(ano, ano);
        if (lista.isEmpty()) {
            System.out.println("Nenhum Autor encontrado!");
        } else {
            System.out.println(lista);
        }
    }

    private void menuIdioma() {
        String msg = """
        ------------------------------------------------
        Digite a abreviação do idioma:
        ------------------------------------------------
            🇧🇷 pt - Português
            🇪🇸 es - Espanhol
            🇫🇷 fr - Francês
            🇺🇸 en - Inglês
        ------------------------------------------------
        Escolha uma opção:
        ------------------------------------------------
        """;
        System.out.println(msg);
    }

    private void buscarLivrosPorIdioma() {
        String idioma = leitura.nextLine().trim().replaceAll("[^a-zA-Z\\-]", "");
        ;
        List<Livro> lista = repositorioLivros.buscarPorIdioma(idioma);
        if (lista.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
            return;
        }
        System.out.println(lista);
        System.out.println("Quantidade de Livros: " + lista.size());
        desenhaLinha();
    }

    private void buscarTop10LivrosBD() {
        List<Livro> lista = repositorioLivros.findTop10ByOrderByDownloadCountDesc();
        System.out.println(lista);
    }

    private void buscarAutorPorNomeBD() {
        System.out.println("Digite o nome do Autor:");
        String nomeAutor = leitura.nextLine();
        List<Autor> lista = repositorioAutores.findByNameContainingIgnoreCaseOrderByNameAsc(nomeAutor);
        System.out.println(lista);
    }

    private void buscarEstatisticasAutoresAno() {
        System.out.println("Estatísticas dos Anos de Nasc. e Morte:");
        List<Autor> lista = repositorioAutores.findAllByOrderByNameAsc();

        IntSummaryStatistics statistics1 = lista.stream()
                .mapToInt(Autor::getBirthYear)
                .summaryStatistics();
        desenhaLinha();
        IntSummaryStatistics statistics2 = lista.stream()
                .mapToInt(Autor::getDeathYear)
                .summaryStatistics();

        System.out.println("Estatísticas dos Autores:");
        desenhaLinha();
        System.out.println("Total de " + statistics1.getCount() + " Autores analisados.");
        desenhaLinha();
        System.out.println("ANO DE NASCIMENTO:");
        System.out.printf("Média de Nasc.   : %.2f%n", statistics1.getAverage());
        System.out.printf("Mínimo           : %d%n", statistics1.getMin());
        System.out.printf("Máximo           : %d%n", statistics1.getMax());
        desenhaLinha();
        System.out.println("ANO DE MORTE:");
        System.out.printf("Média de Morte   : %.2f%n", statistics2.getAverage());
        System.out.printf("Mínimo           : %d%n", statistics2.getMin());
        System.out.printf("Máximo           : %d%n", statistics2.getMax());
        desenhaLinha();

        desenhaLinha();
    }


    private void desenhaLinha() {
        System.out.println("------------------------------------------------");
    }

}