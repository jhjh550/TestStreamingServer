package example.com.teststreamingserver;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
    implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener{
//    MyStreamingServer server;
    private static final int serverPort = 8181;
    StreamOverHttp mStreamOverHttp;
    MediaPlayer mp = null;
    boolean mFirst = true;
    SurfaceHolder msh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
//            server = new MyStreamingServer(serverPort);
            mStreamOverHttp = new StreamOverHttp(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SurfaceView sv = (SurfaceView) findViewById(R.id.surfaceView);
        msh = sv.getHolder();
        msh.addCallback(this);

    }

    public void loadVideoSource(){
        String path = "http://localhost:8181/1.mp4";

        mp = new MediaPlayer();
        try {
            mp.setDataSource(path);
            mp.setDisplay(msh);
            mp.prepareAsync();
            mp.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnPlay:
                mp.start();
                break;
            case R.id.btnStop:
                deletePlayer();
                break;
            case R.id.btnPause:
                mp.pause();
                break;
        }
    }

    public void deletePlayer(){
        if(mp != null){
            mp.stop();
            mp.release();
            mp = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        server.stop();
        deletePlayer();
    }








    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
//        if(mFirst){
//            mFirst = false;
//            mp.start();
//        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        loadVideoSource();
        msh.setFixedSize(mp.getVideoWidth(), mp.getVideoHeight());
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
