package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.repositories.ProdutoRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.Integer.valueOf;

@Service
public class FileService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public void writeProductToFile() {
        List<Produto> listaProdutos = produtoRepository.findAll();
        if (listaProdutos.isEmpty()) {
            System.out.println("Não há produtos para gravar");
            return;
        }
        String filePath = "Produtos.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Lista de Produtos");
            writer.newLine();
            writer.write(String.format("%-7s %-20s %-20s %-10s %-22s %-22s", "ID", "Item", "Nome", "Preço", "Quantidade de Produto", "Quantidade de Medida"));
            for (Produto entity : listaProdutos) {
                writer.write(String.format("%-7d %-20s %-20s %-10s %-22s %-22s", entity.getId(), entity.getItem().getNome(), entity.getPreco(), entity.getQtdProduto(), entity.getQtdMedida()));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void gravarRegistro(String nomeArq, String registro){
        BufferedWriter saida = null;
        try{
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        } catch (IOException e) {
            System.out.println("Erro ao abur o arquivo: " + e.getMessage());
        }
        try{
            saida.append(registro + "\n");
            saida.close();
        } catch (IOException e) {
            System.out.println("Erro ao abur o arquivo: " + e.getMessage());

        }

    }

    public void gravaArquivoTxt(List<Item> itens, String nomeArq){
        int qtdRegistroDados = 0;

        String header = "00NOTA20242";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        header += "01";
        gravarRegistro(nomeArq, header);
        for(Item i: itens){
            String corpo = "02";
            corpo += String.format("%-25.25s",i.getCategoria().getNome());
            corpo += String.format("%-30.30s",i.getNome());
            corpo += String.format("%-4.4s",i.getPerecivel());
            corpo += String.format("%-25.25s",i.getUnidade_medida().getNome());
            corpo += String.format("%05d",i.getDias_vencimento());

            gravarRegistro(nomeArq, corpo);
            qtdRegistroDados++;
        }
        String trailer = "01";
        trailer += String.format("%010d", qtdRegistroDados);
        gravarRegistro(nomeArq, trailer);
    }

    public void leArquivoTxt(String nomeArq){
        BufferedReader entrada = null;

        String registro, tipoRegistro;
        String categoria, nome, perecivel, unidade_medida;
        Integer dias_vencimento;
        int qtdFalta;
        int contaRegDados = 0;
        int qtdRegGravados;

        try{
            entrada = new BufferedReader(new FileReader(nomeArq));
        }catch(IOException erro){
            System.out.println("Erro ao abrir o arquivo " + erro.getMessage());
        }

        try{
            registro = entrada.readLine();
            while(registro != null){
                tipoRegistro = registro.substring(0,2);
                switch (tipoRegistro){
                    case "00":
                        System.out.println("HEADER");
                        System.out.println("Data: " + registro.substring(2, 6));
                        System.out.println("Ano e Semestre: " + registro.substring(6, 11));
                        System.out.println("Hora de gravação do arquivo: " + registro.substring(11, 30));
                        System.out.println("Versão do documento de layout: " + registro.substring(30, 32));
                        break;
                    case "01":
                        System.out.println("TRAILER");
                        qtdRegGravados = Integer.parseInt(registro.substring(2, 12));
                        if (qtdRegGravados == contaRegDados){
                            System.out.println("Quantidade de registros gravados compativel com qauntidade de reg de dados lidos: " + qtdRegGravados);
                        }else {
                            System.out.println("Quantidade de registros gravados incompativel com qauntidade de reg de dados lidos: " + qtdRegGravados);
                        }
                        contaRegDados = 0;
                        break;
                    case "02":
                        System.out.println("DADOS");
                        categoria = registro.substring(2, 27).trim();
                        nome = registro.substring(27, 57).trim();
                        perecivel = registro.substring(57, 61).trim();
                        unidade_medida = registro.substring(61, 86).trim();
                        if (registro.substring(86, 91).trim().equals("null")) {
                            dias_vencimento = null;
                        } else{
                            dias_vencimento = Integer.valueOf(registro.substring(86, 91).trim());
                        }

                        contaRegDados++;
                        System.out.println(contaRegDados);
                       // Item item = new Item();
                        System.out.println(categoria + " " + nome + " " + perecivel + " " + unidade_medida + " " + dias_vencimento);


                        break;
                    default:
                        System.out.println("ERRO");
                        break;
                }

                registro = entrada.readLine();

            }
            entrada.close();

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo " + e.getMessage());
            e.printStackTrace();
        }
    }
}
