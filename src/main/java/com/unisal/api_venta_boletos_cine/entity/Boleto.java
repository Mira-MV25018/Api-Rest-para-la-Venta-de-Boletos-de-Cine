@Getter @Setter @Entity
@Table(name = "boleto",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_funcion", "numero_asiento"}))
public class Boleto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleto")
    private Long idBoleto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_funcion")
    private Funcion funcion;

    @Column(name = "numero_asiento")
    private String numeroAsiento;

    @Column(name = "precio_pagado")
    private Double precioPagado;
}