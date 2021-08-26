package com.example.trabajopractico2servicio;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ServicioMensajes extends Service {
    private ContentResolver cr;
    private Uri mensajes;

    public ServicioMensajes() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mensajes = Uri.parse("content://sms");
        cr = getContentResolver();

        //Log.d("Mensaje", "Servicio Iniciado");

        while (true){
            mostrarMensajes();
            try{
                Thread.sleep(9000);
            }catch (InterruptedException e){
                e.printStackTrace();
                return super.onStartCommand(intent, flags, startId);
            }
        }
    }
    private void mostrarMensajes(){
        Cursor cursor = cr.query(mensajes, null, null, null, null);

        if (cursor.getCount() == 0) Log.d("Mensaje", "No hay mensajes");

        while (cursor.moveToNext() && cursor.getPosition() < 5) {
            Log.d("Mensaje", " "+
                    "\n         Remitente: " + cursor.getString(2) +
                    "\nFecha de recepción: " + cursor.getString(4) +
                    "\n             Texto: " + cursor.getString(12));
        }

        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Mensaje", "Servicio terminado");
    }
}
