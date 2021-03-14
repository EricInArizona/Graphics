// This is just basic OpenGL for Android.
// This is based on example code from the
// Android tutorials.


// Make the assets directory in app\src\main\assets


package com.domainnotsetyet.graphics;



import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.app.ActionBar;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;



public class MainActivity extends Activity
  {
  private static final String versionDate =
                                     "3/14/2021";

  private static final int MenuStatus =
                                  Menu.FIRST;
  private static final int MenuSomething2 =
                                  Menu.FIRST + 1;
  private static final int MenuAbout =
                                  Menu.FIRST + 2;

  private MainSurfaceView surfView;



  @Override
  protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);

    try
    {

    surfView = new MainSurfaceView( this,
                                 RenderHandler );
    setContentView( surfView );

    ActionBar actBar = getActionBar();
    if( actBar != null )
      {
      if( !actBar.isShowing())
        actBar.show();

      // Set a blank title so it doesn't show
      //  the default.
      // actBar.setTitle( " " );
      actBar.setTitle( "Version: " + versionDate );
      // actBar.setSubtitle( "This is the subtitle" );
      }

    // MainDialogs.ShowMessageBox( "Got to end.", this );

    }
    catch( Exception e )
      {
      String sError = "There was an error in onCreate() in the MainActivity." +
          "\n" + e.getMessage();

      MainDialogs.ShowMessageBox( sError, this );
      }
    }


  @Override
  protected void onPause()
    {
    super.onPause();

    // StopTimerThread();
    }


  @Override
  protected void onResume()
    {
    super.onResume();

    // StartTimerThread( TimerThreadDelay );
    }



  @Override
  public boolean onCreateOptionsMenu( Menu menu )
    {
    super.onCreateOptionsMenu( menu );

    // This is only called once, the first time
    // the options menu is displayed. To update
    // the menu every time it is displayed, see
    // onPrepareOptionsMenu(Menu).

    MenuItem MItem = menu.add( 0, MenuStatus,
                      0, "Status" );
    MItem.setShowAsAction(
                  MenuItem.SHOW_AS_ACTION_ALWAYS );

    MItem = menu.add( 0, MenuSomething2, 0,
                                    "Something" );
    MItem.setShowAsAction(
                 MenuItem.SHOW_AS_ACTION_IF_ROOM );

    MItem = menu.add( 0, MenuAbout, 0,
                                 "About" );
    MItem.setShowAsAction(
                  MenuItem.SHOW_AS_ACTION_IF_ROOM );

    // "If you return false it will not be shown."
    return true;
    }



  @Override
  public boolean onOptionsItemSelected(MenuItem item)
    {
    doOnOptionItemsSelected( item );

    return super.onOptionsItemSelected( item );
    }


  private void doOnOptionItemsSelected( MenuItem item )
    {
    switch( item.getItemId() )
      {
      case MenuStatus:
        String status = surfView.getStatus();
        MainDialogs.ShowMessageBox( "Status: " + status, this );
        break;

      case MenuSomething2:
        // doSomething();
        break;

      case MenuAbout:
        MainDialogs.ShowMessageBox( "Programming by Eric Chauvin. Version date: " + versionDate, this );
        break;

      }
    }



  private final Handler RenderHandler = new Handler(
                            Looper.getMainLooper())
    {
    @Override
    public void handleMessage( Message msg )
      {
      // Can't call MainDialogs things from here.
      DoRenderHandler();
      }
    };


  void DoRenderHandler()
    {
    MainDialogs.ShowMessageBox( "Render handler.", this );
    }


  }
