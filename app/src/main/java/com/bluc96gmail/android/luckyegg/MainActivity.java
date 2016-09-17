package com.bluc96gmail.android.luckyegg;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_POKEMON_REQUEST = 0;

    private Button mAddButton;
    private Button mResetButton;
    private List<Addition> mAdditions;
    private TextView mTransferText;
    private TextView mEvolveText;
    private TextView mRecommendationText;
    private TextView mTimeText;
    private TextView mTimeText2;
    private TextView mExpText;
    private TextView mPercentageText;
    private TextView mTitleText;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAdditions == null) {
            mAdditions = new ArrayList<>();
        }


        mTransferText = (TextView) findViewById(R.id.transfer_text);
        mEvolveText = (TextView) findViewById(R.id.evolve_text);
        mRecommendationText = (TextView) findViewById(R.id.recommendation_text);
        mTimeText = (TextView) findViewById(R.id.time_text);
        mExpText = (TextView) findViewById(R.id.exp_text);
        mTimeText2 = (TextView) findViewById(R.id.time_text_2);
        mPercentageText = (TextView) findViewById(R.id.percentage_text);
        mProgressBar = (ProgressBar) findViewById(R.id.customProgress);
        mTitleText = (TextView) findViewById(R.id.title_text_view);


        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //working in inner class
                if (mAdditions.size() <= 15) {
                    Intent intent = AddPokemonActivity.newIntent(MainActivity.this);
                    startActivityForResult(intent, ADD_POKEMON_REQUEST);
                } else {
                    Toast.makeText(MainActivity.this, R.string.pokemon_added_cap, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mResetButton = (Button) findViewById(R.id.reset_button);
        mResetButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mAdditions.clear();
                mTransferText.setText("");
                mTimeText.setText(R.string.instructions);
                mRecommendationText.setText("");
                mExpText.setText("");
                mEvolveText.setText("");
                mTimeText2.setText("");
                mProgressBar.setVisibility(View.INVISIBLE);
                mPercentageText.setText("");
                mTitleText.setText(R.string.no_pokemon_added);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_POKEMON_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                boolean alreadyExists = false;
                Addition addition = (Addition) data.getSerializableExtra(AddPokemonActivity.EXTRA_RETURN_ADDITION);
                for (int i = 0; i < mAdditions.size(); i++) {
                    final int k = i;
                    final Addition addition1 = addition;
                    if (mAdditions.get(i).getPokemon().equals(addition.getPokemon())) {
                        alreadyExists = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(R.string.duplicate_pokemon);
                        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAdditions.set(k, addition1);
                                dialog.cancel();
                                updateUI();
                            }
                        });
                        builder.setNegativeButton(R.string.deny, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.create().show();
                    }
                }
                if (!alreadyExists) {
                    mAdditions.add(addition);
                }
                //mRecommendationText.append(addition.getPokemon() + "\n");
                //calculate
                updateUI();

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void updateUI() {
        //calculate
        int totalExp = 0;
        double totalTime = 0;
        int totalNumEvolve = 0;
        int totalMinutes;
        double totalSeconds;
        int percentage;
        if (mAdditions.size() != 0) {

            mTransferText.setText(R.string.transfer_text);
            mEvolveText.setText(R.string.evolve_text);
            mTimeText.setText(R.string.time_text_1);
            mTitleText.setText(R.string.title_text_view);

            for (int i = 0; i < mAdditions.size(); i++) {
                Addition addition = mAdditions.get(i).Calculations();
                int numTransfer = addition.getNumTransfer();
                String pokemon = addition.getPokemon();
                int numEvolve = addition.getNumEvolved();
                totalExp += addition.getExp();
                totalTime += addition.getTime();
                totalNumEvolve += numEvolve;


                if (numTransfer != 1) {
                    mTransferText.append(Integer.toString(numTransfer) + " " + pokemon + "s\n");
                } else {
                    mTransferText.append(Integer.toString(numTransfer) + " " + pokemon + "\n");
                }

                if (numEvolve != 1) {
                    mEvolveText.append(Integer.toString(numEvolve) + " " + pokemon + "s\n");
                } else {
                    mEvolveText.append(Integer.toString(numEvolve) + " " + pokemon + "\n");
                }

            }

            totalMinutes = (int) totalTime;
            totalSeconds = totalTime - totalMinutes;
            if (totalSeconds != 0) {
                mTimeText2.setText(getString(R.string.minute_second_text, totalMinutes, 30));
            } else {
                mTimeText2.setText(getString(R.string.minute_text, totalMinutes));
            }

            mExpText.setText(getString(R.string.exp_text, totalExp));

            if (totalNumEvolve < 60) {
                mRecommendationText.setText(getString(R.string.negative_recommendation_text));
            } else {
                mRecommendationText.setText(getString(R.string.positive_recommendation_text));
            }

            percentage = (int) ((totalTime / 30.0) * 100);
            mPercentageText.setText(getString(R.string.percentage_text, percentage));
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(percentage);

        }
    }





}
