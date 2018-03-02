package idv.alfie.mediaexdemo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int currSoundStream;
    private int sound_hahaha;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSoundPool();
        TextView playSound = (TextView)findViewById(R.id.textView2);
        playSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //音效
                //currSoundStream = playSound(sound_hahaha);
                //mp3
                //playMp3(R.raw.crrect_answer);
                //Url
                playUrl(String.valueOf("https://891622172.wodemo.com/down/20180302/162658/%E4%BA%94%E6%9C%88%E5%A4%A9+-+%E6%B8%A9%E6%9F%94.mp3"));
            }
        });
        TextView stopSound = (TextView)findViewById(R.id.textView);
        stopSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //音效
                //stopSound(currSoundStream);
                //mp3
                //stopMp3();
                //Url
                stopUrl();
            }
        });
        final TextView loadVideo = (TextView)findViewById(R.id.textView3);
        loadVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Local mp4
                //loadVideo(R.raw.mayday);
                //Url mp4
                loadVideo("http://www.html5videoplayer.net/videos/toystory.mp4");
            }
        });
    }

    public void initSoundPool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = new SoundPool.Builder().build();
        }else{
            int maxStreams = 1;
            int streamType = AudioManager.STREAM_MUSIC;
            int srcQuality = 0;
            soundPool = new SoundPool(maxStreams, streamType, srcQuality);
        }
        int priority = 1;
        //預載入的音效
        sound_hahaha = soundPool.load(this, R.raw.hahaha,priority);
    }

    public int playSound(int soundId){
        if (soundPool != null){
            int leftVolume = 1;
            int rightVolume = 1;
            int priority = 0;
            int loop = 0; //-1 循環
            float rate = (float) 1; //0.5, 2 倍速
            return soundPool.play(soundId,leftVolume,rightVolume,priority,loop,rate);
        }
        return -1;
    }

    public void stopSound(int soundStreamID){
        if (soundPool != null){
            soundPool.stop(soundStreamID);
        }
    }

    public void playMp3(int sound_source){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this, sound_source);
        }
        mediaPlayer.start();
    }

    public void stopMp3(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playUrl(String sound_source){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(sound_source);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    public void stopUrl(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void loadVideo(int path){
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+path));
        videoView.start();
    }

    public void loadVideo(String path){
        VideoView videoView = (VideoView)findViewById(R.id.videoView);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
    }
}
