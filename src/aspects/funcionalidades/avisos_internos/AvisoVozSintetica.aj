package  aspects.funcionalidades.avisos_internos;


import core.funcionalidades.Calendario;
import core.userinterface.ServiceAssistant;

public aspect AvisoVozSintetica {
	before() : execution(* *.main(..)) {
		ServiceAssistant.setVozSintetizadaAviso();
		
		Calendario.setMemoVoz();
	}}