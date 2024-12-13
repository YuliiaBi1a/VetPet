package com.vetpet.VetPet;

import com.vetpet.VetPet.controllers.StatisticsController;
import com.vetpet.VetPet.repository.AppointmentRepository;
import com.vetpet.VetPet.repository.GuardianRepository;
import com.vetpet.VetPet.repository.PetRepository;
import com.vetpet.VetPet.services.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
class StatisticsControllerTest {

    private StatisticsService statisticsService;
    private StatisticsController statisticsController;

    @BeforeEach
    void setUp() {
        // Mock del servicio StatisticsService
        statisticsService = mock(StatisticsService.class);
        // Inicializar el controlador con el servicio mockeado
        statisticsController = new StatisticsController(statisticsService);
    }

    @Test
    void testGetGlobalStatistics() {
        // Datos simulados para la prueba
        Map<String, Integer> mockStatistics = new HashMap<>();
        mockStatistics.put("users", 150);
        mockStatistics.put("orders", 75);

        // Configuración del comportamiento del mock
        when(statisticsService.getGlobalStatistics()).thenReturn(mockStatistics);

        // Llamada al método del controlador
        ResponseEntity<Map<String, Integer>> response = statisticsController.getGlobalStatistics();

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue()); // Verificar el código de estado HTTP
        assertEquals(mockStatistics, response.getBody()); // Verificar que el cuerpo de la respuesta es correcto

        // Verificar que el servicio se llamó una vez
        Mockito.verify(statisticsService, Mockito.times(1)).getGlobalStatistics();
    }
    @Test
    void testStatisticsServiceGetGlobalStatistics() {
        // Mock de los repositorios
        AppointmentRepository appointmentRepository = mock(AppointmentRepository.class);
        PetRepository petRepository = mock(PetRepository.class);
        GuardianRepository guardianRepository = mock(GuardianRepository.class);

        // Configurar el comportamiento de los mocks
        when(appointmentRepository.count()).thenReturn(50L);
        when(petRepository.count()).thenReturn(200L);
        when(guardianRepository.count()).thenReturn(100L);

        // Crear instancia del servicio con los repositorios mockeados
        StatisticsService statisticsService = new StatisticsService(appointmentRepository, petRepository, guardianRepository);

        // Llamar al método a probar
        Map<String, Integer> statistics = statisticsService.getGlobalStatistics();

        // Verificaciones
        assertEquals(50, statistics.get("appointments"));
        assertEquals(200, statistics.get("pets"));
        assertEquals(100, statistics.get("guardians"));

        // Verificar que los repositorios fueron llamados una vez
        Mockito.verify(appointmentRepository, Mockito.times(1)).count();
        Mockito.verify(petRepository, Mockito.times(1)).count();
        Mockito.verify(guardianRepository, Mockito.times(1)).count();
    }
}

