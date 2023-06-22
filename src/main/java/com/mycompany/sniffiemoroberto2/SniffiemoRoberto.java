package com.mycompany.sniffiemoroberto2;

import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author Joaquin
 */
public class SniffiemoRoberto {

    public static void main(String[] args) {
        //listarPuertosSeriales();
        leerPuertoSerial("COM3");

    }

    public static void leerPuertoSerial(String puerto) {
        //SerialPort comPort = SerialPort.getCommPorts()[0];
        SerialPort comPort = SerialPort.getCommPort(puerto);
        comPort.setComPortParameters(57600, 8, 1, 0);
        comPort.openPort();

        //comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        //comPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);

        while (true) {
            // Obtener el dato
            byte[] readBuffer = new byte[comPort.bytesAvailable()];
            int numRead = comPort.readBytes(readBuffer, readBuffer.length);
            String aux = bytesToHex(readBuffer);

            // Si el dato no esta vacio lo imprimo
            if (!aux.isBlank()) {
                System.out.println(aux);
            }

            // Agrego demora para la siguiente lectura si es que lo deseo
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SniffiemoRoberto2.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    }

    public static void listarPuertosSeriales() {
        System.out.println("Puertos Seriales:");

        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName());
        }
    }

    //******************************************************************
    //METODOS AUXILIARES
    //******************************************************************
    //
    //CONVERSOR DE BYTE A STRING HEX
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
