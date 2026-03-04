package ufck.eventflow.eventflow.service;

// Haven't you changed your opinion about Rust by any chance? I have tbh
// I now consider closing the project quicker via Java is a better way
import org.springframework.stereotype.Service;
import java.util.Random;

// But c'mon, Java is still not an option :(
@Service
public class PaymentMockService {
    // Let's be honest, I'm not going to actually implement payment stuff
    public boolean processPayment(String userId, double amount) {
        return new Random().nextDouble() < 0.95;
    }
}