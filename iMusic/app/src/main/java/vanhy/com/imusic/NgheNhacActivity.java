package vanhy.com.imusic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NgheNhacActivity extends AppCompatActivity {

    private ImageButton btnImageMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nghe_nhac);
        btnImageMore = (ImageButton) findViewById(R.id.btnImageMore);
        btnImageMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog dialog = new BottomSheetDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });
    }
}
