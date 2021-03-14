// Basic example SurfaceRenderer.



// These functions get called in a separate
// thread.


package com.domainnotsetyet.graphics;



// For the interface: GLSurfaceView.Renderer
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Message;
import android.opengl.Matrix;
import android.view.MotionEvent;



public class SurfaceRenderer implements
                           GLSurfaceView.Renderer
  {
  private Handler renderHandler;
  private Shader shader;
  private String status = "";
  private Triangle mainTri;
  private float previousX = -1;
  private float previousY = -1;



  public SurfaceRenderer( Handler useHandler )
    {
    renderHandler = useHandler;
    shader = new Shader( renderHandler );

    mainTri = new Triangle(
              0.0f,  0.622008459f, 0.0f, // top
              -0.5f, -0.311004243f, 0.0f, // bottom left
              0.5f, -0.311004243f, 0.0f );  // bottom right

    }



  public synchronized void appendStatus(
                                    String toAdd )
    {
    status += toAdd + "\n";
    }


  public synchronized String getStatus()
    {
    return status + "\n"; // + shader.getStatus();
    }



  public void onSurfaceCreated( GL10 gl,
                                EGLConfig config )
    {
    // Set the color to clear it to.
    GLES20.glClearColor( 1.0f, 0.0f, 0.0f, 1.0f );

    shader.onSurfaceCreated();
    mainTri.onSurfaceCreated();
    }



  public void onSurfaceChanged( GL10 gl,
                                int width,
                                int height )
    {
    // appendStatus( "Surface changed." );

    GLES20.glViewport( 0, 0, width, height );

    shader.onSurfaceChanged( width, height );
    }



  void doTouch( MotionEvent e )
    {
    float x = e.getX();
    float y = e.getY();

    if( previousX < 0 )
      {
      previousX = x;
      previousY = y;
      return;
      }

    switch (e.getAction())
      {
      case MotionEvent.ACTION_MOVE:

      }

    previousX = x;
    previousY = y;
    }



  public void onDrawFrame( GL10 gl )
    {
    try
    {
    // Draw the background color that was set in
    // onSurfaceCreated().
    GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT );

    // The position of the eye.
    float eyeX = 0;
    float eyeY = 0;
    float eyeZ = -5;  // Negative is toward the
                      // viewer.
    // A vector.
    // If I look to the right, then objects
    // move to the left.  If I look up, then
    // objects move down.
    float lookX = -0.3f; // Looking to the right.
    float lookY = 0.15f; // Look up a little.
    float lookZ = 1;
    // A vector.
    float upX = 0.2f;
    float upY = 1;
    float upZ = 0;
    shader.onDrawFrame( eyeX, eyeY, eyeZ,
                        lookX, lookY, lookZ,
                        upX, upY, upZ );


    mainTri.draw( shader.getVertexPosHandle() );
    }
    catch( Exception e )
      {
      String sError = "Error in" +
           " onDrawFrame()." +
           "\n" + e.getMessage();

      appendStatus( sError );
      }
    }




  }

