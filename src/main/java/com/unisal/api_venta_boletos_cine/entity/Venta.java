@Getter @Setter @Entity @Table(name = "venta")
public class Venta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_venta")
    private Long idVenta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta = LocalDateTime.now();

    @Column(name = "total_pagado")
    private Double totalPagado;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Boleto> boletos = new ArrayList<>();
}