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

import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoRespository produtoRepository;

    @GetMapping
    public ResponseEntity getAllProduct(){
        var allProducts = produtoRepository.findAll();
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



}
