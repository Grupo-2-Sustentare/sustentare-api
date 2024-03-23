package school.sptech.susutentarecrud.controllers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import school.sptech.susutentarecrud.domain.produto.Produto;
import school.sptech.susutentarecrud.domain.produto.ProdutoDTO;
import school.sptech.susutentarecrud.domain.produto.ProdutoRespository;
import school.sptech.susutentarecrud.mappers.ProdutoMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRespository produtoRepository;

    @GetMapping
    public ResponseEntity getAllProduct(){
        List<Produto> allProducts = produtoRepository.findAll();
        selectionSort2(allProducts);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/selectionSort")
    public ResponseEntity todosProdutosOrdenadoID(){
        List<Produto> allProducts = produtoRepository.findAll();
        selectionSort(allProducts);
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/bubbleSort")
    public ResponseEntity todosProdutosOrdenadoPreco(){
        List<Produto> allProducts = produtoRepository.findAll();
        bubbleSort(allProducts);
        return ResponseEntity.ok(allProducts);
    }


    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody ProdutoDTO dados){
        Produto produto = ProdutoMapper.INSTANCE.toProduto(dados);
        System.out.println(dados.lote());
        System.out.println(dados.empresa());
        System.out.println(dados.nome());
        System.out.println(dados.preco());
        System.out.println(dados.qtdProduto());
        produtoRepository.save(produto);

        return ResponseEntity.status(200).body(produto);


    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarProduto(@PathVariable Integer id,@RequestBody ProdutoDTO dados){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()){
            Produto produto = optionalProduto.get();
            produto.setId(produto.getId());
            produto.setEmpresa(dados.empresa());
            produto.setLote(dados.lote());
            produto.setNome(dados.nome());
            produto.setPreco(dados.preco());
            produto.setQtdProduto(dados.qtdProduto());
            return ResponseEntity.ok(produto);
        }else {
            throw new EntityNotFoundException();
        }
    }

    @DeleteMapping
    public ResponseEntity deletarProduto(@RequestParam Integer id){
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (optionalProduto.isPresent()){
            produtoRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(400).build();
    }






    public static List<Produto> selectionSort(List<Produto> v){
        for (int i = 0; i < v.size() - 1; i++) {
            for (int j = i + 1 ; j < v.size(); j++) {
                if ( v.get(j).getId() < v.get(i).getId()){
                    Produto aux = v.get(j);
                    v.set(j,v.get(i));
                    v.set(i,aux);
                }

            }
        }
        return v;
    }


    public static List<Produto> selectionSort2(List<Produto> v){

        for (int i = 0; i < v.size() - 1; i++) {
            int menorIndice = i;
            for (int j = i + 1 ; j < v.size(); j++) {
                if (v.get(j).getEmpresa().compareTo(v.get(menorIndice).getEmpresa()) < 0){
                    menorIndice = j;
                }
            }
            Produto aux = v.get(i);
            v.set(i, v.get(menorIndice));
            v.set(menorIndice, aux);
        }
        return v;
    }


    public static List<Produto> bubbleSort(List<Produto> v) {
        for (int i = 0; i < v.size() - 1; i++) {
            for (int j = 1; j < v.size() - i; j++) {
                if (v.get(j-1).getPreco() < v.get(j).getPreco()) {
                    Produto aux = v.get(j);
                    v.set(j, v.get(j - 1));
                    v.set(j - 1, aux);
                }
            }
        }
        return v;
    }




}
