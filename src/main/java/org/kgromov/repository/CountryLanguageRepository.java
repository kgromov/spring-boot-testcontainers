package org.kgromov.repository;

import org.kgromov.domain.CountryLanguage;
import org.kgromov.domain.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryLanguageRepository extends JpaRepository<CountryLanguage, LanguageCode> {
}
