package com.codeogenic.myapplication;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.codeogenic.checkablebutton.CheckableButton;
import com.thebluealliance.spectrum.SpectrumDialog;
import com.thebluealliance.spectrum.internal.ColorCircleDrawable;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    SeekBar radius,borderWidth,borderWidth2;
    CircleImageView checkedColor, uncheckedColor, checkedBorderColor, uncheckedBorderColor;
    CheckableButton checkableButton;
    HashMap<String, Integer> selectedColours = new HashMap<>();
    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkableButton = (CheckableButton) findViewById(R.id.btn1);
        label = (TextView) findViewById(R.id.check_label);

        radius = (SeekBar) findViewById(R.id.seekBar);
        borderWidth= (SeekBar) findViewById(R.id.seekBar2);
        borderWidth2= (SeekBar) findViewById(R.id.seekBar3);
        checkedColor = (CircleImageView) findViewById(R.id.checked_colour);
        uncheckedColor = (CircleImageView) findViewById(R.id.unchecked_colour);
        uncheckedBorderColor = (CircleImageView) findViewById(R.id.border_colour);
        checkedBorderColor = (CircleImageView) findViewById(R.id.checked_border_colour);

        checkedColor.setOnClickListener(new ColorBtnListener());
        uncheckedColor.setOnClickListener(new ColorBtnListener());
        uncheckedBorderColor.setOnClickListener(new ColorBtnListener());
        checkedBorderColor.setOnClickListener(new ColorBtnListener());


        checkableButton.setOnCheckChangeLisnter(new CheckableButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View buttonView, boolean isChecked) {

                label.setText(isChecked?"checked":"unchecked");
            }
        });

        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                checkableButton.setRadius(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        borderWidth.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                checkableButton.setBorderWidth(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        borderWidth2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                checkableButton.setCheckedBorderWidth(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        selectedColours.put("checkedColour", ContextCompat.getColor(this,R.color.blue));
        selectedColours.put("uncheckedColour",  ContextCompat.getColor(this,R.color.purple));
        selectedColours.put("checkedborderColour",  ContextCompat.getColor(this,R.color.peach));
        selectedColours.put("uncheckedBorder", ContextCompat.getColor(this, R.color.greylight));
    }

    private class ColorBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.checked_colour:
                    SpectrumDialog color1 = new SpectrumDialog.Builder(MainActivity.this)
                            .setSelectedColor(selectedColours.get("checkedColour"))
                            .setColors(R.array.demo_colors).setTitle("Select checked Colour").build();
                    color1.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            selectedColours.put("checkedColour", color);
                            checkedColor.setImageDrawable(new ColorDrawable(color));
                            checkableButton.setCheckedBackgroundColor(color);
                        }
                    });
                    color1.show(getSupportFragmentManager(),"color1");
                    break;
                case R.id.unchecked_colour:
                    SpectrumDialog color2 = new SpectrumDialog.Builder(MainActivity.this)
                            .setSelectedColor(selectedColours.get("uncheckedColour")).setColors(R.array.demo_colors)
                            .setTitle("Select unchecked Colour").build();
                    color2.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            uncheckedColor.setImageDrawable(new ColorDrawable(color));
                            selectedColours.put("uncheckedColour", color);
                            checkableButton.setUncheckedBackgroundColor(color);
                        }
                    });
                    color2.show(getSupportFragmentManager(),"color3");
                    break;
                case R.id.checked_border_colour:
                    SpectrumDialog color3 = new SpectrumDialog.Builder(MainActivity.this)
                            .setSelectedColor(selectedColours.get("checkedborderColour")).setColors(R.array.demo_colors)
                            .setTitle("Select border Colour").build();
                    color3.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            checkedBorderColor.setImageDrawable(new ColorDrawable(color));
                            selectedColours.put("checkedborderColour", color);
                            checkableButton.setCheckedBorderColor(color);
                        }
                    });
                    color3.show(getSupportFragmentManager(),"color3");

                    break;
                case R.id.border_colour:
                    SpectrumDialog color4 = new SpectrumDialog.Builder(MainActivity.this)
                            .setSelectedColor(selectedColours.get("uncheckedBorder")).setColors(R.array.demo_colors)
                            .setTitle("Select border Colour").build();
                    color4.setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override
                        public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            uncheckedBorderColor.setImageDrawable(new ColorDrawable(color));
                            selectedColours.put("uncheckedBorder", color);
                            checkableButton.setBorderColor(color);
                        }
                    });
                    color4.show(getSupportFragmentManager(),"color4");
                    break;


            }
        }
    }
}
