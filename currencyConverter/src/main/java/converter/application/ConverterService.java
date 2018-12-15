package converter.application;

import converter.repository.ConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import converter.domain.ConversionDTO;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class ConverterService {
    @Autowired
    private ConverterRepository converterRepo;

    public ConversionDTO findConversion(String fromto) {
        System.out.println("converter service, c = " + fromto);
        return converterRepo.findConversionByFromto(fromto);
    }

    public void setNewRate(double value, String fromto) {
        System.out.println("ConvService setNewRate " + fromto + " " +value+"");
        converterRepo.setNewRate(value, fromto);
    }

    public void setNewCount(int count, String fromto) {
        converterRepo.setNewCount(count, fromto);
    }

    public int countSum() {
        return converterRepo.countSum();
    }
}
