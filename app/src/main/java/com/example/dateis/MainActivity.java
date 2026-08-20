package com.example.dateis;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import kotlin.time.Instant;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        View yandex_btn = findViewById(R.id.create);
        View vk_btn = findViewById(R.id.constraintLayout2);
        View create_account = findViewById(R.id.create2);
        setupPressedAnimation(yandex_btn);
        setupPressedAnimation(vk_btn);

        setupPressedAnimation(
                findViewById(R.id.constraintLayout4),
                Color.parseColor("#F2F3F5"),
                Color.parseColor("#D8D8D8")
        );

        setupPressedAnimation(
                findViewById(R.id.constraintLayout5),
                Color.parseColor("#F2F3F5"),
                Color.parseColor("#D8D8D8")
        );

        setupPressedAnimation(
                findViewById(R.id.create2),
                Color.parseColor("#D3468F"),
                Color.parseColor("#B53B7B")
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupPressedAnimation(View button) {

        LayerDrawable layers = (LayerDrawable) button.getBackground();
        GradientDrawable overlay =
                (GradientDrawable) layers.getDrawable(1);

        overlay.setColor(Color.parseColor("#80000000"));

        button.setOnTouchListener((v, event) -> {

            int fromColor;
            int toColor;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                fromColor = Color.parseColor("#80000000");
                toColor = Color.parseColor("#99000000");

            } else if (event.getAction() == MotionEvent.ACTION_UP ||
                    event.getAction() == MotionEvent.ACTION_CANCEL) {

                fromColor = Color.parseColor("#99000000");
                toColor = Color.parseColor("#80000000");

            } else {
                return false;
            }

            ValueAnimator animator = ValueAnimator.ofObject(
                    new ArgbEvaluator(),
                    fromColor,
                    toColor
            );

            animator.setDuration(100);

            animator.addUpdateListener(animation -> {
                overlay.setColor((int) animation.getAnimatedValue());
            });

            animator.start();

            return false;
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupPressedAnimation(
            View button,
            int normalColor,
            int pressedColor
    ) {
        GradientDrawable background =
                (GradientDrawable) button.getBackground().mutate();

        background.setColor(normalColor);

        button.setOnTouchListener((v, event) -> {

            int from;
            int to;

            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                from = normalColor;
                to = pressedColor;

            } else if (event.getAction() == MotionEvent.ACTION_UP ||
                    event.getAction() == MotionEvent.ACTION_CANCEL) {

                from = pressedColor;
                to = normalColor;

                if (button.getId() == R.id.create2) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }

            } else {
                return false;
            }

            ValueAnimator animator = ValueAnimator.ofObject(
                    new ArgbEvaluator(),
                    from,
                    to
            );

            animator.setDuration(150);

            animator.addUpdateListener(animation -> {
                background.setColor(
                        (int) animation.getAnimatedValue()
                );
            });

            animator.start();

            return false;
        });
    }
}

