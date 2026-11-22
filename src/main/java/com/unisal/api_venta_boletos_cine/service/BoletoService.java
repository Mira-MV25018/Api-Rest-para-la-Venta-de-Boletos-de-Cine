package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.BoletoDTO;
import com.unisal.api_venta_boletos_cine.entity.Boleto;
import com.unisal.api_venta_boletos_cine.entity.Funcion;
import com.unisal.api_venta_boletos_cine.entity.Venta;
import com.unisal.api_venta_boletos_cine.exception.ConflictoException;
import com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException;
import com.unisal.api_venta_boletos_cine.repository.BoletoRepository;
import com.unisal.api_venta_boletos_cine.repository.FuncionRepository;
import com.unisal.api_venta_boletos_cine.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final VentaRepository ventaRepository;
    private final FuncionRepository funcionRepository;

    public List<BoletoDTO> obtenerTodos() {
        return boletoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public BoletoDTO obtenerPorId(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleto no encontrado con ID: " + id));
        return convertirADTO(boleto);
    }

    public BoletoDTO crearBoleto(BoletoDTO dto) {
        Venta venta = ventaRepository.findById(dto.getIdVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + dto.getIdVenta()));

        Funcion funcion = funcionRepository.findById(dto.getIdFuncion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Función no encontrada con ID: " + dto.getIdFuncion()));

        if (boletoRepository.existsByFuncionAndNumeroAsiento(funcion, dto.getNumeroAsiento())) {
            throw new ConflictoException("El asiento " + dto.getNumeroAsiento() + " ya está ocupado para esta función.");
        }

        Boleto boleto = new Boleto();
        boleto.setVenta(venta);
        boleto.setFuncion(funcion);
        boleto.setNumeroAsiento(dto.getNumeroAsiento());
        boleto.setPrecioPagado(dto.getPrecioPagado());

        return convertirADTO(boletoRepository.save(boleto));
    }

    public BoletoDTO actualizarBoleto(Long id, BoletoDTO dto) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleto no encontrado con ID: " + id));

        Venta venta = ventaRepository.findById(dto.getIdVenta())
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + dto.getIdVenta()));

        Funcion funcion = funcionRepository.findById(dto.getIdFuncion())
                .orElseThrow(() -> new RecursoNoEncontradoException("Función no encontrada con ID: " + dto.getIdFuncion()));

        boolean cambioFuncionOAsiento = !boleto.getFuncion().getIdFuncion().equals(funcion.getIdFuncion())
                || !boleto.getNumeroAsiento().equals(dto.getNumeroAsiento());

        if (cambioFuncionOAsiento && boletoRepository.existsByFuncionAndNumeroAsiento(funcion, dto.getNumeroAsiento())) {
            throw new ConflictoException("El asiento " + dto.getNumeroAsiento() + " ya está ocupado para esta función.");
        }

        boleto.setVenta(venta);
        boleto.setFuncion(funcion);
        boleto.setNumeroAsiento(dto.getNumeroAsiento());
        boleto.setPrecioPagado(dto.getPrecioPagado());

        return convertirADTO(boletoRepository.save(boleto));
    }

    public void eliminarBoleto(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Boleto no encontrado con ID: " + id));
        boletoRepository.delete(boleto);
    }

    private BoletoDTO convertirADTO(Boleto boleto) {
        BoletoDTO dto = new BoletoDTO();
        dto.setIdBoleto(boleto.getIdBoleto());
        dto.setIdVenta(boleto.getVenta().getIdVenta());
        dto.setIdFuncion(boleto.getFuncion().getIdFuncion());
        dto.setNumeroAsiento(boleto.getNumeroAsiento());
        dto.setPrecioPagado(boleto.getPrecioPagado());
        return dto;
    }
}