package com.xinlan.wavebtn;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
{
    private View btn;
    private WaveView mWave;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btn = findViewById(R.id.btn);
        mWave = (WaveView)findViewById(R.id.wave_button);
        
        btn.setOnClickListener(new OnClick());
    }

    private final class OnClick implements OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            if(mWave.getIsPlay())
            {
                mWave.stop();
            }else{
                mWave.play();
            }//end if
        }
    }
}//end class
