/*
 * Copyright (C) 2010 Daniel Nilsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package afzkl.development.mColorPicker;

import afzkl.development.mColorPicker.views.ColorPanelView;
import afzkl.development.mColorPicker.views.ColorPickerView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ColorPickerActivity extends Activity implements
		View.OnClickListener {

	private ColorPickerView mColorPickerView;
	private ColorPanelView mOldColorPanel;
	private ColorPanelView mNewColorPanel;

	private Button mCancelButton;
	private Button mOkButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// To fight color branding.
		getWindow().setFormat(PixelFormat.RGBA_8888);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_picker);

		setUp();

	}

	private void setUp() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ColorPickerActivity.this);

		mColorPickerView = (ColorPickerView) findViewById(R.id.ColorPickerView);
		mOldColorPanel = (ColorPanelView) findViewById(R.id.OldColorPanel);
		mNewColorPanel = (ColorPanelView) findViewById(R.id.NewColorPanel);
		mOkButton = (Button) findViewById(R.id.OkButton);
		mCancelButton = (Button) findViewById(R.id.CancelButton);

		((LinearLayout) mOldColorPanel.getParent()).setPadding(Math
				.round(mColorPickerView.getDrawingOffset()), 0, Math
				.round(mColorPickerView.getDrawingOffset()), 0);

		mColorPickerView
				.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {

					@Override
					public void onColorChanged(int color) {
						mNewColorPanel.setColor(color);
					}
				});

		int color = prefs.getInt("activity", 0xff308dbd);

		mOldColorPanel.setColor(color);
		mColorPickerView.setColor(color, true);
		mColorPickerView.setAlphaSliderVisible(true);

		mOkButton.setOnClickListener(this);
		mCancelButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.OkButton:

			SharedPreferences customSharedPreference = PreferenceManager
					.getDefaultSharedPreferences(ColorPickerActivity.this);
			SharedPreferences.Editor editor = customSharedPreference.edit();
			editor.putInt("activity", mColorPickerView.getColor());
			editor.commit();

			finish();

			break;

		case R.id.CancelButton:

			finish();

			break;
		}

	}

}
