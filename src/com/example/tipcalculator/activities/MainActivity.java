package com.example.tipcalculator.activities;

import java.text.DecimalFormat;

import com.example.tipcalculator.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int TIP_MAX = 25;
	private static final int TIP_MIN = 10;
	private static final int DEFAULT_TIP_RATE = 18;

	private int tip_rate = DEFAULT_TIP_RATE;
	private TextView tv_tip_rate;
	private SeekBar sb_tip_rate;
	private TextView tv_tip_amount;
	private EditText et_amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et_amount = (EditText) findViewById(R.id.et_amount);
		et_amount.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// Nothing
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// Nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				updateTipAmountUi();
			}
			
		});

		tv_tip_amount = (TextView) findViewById(R.id.tv_tip_amount);

		tv_tip_rate = (TextView) findViewById(R.id.tv_tip_rate);

		sb_tip_rate = (SeekBar) findViewById(R.id.sb_tip_rate);
		sb_tip_rate
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// Nothing
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// Nothing
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						double percent = 1.0 * progress / seekBar.getMax();
						tip_rate = (int) Math.round(percent
								* (TIP_MAX - TIP_MIN) + TIP_MIN);
						updateTipRateUi();
						updateTipAmountUi();

					}
				});
		int progress = (int) Math.round(1.0 * (tip_rate - TIP_MIN)
				/ (TIP_MAX - TIP_MIN) * sb_tip_rate.getMax());
		sb_tip_rate.setProgress(progress);

		updateTipAmountUi();
	}

	private void updateTipRateUi() {
		tv_tip_rate.setText(tip_rate + "%");
	}

	private void updateTipAmountUi() {
		try {
			float amount = Float.parseFloat(et_amount.getText().toString());
			double tip_amount = amount * tip_rate / 100.0;
			tv_tip_amount.setText(DecimalFormat.getCurrencyInstance().format(
					tip_amount));
		} catch (NumberFormatException e) {
			tv_tip_amount.setText("");
		}
	}
}
