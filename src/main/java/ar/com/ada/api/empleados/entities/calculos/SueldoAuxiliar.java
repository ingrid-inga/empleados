package ar.com.ada.api.empleados.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.empleados.entities.Empleado;

public class SueldoAuxiliar implements ISueldoCalculator {

    @Override
    public BigDecimal calcularSueldo(Empleado empleado) {
        return empleado.getCategoria().getSueldoBase();
    }

}