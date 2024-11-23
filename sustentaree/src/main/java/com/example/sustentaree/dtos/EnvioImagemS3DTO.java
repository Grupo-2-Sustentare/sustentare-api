package com.example.sustentaree.dtos;


public class EnvioImagemS3DTO {
    byte[] imagemBytes;
    String nomeArquivo;
    String functionName;

    public EnvioImagemS3DTO(byte[] imagemBytes, String nomeArquivo, String functionName) {
        this.imagemBytes = imagemBytes;
        this.nomeArquivo = nomeArquivo;
        this.functionName = functionName;
    }

    public byte[] getImagem() {
        return imagemBytes;
    }

    public void setImagem(byte[] imagemBytes) {
        this.imagemBytes = imagemBytes;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
}
