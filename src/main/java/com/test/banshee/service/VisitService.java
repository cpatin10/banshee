package com.test.banshee.service;

import com.test.banshee.dto.visit.CreateVisitDTO;
import com.test.banshee.dto.visit.UpdateVisitDTO;

public interface VisitService {

    Long createVisit(CreateVisitDTO createVisitDTO);

    void updateVisit(long visitId, UpdateVisitDTO updateVisitDTO);

    void deleteVisit(long visitId);

}
