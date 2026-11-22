package com.unisal.api_venta_boletos_cine.service;

import com.unisal.api_venta_boletos_cine.dto.ClienteDTO;
import com.unisal.api_venta_boletos_cine.entity.Cliente;
import com.unisal.api_venta_boletos_cine.exception.ConflictoException;
import com.unisal.api_venta_boletos_cine.exception.RecursoNoEncontradoException;
import com.unisal.api_venta_boletos_cine.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteDTO> obtenerTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ClienteDTO obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));
        return convertirADTO(cliente);
    }

    public ClienteDTO crearCliente(ClienteDTO dto) {
        if (clienteRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictoException("Ya existe un cliente registrado con el correo: " + dto.getCorreo());
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        return convertirADTO(clienteRepository.save(cliente));
    }

    public ClienteDTO actualizarCliente(Long id, ClienteDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));

        if (!cliente.getCorreo().equals(dto.getCorreo()) && clienteRepository.existsByCorreo(dto.getCorreo())) {
            throw new ConflictoException("El correo ya está en uso por otro cliente: " + dto.getCorreo());
        }

        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());
        return convertirADTO(clienteRepository.save(cliente));
    }

    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con ID: " + id));
        clienteRepository.delete(cliente);
    }

    private ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setCorreo(cliente.getCorreo());
        dto.setTelefono(cliente.getTelefono());
        return dto;
    }
}