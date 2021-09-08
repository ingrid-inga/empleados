package ar.com.ada.api.empleados.entities;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "empleado_id")
    private Integer empleadoId;

    private String nombre;
    private Integer edad;

    @ManyToOne //JoinColumn va donde está la FK
    @JoinColumn(name = "categoria_id", referencedColumnName = "categoria_id")
    private Categoria categoria;
    
    private BigDecimal sueldo;

    @Column(name = "estado_id")
    private int estado;

    @Column(name = "fecha_alta")
    private Date fechaAlta;

    @Column(name = "fecha_baja")
    private Date fechaBaja;

    public Integer getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Integer empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
        this.categoria.agregarEmpleado(this);
    }

    public BigDecimal getSueldo() {
        return sueldo;
    }

    public void setSueldo(BigDecimal sueldo) {
        this.sueldo = sueldo;
    }

    public EstadoEmpleadoEnum getEstado() {
        return EstadoEmpleadoEnum.parse(this.estado);
    }

    public void setEstado(EstadoEmpleadoEnum estado) {
        this.estado = estado.getValue();
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public enum EstadoEmpleadoEnum {

        ACTIVO(1), BAJA(2);

        private final int value;

        private EstadoEmpleadoEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EstadoEmpleadoEnum parse(int id) {
            EstadoEmpleadoEnum status = null; //Default
            for (EstadoEmpleadoEnum item : EstadoEmpleadoEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;

        }

    }

    public BigDecimal obtenerVentasAnuales() {
        Random randomGenerator = new Random();

        // Genero un numero rando hasta 10000
        double venta = randomGenerator.nextDouble() * 10000 + 1;
        // redondeo en 2 decimales el random truncando
        venta = ((long) (venta * 100)) / 100d;

        return new BigDecimal(venta);
    }
}
