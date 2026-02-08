package com.git.devdioge.paciente_api.model;


import com.git.devdioge.paciente_api.exception.DataPassadaException;
import jakarta.persistence.*;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_paciente")
public final class Paciente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Setter
    @Column(name = "codigo")
    private String codigo;

    @Setter
    @Column(name = "nome")
    private String nome;

    @Setter
    @Column(name = "numero")
    private String numero;

    @Setter
    @Column(name = "bairro")
    private String bairro;

    @Column(name = "tipoConsulta")
    private String tipoConsulta;

    @Setter
    @Column(name = "motivo")
    private String status;

    @Setter
    @Column(name = "data_consulta")
    private LocalDateTime dataConsulta;

    @Column(name = "data_marcacao")
    private LocalDateTime dataMarcacao;

    private Paciente(){}

    private Paciente(String codigo, String nome, String numero, String bairro, String tipoConsulta, String status,
                     LocalDateTime dataConsulta, LocalDateTime dataMarcacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.numero = numero;
        this.bairro = bairro;
        this.tipoConsulta = tipoConsulta;
        this.status = status;
        this.dataConsulta = dataConsulta;
        this.dataMarcacao = dataMarcacao;
    }


    public Long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getTipoConsulta() {
        return tipoConsulta;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public LocalDateTime getDataMarcacao() {
        return dataMarcacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Paciente paciente = (Paciente) o;
        return Objects.equals(codigo, paciente.codigo);
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }


    @Override
    public String toString() {
        return "Paciente{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", numero='" + numero + '\'' +
                ", bairro='" + bairro + '\'' +
                ", tipoConsulta='" + tipoConsulta + '\'' +
                ", statusCode='" + status + '\'' +
                ", dataConsulta=" + dataConsulta +
                ", dataMarcacao=" + dataMarcacao +
                '}';
    }

    public static class Builder {
        private String codigo = UUID.randomUUID().toString();
        private String nome;
        private String numero;
        private String bairro;
        private String tipoConsulta;
        private String status;
        private LocalDateTime dataConsulta;
        private LocalDateTime dataMarcacao = LocalDateTime.now();


        public Builder setCodigo(String codigo) {
            this.codigo = codigo;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setNumero(String numero) {
            this.numero = numero;
            return this;
        }

        public Builder setBairro(String bairro) {
            this.bairro = bairro;
            return this;
        }

        public Builder setTipoConsulta(String tipoConsulta) {
            this.tipoConsulta = tipoConsulta;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setDataConsulta(LocalDateTime dataConsulta) {
            this.dataConsulta = dataConsulta;
            return this;
        }


        public Paciente build() {
            if (this.codigo == null || this.codigo.isBlank())
                throw new IllegalArgumentException("Campo código obrigatória está null ou vázio");

            if (this.nome == null || this.nome.isBlank())
                throw new IllegalArgumentException("Campo nome obrigatória está null ou vázio");

            if (this.numero == null || this.numero.isBlank())
                throw new IllegalArgumentException("Campo número obrigatória está null ou vázio");

            if (this.bairro == null || this.bairro.isBlank())
                throw new IllegalArgumentException("Campo bairro obrigatória está null ou vázio");

            if (this.tipoConsulta == null || this.tipoConsulta.isBlank())
                throw new IllegalArgumentException("Campo tipo_Consulta obrigatória está null ou vázio");

            if (this.status == null || status.isBlank())
                throw new IllegalArgumentException("Campo statusCode statusCode está null ou vázio");

            if (this.dataConsulta == null )
                throw new IllegalArgumentException("Campo data_Consulta statusCode está null ou vázio");

            if (this.dataConsulta.isBefore(LocalDateTime.now()))
                throw new DataPassadaException("Data da consulta errada, precisa ser no futuro");

            if (this.dataMarcacao == null)
                throw new IllegalArgumentException("Campo data_marcação statusCode está null ou vázio");

            return new Paciente(this.codigo,this.nome,
                    this.numero, this.bairro,this.tipoConsulta,
                    this.status,this.dataConsulta, this.dataMarcacao);
        }

        /**
         * Especificamente criado para atualização parcial, use exclusivamente para isso ou algum método que precise
         * necessáriamente de campos possivel null.
         * @return
         */
        public Paciente patchBuild() {
            return new Paciente(this.codigo,this.nome,
                    this.numero, this.bairro,this.tipoConsulta,
                    this.status,this.dataConsulta, this.dataMarcacao);
        }

    }
}
