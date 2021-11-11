package net.bigpoint.assessment.gasstation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;

public final class GasStationImplementation implements GasStation {

    Collection<GasPump> GasPumps = new ArrayList<GasPump>();
    List<Iterator<GasPump>> UsedGasPumps = new ArrayList<Iterator<GasPump>>();
    Map<GasType, Double> Prices = new HashMap<GasType, Double>();

    double Revenue = 0;
    int NumberOfSales = 0;
    int NumberOfCancellationsNoGas = 0;
    int NumberOfCancellationsTooExpensive = 0;

    /**
     * Add a gas pump to this station. This is used to set up this station.
     * 
     * @param pump the gas pump
     */
    public void addGasPump(GasPump pump) {
        double standartPrice = 1.5;
        // It is unclear if a gas station always has all gas types available
        Prices.putIfAbsent(pump.getGasType(), standartPrice);

        GasPumps.add(pump);
    }

    /**
     * Get all gas pumps that are currently associated with this gas station.
     * 
     * Modifying the resulting collection should not affect this gas station.
     * 
     * @return A collection of all gas pumps.
     */
    public Collection<GasPump> getGasPumps() {
        // Create new collection so it can be manipulated
        Collection<GasPump> gasPumps = new HashSet<>();
        gasPumps.addAll(GasPumps);

        return gasPumps;
    }

    /**
     * Simulates a customer wanting to buy a specific amount of gas.
     * 
     * @param type             The type of gas the customer wants to buy
     * @param amountInLiters   The amount of gas the customer wants to buy. Nothing
     *                         less than this amount is acceptable!
     * @param maxPricePerLiter The maximum price the customer is willing to pay per
     *                         liter
     * @return the price the customer has to pay for this transaction
     * @throws NotEnoughGasException    Should be thrown in case not enough gas of
     *                                  this type can be provided by any single
     *                                  {@link GasPump}.
     * @throws GasTooExpensiveException Should be thrown if gas is not sold at the
     *                                  requested price (or any lower price)
     */
    public double buyGas(GasType type, double amountInLiters, double maxPricePerLiter)
            throws NotEnoughGasException, GasTooExpensiveException {

        Iterator<GasPump> iterator = GasPumps.iterator();

        // Will throw exception when done with every Gas Pump
        Boolean hasNoGas = false;

        while (iterator.hasNext()) {

            // Search for unused gas pump
            GasPump gasPump = iterator.next();

            if (gasPump.getGasType() == type) {

                // iterators of used gas pumps are contained in used gas pumps, so the program knows which are in use
                if (!UsedGasPumps.contains(iterator)) {

                    hasNoGas = false;
                    if (maxPricePerLiter > Prices.get(type)) {

                        ++NumberOfCancellationsTooExpensive;
                        throw new GasTooExpensiveException();
                        // No need to check any other gas pumps because it always costs the same
                    }
                    
                    if (amountInLiters > gasPump.getRemainingAmount()) {
                        ++NumberOfCancellationsNoGas;
                        hasNoGas = true;

                    } else {

                        // mark gas pump as used
                        // would use an atomic instead in C++
                        UsedGasPumps.add(iterator);

                        gasPump.pumpGas(amountInLiters);
                        Revenue += Prices.get(type) * amountInLiters;
                        ++NumberOfSales;

                        // Normaly I would let GasPump return a bool when done.
                        ///////////////////////////////////////////////////////
                        try {
                            Thread.sleep((long) (amountInLiters * 100));
                        } catch (InterruptedException e) {
                            // ignored
                        }
                        ///////////////////////////////////////////////////////

                        // Free pump from usage
                        UsedGasPumps.remove(iterator);
                        return Prices.get(type) * amountInLiters;
                    }
                }
            }
        }
        if (hasNoGas) {
            throw new NotEnoughGasException();
        }
        // when no transaction has been done because there is no gas pump of that type
        return 0;
    }

    /**
     * @return the total revenue generated
     */
    public double getRevenue() {
        return Revenue;
    }

    /**
     * Returns the number of successful sales. This should not include cancelled
     * sales.
     * 
     * @return the number of sales that were successful
     */
    public int getNumberOfSales() {
        return NumberOfSales;
    }

    /**
     * @return the number of cancelled transactions due to not enough gas being
     *         available
     */
    public int getNumberOfCancellationsNoGas() {
        // return number of unsuccessful sales due to not enough gas
        return NumberOfCancellationsNoGas;
    }

    /**
     * Returns the number of cancelled transactions due to the gas being more
     * expensive than what the customer wanted to pay
     * 
     * @return the number of cancelled transactions
     */
    public int getNumberOfCancellationsTooExpensive() {
        // return number of unsuccessful sales due to high price
        return NumberOfCancellationsTooExpensive;
    }

    /**
     * Get the price for a specific type of gas
     * 
     * @param type the type of gas
     * @return the price per liter for this type of gas
     */
    public double getPrice(GasType type) {
        // One can not be sure that a price exists
        if (Prices.get(type) != null) {
            return Prices.get(type);
        }

        // since we run a business, the gas station will never pay for people to get
        // their gas here
        return -1;
    }

    /**
     * Set a new price for a specific type of gas
     * 
     * @param type  the type of gas
     * @param price the new price per liter for this type of gas
     */
    public void setPrice(GasType type, double price) {
        if (Prices.putIfAbsent(type, price) != null) {
            Prices.replace(type, price);
        }
    }
}