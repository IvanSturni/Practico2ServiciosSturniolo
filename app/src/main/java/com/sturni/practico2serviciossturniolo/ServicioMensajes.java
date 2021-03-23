package com.sturni.practico2serviciossturniolo;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;


public class ServicioMensajes extends Service {
    private ContentResolver cr;
    private Uri todosLosMensajes;

    public ServicioMensajes() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        todosLosMensajes = Uri.parse("content://sms");
        cr = getContentResolver();

        Log.d("Mensaje","Servicio Iniciado");

        // Loop Principal
        while (true) {
            mostrarMensajes();
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return super.onStartCommand(intent, flags, startId);
            }
        }
    }

    private void mostrarMensajes() {
        Cursor cursor = cr.query(todosLosMensajes,null,null,null,null);

        /* Columnas del content provider
        0 _id
        1 thread_id
        2 address
        3 person
        4 date
        5 date_sent
        6 protocol
        7 read
        8 status
        9 type
        10 reply_path_present
        11 subject
        12 body
        13 service_center
        14 locked
        15 sub_id
        16 error_code
        17 creator
        18 seen
        */

        // Si no hay mensajes, lo muestra
        if(cursor.getCount() == 0) Log.d("Mensaje", "No hay Mensajes");

        // Mientras haya mostrado menos de 5 mensajes y existan mensajes, muestra la información de cada mensaje
        while (cursor.moveToNext() && cursor.getPosition() < 5) {
            Log.d("Mensaje", " "+
                    "\n         Remitente: " + cursor.getString(2) +
                    "\nFecha de recepción: " + cursor.getString(4) +
                    "\n           Mensaje: " + cursor.getString(12));
        }

        cursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Mensaje", "Servicio Destruido");
    }
}