package logistics;
import transport.Transport;
import transport.Ship;

public class SeaLogistics extends logistics.Logistics {
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}