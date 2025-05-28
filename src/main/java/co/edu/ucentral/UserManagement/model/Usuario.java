package co.edu.ucentral.UserManagement.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY strategy works well with MySQL AUTO_INCREMENT
    private Long id;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "correo", length = 100, unique = true)
    private String correo;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "rol", length = 50)
    private String rol;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro; // Changed type to LocalDate

    @Column(name = "password") // Add this column to your MySQL 'usuarios' table
    private String password;


}