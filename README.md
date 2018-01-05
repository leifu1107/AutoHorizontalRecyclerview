# AutoHorizontalRecyclerview
自动水平滚动的RecyclerView
![](https://github.com/leifu1107/AutoHorizontalRecyclerview/raw/master/screenshots/1.gif) 

#### 思路
1.准备N张图片

2.使用recyclerview.scrollBy  每隔一段时间水平滚动一段距离

实现代码
---------
1.activty_main布局文件
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="leifu.autohorizontalrecyclerview.MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv_RecyclerView"
        android:layout_width="match_parent"
        android:layout_height="200dp"></android.support.v7.widget.RecyclerView>
</LinearLayout>

```

2.item布局
```java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:orientation="vertical">

    <ImageView
        android:id="@+id/iv_img"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:layout_margin="10dp"
        android:scaleType="fitXY"/>
</LinearLayout>

```
3.MainActivity代码
```java
public class MainActivity extends AppCompatActivity {

    private RecyclerView rv_RecyclerView;
    private Integer[] imgArray = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10};
    private ArrayList<Integer> datas;
    private RecyAdapter recyAdapter;
    private LinearLayoutManager layoutManager;

    private Handler mHandler = new Handler();
    Runnable scrollRunnable = new Runnable() {
        @Override
        public void run() {
            rv_RecyclerView.scrollBy(1, 0);
            mHandler.postDelayed(scrollRunnable, 10);
        }
    };

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

```
4.adpter代码
```java
public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<Integer> datas;
    private OnItemClickListener onItemClickListener;

    public RecyAdapter(Context context, List<Integer> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.iv_img.setImageResource(datas.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View view) {
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, (Integer) view.getTag());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int tag);
    }
}
```

