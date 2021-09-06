package com.example.trabajopractico2servicio;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;

public class ServicioMensajes extends Service {

    public ServicioMensajes() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread tarea = new Thread(new Runnable() {

            @SuppressLint("Range")
            @Override
            public void run() {

                Uri mensajes = Uri.parse("content://sms/inbox");
                ContentResolver cr = getContentResolver();

                while (true) {
                    Cursor cursor = cr.query(mensajes, null, null, null, null);
                    if (cursor != null && cursor.getCount() > 0) {

                        while (cursor.moveToNext() && cursor.getPosition() < 5) {
                            Log.d("Mensaje", " " +
                                    "\n         Remitente: " + cursor.getString(cursor.getColumnIndex((Telephony.Sms.ADDRESS))) +
                                    "\n             Texto: " + cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY)));
                            }

                        try {
                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            Log.d("mensaje", e.getMessage());
                            break;
                        }
                    }
                cursor.close();
                }
            }
        });
        tarea.start();
        return START_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


