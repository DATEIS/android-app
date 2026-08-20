package com.example.dateis;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.TypefaceSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

@RequiresApi(api = Build.VERSION_CODES.O)
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        setupPressedAnimation(
                findViewById(R.id.imageButton),
                Color.parseColor("#F2F3F5"),
                Color.parseColor("#D8D8D8")
        );

        setupPressedAnimation(
                findViewById(R.id.create2),
                Color.parseColor("#FFFFFFFF"),
                Color.parseColor("#B2FFFFFF")
        );

        EditText etEmail = findViewById(R.id.etEmail);

        Typeface manropeRegular = getResources().getFont(R.font.manrope_regular);
        Typeface manropeBold = getResources().getFont(R.font.manrope_semibold);

        SpannableString hint = new SpannableString("Email or Phone number");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            hint.setSpan(
                    new TypefaceSpan(manropeBold),
                    0, 5,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            hint.setSpan(
                    new TypefaceSpan(manropeRegular),
                    6, 8,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            hint.setSpan(
                    new TypefaceSpan(manropeBold),
                    9, 21,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }

        etEmail.setHint(hint);


        setupClickableText();

    }

    @SuppressLint("ClickableViewAccessibility")
    private void setupPressedAnimation(
            View button,
            int normalColor,
            int pressedColor
    ) {

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

                if (button.getId() == R.id.imageButton) {
                    onBackPressed();
                    overridePendingTransition(R.anim.back_left, R.anim.back_right);
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

            if (button.getId() == R.id.create2) {
                TextView textBtn = findViewById(R.id.textView9);
                animator.addUpdateListener(animation -> {
                    textBtn.setTextColor((int) animation.getAnimatedValue());
                });
            } else {
                GradientDrawable background =
                        (GradientDrawable) button.getBackground().mutate();


                background.setColor(normalColor);
                animator.addUpdateListener(animation -> {
                    background.setColor(
                            (int) animation.getAnimatedValue()
                    );
                });
            }

            animator.start();

            return false;
        });
    }


    private void setupClickableText() {
        TextView textView = findViewById(R.id.tvTerms);
        String fullText = "By continuing you agree to \n" +
                "Terms of Service & Privacy Policy";

        SpannableString spannableString = new SpannableString(fullText);

        String word1 = "Terms of Service";
        int start1 = fullText.indexOf(word1);
        int end1 = start1 + word1.length();

        ClickableSpan span1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(LoginActivity.this, "Open Terms of Service", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://example.com/privacy"));
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLACK);
                ds.setTypeface(getResources().getFont(R.font.mr_medium));
            }
        };

        String word2 = "Privacy Policy";
        int start2 = fullText.indexOf(word2);
        int end2 = start2 + word2.length();

        ClickableSpan span2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(LoginActivity.this, "Open Privacy Policy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(Color.BLACK);
                ds.setTypeface(getResources().getFont(R.font.mr_medium));
            }
        };

        spannableString.setSpan(span1, start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(span2, start2, end2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
