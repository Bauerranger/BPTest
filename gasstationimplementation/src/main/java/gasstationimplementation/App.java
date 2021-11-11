package gasstationimplementation;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GasStationImplementation station = new GasStationImplementation();
        GasPump gasPump = new GasPump(GasType.REGULAR, 100.);
        station.addGasPump(gasPump);
        station.setPrice(GasType.REGULAR, 1.);
        System.out.println( station.buyGas(GasType.REGULAR, 10., 1. ) );
    }
}
