package net.bigpoint.assessment.gasstation;

import java.util.Collection;
import java.util.Map;

import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;

public final class GasStationImplementation implements GasStation {

    Collection<GasPump> GasPumps = null;
    Map<GasType, Double> Prices;

    /**
     * Add a gas pump to this station. This is used to set up this station.
     * 
     * @param pump the gas pump
     */
    public void addGasPump(GasPump pump) {
        // Check if number of gas types is same as gas types in map
        // If number of gas types != map
        // add gas type to map
        // set standart price
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
        // Create new collection on heap
        // Add entries from the member variable Gaspumps
        // Return new collection
        return GasPumps;
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
                // gaspump liters -  amountinliters
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
        // return revenue
        return 0;
    }

    /**
     * Returns the number of successful sales. This should not include cancelled
     * sales.
     * 
     * @return the number of sales that were successful
     */
    public int getNumberOfSales() {
        // return number of sales
        return 0;
    }

    /**
     * @return the number of cancelled transactions due to not enough gas being
     *         available
     */
    public int getNumberOfCancellationsNoGas() {
        // return number of unsuccessful sales due to not enough gas
        return 0;
    }

    /**
     * Returns the number of cancelled transactions due to the gas being more
     * expensive than what the customer wanted to pay
     * 
     * @return the number of cancelled transactions
     */
    public int getNumberOfCancellationsTooExpensive() {
        // return number of unsuccessful sales due to high price
        return 0;
    }

    /**
     * Get the price for a specific type of gas
     * 
     * @param type the type of gas
     * @return the price per liter for this type of gas
     */
    public double getPrice(GasType type) {
        // Check if key is there 
        // return value
        //if not throw exception.
        return 0;
    }

    /**
     * Set a new price for a specific type of gas
     * 
     * @param type  the type of gas
     * @param price the new price per liter for this type of gas
     */
    public void setPrice(GasType type, double price) {
        
        switch (type) {
            case REGULAR:  
            // Check if key is there 
            // change value.
            // If there is no pump for this type
            // throw exception
                     break;
            case DIESEL:
                     break;
            case SUPER:
                     break;
            default:
                     break;
        }
    }
}