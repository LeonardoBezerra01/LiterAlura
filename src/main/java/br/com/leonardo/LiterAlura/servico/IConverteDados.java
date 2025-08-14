package br.com.leonardo.LiterAlura.servico;

import java.util.List;

public interface IConverteDados {
    <T> List<T> obterDadosArray(String json, Class<T> classe);
    <T> T obterDados(String json, Class<T> classe);
}
