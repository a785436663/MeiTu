package jeremy.meitu;

import android.os.Bundle;

import jeremy.meitu.base.BaseActivity;
import jeremy.meitu.random.RandomFrag;

public class MainActivity extends BaseActivity {
    @Override
    protected void initView(Bundle savedInstanceState) {
        switchFragment(R.id.root, new RandomFrag());
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
