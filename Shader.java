


package com.domainnotsetyet.graphics;


// These functions get called in s separate
// thread.


import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Message;
import android.os.Handler;



public class Shader
  {
  private Handler renderHandler;
  private int program = 0;
  private int width = 100;
  private int height = 100;
  private int vertexPosHandle = 0;
  private int colorHandle = 0;
  private String status = "";
  // MVP is the Model View Projection Matrix:
  private final float[] mVPMatrix = new float[16];
  private final float[] projectionMatrix = new float[16];
  private final float[] viewMatrix = new float[16];
  private float color[] = { 0.63671875f,
                      0.76953125f,
                      0.22265625f,
                      1.0f };

  private final String vertexShaderCode =
        "uniform mat4 uMVPMatrix;" +
        "attribute vec4 vPosition;" +
        "void main() {" +
        "  gl_Position = uMVPMatrix * vPosition;" +
        "}";

/*
  private final String vertexShaderCode =
        "attribute vec4 vPosition;" +
        "void main() {" +
        "  gl_Position = vPosition;" +
        "}";
*/

  private final String fragmentShaderCode =
        "precision mediump float;" +
        "uniform vec4 vColor;" +
        "void main() {" +
        "  gl_FragColor = vColor;" +
        "}";



  public Shader(  Handler useHandler )
    {
    renderHandler = useHandler;
    }


  public void onSurfaceCreated()
    {

    }




  public int loadShader( int type,
                              String shaderCode )
    {
    // GL_VERTEX_SHADER
    // GL_FRAGMENT_SHADER

    int shader = GLES20.glCreateShader( type );
    if( shader == 0 )
      {
      appendStatus( "Can't create shader." );
      return 0;
      }

    GLES20.glShaderSource( shader, shaderCode );

    GLES20.glCompileShader( shader );

    int[] compiled = new int[1];
    GLES20.glGetShaderiv( shader,
                          GLES20.GL_COMPILE_STATUS,
                          compiled, 0);

    if( compiled[0] == 0 )
      {
      appendStatus( "glCompileShader() failed." );
      appendStatus( GLES20.glGetShaderInfoLog( shader ));
      return 0;
      }

    return shader;
    }



  public void onSurfaceChanged( // GL10 gl,
                                int width,
                                int height )
    {
    float ratio = (float)width / (float)height;

    // Angle is field of view in Y direction,
    // in degrees.
    float angle = 30.0f;
    float nearZclip = 1.0f;
    float farZclip = 1000.0f;

    Matrix.perspectiveM( projectionMatrix, 0,
             angle, ratio, nearZclip, farZclip );

    // Matrix.frustumM( projectionMatrix, 0, -ratio,
       //                   ratio, -1, 1, 3, 7 );

    int vertexShader = loadShader(
                        GLES20.GL_VERTEX_SHADER,
                           vertexShaderCode );

    if( vertexShader == 0 )
      {
      appendStatus( "vertexShader was bad." );
      return;
      }

    int fragmentShader = loadShader(
                         GLES20.GL_FRAGMENT_SHADER,
                         fragmentShaderCode );

    if( fragmentShader == 0 )
      {
      appendStatus( "fragmentShader was bad." );
      return;
      }

    // I could create as many different programs
    // here as I want.  Each with a different
    // handle.
    program = GLES20.glCreateProgram();
    if( program == 0 )
      {
      appendStatus( "Can't create program." );
      return;
      }

    // add the vertex shader to program
    GLES20.glAttachShader( program, vertexShader );
    GLES20.glAttachShader( program, fragmentShader );
    GLES20.glLinkProgram( program );

    // Use which ever program handle I want.
    GLES20.glUseProgram( program );

    // Did it compile and link it?
    appendStatus( GLES20.glGetProgramInfoLog(
                                     program ));


    // get handle to fragment shader's vColor member
    colorHandle = GLES20.glGetUniformLocation(
                              program, "vColor");

    // Set color for drawing the triangle
    GLES20.glUniform4fv( colorHandle, 1, color, 0 );
    }



  public void onDrawFrame(
            float eyeX, float eyeY, float eyeZ,
            float lookX, float lookY, float lookZ,
            float upX, float upY, float upZ )
    {
    // Make sure you are using the perspective
    // projection matrix with this LookAt camera
    // setting: Matrix.perspectiveM()
    // The setLookAtM() function wants to know
    // about a central point that you are looking
    // toward from the eye point.  But I'm
    // calling this with a LookAt vector,
    // which is a vector that points in the
    // direction you are looking at, so
    // the expression eyeX + lookX points
    // it toward the center point that it expects.
    Matrix.setLookAtM( viewMatrix, 0,
          eyeX, eyeY, eyeZ,
          eyeX + lookX, eyeY + lookY, eyeZ + lookZ,
          upX, upY, upZ );

    // Calculate the projection and view
    // transformation
    Matrix.multiplyMM( mVPMatrix, 0,
                       projectionMatrix, 0,
                       viewMatrix, 0);

    // get handle to shape's transformation matrix
    int mVPMatrixHandle = GLES20.
          glGetUniformLocation( program,
                                "uMVPMatrix" );

    // Pass the projection and view transformation to the shader
    GLES20.glUniformMatrix4fv( mVPMatrixHandle, 1,
                             false, mVPMatrix, 0 );

    vertexPosHandle = GLES20.glGetAttribLocation(
                          program, "vPosition" );

    // if( vertexPosHandle == 0 )

    GLES20.glEnableVertexAttribArray(
                              vertexPosHandle );


    }


  public int getVertexPosHandle()
    {
    return vertexPosHandle;
    }


  public synchronized void appendStatus(
                                    String toAdd )
    {
    status += toAdd + "\n";
    }


  public synchronized String getStatus()
    {
    return status;
    }



  private void sendHandlerMessage()
    {
    try
    {
    // Returns a new Message from the global
    // message pool.
    // Give the message some numbers that mean
    // something.
    Message toSend = renderHandler.
                       obtainMessage( 1, 1, 1 );
    toSend.sendToTarget();
    // sendMessageDelayed() ?
    }
    catch( Exception Except )
      {
      // return;
      }
    }


  public int getProgram()
    {
    return program;
    }



  }
