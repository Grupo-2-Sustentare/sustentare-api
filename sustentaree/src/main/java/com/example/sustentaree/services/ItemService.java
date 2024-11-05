package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.item.ItemCriacaoDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.dtos.produto.ProdutoListagemDTO;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.mapper.ProdutoMapper;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class ItemService {
  @Autowired
  private final ItemRepository repository;
  @Autowired
  private final UnidadeMedidaService unidadeMedidaService;
  @Autowired
  private final CategoriaItemService categoriaItemService;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  @Autowired
  private LambdaService lambdaService;
  @Autowired
  private ImagemService imagemService;

  public ItemService(ItemRepository repository, UnidadeMedidaService unidadeMedidaService, CategoriaItemService categoriaItemService) {
    this.repository = repository;
    this.unidadeMedidaService = unidadeMedidaService;
    this.categoriaItemService = categoriaItemService;
  }

//  public List<ItemListagemDTO> listar() {
//    List<Item> itens = this.repository.findByAtivoTrue();
//
//    ItemMapper mapper = ItemMapper.INSTANCE;
//    List<ItemListagemDTO> response = mapper.toItemListDto(itens);
//
//    int contador = 0;
//    for (ItemListagemDTO item : response) {
//      try {
//        byte[] imagem = lambdaService.downloadFile("sustentaree-s3", "/itens/imagens/" + itens.get(contador).getId().toString());
//        response.get(contador).setImagem(convertToJPEG(imagem, 1));
//      } catch (Exception e) {
//        System.out.println(e);
//      }
//
//      contador++;
//    }
//    return response;
//  }

  public List<ItemListagemDTO> listar() {
    List<Item> itens = this.repository.findByAtivoTrue();
    if (itens == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum item encontrado");
    }

    List<ItemListagemDTO> ItemListagemDTO = imagemService.addImagensS3Itens(itens);

    if (ItemListagemDTO == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao tentar adicionar imagem");
    }

    return ItemListagemDTO;
  }

  public List<Item> listarSemImagem() {

    return  this.repository.findByAtivoTrue();
  }

//  private String convertToJPEG(byte[] imageBytes, float quality) throws IOException {
//    // Converte o byte array para BufferedImage
//    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
//    if (image == null) {
//      throw new IOException("Image could not be decoded");
//    }
//
//    // Cria uma nova imagem em RGB para garantir o formato JPEG
//    BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//    rgbImage.getGraphics().drawImage(image, 0, 0, null);
//
//    // Prepara o fluxo de saída para a imagem comprimida
//    ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();
//
//    // Obtém o ImageWriter para o formato JPEG
//    Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
//    if (!writers.hasNext()) {
//      throw new IOException("No writers found for JPEG format");
//    }
//
//    ImageWriter writer = writers.next();
//    ImageWriteParam param = writer.getDefaultWriteParam();
//    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//    param.setCompressionQuality(quality); // Define a qualidade (0 a 1)
//
//    try (ImageOutputStream ios = ImageIO.createImageOutputStream(compressedOutput)) {
//      writer.setOutput(ios);
//      writer.write(null, new javax.imageio.IIOImage(rgbImage, null, null), param);
//    } finally {
//      writer.dispose();
//    }
//
//    // Converte a imagem comprimida de volta para base64
//    return Base64.getEncoder().encodeToString(compressedOutput.toByteArray());
//  }


  public Item porId(int id) {
    return this.repository.findById(id).orElseThrow(
        () -> new RuntimeException("Item não encontrado")
    );
  }

  @Transactional
  public Item criar(
      Item novoItem,
      int unidadeMedidaId,
      int categoriaItemId,
      int idResponsavel
  ) {

    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    UnidadeMedida unidadeMedida = this.unidadeMedidaService.porId(unidadeMedidaId);
    CategoriaItem categoriaItem = this.categoriaItemService.porId(categoriaItemId);


    novoItem.setUnidade_medida(unidadeMedida);
    novoItem.setCategoria(categoriaItem);

    return this.repository.save(novoItem);
  }

  @Transactional
  public Item Atualizar(
      Item item,
      int id,
      int unidadeMedidaId,
      int categoriaItemId,
      int idResponsavel
  ) {
    Item itemAtual = this.porId(id);
    item.setId(itemAtual.getId());
    return this.criar(item, unidadeMedidaId, categoriaItemId, idResponsavel);
  }

  @Transactional
  public void deletar(int id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    this.repository.updateAtivoById(false, id);
  }

  public Item itemParado(){
    return repository.findByItemParado();
  }

  public List<Item> kpiVencimento(){
    return repository.getItemVencimento();
  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }

  public Boolean procurarItemPorNome(String nome){
    return this.repository.existsByNome(nome).isPresent();
  }

  public Integer getUltimoId(){
    Optional<Item> item = this.repository.getUltimoId();
    if (item.isPresent()){
      return item.get().getId();
    }
    return 0;
  }
}
