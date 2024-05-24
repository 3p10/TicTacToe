package com.example.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private boolean playerXTurn = true;
    private int[][] board = new int[3][3];
    private TextView statusTextView;
    private GridLayout gridLayout;
    private static final int DELAY_MILLIS = 3000; // 3 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        statusTextView = findViewById(R.id.statusTextView);
        gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button button = (Button) gridLayout.getChildAt(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick((Button) v);
                }
            });
        }
    }

    private void onButtonClick(Button button) {
        if (!button.getText().toString().equals("")) return;

        String tag = (String) button.getTag();
        int row = Character.getNumericValue(tag.charAt(0));
        int col = Character.getNumericValue(tag.charAt(2));

        if (playerXTurn) {
            button.setText("X");
            board[row][col] = 1;
            if (checkForWin(1)) {
                statusTextView.setText("Player X Wins!");
                endGame();
            } else {
                playerXTurn = false;
                statusTextView.setText("Player O's Turn");
            }
        } else {
            button.setText("O");
            board[row][col] = 2;
            if (checkForWin(2)) {
                statusTextView.setText("Player O Wins!");
                endGame();
            } else {
                playerXTurn = true;
                statusTextView.setText("Player X's Turn");
            }
        }

        if (isBoardFull() && !statusTextView.getText().toString().contains("Wins")) {
            statusTextView.setText("It's a Draw!");
            endGame();
        }
    }

    private boolean checkForWin(int player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player)
                return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player)
                return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) return false;
            }
        }
        return true;
    }

    private void disableButtons() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setEnabled(false);
        }
    }

    private void endGame() {
        disableButtons();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(GameActivity.this, MainActivity.class));
                finish();
            }
        }, DELAY_MILLIS);
    }
}
