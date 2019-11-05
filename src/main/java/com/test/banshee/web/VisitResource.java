package com.test.banshee.web;

import com.test.banshee.service.VisitService;
import com.test.banshee.dto.visit.CreateVisitDTO;
import com.test.banshee.dto.visit.UpdateVisitDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Validated
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitResource {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<Void> createVisit(@Valid @RequestBody final CreateVisitDTO createVisitDTO)
            throws URISyntaxException {
        final long visitId = visitService.createVisit(createVisitDTO);
        return ResponseEntity.created(new URI("/api/customers/" + visitId)).build();
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<Void> updateVisit(
            @PathVariable("visitId") final long visitId,
            @Valid @RequestBody final UpdateVisitDTO updateVisitDTO) {
        visitService.updateVisit(visitId, updateVisitDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<Void> deleteVisit(@PathVariable("visitId") final long visitId) {
        visitService.deleteVisit(visitId);
        return ResponseEntity.noContent().build();
    }

}
