package ar.com.ada.api.empleados.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.empleados.entities.Empleado;

public interface ISueldoCalculator {
    BigDecimal calcularSueldo(Empleado empleado);
    
}
