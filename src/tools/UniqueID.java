package tools;
import java.util.UUID;

//chance of two random UUIDs colliding is about 10^-37
public class UniqueID {
    public static String generateID () {
        return UUID.randomUUID().toString();
    }
}
