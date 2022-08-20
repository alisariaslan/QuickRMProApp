package com.pakachu.quickrmpremium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public String sonuc(Double agirlik, Integer tk) {
        RadioGroup radioGr = (RadioGroup) findViewById(R.id.radioGroup);
        int id = radioGr.getCheckedRadioButtonId();
        Double sonuc = new Double(0);
        double tekrar = tk;
        double[] ort = new double[7];
        switch (id) {
            case R.id.radioButton1:
                // Epley
                tekrar--;
                sonuc = (0.033 * tekrar * agirlik) + agirlik;
                break;
            case R.id.radioButton2:
                // Bryizcki
                sonuc = agirlik / (1.0278 - (0.0278 * tekrar));
                break;
            case R.id.radioButton3:
                // Lombardi

                sonuc = agirlik * Math.pow(tekrar, 0.10);
                break;
            case R.id.radioButton4:
                // Mayhew et al
                double e = 2.71828;
                sonuc = (100 * agirlik) / (52.2 + (41.9 * (Math.pow(e, (-0.055 * tekrar)))));
                break;
            case R.id.radioButton5:
                // O conner et al
                tekrar--;
                sonuc = (agirlik * (1 + (tekrar / 40)));
                break;
            case R.id.radioButton6:
                // Wathen
                e = 2.71828;
                sonuc = (100 * agirlik) / (48.80 + (53.8 * (Math.pow(e, (-0.075 * tekrar)))));
                break;
            case R.id.radioButton7:
                // Lander
                sonuc = (100 * agirlik) / (101.3 - (2.67123 * tekrar));
                break;
            case R.id.radioButton:
                // tümü
                tekrar--;
                sonuc = (0.033 * tekrar * agirlik) + agirlik;
                ort[0] = sonuc;
                tekrar++;
                sonuc = agirlik / (1.0278 - (0.0278 * tekrar));
                ort[1] = sonuc;
                sonuc = agirlik * Math.pow(tekrar, 0.10);
                ort[2] = sonuc;
                e = 2.71828;
                sonuc = (100 * agirlik) / (52.2 + (41.9 * (Math.pow(e, (-0.055 * tekrar)))));
                ort[3] = sonuc;
                tekrar--;
                sonuc = (agirlik * (1 + (tekrar / 40)));
                ort[4] = sonuc;
                tekrar++;
                sonuc = (100 * agirlik) / (48.80 + (53.8 * (Math.pow(e, (-0.075 * tekrar)))));
                ort[5] = sonuc;
                sonuc = (100 * agirlik) / (101.3 - (2.67123 * tekrar));
                ort[6] = sonuc;
                for (int i = 1; i < 7; i++) {
                    sonuc += ort[i];
                }
                sonuc = sonuc / 7;
                break;
            default:
                break;
        }
        String editedText = sonuc.toString();
        if (editedText.length() > 5)
            editedText = editedText.substring(0, 5);
        return editedText;
    }

   private String birim = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        birim = getResources().getString(R.string.maks);

        Animation animation=AnimationUtils.loadAnimation(this,R.anim.bounce);

        ImageView imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(animation);
            }
        });

        ImageView imageView2=findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(MainActivity.this, WeightNotes.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent,
                            ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                }
            }
        });


        Button btn_main_nasilcalisir = findViewById(R.id.btn_main_how);
        btn_main_nasilcalisir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, MainActivity2_how.class));

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(getString(R.string.aciklamaasil)).setPositiveButton("Anladım", dialogClickListener)
                        .setTitle(getString(R.string.nasilcalisir))
                        .setIcon(R.drawable.ic_baseline_help_24).show();

            }
        });

        EditText edt = (EditText) findViewById(R.id.editTextNumberDecimal);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                hesapla();
            }
        });

        EditText edt2 = (EditText) findViewById(R.id.editTextNumberSigned);
        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                hesapla();
            }
        });

        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                hesapla();
            }
        });

        Button diger = findViewById(R.id.btn_diger);
        diger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(GoToStore());
            }
        });

    }

    public void hesapla() {
        EditText et1 = findViewById(R.id.editTextNumberDecimal);
        String yazanAgirlik = et1.getText().toString();
        EditText et2 = findViewById(R.id.editTextNumberSigned);
        String yazanTekrar = et2.getText().toString();
        if (yazanAgirlik.matches("") | yazanTekrar.matches(""))
            return;

        Double agirlik = Double.parseDouble(String.valueOf(yazanAgirlik));
        Integer tekrar = Integer.parseInt(String.valueOf(yazanTekrar));

        TextView tv6 = findViewById(R.id.textView6);

        TextView tv7 = findViewById(R.id.textView7);
        TextView tv8 = findViewById(R.id.textView8);
        TextView tv9 = findViewById(R.id.textView9);
        TextView tv10 = findViewById(R.id.textView10);
        TextView tv11 = findViewById(R.id.textView11);
        TextView tv12 = findViewById(R.id.textView12);
        TextView tv13 = findViewById(R.id.textView13);
        TextView tv14 = findViewById(R.id.textView14);
        TextView tv15 = findViewById(R.id.textView15);
        TextView tv16 = findViewById(R.id.textView16);
        TextView tv17 = findViewById(R.id.textView17);

        tv6.setText("1 " + birim + ": " + sonuc(agirlik, tekrar) + " Kg");

        RadioGroup radioGr = (RadioGroup) findViewById(R.id.radioGroup);
        int id = radioGr.getCheckedRadioButtonId();
        if (id == R.id.radioButton4)
            return;

        tv7.setText("2 " + birim + ": " + sonuc(agirlik, tekrar - 1) + " Kg");
        tv8.setText("3 " + birim + ": " + sonuc(agirlik, tekrar - 2) + " Kg");
        tv9.setText("4 " + birim + ": " + sonuc(agirlik, tekrar - 3) + " Kg");
        tv10.setText("5 " + birim + ": " + sonuc(agirlik, tekrar - 4) + " Kg");
        tv11.setText("6 " + birim + ": " + sonuc(agirlik, tekrar - 5) + " Kg");
        tv12.setText("7 " + birim + ": " + sonuc(agirlik, tekrar - 6) + " Kg");
        tv13.setText("8 " + birim + ": " + sonuc(agirlik, tekrar - 7) + " Kg");
        tv14.setText("9 " + birim + ": " + sonuc(agirlik, tekrar - 8) + " Kg");
        tv15.setText("10 " + birim + ": " + sonuc(agirlik, tekrar - 9) + " Kg");
        tv16.setText("11 " + birim + ": " + sonuc(agirlik, tekrar - 10) + " Kg");
        tv17.setText("12 " + birim + ": " + sonuc(agirlik, tekrar - 11) + " Kg");

        tv6.setVisibility(View.VISIBLE);
        tv7.setVisibility(View.VISIBLE);
        tv8.setVisibility(View.VISIBLE);
        tv9.setVisibility(View.VISIBLE);
        tv10.setVisibility(View.VISIBLE);
        tv11.setVisibility(View.VISIBLE);
        tv12.setVisibility(View.VISIBLE);
        tv13.setVisibility(View.VISIBLE);
        tv14.setVisibility(View.VISIBLE);
        tv15.setVisibility(View.VISIBLE);
        tv16.setVisibility(View.VISIBLE);
        tv17.setVisibility(View.VISIBLE);

        LinearLayout ll_sonuclar = findViewById(R.id.ll_sonuclar);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        ll_sonuclar.startAnimation(animation);



    }

    private Intent GoToStore() {
        Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
        openURL.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Pakachu"));
        return openURL;
    }
}