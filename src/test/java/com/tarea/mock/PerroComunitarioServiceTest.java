package com.tarea.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.tarea.mock.entidades.Perro;
import com.tarea.mock.excepciones.PerroNoEncontradoException;
import com.tarea.mock.repositorios.PerroRepository;
import com.tarea.mock.servicios.PerroComunitarioService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PerroComunitarioServiceTest {

    PerroRepository mockRepository;
    PerroComunitarioService service;

    @BeforeEach
    public void inicializarPrueba(){
        // Mock del repositorio
        mockRepository = Mockito.mock(PerroRepository.class);
        // Servicio a probar
        service = new PerroComunitarioService(mockRepository);
    }

    @Test
    public void deberiaDevolverPerroCuandoElPerroExiste() {        
        // Configuracion del mock
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

        // Ejecucion que devuelve perro
        Perro resultado = service.obtenerPerroPorNombre("Fido");

        // Verificacion
        assertEquals("Fido", resultado.getNombre());
        assertEquals(4, resultado.getEdad());
        verify(mockRepository, times(1)).buscarPorNombre("Fido");        
    }
    
    @Test
    public void deberiaLanzarPerroNoEncontradoExceptioCuandoElPerroNoExiste() {  
        // Configuracion del mock
        when(mockRepository.buscarPorNombre("Rex")).thenReturn(null);

        // Ejecucion que lanza excepción
        assertThrows(PerroNoEncontradoException.class, () -> {
            service.obtenerPerroPorNombre("Rex");      
        });  

        // Verificacion
        verify(mockRepository, times(1)).buscarPorNombre("Rex");
    }
    
    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsNull() {
        // Ejecucion que lanza excepción
        assertThrows(IllegalArgumentException.class, () -> {
            service.obtenerPerroPorNombre(null);
        });

        // Verificacion
        verifyNoInteractions(mockRepository);
    }

    @Test
    public void deberiaLanzarIllegalArgumentExceptionCuandoElNombreEsVacio() {
        // Ejecucion que lanza excepcion
        assertThrows(IllegalArgumentException.class, () -> {
            service.obtenerPerroPorNombre("");
        });

        // Verificacion
        verifyNoInteractions(mockRepository);
    }

    @Test
    public void deberiaConsultarRepositorioUnaSolaVezCuandoElPerroExiste() {
        // Configuracion del mock
        Perro perroEsperado = new Perro("Fido", 4);
        when(mockRepository.buscarPorNombre("Fido")).thenReturn(perroEsperado);

        // Ejecucion
        service.obtenerPerroPorNombre("Fido");

        // Verificacion
        verify(mockRepository, times(1)).buscarPorNombre("Fido");
    }
}
