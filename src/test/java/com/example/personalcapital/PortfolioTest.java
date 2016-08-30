package com.example.personalcapital;

import static org.junit.Assert.*;

import org.junit.Test;

public class PortfolioTest {
    
    @Test(expected=Exception.class)
    public void invalidPortfolio() throws Exception{
        Portfolio p = new Portfolio(0, 0);
        p.getFuturePortfolioValue(100000, 20);
        fail("Should not have reached here");
    }
    
    @Test(expected=Exception.class)
    public void invalidInvestment() throws Exception{
        Portfolio p = new Portfolio(0, 0);
        p.getFuturePortfolioValue(-1, 20);
        fail("Should not have reached here");
    }
    
    @Test(expected=Exception.class)
    public void invalidYears() throws Exception{
        Portfolio p = new Portfolio(0, 0);
        p.getFuturePortfolioValue(100000, 0);
        fail("Should not have reached here");
    }
    
    @Test(expected=Exception.class)
    public void invalidSimulations() throws Exception{
        Portfolio p = new Portfolio(0, 0);
        p.setSimulations(-30000);
        p.getFuturePortfolioValue(100000, 0);
        fail("Should not have reached here");
    }

    @Test(expected=Exception.class)
    public void invalidInflationRate() throws Exception{
        Portfolio p = new Portfolio(0, 0);
        p.setInflationRate(-6.7);
        p.getFuturePortfolioValue(100000, 0);
        fail("Should not have reached here");
    }

    @Test
    public void getAggressivePortfolioValue() throws Exception {
        Portfolio p = new Portfolio(9.4324, 15.6785);
        double value = p.getFuturePortfolioValue(100000, 20);
        assertTrue(value > 0);
    }
    
    @Test
    public void getConservativePortfolioValue() throws Exception{
        Portfolio p = new Portfolio(6.4324, 7.6785);
        double value = p.getFuturePortfolioValue(100000, 20);
        assertTrue(value > 0);
    }

    @Test(expected=Exception.class)
    public void invalidBestCase() throws Exception{
        Portfolio p = new Portfolio(6.4324, 7.6785);
        p.getBestCase();
        fail("Should not have reached here");
    }

    @Test(expected=Exception.class)
    public void invalidWorstCase() throws Exception{
        Portfolio p = new Portfolio(6.4324, 7.6785);
        p.getWorstCase();
        fail("Should not have reached here");
    }

    @Test
    public void testBestWorstCase_Aggressive() throws Exception{
        Portfolio p = new Portfolio(9.4324, 15.6785);
        p.getFuturePortfolioValue(100000, 20);
        assertTrue(p.getBestCase() > p.getWorstCase());
    }

    @Test
    public void testGetWorstCase_Conservative() throws Exception{
        Portfolio p = new Portfolio(6.4324, 7.6785);
        p.getFuturePortfolioValue(100000, 20);
        assertTrue(p.getBestCase() > p.getWorstCase());
    }

}
