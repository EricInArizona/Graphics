// Basic example SurfaceView.



package com.domainnotsetyet.graphics;


import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
// import android.os.Looper;
import android.os.Handler;
// import android.os.Message;
import java.lang.Runnable;
import android.view.KeyEvent;



public class MainSurfaceView extends GLSurfaceView
  {
  private SurfaceRenderer surfRend;


  public MainSurfaceView( MainActivity useActivity,
                     Handler useHandler )
    {
    super( useActivity );

    setEGLContextClientVersion( 2 ); // 3

    surfRend = new SurfaceRenderer( useHandler );
    setRenderer( surfRend );

    setRenderMode( GLSurfaceView.
                //       RENDERMODE_CONTINUOUSLY );
                         RENDERMODE_WHEN_DIRTY );

    }



  public String getStatus()
    {
    return surfRend.getStatus();
    }



  @Override
  public boolean onKeyDown( int keyCode,
                            KeyEvent event)
    {
/*
    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
      {
      queueEvent( new Runnable()
        {
        public void run()
          {
          // handeTheEvent();
          }});

      return true;
      }
    */

    return super.onKeyDown( keyCode, event );
    }



  // This is like onTouch in View.OnTouchListener.
  @Override
  public boolean onTouchEvent( MotionEvent e )
    {
    // Running on what thread?
    surfRend.doTouch( e );

    return true;
    }



  }
