package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.repositories.ProdutoRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

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

}
