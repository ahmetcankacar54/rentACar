package kodlama.io.rentACar.adapters;

import kodlama.io.rentACar.Common.constants.Messages;
import kodlama.io.rentACar.business.abstracts.PosService;
import kodlama.io.rentACar.core.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FakePosServiceAdapter implements PosService {
    @Override
    public void pay() {
        boolean isPaymentSuccessful = new Random().nextBoolean();
        if (!isPaymentSuccessful) throw new BusinessException(Messages.Payment.Failed);
    }
}
