package rsooo.app.jp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SlideBarWidgetActivity extends Activity {
    
	Context context;
	SlideBarDrugViewController controller;
	View upperView;
	View slidebarView;
	View bottomView;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        upperView = (View)this.findViewById(R.id.view0);
        slidebarView = (View)this.findViewById(R.id.view1);
        bottomView = (View)this.findViewById(R.id.view2);
        context = this;
        
        
    }
    
	public void onWindowFocusChanged(boolean hasFocus) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		final int screenHeight = display.getHeight();
		
        Rect r = new Rect();
        this.upperView.getGlobalVisibleRect(r);
		if(controller == null){
			controller = new SlideBarDrugViewController(upperView, slidebarView, r.top, screenHeight);
		} 
//        Log.e("test",r.toString());
	}
	 
	@Override
	public boolean onTouchEvent(MotionEvent event){
			return controller.onTouchEvent(event);			
	}
    
}