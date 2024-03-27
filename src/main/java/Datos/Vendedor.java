/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

/**
 *
 * @author User
 */
public class Vendedor {

    private int id;
    private String nombre;
    private double sueldoBasico;
    private String mes;
    private int faltas;
    private int horasExtras;

    public Vendedor() {
    }

    //constructor que nos ayudar a eliminar 
    public Vendedor(int id) {
        this.id = id;
    }

    //constrctor que nos ayudar a modificar
    public Vendedor(String nombre, double sueldoBasico, String mes, int faltas, int horasExtras) {
        this.nombre = nombre;
        this.sueldoBasico = sueldoBasico;
        this.mes = mes;
        this.faltas = faltas;
        this.horasExtras = horasExtras;
    }

    //constructor que nos ayudara a eliminar
    public Vendedor(int id, String nombre, double sueldoBasico, String mes, int faltas, int horasExtras) {
        this.id = id;
        this.nombre = nombre;
        this.sueldoBasico = sueldoBasico;
        this.mes = mes;
        this.faltas = faltas;
        this.horasExtras = horasExtras;
    }

    public int getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(int horasExtras) {
        this.horasExtras = horasExtras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSueldoBasico() {
        return sueldoBasico;
    }

    public void setSueldoBasico(double sueldoBasico) {
        this.sueldoBasico = sueldoBasico;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    //metodos propios
    // fila[4] = (double) Math.round(articulo.Precio_Bruto() * 100d) / 100d;
    public double calcularAFP() {
        return (double) Math.round((this.sueldoBasico * 0.10) * 100d) / 100d;
    }

    public double calcularEssalud() {
        return (double) Math.round((this.sueldoBasico * 0.05) * 100d) / 100d;

    }

    public double totadescuento() {
        double total1 = (faltas * 0.05);
        double total2 = calcularAFP() + calcularEssalud();
        //return (double) Math.round((total1 + total2) * 100d) / 100d;
        return total1 + total2;
    }

    public double pagoHorasExtras() {
        return (double) Math.round((this.horasExtras * 0.02) * 100d) / 100d;
    }

    public double sueldoNeto() {
        return (double) Math.round((this.sueldoBasico - this.totadescuento()
                + this.pagoHorasExtras()) * 100d) / 100d;
    }
}
        