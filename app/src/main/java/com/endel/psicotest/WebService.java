package com.endel.psicotest;


import java.util.ArrayList;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

import com.endel.psicotest.vista.LayoutBasico;
import com.endel.psicotest.vo.DocumentsIDs;
import com.endel.psicotest.vo.RespuestasUsuarioNM_VO;

public class WebService {

    HttpsTransportSE androidHttpTransport;

    private String NAMESPACE;
    private String URL;
    Context contx;
    public static String ip = "156.35.71.167:8080";
   //192.168.0.100
    //public static String ip = "192.168.0.100:8080";
   //public static String ip = "psicotest.endel.es";

    public WebService(String ip, Context ctx) {
        contx = ctx;
        NAMESPACE = "http://" + ip + "/Test2/";
        URL = "http://" + ip + "/Test2/Service1.asmx";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    public String ObtenerExperimento(String Id) {
        String SOAP_ACTION = "http://192.168.0.1/Android2/ObtenerAgendaIdentificador";
        String METHOD_NAME = "ObtenerExperimentoIdentificador";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("id", Id);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            int Timeout = 60000;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, Timeout);
            androidHttpTransport.call(SOAP_ACTION, envelope);

            Object result = (Object) envelope.getResponse();

            return result.toString();
        } catch (Exception e) {
           return "ERROR";        }
    }



    public boolean EstaDisponible(String ip) {
        String SOAP_ACTION = "http://" + ip + "/Test2/InsertarResultado2";
        String METHOD_NAME = "InsertarResultado2";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            int Timeout = 60000;

            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, Timeout);

            androidHttpTransport.call(SOAP_ACTION, envelope);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }



    public boolean isNetworkAvailable() {
        Context context = contx;
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static boolean enviarTests () {
        String NAMESPACE ="http://"+ip+"/Test2/";
        String URL = "http://"+ip+"/Test2/service1.asmx";

        String SOAP_ACTION1 = "http://" + ip + "/Test2/InsertarResultado2";
        String METHOD_NAME1 = "InsertarResultado2";

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME1);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.dotNet = true;
            //envelope.setOutputSoapObject(request);

            int Timeout = 60000;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL, Timeout);
            //androidHttpTransport.call(SOAP_ACTION1, envelope);
            //SoapObject  result = (SoapObject) envelope.bodyIn;


//            DocumentsIDs documentIdVector = new DocumentsIDs();
            RespuestasUsuarioNM_VO respuestasUsuarioNM_vo;

            for (int i = 0; i < Logica.listaRespuestas.size(); i++) {
                request = new SoapObject(NAMESPACE, METHOD_NAME1);
                respuestasUsuarioNM_vo = Logica.listaRespuestas.get(i);

                request.addProperty("IdRespuesta", respuestasUsuarioNM_vo.getIdRespuesta());
                request.addProperty("IdUsuario", respuestasUsuarioNM_vo.getIdUsuario());
                request.addProperty("valor", respuestasUsuarioNM_vo.getValor());

                //documentIdVector.add(respuestasUsuarioNM_vo.getIdUsuario(), respuestasUsuarioNM_vo.getIdRespuesta(), respuestasUsuarioNM_vo.getValor());
                //PropertyInfo documentIdsPropertyInfo;
                //documentIdsPropertyInfo = new PropertyInfo();
                //documentIdsPropertyInfo.setName("resultados"
                //documentIdsPropertyInfo.setValue(documentIdVector);
                //documentIdsPropertyInfo.setType(documentIdVector.getClass());

                //request.addProperty(documentIdsPropertyInfo);

                envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                 Timeout = 60000;
                androidHttpTransport = new HttpTransportSE(URL, Timeout);
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //result = (SoapObject) envelope.getResponse();
                //result = (SoapObject) envelope.bodyIn;
                //Vector response = (Vector) envelope.getResponse();

            }
            //request = new SoapObject(NAMESPACE, METHOD_NAME1);
            //envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //envelope.dotNet = true;
            //envelope.setOutputSoapObject(request);

            //androidHttpTransport = new HttpTransportSE(URL, Timeout);
            //androidHttpTransport.call(SOAP_ACTION1, envelope);
            //result = (SoapObject) envelope.bodyIn;

            return true;
        } catch (Exception e) {
            //VariablesGlobales.PublicToast(LayoutBasico.activity, "incorrecto");
            e.printStackTrace();

            return false;
        }
    }




}

