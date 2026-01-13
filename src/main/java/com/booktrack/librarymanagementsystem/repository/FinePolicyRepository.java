package com.booktrack.librarymanagementsystem.repository;

import com.booktrack.librarymanagementsystem.entity.FinePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinePolicyRepository extends JpaRepository<FinePolicy, UUID> {
    Optional<FinePolicy> findByCategory(String category);
}
