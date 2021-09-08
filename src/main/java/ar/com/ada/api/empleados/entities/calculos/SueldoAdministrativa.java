package ar.com.ada.api.empleados.entities.calculos;

import java.math.BigDecimal;

import ar.com.ada.api.empleados.entities.Empleado;

public class SueldoAdministrativa implements ISueldoCalculator {

    @Override
    public BigDecimal calcularSueldo(Empleado empleado) {
        BigDecimal sueldoBase = empleado.getCategoria().getSueldoBase();
        if (empleado.getSueldo().compareTo(sueldoBase) == -1)
            return sueldoBase;
        return empleado.getSueldo();
    }

}