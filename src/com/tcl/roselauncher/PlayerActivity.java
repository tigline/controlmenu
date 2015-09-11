/**
 * 
 */
package com.tcl.roselauncher;




import com.tcl.controlmenu.MainActivity;
import com.tcl.controlmenu.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @Project SimpleGame-android	
 * @author houxb
 * @Date 2015-9-9
 */
public class PlayerActivity extends Activity implements OnClickListener{
	private Button startButtom;
	private ImageView t1_icon2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        startButtom = (Button) findViewById(R.id.bt_start);
        t1_icon2 = (ImageView) findViewById(R.id.t1_icon2);
        Animation animation1 = AnimationUtils.loadAnimation(
        		PlayerActivity.this, R.anim.tutorail_rotate);
		LinearInterpolator lin = new LinearInterpolator();
		animation1.setInterpolator(lin);


		t1_icon2.startAnimation(animation1);
        startButtom.setOnClickListener(this);
    }
	/* (non-Javadoc)
	 * @see android.view.View.OnCli ckListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_start:
			Intent intent = new Intent(PlayerActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
}
