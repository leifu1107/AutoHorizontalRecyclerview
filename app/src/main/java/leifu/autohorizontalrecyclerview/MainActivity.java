package leifu.autohorizontalrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_RecyclerView;
    private Integer[] imgArray = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};
    private ArrayList<Integer> datas;
    private RecyAdapter recyAdapter;
    private Handler mHandler = new Handler();
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_RecyclerView = (RecyclerView) findViewById(R.id.rv_RecyclerView);

        datas = new ArrayList<>();
        for (int i = 0; i < imgArray.length; i++) {
            datas.add(imgArray[i]);
        }
        recyAdapter = new RecyAdapter(this, datas);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_RecyclerView.setLayoutManager(layoutManager);
        rv_RecyclerView.setAdapter(recyAdapter);
        recyAdapter.setOnItemClickListener(new RecyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int tag) {
                Toast.makeText(MainActivity.this, "第" + tag + "张图片被点击了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            rv_RecyclerView.scrollBy(1, 0);
            mHandler.postDelayed(scrollRunnable, 10);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(scrollRunnable, 10);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(scrollRunnable);
    }


}
