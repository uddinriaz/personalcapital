package com.example.personalcapital;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Portfolio gives future investment portfolio value based on given current investment amount and no. of years in future to consider.
 * 
 * The future portfolio value is based on normal distribution (Gaussian distribution) with configurable mean and standard deviation.
 * Other configurable options are
 *  simulations: no. of simulations to run for each future value predicted. Average of these simulations is the predicted future value.
 *  inflationRate: YoY inflation rate.
 *  futureNoOfYears: no. of years in future.
 *  
 * 
 * @author riazuddin
 *
 */
public class Portfolio{
    
    private int simulations = 10000;
    private double inflationRate = 3.5;
    private double mean;
    private double sd;
    private List<Double> futureValues = new ArrayList<>(1);
    
    public Portfolio(double mean, double standardDeviation){
        this.mean = mean;
        this.sd = standardDeviation;
    }
    
    /**
     * Computes the future portfolio value based on given investment amount and future years. 
     * Default no. of simulations is 10000 and no. of years in future is 20.
     * 
     * Algorithm
     * 1. Generate a portfolio future value based on investment amount, mean and sd.
     * 2. Adjust the amount based on inflation. By default, generated future values is adjusted for 20 years with YoY inflation of 3.5%
     * 3. Average of all generated future values is predicted future value of portfolio. 
     * 
     * @param mean Current Mean percentage of the portfolio 
     * @param sd    Current standard deviation percentage of the portfolio
     * @return  Calculated future value of the portfolio based on given no. of simulations and no. of years in future.
     * 
     * @throws Exception
     */
    public double getFuturePortfolioValue(int investment, int noOfYears) throws Exception{
        
        if(noOfYears < 1)
            throw makeException("Invalid future years. Enter value greater than 1");
 
        if(investment < 1)
            throw makeException("Invalid investment amount. Enter value greater than 1");
        
        if(mean < 0)
            throw makeException("Invalid mean.");

        if(sd < 1)
            throw makeException("Invalid standard deviation");

        if(simulations < 1)
            throw makeException("Invalid simulation. Enter value greater than 1");
        
        if(inflationRate < 1)
            throw makeException("Invalid inflation rate. Enter value greater than 1");
        
        futureValues.clear();
        double simulationSum = 0f;
        Random r = new Random();
        for(int i = 0; i < simulations; i++){
            //get a future value based on Gaussian distribution.
            double d = investment * (1 + (((r.nextGaussian() * sd) + mean)/100));
            
            futureValues.add(d);
            simulationSum += adjustInflation(d);
        }
        
        Collections.sort(futureValues);
        return investment + (simulationSum/simulations);
    }

    /**
     * Generated future values are sorted and the 90th percentile is returned.
     * 
     * @return
     * @throws Exception
     */
    public double getBestCase() throws Exception {
        return getPercentile(90);
    }
    
    /**
     * Generated future values are sorted ascending order and 10th percentile is returned.
     * 
     * @return
     * @throws Exception
     */
    public double getWorstCase() throws Exception{
        return getPercentile(10);
    }

    private double getPercentile(int n) throws Exception{
        if(futureValues.isEmpty())
            throw makeException("No simulations run yet");
        
        int idx = Math.round(n * simulations/100);
        return futureValues.get(idx);
    }
    
    private double adjustInflation(double portfolioValue){
        for(int i = 0; i < 20; i++)
            portfolioValue += (portfolioValue * inflationRate)/100;
        
        return portfolioValue;
    }

    public int getSimulations() {
        return simulations;
    }

    public void setSimulations(int simulations) {
        this.simulations = simulations;
    }

    public double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(double inflationRate) {
        this.inflationRate = inflationRate;
    }

    private static Exception makeException(String msg){
        return new Exception(msg);
    }
}
