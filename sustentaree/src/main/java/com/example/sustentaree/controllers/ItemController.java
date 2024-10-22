package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.services.FileService;
import com.example.sustentaree.services.ItemService;
import com.example.sustentaree.services.LambdaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemCriacaoDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.mapper.ItemMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

  @Operation(summary = "Criar um item", description = "Cria um item com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item criado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      )),
      @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      ))
  })

  @PostMapping
//    TODO explicar o motivo de usar o @RequestParam para os id's ao invés de @RequestBody neste caso específico (Raphael)
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

  @Operation(summary = "Listar itens", description = "Lista todos os itens cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      ))
  })

  @GetMapping
  public ResponseEntity<List<ItemListagemDTO>> listar() {
    List<ItemListagemDTO> items = this.service.listar();

    if (items.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(items);
  }

  @Operation(summary = "Buscar item por ID", description = "Retorna um item com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item retornado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemListagemDTO.class)
      ))
  })

  @GetMapping("/{id}")
  public ResponseEntity<ItemListagemDTO> buscarPorId(@PathVariable Integer id) {
    Item item = this.service.porId(id);

    ItemMapper mapper = ItemMapper.INSTANCE;
    ItemListagemDTO response = mapper.toItemListagemDTO(item);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Atualizar um item", description = "Atualiza um item com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ItemCriacaoDTO.class)
      ))
  })

  @PutMapping("{id}")
  public ResponseEntity<ItemListagemDTO> atualizar(
      @PathVariable int id,
      @RequestBody @Valid AlterarItemDTO alterarItemDTO,
      @RequestParam int unidadeMedidaId,
      @RequestParam int categoriaItemId,
      @RequestParam int idResponsavel
  ) {
    ItemMapper mapper = ItemMapper.INSTANCE;

    Item item = mapper.toItemUpdate(alterarItemDTO);
    Item itemAtualizado = this.service.Atualizar(item, id, unidadeMedidaId, categoriaItemId, idResponsavel);

    ItemListagemDTO response = mapper.toItemListagemDTO(itemAtualizado);
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Remover um item", description = "Remove um item com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
      @ApiResponse(responseCode = "404", description = "Item não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
  })

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remover(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);

    return ResponseEntity.notFound().build();
  }

  @PostMapping(value = "/importarTxt", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity gravarTxt(@RequestParam("file") MultipartFile file){
    fileService.importarTxt(file);
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


}
