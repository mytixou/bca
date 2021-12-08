package fr.tixou.bca.repository;

import fr.tixou.bca.domain.ConsommationPch;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConsommationPch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsommationPchRepository extends JpaRepository<ConsommationPch, Long> {}
