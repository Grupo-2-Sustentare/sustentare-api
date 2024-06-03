package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.dtos.fechamento.AlterarFechamentoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoCriacaoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class FechamentoMapper {
  public static final FechamentoMapper INSTANCE = Mappers.getMapper(FechamentoMapper.class);

  public abstract Fechamento toFechamento(FechamentoCriacaoDTO fechamentoCriacaoDTO);

  public abstract FechamentoListagemDTO toFechamentoListagemDTO(Fechamento fechamento);

  public abstract List<FechamentoListagemDTO> toFechamentoList(List<Fechamento> fechamentoList);

  public abstract Fechamento toFechamento(AlterarFechamentoDTO alterarFechamentoDTO);

//    public static Fechamento toFechamento(FechamentoCriacaoDTO fechamentoCriacaoDTO) {
//
//        Fechamento fechamento = new Fechamento();
//        fechamento.setDataInicio(fechamentoCriacaoDTO.getDataInicio());
//        fechamento.setDataFim(fechamentoCriacaoDTO.getDataFim());
//        fechamento.setDataFechamento(fechamentoCriacaoDTO.getDataFechamento());
//        fechamento.setIsManual(fechamentoCriacaoDTO.getIsManual());
//
//        return fechamento;
//    }
}
