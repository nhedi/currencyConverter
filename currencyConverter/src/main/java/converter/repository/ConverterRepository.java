package converter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import converter.domain.Conversion;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ConverterRepository extends JpaRepository<Conversion, String> {

    @Modifying
    @Query(value = "UPDATE CONVERSION SET CON_COUNT = ? WHERE CON_FROMTO= ?", nativeQuery = true)
    void setNewCount(int count, String fromto);

    Conversion findConversionByFromto(String fromto);

    @Modifying
    @Query(value = "UPDATE CONVERSION SET CON_RATE = ? WHERE CON_FROMTO= ?", nativeQuery = true)
    void setNewRate(double rate, String fromto);

    @Query(value = "SELECT SUM(CON_COUNT) FROM CONVERSION", nativeQuery = true)
    int countSum();
}
