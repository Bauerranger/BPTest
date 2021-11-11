package net.bigpoint.assessment.gasstation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;

public final class GasStationImplementation implements GasStation {

    Collection<GasPump> GasPumps = new HashSet<GasPump>();
    List<Integer> UsedGasPumps = new ArrayList<Integer>();
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
        // It is unclear if a gas station always has all gas types available
        if (Prices.size() < GasType.values().length) {
            // add gas type to map
            // set standart price
            Prices.put(pump.getGasType(), 1.5);
        }

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
        for (GasPump gasPump : GasPumps) {
            gasPumps.add(gasPump);
        }

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
        // If type of gas pump
        // Search for unused gas pump
        // if unused gas pump
        // If amount of litres > litres of gas type
        if (amountInLiters < 0) {
            // Add to failed because of no more litres
            // Throw exception
            throw new NotEnoughGasException();
        }
        // If maxPricePerLiter > GasType price
        if (maxPricePerLiter < 0) {
            // Add to failed because of price
            // Throw exception
            throw new GasTooExpensiveException();
        }
        // mark gas pump as used
        // gaspump liters - amountinliters
        // total revenue + current price of gas
        // add to number of successful sales
        // else throw exception no gas pump free
        // else
        // Throw exception no pump for that gas type
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
        if(Prices.get(type) != null){
            return Prices.get(type);
        }
        
        // since we run a business, the gas station will never pay for people to get their gas here
        return -1;
    }

    /**
     * Set a new price for a specific type of gas
     * 
     * @param type  the type of gas
     * @param price the new price per liter for this type of gas
     */
    public void setPrice(GasType type, double price) {
        if(Prices.putIfAbsent(type, price) != null){
            Prices.replace(type, price);
        }
    }
}