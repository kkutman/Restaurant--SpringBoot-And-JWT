package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.ChequeResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;

import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {


}