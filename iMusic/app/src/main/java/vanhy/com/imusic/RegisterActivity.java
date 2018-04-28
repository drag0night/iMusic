package vanhy.com.imusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class RegisterActivity extends AppCompatActivity {

    RelativeLayout register_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_layout = findViewById(R.id.register_layout);
        register_layout.setBackgroundResource(R.drawable.background);
    }
}
