package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.services.lambda.model.LambdaException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/lambdaTest")
public class LambdaServiceTest {

    @Autowired
    private CategoriaItemService categoriaItemService;
    @Autowired
    private UnidadeMedidaService unidadeMedidaService;

    private S3Client criarClienteS3() {
        Region region = Region.US_EAST_1;
        S3Client s3 = S3Client.builder().region(region).build();
        return s3;
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid byte[] imagem, @RequestParam String nomeArquivo, @RequestParam String functionName) {
        System.out.println(imagem);
        System.out.println(nomeArquivo);
        System.out.println(functionName);
        String funcao = "envioDeImagem";
        Region region = Region.US_EAST_1;

        LambdaClient awsLambda = LambdaClient.builder()
                .region(region)
                .build();

        // Objeto para serializar/deserializar JSON
        ObjectMapper objectMapper = new ObjectMapper();

        InvokeResponse res = null;
        try {
            String imagemBase64 = Base64.getEncoder().encodeToString(imagem);
            // "json" para enviar ao Lambda
            Map<String, String> parametros = Map.of(
                    "imagem", imagemBase64,
                    "nomeArquivo", nomeArquivo,
                    "functionName", functionName
            );

            // Serializa o objeto para JSON e cria um SdkBytes (que é o payload)
            SdkBytes payload = SdkBytes.fromUtf8String(objectMapper.writeValueAsString(parametros));

            // Configura a requisição para a Lambda
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(funcao)
                    .payload(payload)
                    .build();

            // Invoca a Lambda
            res = awsLambda.invoke(request);

            System.out.println(res);


            // Deserializa o JSON de resposta (convertendo para String)
            String value = res.payload().asUtf8String();

            // Deserializa o JSON de resposta (convertendo para objeto do tipo RespostaCpf)
            byte[] respostaImagem =
                    objectMapper.readValue(value, byte[].class);

			System.out.println(respostaImagem);

            System.out.println();
            if (respostaImagem != null) {
                System.out.println("Imagem válida!");
            } else {
                System.out.println("Imagem inválida!");
            }

        } catch (LambdaException | JsonProcessingException e) {
            System.err.println(e.getMessage());
        }


        awsLambda.close();
        return ResponseEntity.status(201).build();
    }

    @GetMapping("/baixarImagem")
    public ResponseEntity<byte[]> downloadFile(@RequestParam String bucket, @RequestParam String key) {
        S3Client s3Service = criarClienteS3();

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseBytes responseBytes = s3Service.getObjectAsBytes(getObjectRequest);
            byte[] data = responseBytes.asByteArray();

            // Verifique o tamanho da imagem para garantir que ela foi baixada corretamente
            System.out.println("Tamanho da imagem: " + data.length);

            HttpHeaders headers = new HttpHeaders();
            // Defina o Content-Type com base no tipo da imagem (pode ser obtido do S3)
            headers.setContentType(MediaType.IMAGE_JPEG); // Substitua por MediaType.IMAGE_PNG se necessário

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    private byte[] baixarArquivoS3(String nomeArquivo) {
//        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
//                .bucket("sustentaree-s3")
//                .key(nomeArquivo)
//                .build();
//
//        S3Client s3 = criarClienteS3();
//
//        byte[] arquivo = s3.getObjectAsBytes(getObjectRequest).asByteArray();
//        return arquivo;
//    }

//    @GetMapping()
//    public ResponseEntity<byte[]> getFoto() {
//
//        String referenciaArquivoFoto = "/produtos/imagens/27";
//
//        byte[] arquivo = baixarArquivoS3(referenciaArquivoFoto);
//
//        return ResponseEntity.ok(arquivo);
//    }

    @Autowired
    ItemService itemService;


}
