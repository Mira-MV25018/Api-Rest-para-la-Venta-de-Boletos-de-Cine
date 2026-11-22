package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.VentaDTO;
import com.unisal.api_venta_boletos_cine.entity.Cliente;
import com.unisal.api_venta_boletos_cine.entity.Venta;
import com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException;
import com.unisal.api_venta_boletos_cine.repository.ClienteRepository;
import com.unisal.api_venta_boletos_cine.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;

    public List<VentaDTO> obtenerTodas() {
        return ventaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public VentaDTO obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        return convertirADTO(venta);
    }

    public VentaDTO crearVenta(VentaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + dto.getIdCliente()));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFechaVenta(dto.getFechaVenta() != null ? dto.getFechaVenta() : LocalDateTime.now());
        venta.setTotalPagado(dto.getTotalPagado());

        return convertirADTO(ventaRepository.save(venta));
    }

    public VentaDTO actualizarVenta(Long id, VentaDTO dto) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + dto.getIdCliente()));

        venta.setCliente(cliente);
        if (dto.getFechaVenta() != null) {
            venta.setFechaVenta(dto.getFechaVenta());
        }
        venta.setTotalPagado(dto.getTotalPagado());

        return convertirADTO(ventaRepository.save(venta));
    }

    public void eliminarVenta(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));
        ventaRepository.delete(venta);
    }

    private VentaDTO convertirADTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setIdVenta(venta.getIdVenta());
        dto.setIdCliente(venta.getCliente().getIdCliente());
        dto.setFechaVenta(venta.getFechaVenta());
        dto.setTotalPagado(venta.getTotalPagado());
        return dto;
    }
}