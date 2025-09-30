package br.com.fiap.efeng.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "SENSOR_IOT")
public class SensorIOT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SENSOR")
    private Long id;

    @Column(name = "TIPO_SENSOR", nullable = false, length = 50)
    private String tipoSensor;

    @Column(name = "DATA_INSTALACAO", nullable = false)
    private LocalDate dataInstalacao;

    @ManyToOne
    @JoinColumn(name = "ID_DISPOSITIVO")
    private Dispositivo dispositivo;
}