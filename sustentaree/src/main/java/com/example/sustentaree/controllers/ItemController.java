package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.services.FileService;
import com.example.sustentaree.services.ItemService;
import com.example.sustentaree.services.LambdaService;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemCriacaoDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.mapper.ItemMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/itens")
public class ItemController {
  @Autowired
  private final ItemService service;
  @Autowired
  private FileService fileService;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private LambdaService lambdaService;

  public ItemController(ItemService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<ItemListagemDTO> criar(
      @RequestBody @Valid ItemCriacaoDTO dto,
      @RequestParam int unidadeMedidaId,
      @RequestParam int categoriaItemId,
      @RequestParam int idResponsavel
  ) {

    if (dto.getImagem() != null){
      CompletableFuture.runAsync(() ->
              {
                byte[] imagemBytes = Base64.getDecoder().decode(dto.getImagem());
                Integer idUsuario = service.getUltimoId() + 1;
                String nomeArquivo = "/itens/imagens/"+idUsuario.toString();
                lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
              }
      );
    }

    ItemMapper mapper = ItemMapper.INSTANCE;
    Item novoItem = mapper.toItem(dto);
    novoItem.setAtivo(true);
    Item item = this.service.criar(novoItem, unidadeMedidaId, categoriaItemId, idResponsavel);
    ItemListagemDTO response = mapper.toItemListagemDTO(item);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<ItemListagemDTO>> listar() {
    List<ItemListagemDTO> items = this.service.listar();

    if (items.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(items);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ItemListagemDTO> buscarPorId(@PathVariable Integer id) {
    Item item = this.service.porId(id);

    ItemMapper mapper = ItemMapper.INSTANCE;
    ItemListagemDTO response = mapper.toItemListagemDTO(item);

    return ResponseEntity.ok(response);
  }

  @PutMapping("{id}")
  public ResponseEntity<ItemListagemDTO> atualizar(
      @PathVariable Integer id,
      @RequestBody @Valid AlterarItemDTO alterarItemDTO,
      @RequestParam int unidadeMedidaId,
      @RequestParam int categoriaItemId,
      @RequestParam int idResponsavel
  ) {

    if (alterarItemDTO.getImagem() != null){
      CompletableFuture.runAsync(() ->
              {
                byte[] imagemBytes = Base64.getDecoder().decode(alterarItemDTO.getImagem());
                String nomeArquivo = "/itens/imagens/"+id.toString();
                lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
              }
      );
    }

    ItemMapper mapper = ItemMapper.INSTANCE;

    Item item = mapper.toItemUpdate(alterarItemDTO);
    Item itemAtualizado = this.service.Atualizar(item, id, unidadeMedidaId, categoriaItemId, idResponsavel);

    ItemListagemDTO response = mapper.toItemListagemDTO(itemAtualizado);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remover(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);

    return ResponseEntity.noContent().build();
  }

  @PostMapping(value = "/importarTxt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity gravarTxt(@RequestParam("file") MultipartFile file, @RequestParam("idResponsavel") int idResponsavel) {
    fileService.importarTxt(file, idResponsavel);
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @GetMapping("/exportarTxt")
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity lerTxt(){
    byte[] exportarFile = fileService.exportarTxt();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.TEXT_PLAIN);
    headers.setContentDispositionFormData("attachment", "arquivo.txt");

    return new ResponseEntity<>(exportarFile, headers, HttpStatus.OK);
  }

  @GetMapping("/por-categria/{id}")
  public ResponseEntity<List<ItemListagemDTO>> listarPorCategoria(@PathVariable Integer id) {
    List<Item> itens = this.service.listByCategoriaItem(id);
    ItemMapper mapper = ItemMapper.INSTANCE;
    List<ItemListagemDTO> items = mapper.toItemListDto(itens);

    if (items.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(items);
  }

  @GetMapping("/por-unidade-medida/{id}")
  public ResponseEntity<List<ItemListagemDTO>> listarPorUnidadeMedida(@PathVariable Integer id) {
    List<Item> itens = this.service.listByUnidadeMedida(id);
    ItemMapper mapper = ItemMapper.INSTANCE;
    List<ItemListagemDTO> items = mapper.toItemListDto(itens);

    if (items.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(items);
  }

}
