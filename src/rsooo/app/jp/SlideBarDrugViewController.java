package rsooo.app.jp;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/**
 * スライドバーのドラッグの制御を行うコントローラ
 * @author akira
 *
 */
public class SlideBarDrugViewController { 
	public static final int INITIAL_DRUG_VIEW_HEIGHT = 200;
	
	//スライドバーの位置を状態
	private boolean isDrugging;
	private View drugView;
	private View slideBarView;
	//スライドビューの高さ。これを通知する
	private int viewHeight;
	
	/**
	 * ビューの稼動範囲をあらわすパラメータ
	 */
	final private int minPx;
	final private int maxHeight;

	private int downoffsetY = 0;

	/**
	 * 
	 * @param drugView 可変ビュー(スライドバーの上部ビュー)
	 * @param slideBarView スライドバーとなるビュー
	 * @param minpx 可変ビューの上部(top)の絶対座標
	 * @param maxHeight　スライドバーが下に動く最大サイズ。画面全体の大きさを入れると最下端まで移動させられる
	 */
	public SlideBarDrugViewController(View drugView, View slideBarView, int minPx, int maxHeight){
		this.drugView = drugView;
		this.slideBarView = slideBarView;
		this.minPx = minPx;
		Rect r = new Rect();
		slideBarView.getGlobalVisibleRect(r);
		this.maxHeight = maxHeight - minPx - r.height();
	}

	/**
	 * Acitvityからの委譲
	 * @param event
	 * @return 
	 */ 
    public boolean onTouchEvent(MotionEvent event) { 
    	Rect slideBarRect = new Rect();
    	this.slideBarView.getGlobalVisibleRect(slideBarRect);
    	 
    	switch(event.getAction()){
        case MotionEvent.ACTION_MOVE:
        	if(isDrugging){
        		moveView((int)event.getRawY() - minPx - downoffsetY);
        	}
        	break;
        case MotionEvent.ACTION_DOWN:
        	final int x = (int)event.getRawX();// - offset.x;
        	final int y = (int)event.getRawY();// - offset.y;
        	downoffsetY = y -slideBarRect.top;
        	if(slideBarRect.contains(x,y)){
        		this.isDrugging = true; 
        	} 
        	break;
        case MotionEvent.ACTION_UP:
        	if(this.isDrugging){
	        	moveView((int)event.getRawY() - minPx - downoffsetY);
	        	this.isDrugging = false;
        	}
        	break;
        }
        return true;
    }

    /**
     * ビューの大きさを変えて動か�?     
     * * @param height
     */
    private void moveView(final int height){
		viewHeight = height;
		
		if(viewHeight < 0){
			viewHeight = 1;
		}else if(viewHeight > maxHeight){
			viewHeight = maxHeight;
		}
//		ownerActivity.viewDruggedEvent(viewHeight);
		LayoutParams param = drugView.getLayoutParams();
		param.height = viewHeight;
		drugView.setLayoutParams(param);
    }
    
    public void changeViewSizeMaximum(){
    	LayoutParams param = drugView.getLayoutParams();
		final int currentheight = param.height;
		for(int i = currentheight; i <= maxHeight;i += 5){
//			Log.v("testt", i + "");
			this.moveView(i);
			drugView.invalidate();
			this.slideBarView.invalidate();
		}
		this.moveView(maxHeight);
    }

    public void changeViewSizeMinimum(){
    	this.moveView(0);
    }

		
	
}
