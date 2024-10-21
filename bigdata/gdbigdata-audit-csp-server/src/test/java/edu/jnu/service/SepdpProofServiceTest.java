package edu.jnu.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SepdpProofServiceTest {

    @org.junit.jupiter.api.Test
    void getMList() {
    }

    @org.junit.jupiter.api.Test
    void getSList() {
    }

    @org.junit.jupiter.api.Test
    void getGamma() {
        ArrayList<BigInteger> aList = new ArrayList<>();
        aList.add(new BigInteger("1"));
        aList.add(new BigInteger("1"));
        ArrayList<BigInteger> bList = new ArrayList<>();
        bList.add(new BigInteger("1"));
        bList.add(new BigInteger("0"));
        SepdpProofService sepdpProofService = new SepdpProofService();
        BigInteger gamma = sepdpProofService.getGamma(aList,bList);

        System.out.println(gamma.toString());
    }

    @org.junit.jupiter.api.Test
    void getAlpha() {
    }
}