package com.bluc96gmail.android.luckyegg;


import java.io.Serializable;
import java.util.UUID;

/**
 * Created by bluc on 7/21/16.
 */
public class Addition implements Serializable{

    private int mNumCandies;
    private int mNumPokemon;
    private String mPokemon;
    private int mCandiesToEvolve;
    private int mNumTransfer;
    private int mNumEvolved;
    private int mExp;
    private double mTime;
    public Addition Calculations() {

        //Pre-transfer
        while (true) {
            if (mNumPokemon == 0 || mNumCandies < mCandiesToEvolve) {
                break;
            } else {
                //Evolve
                mNumPokemon--;
                mNumCandies -= mCandiesToEvolve;
                mNumCandies++;
                mNumEvolved++;
            }
        }

        //Post-Transfer
        while (true) {
            if (mNumPokemon == 0 || mNumPokemon + mNumCandies < mCandiesToEvolve + 1) {
                break;
            }
            //transfer
            while (mNumCandies < mCandiesToEvolve) {
                mNumTransfer++;
                mNumPokemon--;
                mNumCandies++;
            }
            //evolve
            mNumPokemon--;
            mNumCandies -= mCandiesToEvolve;
            mNumCandies++;
            mNumEvolved++;
        }

        mExp = mNumEvolved * 1000;
        mTime = mNumEvolved * 0.5;

        return this;
    }


    public int getNumCandies() {
        return mNumCandies;
    }

    public void setNumCandies(int numCandies) {
        mNumCandies = numCandies;
    }

    public int getNumPokemon() {
        return mNumPokemon;
    }

    public void setNumPokemon(int numPokemon) {
        mNumPokemon = numPokemon;
    }

    public String getPokemon() {
        return mPokemon;
    }

    public void setPokemon(String pokemon) {
        mPokemon = pokemon;
    }


    public int getCandiesToEvolve() {
        return mCandiesToEvolve;
    }

    public void setCandiesToEvolve(int candiesToEvolve) {
        mCandiesToEvolve = candiesToEvolve;
    }

    public double getTime() {
        return mTime;
    }

    public void setTime(double time) {
        mTime = time;
    }

    public int getExp() {
        return mExp;
    }

    public void setExp(int exp) {
        mExp = exp;
    }

    public int getNumEvolved() {
        return mNumEvolved;
    }

    public void setNumEvolved(int numEvolved) {
        mNumEvolved = numEvolved;
    }

    public int getNumTransfer() {
        return mNumTransfer;
    }

    public void setNumTransfer(int numTransfer) {
        mNumTransfer = numTransfer;
    }

    public Addition(String pokemon, int numCandies, int numPokemon) {
        mNumCandies = numCandies;
        mNumPokemon = numPokemon;
        mPokemon = pokemon;
    }

    public Addition() {

    }




}
