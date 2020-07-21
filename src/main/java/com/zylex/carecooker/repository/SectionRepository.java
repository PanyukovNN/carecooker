package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.SectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SectionRepository extends JpaRepository<Section, Long> {

    Section findByName(String name);

    List<Section> findBySectionType(SectionType sectionType);
}
