package vanhy.com.imusic;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vanhy.com.imusic.SQLite.SQLite;

public class LoginActivity extends AppCompatActivity{

    private RelativeLayout login_layout;
    private CardView btnLogin;
    private TextView linkResgister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SQLite.createDatabase(this);
        init();
        linkResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btnLogin = (CardView) findViewById(R.id.cardView);
        linkResgister = (TextView) findViewById(R.id.textViewDangky);
    }
}
