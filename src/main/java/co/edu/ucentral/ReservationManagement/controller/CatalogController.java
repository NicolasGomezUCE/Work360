package co.edu.ucentral.ReservationManagement.controller;

import co.edu.ucentral.ReservationManagement.model.dto.SpaceAvailable;
import co.edu.ucentral.ReservationManagement.model.entity.Site;
import co.edu.ucentral.ReservationManagement.model.entity.Space;
import co.edu.ucentral.ReservationManagement.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    // GET /api/sites
    @GetMapping("/sites")
    public ResponseEntity<List<Site>> getSites() {
        List<Site> sites = catalogService.getAllSites();
        return ResponseEntity.ok(sites); // 200
    }

    // GET /api/sites/{id}/spaces?type=&date=&from=&to=
    @GetMapping("/sites/{siteId}/spaces")
    public ResponseEntity<List<SpaceAvailable>> getAvailableSpaces(
            @PathVariable Long siteId,
            @RequestParam(required = false) Space.SpaceType type,
            @RequestParam LocalDateTime date,
            @RequestParam String from, // Asumimos que 'from' y 'to' son horas (ej: "09:00:00")
            @RequestParam String to) {

        // Combinar fecha y hora para obtener LocalDateTime
        LocalDateTime startDateTime = LocalDateTime.of(date.toLocalDate(), java.time.LocalTime.parse(from));
        LocalDateTime endDateTime = LocalDateTime.of(date.toLocalDate(), java.time.LocalTime.parse(to));

        // Validación básica
        if (startDateTime.isAfter(endDateTime) || startDateTime.isEqual(endDateTime)) {
            return ResponseEntity.badRequest().build();
        }

        List<SpaceAvailable> spaces = catalogService.getSpacesAndAvailability(
                siteId, type, startDateTime, endDateTime);

        return ResponseEntity.ok(spaces); // 200
    }
}