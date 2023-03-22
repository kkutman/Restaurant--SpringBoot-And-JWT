package peaksoft.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.dto.response.MenuItemResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;

import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Modifying
    @Transactional
    @Query("delete from Cheque c where c.id = ?1")
    void delete(Long id);
}