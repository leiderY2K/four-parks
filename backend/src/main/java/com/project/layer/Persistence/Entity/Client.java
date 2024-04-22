package com.project.layer.Persistence.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Entity
@Data
@Table(name = "CLIENTE")
public class Client {
    
    @Id
    @Column(name = "IDCLIENTE", nullable = false)
    private int idCliente;

    @Column(name = "PRIMERNOMBRE", nullable = false)
    private String primerNombre;

    @Column(name = "SEGUNDONOMBRE", nullable = true)
    private String segundoNombre;

    @Column(name = "PRIMERAPELLIDO", nullable = false)
    private String primerApellido;

    @Column(name = "SEGUNDOAPELLIDO", nullable = true)
    private String segundoApellido;

    @Column(name = "TELEFONOCLIENTE", nullable = false)
    @Pattern(regexp = "^(\\(?(\\+?\\d{2,3})?\\)?[- ]?\\(?(\\d{3})\\)?[- ]?)?(\\d{3})[- ]?(\\d{4})$")
    private String telefono;

    @Column(name = "CORREO", nullable = false)
    private String correo;

    @Column(name = "DIRECCIONCLIENTE", nullable = false)
    private String direccion;

    @Column(name = "PASSWORDCLIENTE", nullable = false)
    private String password;
}
