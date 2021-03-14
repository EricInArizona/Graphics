


package com.domainnotsetyet.graphics;



import android.opengl.GLES20;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;


public class Triangle
  {
  private FloatBuffer vertexBuffer;
  private static final int CoordsPerVertex = 3;
  private final int vertexCount = 3;
  private final int vertexStride =
                              CoordsPerVertex * 4;

  float coords[] = {
           0.0f,  0.622008459f, 0.0f, // top
          -0.5f, -0.311004243f, 0.0f, // bottom left
           0.5f, -0.311004243f, 0.0f  // bottom right
           };



  private Triangle()
    {
    }

  // In counter-clockwise order from top to
  // bottom-left, to bottom-right.
  public Triangle(
             float topX, float topY, float topZ,
             float leftX, float leftY, float leftZ,
         float rightX, float rightY, float rightZ )
    {
    coords[0] = topX;
    coords[1] = topY;
    coords[2] = topZ;
    coords[3] = leftX;
    coords[4] = leftY;
    coords[5] = leftZ;
    coords[6] = rightX;
    coords[7] = rightY;
    coords[8] = rightZ;
    }



  public void draw( int vertexPosHandle )
    {
    GLES20.glVertexAttribPointer( vertexPosHandle,
                               CoordsPerVertex,
                               GLES20.GL_FLOAT,
                               false,
                               vertexStride,
                               vertexBuffer );

    // Draw the triangle
    GLES20.glDrawArrays( GLES20.GL_TRIANGLES,
                                 0, vertexCount );

    GLES20.glDisableVertexAttribArray(
                              vertexPosHandle );
    }



  public void onSurfaceCreated()
    {
    // Times 4 bytes per float.
    ByteBuffer bb = ByteBuffer.allocateDirect(
                              coords.length * 4);

    bb.order( ByteOrder.nativeOrder() );

    vertexBuffer = bb.asFloatBuffer();
    vertexBuffer.put( coords );
    vertexBuffer.position( 0 );
    }



  }
