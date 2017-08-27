package com.ima.bhiwandi.ima;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private TextView userName;
    private Button sendNotif, signout;
    private Context mContext;
//    private ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Define Toolbar */

        //firebase authentication
        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Toast.makeText(getApplicationContext(), "onAuthStateChange:SIGNED-OUT", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } else {
                    //user signed out
                    userName = (TextView) findViewById(R.id.signedAs);
                    userName.setText("Signed in as : " + FirebaseInstanceId.getInstance().getToken());
/*                    EditText editToken = (EditText) findViewById(R.id.editToken);
                    editToken.setText(FirebaseInstanceId.getInstance().getToken());*/
                    Toast.makeText(getApplicationContext(), "onAuthStateChange:SIGNED-IN", Toast.LENGTH_LONG).show();
                }
            }
        };

        sendNotif = (Button) findViewById(R.id.bn_notif);

        sendNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_LONG).show();

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                        .setSmallIcon(R.drawable.ima)
                        .setContentTitle("FCM Message")
                        .setContentText("New Notif")
                        //.setContentText(remoteMessage.getNotification().getBody()
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);
                //.setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//                notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
            }
        });

/*
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
*/
    }
/*
    //sign out method
    public void signOut() {
        auth.signOut();
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
