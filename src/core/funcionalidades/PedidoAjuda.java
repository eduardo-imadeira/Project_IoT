package  core.funcionalidades;

import core.funcionalidades.avisos_internos.MsgInterna;
import core.funcionalidades.mensagens.ServicoMensagem;
import  core.i18n.I18N;
import  core.i18n.Messages;

public class PedidoAjuda {
	
	
	private static PedidoAjuda INSTANCE = null;
	
	protected PedidoAjuda() {
	}
	
	
	public static PedidoAjuda getInstance() {
		return INSTANCE;
	}
	
	public static void setInstance() {
		INSTANCE = new PedidoAjuda();
	}
	
	public void sendAlert(String currentUpdateHour, boolean vozSintetizada) {
		
		
		MsgInterna notificacao = new MsgInterna(I18N.getString(Messages.SOS_NOTIFICATION));
		if(vozSintetizada)notificacao.setVozSintetizadaAviso();
		notificacao.sendWarning();
		
		
		ServicoMensagem.sendMessage(I18N.getString(Messages.SOS_SUBMITTED) +" "+ currentUpdateHour);


	}
	
	

}

