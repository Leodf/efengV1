package br.com.fiap.efeng.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "ALERTA_ENERGIA")
public class AlertaEnergia {

    public enum Status {
        PENDENTE, RESOLVIDO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alerta_seq")
    @SequenceGenerator(name = "alerta_seq", sequenceName = "seq_alerta", allocationSize = 1)
    @Column(name = "ID_ALERTA")
    private Long id;

    @Column(name = "MENSAGEM", nullable = false, length = 500)
    private String mensagem;

    @Column(name = "DATA_ALERTA", nullable = false)
    private LocalDateTime dataAlerta;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "ID_CONSUMO")
    private ConsumoEnergia consumo;
}