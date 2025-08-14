<h1 align="center">Catálogo de Livros (LiterAlura) em Java</h1>
<h2 align="center">Challenge da Formação ONE Oracle/Alura</h2>

<p align="center">
  <img src="Badge-Literalura.png" alt="Badge do Challenge">
</p>

## Índice

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Funcionalidades da Aplicação](#funcionalidades-da-aplicação)
- [Persistência de Dados](#persistência-de-dados)
- [Conclusão](#conclusão)

## Descrição

Este projeto tem como objetivo principal a criação de um catálogo de livros utilizando a linguagem de programação Java, o framework Spring Boot e um banco de dados relacional (PostgreSQL). O desafio consiste em consumir dados de uma API externa e gratuita, a Gutendex, e persistir esses dados no banco de dados.
A API Gutendex é um catálogo com informações sobre os mais de 70 mil livros presentes no Project Gutenberg (biblioteca online gratuita).
Site da API, ([https://www.exchangerate-api.com/](https://gutendex.com/)).
Este projeto foi desenvolvido como parte do programa de formação da Oracle em parceria com a Alura, proporcionando aprendizado prático e a oportunidade de aplicar conceitos avançados de Java e Spring, como consumo de APIs externas, persistência de dados, entre outros.



## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para facilitar a criação de aplicações Java.
- **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
- **Gutendex API**: API pública e gratuita que fornece dados de mais de 70 mil livros.



## Estrutura do Projeto

O projeto foi organizado em pacotes para melhor modularização e clareza do código. Cada pacote tem uma responsabilidade específica:
- **br.com.leonardo.LiterAlura**: Contém a classe principal da aplicação (LiterAluraApplication.java) que inicializa o Spring Boot e executa a lógica principal do programa.
- **.principal**: Contém a classe Principal.java, que gerencia o menu de interação com o usuário e a lógica da aplicação.
- **.modelo**: Este pacote inclui as classes que representam os objetos de negócio da aplicação:
  - Autor.java: Representa um autor de livro, com atributos como nome, ano de nascimento e ano de falecimento.
  - Livro.java: Representa um livro, com atributos como título, autores, idiomas e número de downloads.
  - ListaLivro.java:  Classe utilizada para mapear o resultado da API, contendo uma lista de livros.
- **.repositorio**: Este pacote armazena as interfaces de repositório, que estendem JpaRepository para interagir com o banco de dados.
  - RepositorioAutores.java: Interface responsável pela persistência dos dados dos autores.
  - RepositorioLivros.java: Interface responsável pela persistência dos dados dos livros.
- **.servico**: Contém as classes de serviço que lidam com a comunicação com a API e a conversão de dados.
  - ConsumoApi.java: Classe que realiza a requisição HTTP para a API Gutendex.
  - ConverteDados.java: Implementa a interface IConverteDados para desserializar o JSON recebido da API em objetos Java.
  - IConverteDados.java: Interface que define o contrato para a conversão de dados.
- **resources.**: Contém o arquivo application.properties que é responsável por configurar a conexão com o banco de dados PostgreSQL, definindo a URL, nome de usuário e senha a partir de variáveis de ambiente.



 ## Funcionalidades da Aplicação

A aplicação oferece um menu de opções para o usuário, permitindo realizar as seguintes ações:
- Buscar livro pelo título: Consome a API Gutendex para encontrar um livro e, opcionalmente, o salva no banco de dados.
- Listar livros cadastrados: Exibe todos os livros que foram salvos no banco de dados.
- Listar autores cadastrados: Exibe todos os autores que foram salvos no banco de dados.
- Listar autores vivos em determinado ano: Busca no banco de dados por autores que estavam vivos em um ano específico.
- Listar livros em um determinado idioma: Filtra os livros cadastrados no banco de dados por idioma.
- Top 10 Livros (Mais baixados): Exibe uma lista dos 10 livros com o maior número de downloads, ordenados de forma decrescente.
- Buscar autor por nome: Pesquisa autores no banco de dados com base em uma parte do nome, ignorando maiúsculas e minúsculas.
- Estatísticas autor: Calcula e exibe estatísticas sobre os anos de nascimento e morte dos autores cadastrados, como média, mínimo e máximo.
- Sair: Encerra a aplicação.



## Persistência de Dados

A persistência dos dados é gerenciada pelo Spring Data JPA, que utiliza as interfaces RepositorioLivros e RepositorioAutores. As classes Livro e Autor são mapeadas como entidades (@Entity) e as tabelas correspondentes são criadas no banco de dados PostgreSQL.
- A classe Livro possui um relacionamento @OneToMany com Autor.
- A classe Autor possui um relacionamento @ManyToOne com Livro.
A aplicação utiliza as anotações do Spring para autoinjetar os repositórios (@Autowired), facilitando o acesso e a manipulação dos dados no banco de dados.



## Conclusão

Em resumo, o projeto é um excelente exemplo de como combinar diferentes tecnologias para resolver um problema real, praticando conceitos como consumo de API, persistência de dados, mapeamento de objetos e interação com o usuário em um ambiente de desenvolvimento Java e Spring.
