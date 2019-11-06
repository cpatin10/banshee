package com.test.banshee.web;

import com.test.banshee.dto.visit.CreateVisitDTO;
import com.test.banshee.dto.visit.UpdateVisitDTO;
import com.test.banshee.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@Validated
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitResource {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<Void> createVisit(@Valid @RequestBody final CreateVisitDTO createVisitDTO) {
        final long visitId = visitService.createVisit(createVisitDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(visitId)
                .toUri();
        return ResponseEntity.created(uri).build();
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
