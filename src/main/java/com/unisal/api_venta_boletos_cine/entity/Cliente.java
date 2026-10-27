@Getter @Setter @Entity @Table(name = "cliente")
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;
    private String nombre;
    private String apellido;
    @Column(unique = true, nullable = false)
    private String correo;
    private String telefono;
}