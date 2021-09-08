package ar.com.ada.api.empleados.entities.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import ar.com.ada.api.empleados.entities.Empleado;

public class SueldoVentas implements ISueldoCalculator {

    @Override
    public BigDecimal calcularSueldo(Empleado empleada) {

        BigDecimal ventasAnuales = empleada.obtenerVentasAnuales();

        return empleada.getCategoria().getSueldoBase().add(ventasAnuales.multiply(new BigDecimal(0.1))).setScale(2,
                RoundingMode.HALF_EVEN);
    }

}
