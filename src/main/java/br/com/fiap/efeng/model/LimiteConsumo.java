package br.com.fiap.efeng.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LIMITE_CONSUMO")
public class LimiteConsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "limite_seq")
    @SequenceGenerator(name = "limite_seq", sequenceName = "seq_limite", allocationSize = 1)
    @Column(name = "ID_LIMITE")
    private Long id;

    @Column(name = "LOCALIZACAO", nullable = false, length = 100)
    private String localizacao;

    @Column(name = "LIMITE_KWH_DIA", nullable = false)
    private Double limiteKwhDia;

    @Column(name = "DATA_INICIO", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "DATA_FIM")
    private LocalDate dataFim;
}