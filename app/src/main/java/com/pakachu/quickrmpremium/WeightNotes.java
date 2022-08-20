package com.pakachu.quickrmpremium;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class WeightNotes extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_notes);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        ImageView imageView = findViewById(R.id.imageVieww2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.startAnimation(animation);
            }
        });

        ImageView imageView2 = findViewById(R.id.imageVieww);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        Button btn_add = findViewById(R.id.btn_add),
                btn_clear = findViewById(R.id.btn_clear),
                btn_removelast = findViewById(R.id.btn_removelast),
                btn_ekle = findViewById(R.id.btn_ekle),
                btn_iptal = findViewById(R.id.btn_iptal);
        EditText et_kg = findViewById(R.id.et_kg);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        TextView tv_total = findViewById(R.id.tv_total),
                tv_bench = findViewById(R.id.tv_bench),
                tv_squat = findViewById(R.id.tv_squat),
                tv_deadlift = findViewById(R.id.tv_deadlift);
        LinearLayout ll_addnew = findViewById(R.id.ll_addnew);

        btn_add.setOnClickListener(view -> {
            ll_addnew.setVisibility(View.VISIBLE);
            ll_addnew.startAnimation(animation);
            btn_add.setVisibility(View.GONE);
            btn_clear.setVisibility(View.GONE);
            btn_removelast.setVisibility(View.GONE);
        });
        btn_iptal.setOnClickListener(view -> {
            ll_addnew.clearAnimation();
            ll_addnew.setVisibility(View.GONE);
            btn_add.setVisibility(View.VISIBLE);
            btn_clear.setVisibility(View.VISIBLE);
            btn_removelast.setVisibility(View.VISIBLE);
        });

        Database database = new Database(this);
        Cursor cursor = database.getData();
        int size = cursor.getCount();
        ArrayList<String> hareket = new ArrayList<>();
        ArrayList<Integer> agirlik = new ArrayList<>();
        ArrayList<String> tarih = new ArrayList<>();
        ArrayList<Integer> bench = new ArrayList<>();
        ArrayList<Integer> squat = new ArrayList<>();
        ArrayList<Integer> deadlift = new ArrayList<>();
        Integer maxb=0,maxs=0,maxd=0,total;
        while (cursor.moveToNext()) {
            hareket.add(cursor.getString(1));
            agirlik.add(cursor.getInt(2));
            tarih.add(cursor.getString(3));
            switch (cursor.getString(1)) {
                case "b":
                    bench.add(cursor.getInt(2));
                    break;
                case "s":
                    squat.add(cursor.getInt(2));
                    break;
                case "d":
                    deadlift.add(cursor.getInt(2));
                    break;
            }
        }
        if(size>0) {
            RecyclerView recyclerView = findViewById(R.id.recy);
            RecyAdapter recyAdapter = new RecyAdapter(this, hareket, agirlik, tarih);
            recyclerView.setAdapter(recyAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            boolean a=false,b=false,s=false;
            if(bench.size()>0) {
                maxb= Collections.max(bench);
                tv_bench.setText("Bench Press\n"+maxb+ " kg");
                a=true;
            }
            if(squat.size()>0) {
                maxs= Collections.max(squat);
                tv_squat.setText("Squat\n"+maxs+" kg");
                b=true;
            }
            if(deadlift.size()>0) {
                maxd= Collections.max(deadlift);
                tv_deadlift.setText("Deadlift\n"+maxd+" kg");
                s=true;
            }
           if(a&b&s) {
               total=maxb+maxs+maxd;
               tv_total.setText("Total: "+total+" kg");
           }

        }

        btn_ekle.setOnClickListener(view -> {
            String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
            int radioButtonID = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = radioGroup.findViewById(radioButtonID);
            if (!et_kg.getText().toString().matches("")) {
                Boolean ok = database.addData(radioButton.getTag().toString(), Integer.valueOf(et_kg.getText().toString()), date);
                if (ok) {
                    finish();
                    startActivity(new Intent(WeightNotes.this,WeightNotes.class));
                    Toast.makeText(this, getString(R.string.yeniagirlik), Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, getString(R.string.islembasarisiz), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, getString(R.string.agirlikyaz), Toast.LENGTH_SHORT).show();
        });

        btn_clear.setOnClickListener(view -> {
            DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        database.deleteAll();
                        finish();
                        startActivity(new Intent(WeightNotes.this, WeightNotes.class));
                        Toast.makeText(WeightNotes.this, getString(R.string.verilertemizlendi), Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        finish();
                        break;
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(WeightNotes.this);
            builder.setMessage(getString(R.string.eminmisin)).setPositiveButton(getString(R.string.temizle), dialogClickListener)
                    .setNegativeButton(getString(R.string.iptal), dialogClickListener).setTitle(getString(R.string.uyari)).show();

        });

        btn_removelast.setOnClickListener(view -> {
            database.deleteLast();
            finish();
            startActivity(new Intent(WeightNotes.this, WeightNotes.class));
            Toast.makeText(WeightNotes.this, R.string.ensonagirliktemizlendi, Toast.LENGTH_SHORT).show();
        });


    }
}