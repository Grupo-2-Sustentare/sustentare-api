package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.EnvioImagemS3DTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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

@Service
public class ImagemService {
    @Autowired
    private LambdaService lambdaService;
    @Autowired
    @Lazy
    private ItemService itemService;
    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    @Value("${nome.bucket}")
    private String bucketName;
    @Value("${metodo.Lambda.aws.enviar.imagem.publica}")
    private String publicFunctionName;
    @Value("${metodo.Lambda.aws.enviar.imagem.privada}")
    private String privateFunctionName;
    private String usuarioPath = "/usuarios/imagens/";
    private String itemPath = "/itens/imagens/";


    public EnvioImagemS3DTO tratarImagemItem(String imagem){
        byte[] imagemBytes = Base64.getDecoder().decode(imagem);
        Integer idItem = itemService.getUltimoId() + 1;
        String nomeArquivo = itemPath + idItem.toString();
        return new EnvioImagemS3DTO(imagemBytes, nomeArquivo, publicFunctionName);
    }

    public EnvioImagemS3DTO tratarEditarImagemItem(String imagem, int id){
        byte[] imagemBytes = Base64.getDecoder().decode(imagem);
        String nomeArquivo = itemPath + id;
        return new EnvioImagemS3DTO(imagemBytes, nomeArquivo, publicFunctionName);
    }

    public EnvioImagemS3DTO tratarImagemUsuario(String imagem){
        byte[] imagemBytes = Base64.getDecoder().decode(imagem);
        Integer idUsuario = usuarioService.getUltimoId() + 1;
        String nomeArquivo = usuarioPath + idUsuario.toString();
        return new EnvioImagemS3DTO(imagemBytes, nomeArquivo, privateFunctionName);
    }

    public EnvioImagemS3DTO tratarEditarImagemUsuario(String imagem, int id){
        byte[] imagemBytes = Base64.getDecoder().decode(imagem);
        String nomeArquivo = usuarioPath + id;
        return new EnvioImagemS3DTO(imagemBytes, nomeArquivo, privateFunctionName);
    }

    public UsuarioDTO addImagemS3Usuario(Usuario usuario){
        UsuarioMapper mapper = UsuarioMapper.INSTANCE;
        UsuarioDTO usuarioDTO = mapper.toUsuarioDTO(usuario);
        try {
            byte[] imagem = lambdaService.downloadFile(bucketName, usuarioPath+usuario.getId().toString());
            usuarioDTO.setImagem(Base64.getEncoder().encodeToString(imagem));
        }catch (Exception e){
            System.out.println(e);
        }

        return usuarioDTO;
    }

    public List<UsuarioDTO> addImagensS3Usuarios(List<Usuario> listaUsuarios){
        UsuarioMapper mapper = UsuarioMapper.INSTANCE;
        List<UsuarioDTO> listaUsuariosDTO = mapper.toUsuarioListDTO(listaUsuarios);
        for (UsuarioDTO usuario : listaUsuariosDTO) {
            try {
                byte[] imagem = lambdaService.downloadFile(bucketName, usuarioPath+usuario.getId().toString());
                usuario.setImagem(convertToJPEG(imagem,1));
            }catch (Exception e){
                System.out.println(e);
            }

        }
        return listaUsuariosDTO;
    }

    public List<ItemListagemDTO> addImagensS3Itens(List<Item> listaItens){
        ItemMapper mapper = ItemMapper.INSTANCE;
        List<ItemListagemDTO> itemListagemDTO = mapper.toItemListDto(listaItens);

        for (ItemListagemDTO itemDaVez : itemListagemDTO) {
            try {
                byte[] imagem = lambdaService.downloadFile(bucketName, itemPath+itemDaVez.getId().toString());
                itemDaVez.setImagem(convertToJPEG(imagem,1));
            }catch (Exception e){
                System.out.println(e);
            }

        }
        return itemListagemDTO;
    }

    public String convertToJPEG(byte[] imageBytes, float quality) throws IOException {
        // Converte o byte array para BufferedImage
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (image == null) {
            throw new IOException("Image could not be decoded");
        }

        // Cria uma nova imagem em RGB para garantir o formato JPEG
        BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        rgbImage.getGraphics().drawImage(image, 0, 0, null);

        // Prepara o fluxo de saída para a imagem comprimida
        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();

        // Obtém o ImageWriter para o formato JPEG
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No writers found for JPEG format");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality); // Define a qualidade (0 a 1)

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(compressedOutput)) {
            writer.setOutput(ios);
            writer.write(null, new javax.imageio.IIOImage(rgbImage, null, null), param);
        } finally {
            writer.dispose();
        }

        // Converte a imagem comprimida de volta para base64
        return Base64.getEncoder().encodeToString(compressedOutput.toByteArray());
    }
}
