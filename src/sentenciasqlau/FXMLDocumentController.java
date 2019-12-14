/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sentenciasqlau;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author SAINZ
 */

public class FXMLDocumentController implements Initializable {
    String[] reservada={"DROP","TEXT","CONCAT","DECLARE","CREATE","TRIGGER","INSERT","UPDATE","DELETE","ON","FOR","EACH","ROW","BEFORE","AFTER","INTO","FROM","SET","WHERE","NEW","BEGIN","END","VALUES"};
    char[] simbolo={',','.','(',')','{','}',';'};
    String busqueda;
    @FXML
    private TextArea textSentencia,tablaReservada,identificadores,simbolos,operadores,errores,asignacion;
    @FXML
    private Button Ejecutar;
    @FXML
    private TableView tabla;
    /*@FXML
    private TableColumn tablaReservada;
    ObservableList<String> palabraReservada = FXCollections.observableArrayList();*/
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void btnEjecutar(){
        String cadena=textSentencia.getText();
        //cadena.replaceAll(" ","");
        //Quitamos espacios en blanco, saltos de linea y tabulaciones
        cadena=cadena.replaceAll("\\s","");
        System.out.println("Cadena: " +cadena);
        buscarReservadas(cadena);
        //buscarIdentificador();
    }
    private void buscarReservadas(String cadena){
        int contadorReservadas=0,contadorIdent=0,contadorSim=0,contadorOperador=0;
        String palabrasR="",ident="",identificador="";
        busqueda=cadena;
        //System.out.println(""+reservada.length);
        for(int i=0;i<reservada.length;i++){
            Pattern patron=Pattern.compile(reservada[i]);
            Matcher encaja=patron.matcher(busqueda);
            if(encaja.find()){
                contadorReservadas++;
                palabrasR=palabrasR+"\n"+encaja.group();
                //System.out.println(encaja.group());
                //tablaReservada.setText(encaja.group());
                //palabraReservada.add(encaja.group());
                String resultado=encaja.replaceAll("@");
                busqueda=resultado;
                //tablaReservada.setText(encaja.group());
                //tabla.setItems(palabraReservada); 
            }
            tablaReservada.setText(palabrasR);
        }
        //System.out.println("Palabras reservadas encontradas: " + palabrasR+"\n"+"Cantidad de palabras:"+contadorReservadas);
        System.out.println("Nueva cadena:"+busqueda);
        String[] expresion={"^[a-zA-Z]+[0-9]+[a-z]+[^$%&]","^[a-zA-Z]+[0-9]+[^$%&]","^[a-zA-Z]+[^$%&]"};
        String error="",simbolo="",operador="";
        for(int i=0;i<busqueda.length();i++){
            if(busqueda.charAt(i)!='@'&& busqueda.charAt(i)!='(' && busqueda.charAt(i)!=')' && busqueda.charAt(i)!='=' && busqueda.charAt(i)!=';' && busqueda.charAt(i)!=','){
                ident=ident+busqueda.charAt(i);
            }else{
                //System.out.println("Simbolo:"+busqueda.charAt(i));
                //System.out.println(""+ident);
                if(/*busqueda.charAt(i)=='=' ||*/busqueda.charAt(i)=='<' || busqueda.charAt(i)=='>'|| busqueda.charAt(i)=='+'|| busqueda.charAt(i)=='-'|| busqueda.charAt(i)=='*'|| busqueda.charAt(i)=='/'){
                    contadorOperador++;
                    System.out.println(""+busqueda.charAt(i));
                    operador=operador+"\n"+busqueda.charAt(i);
                    //operadores.setText(operador);
                }
                if(busqueda.charAt(i)=='='){
                    asignacion.setText(String.valueOf(busqueda.charAt(i)));
                }
                if(ident.equals("==")|| ident.equals("=>") ||ident.equals("<=")||ident.equals("!=")){
                    contadorOperador++;
                    operador=operador+"\n"+ident;
                }
                if(busqueda.charAt(i)==',' || busqueda.charAt(i)=='.'||busqueda.charAt(i)==';'||busqueda.charAt(i)==','||busqueda.charAt(i)=='('||busqueda.charAt(i)==')'){
                    contadorSim++;
                    System.out.println(""+busqueda.charAt(i));
                    simbolo=simbolo+"\n"+busqueda.charAt(i);
                }
                /*if(busqueda.charAt(i)=='$'||busqueda.charAt(i)=='%'||busqueda.charAt(i)=='&'||busqueda.charAt(i)=='°'||busqueda.charAt(i)=='?'||busqueda.charAt(i)=='¿'||busqueda.charAt(i)=='!'||busqueda.charAt(i)=='-'){
                    System.out.println("Error"+busqueda.charAt(i));
                    }*/
                for(int k=0;k<ident.length();k++){
                        if(ident.charAt(k)=='$'||ident.charAt(k)=='%'||ident.charAt(k)=='&'||ident.charAt(k)=='°'||ident.charAt(k)=='?'||ident.charAt(k)=='¿'||ident.charAt(k)=='!'||ident.charAt(k)=='-'){
                            System.out.println("Error "+ident);
                            error=error+"\n"+ident;
                            ident="";
                        }
                    }
                for(int j=0;j<expresion.length;j++){
                    Pattern patron=Pattern.compile(expresion[j]);
                    Matcher identificador1=patron.matcher(ident);
                    if(identificador1.find()){
                        contadorIdent++;
                        //identificador=identificador+" "+ ident;
                        System.out.println("Identificador:"+ident);
                        identificador=identificador+"\n"+ident;
                        ident="";
                    }/*else{
                        System.out.println("Error"+ident);
                    }*/
                }
                operadores.setText(operador);
                simbolos.setText(simbolo);
                operadores.setText(operador);
                identificadores.setText(identificador);
                errores.setText(error);
                ident="";
            }
        }
        System.out.println(""+contadorIdent+" "+identificador);
    }
    /*private void buscarIdentificador(){
        String cadenaIdent=busqueda;
        String identificador="[^A-Za-z][^0-9_][^A-Za-z0-9]";
        int contadorIdent=0;
        String ident="";
        /*for(int i=0;i<cadenaIdent.length();i++){
            if(cadenaIdent.charAt(i)!='@'){
                cadenaIdent=String.valueOf(cadenaIdent.charAt(i));
            }
        }*/
        /*for(int i=0;i<cadenaIdent.length();i++){
            /*Pattern expresion=Pattern.compile("^[A-Za-z0-9_A-Za-z0-9]");
            Matcher encaja=expresion.matcher(cadenaIdent);
            if(encaja.find()){
                ident=ident+" " + cadenaIdent;
            }
            contadorIdent++;*/
            /*if(cadenaIdent.matches("^[A-Za-z][0-9][_][A-Z][a-z]")){
                contadorIdent++;
            }
        }
        System.out.println("Identificadores:\nCantidad de identificadores:"+contadorIdent);
    }*/
    
}
